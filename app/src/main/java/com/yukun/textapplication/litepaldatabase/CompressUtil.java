package com.yukun.textapplication.litepaldatabase;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;


import com.yukun.textapplication.press.MediaShrinkQueue;

import org.jdeferred.DoneCallback;
import org.jdeferred.FailCallback;
import org.jdeferred.ProgressCallback;
import org.jdeferred.Promise;

import java.io.File;


/**
 * 压缩视频的工具类
 */
public class CompressUtil {
    private static final int MAX_WIDTH = 384;
    private static final int VIDEO_BITRATE = 1024 * 1024;
    private static final int AUDIO_BITRATE = 128 * 1024;
    private static final long DURATION_LIMIT = Integer.MAX_VALUE;
    private static final String EXPORT_FILE = "tempCompressed";

    private static CompressUtil mCompressUtil;

    private boolean isCompressing = false;

    public boolean isCompressing() {
        return isCompressing;
    }

    public static CompressUtil getInstance() {
        if (mCompressUtil == null) {
            mCompressUtil = new CompressUtil();
        }
        return mCompressUtil;
    }

    public void shrinkVideo(Context context,String selectedVideoPath) {
        //标志任务开始
        isCompressing = true;

        Log.i("压缩的视频路径:%s", selectedVideoPath);
        MediaShrinkQueue mediaShrinkQueue = new MediaShrinkQueue(context, new Handler(), MAX_WIDTH,
                VIDEO_BITRATE, AUDIO_BITRATE, DURATION_LIMIT);
        final File output = new File(Environment.getExternalStorageDirectory().getAbsolutePath()/*context.getExternalFilesDir(Environment.DIRECTORY_MOVIES)*/,System.currentTimeMillis() + ".mp4");
//        Log.i("---fileOut",output.toString());

        final Promise<Void, Exception, Integer> promise = mediaShrinkQueue
                .queue(Uri.fromFile(new File(selectedVideoPath)), output);
        //压缩开始
        if (onCompressStateChangedListener != null) {
            onCompressStateChangedListener.onCompressStart(selectedVideoPath);
        }
        promise.then(new DoneCallback<Void>() {
            @Override
            public void onDone(Void result) {
                Log.i("---over",output.getPath());
                //标记任务结束
                isCompressing = false;
                if (onCompressStateChangedListener != null) {
                    onCompressStateChangedListener.onCompressSuccess();
                    //尝试上传
//                    UploadUtil.getInstance().prepareToUpload(output.getPath());
                }
            }
        }).fail(new FailCallback<Exception>() {
            @Override
            public void onFail(Exception result) {
                isCompressing = false;
                Log.i("---fail",result.toString());

                boolean delete = output.delete();
                if (delete) {
                    Log.d("---","Temp Compressed File Deleted Success");
                } else {
                    Log.e("-----","Temp Compressed File Deleted Fail");
                }

                if (onCompressStateChangedListener != null) {
                    String errorMessage = result.getMessage();
                    onCompressStateChangedListener.onCompressFailed(errorMessage);
                }

            }
        }).progress(new ProgressCallback<Integer>() {
            @Override
            public void onProgress(Integer progress) {
                Log.i("---progress",String.valueOf(progress));
                Log.d("progress:", String.valueOf(progress));

                if (onCompressStateChangedListener != null) {
                    onCompressStateChangedListener.onCompressExecuting(progress);
                }
            }
        });

    }

    OnCompressStateChangedListener onCompressStateChangedListener;

    public void setOnCompressStateChangedListener(OnCompressStateChangedListener onCompressStateChangedListener) {
        this.onCompressStateChangedListener = onCompressStateChangedListener;
    }

    public interface OnCompressStateChangedListener {
        void onCompressStart(String selectedVideoPath);

        void onCompressExecuting(int progress);

        void onCompressSuccess();

        void onCompressFailed(String errMsg);
    }


}
