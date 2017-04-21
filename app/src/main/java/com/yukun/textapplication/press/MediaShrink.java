package com.yukun.textapplication.press;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.media.MediaMetadataRetriever;
import android.media.MediaMuxer;
import android.net.Uri;
import android.os.Build;
import android.os.Looper;
import android.util.Log;

import java.io.IOException;

/**
 * WARN: {@link MediaShrink#shrink(Uri)} のスレッドについて制約
 * 
 * {@link MediaShrink} の呼び出し元のスレッドは {@link Looper} を持たない {@link Thread}
 * である必要がある。 <br/>
 * それに加え {@link Looper#getMainLooper()} の {@link Looper} が周り続けている必要がある。<br/>
 * これらの制約を守らないと圧縮がロックされたままになる。
 * 
 * 原因: ビデオの圧縮で利用している {@link SurfaceTexture} のコールバックが呼び出されるスレッドが以下のようになっているので、
 * 上記の制約を守らないとコールバックが呼び出されない。
 * <ul>
 * <li>スレッドが {@link Looper}を保つ場合、そのスレッド</li>
 * <li>持たない場合、 メインスレッド</li>
 * </ul>
 */
class MediaShrink {

	private static final String LOG_TAG = MediaShrink.class.getSimpleName();

	private static final int PROGRESS_ADD_TRACK = 10;
	private static final int PROGRESS_WRITE_CONTENT = 40;

	private final Context context;

	private int width = -1;
	private long durationLimit = -1;
	private int audioBitRate;
	private int videoBitRate;
	private String output;
	private OnProgressListener onProgressListener;

	static boolean isSupportedDevice(final Context context) {
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
			return false;
		}

		if (!OpenglUtils.supportsOpenglEs2(context)) {
			return false;
		}

		return true;
	}

	public MediaShrink(Context context) {
		this.context = context;
	}

	public void shrink(final Uri src,
			final UnrecoverableErrorCallback errorCallback) throws IOException,
			TooMovieLongException {
		MediaExtractor extractor = null;
		MediaMetadataRetriever metadataRetriever = null;
		MediaMuxer muxer = null;
		VideoShrink videoShrink = null;
		AudioShrink audioShrink = null;

		try {
			extractor = new MediaExtractor();
			metadataRetriever = new MediaMetadataRetriever();

			try {
				extractor.setDataSource(context, src, null);
				metadataRetriever.setDataSource(context, src);
			} catch (IOException e) {
				Log.e(LOG_TAG, "fail to read input.", e);
				throw new IOException("fail to read input.", e);
			}

			checkLength(metadataRetriever);

			int maxProgress = 0;
			int progress = 0;

			// プログレスの計算のため、圧縮するトラックの数を前もって数える
			Integer videoTrack = null;
			Integer audioTrack = null;
			for (int i = 0, length = extractor.getTrackCount(); i < length; i++) {
				final MediaFormat format = extractor.getTrackFormat(i);
				Log.d(LOG_TAG,
						"track [" + i + "] format: " + Utils.toString(format));
				if (isVideoFormat(format)) {
					if (videoTrack != null) {
						// MediaMuxer がビデオ、オーディオそれぞれ1つずつしか含めることができないため。
						Log.w(LOG_TAG,
								"drop track. support one video track only. track:"
										+ i);
						continue;
					}
					videoTrack = i;
					maxProgress += PROGRESS_ADD_TRACK + PROGRESS_WRITE_CONTENT;
				} else if (isAudioFormat(format)) {
					if (audioTrack != null) {
						// MediaMuxer がビデオ、オーディオそれぞれ1つずつしか含めることができないため。
						Log.w(LOG_TAG,
								"drop track. support one audio track only. track:"
										+ i);
						continue;
					}
					audioTrack = i;
					maxProgress += PROGRESS_ADD_TRACK + PROGRESS_WRITE_CONTENT;
				} else {
					Log.e(LOG_TAG, "drop track. unsupported format. track:" + i
							+ ", format:" + Utils.toString(format));
				}
			}

			try {
				muxer = new MediaMuxer(output,
						MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4);
				// トラックの作成。
				Integer newVideoTrack = null;
				if (videoTrack != null) {
					videoShrink = new VideoShrink(extractor, metadataRetriever,
							muxer, errorCallback);
					videoShrink.setWidth(width);
					videoShrink.setBitRate(videoBitRate);
					newVideoTrack = muxer.addTrack(videoShrink
							.createOutputFormat(videoTrack));

					progress += PROGRESS_ADD_TRACK;
					deliverProgress(progress, maxProgress);
				}
				Integer newAudioTrack = null;
				if (audioTrack != null) {
					audioShrink = new AudioShrink(extractor, muxer,
							errorCallback);
					audioShrink.setBitRate(audioBitRate);
					newAudioTrack = muxer.addTrack(audioShrink
							.createOutputFormat(audioTrack));

					progress += PROGRESS_ADD_TRACK;
					deliverProgress(progress, maxProgress);
				}

				muxer.start();

				// コンテンツの作成
				if (videoShrink != null) {
					// ビデオ圧縮の進捗を詳細に取れるようにする
					final int currentProgress = progress;
					final int currentMaxProgress = maxProgress;
					videoShrink.setOnProgressListener(new OnProgressListener() {
						@Override
						public void onProgress(int progress) {
							deliverProgress(currentProgress + progress
									* PROGRESS_WRITE_CONTENT / 100,
									currentMaxProgress);
						}
					});
					videoShrink.shrink(videoTrack, newVideoTrack);

					progress += PROGRESS_WRITE_CONTENT;
					deliverProgress(progress, maxProgress);
				}
				if (audioShrink != null) {
					// オーディオ圧縮の進捗を詳細に取れるようにする
					final int currentProgress = progress;
					final int currentMaxProgress = maxProgress;
					audioShrink.setOnProgressListener(new OnProgressListener() {
						@Override
						public void onProgress(int progress) {
							deliverProgress(currentProgress + progress
									* PROGRESS_WRITE_CONTENT / 100,
									currentMaxProgress);
						}
					});
					audioShrink.shrink(audioTrack, newAudioTrack);

					progress += PROGRESS_WRITE_CONTENT;
					deliverProgress(progress, maxProgress);
				}
			} catch (IOException e) {
				Log.e(LOG_TAG, "fail to write output.", e);
				throw new IOException("fail to write output.", e);
			} catch (Throwable e) {
				// muxer はきちんと書き込みせずに閉じると RuntimeException を発行するので
				// muxer を開いたあとは全ての例外が unrecoverable。
				Log.e(LOG_TAG, "unrecoverable error occured on media shrink.",
						e);
				errorCallback.onUnrecoverableError(e);
			} finally {
				if (muxer != null) {
					muxer.stop();
					muxer.release();
				}
			}
		} catch (IOException | TooMovieLongException e) {
			// recoverable error
			throw e;
		} catch (Throwable e) {
			Log.e(LOG_TAG, "unrecoverable error occured on media shrink.", e);
			errorCallback.onUnrecoverableError(e);
		} finally {
			try {
				if (extractor != null) {
					extractor.release();
				}

				if (metadataRetriever != null) {
					metadataRetriever.release();
				}
			} catch (RuntimeException e) {
				Log.e(LOG_TAG, "fail to finalize shrink.", e);
				errorCallback.onUnrecoverableError(e);
			}
		}
	}

	private void deliverProgress(int progress, int maxProgress) {
		if (onProgressListener != null) {
			onProgressListener.onProgress(progress * 100 / maxProgress);
		}
	}

	private void checkLength(final MediaMetadataRetriever metadataRetriever)
			throws TooMovieLongException {
		if (durationLimit <= 0) {
			return;
		}

		final long durationSec = Long.valueOf(metadataRetriever
				.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)) / 1000;
		if (durationSec > durationLimit) {
			Log.e(LOG_TAG, "movie duration (" + durationSec
					+ " sec)is longer than duration limit(" + durationLimit
					+ " sec). ");
			throw new TooMovieLongException("movie duration (" + durationSec
					+ " sec)is longer than duration limit(" + durationLimit
					+ " sec). ");
		}
	}

	private boolean isVideoFormat(MediaFormat format) {
		return format.getString(MediaFormat.KEY_MIME).startsWith("video/");
	}

	private boolean isAudioFormat(MediaFormat format) {
		return format.getString(MediaFormat.KEY_MIME).startsWith("audio/");
	}

	/**
	 * 出力先の指定。(設定必須)
	 */
	public void setOutput(String output) {
		this.output = output;
	}

	/**
	 * 設定必須
	 */
	public void setVideoBitRate(int videoBitRate) {
		this.videoBitRate = videoBitRate;
	}

	/**
	 * 設定必須
	 */
	public void setAudioBitRate(int audioBitRate) {
		this.audioBitRate = audioBitRate;
	}

	/**
	 * 指定必須。
	 * 
	 * Warning: Nexus 7 では決まった幅(640, 384など)でないとエンコード結果がおかしくなる。
	 * セットした値で正しくエンコードできるかテストすること。
	 */
	public void setWidth(int ｗidth) {
		this.width = ｗidth;
	}

	public void setOnProgressListener(OnProgressListener listener) {
		this.onProgressListener = listener;
	}

	/**
	 * ビデオの最大の長さ。 この長さを越えるビデオのエンコードは行わない。
	 * 
	 * @param durationLimit
	 *            0以下の時、無視される。
	 */
	public void setDurationLimit(long durationLimit) {
		this.durationLimit = durationLimit;
	}
}
