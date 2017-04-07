package com.maneater.util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;



import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

/***bitmap处理工具,未优化处理*/
public class BitmapUtil {

	private static final String TAG = "picscreator包下BitmapUtil";

	/**静态方法,获取位图字节大小*/
	public static int getByteCount(Bitmap bitmap) {
		L.i(TAG, "getByteCount中bitmap.getRowBytes() * bitmap.getHeight()获取传入位图的字节大小:" + (bitmap.getRowBytes() * bitmap.getHeight()));
		return bitmap.getRowBytes() * bitmap.getHeight();
	}

	/**静态方法,读位图,根据传入的uri*/
	public static Bitmap readBitmap(Context context, Uri selectedImage, int width, int higth) {
		L.i(TAG, "readBitmap中传入的参数,uri:" +selectedImage+",width:"+width+",higth:"+higth);
		Bitmap bm = null;
		try {
			BitmapFactory.Options factory = new BitmapFactory.Options();
			factory.inJustDecodeBounds = true;
			L.i(TAG, "创建位图之前injustDecodeBounds为true,先获取位图宽高属性:"  );
			AssetFileDescriptor fileDescriptor = null;//读取raw文件夹下资源
			//媒体流
			fileDescriptor = context.getContentResolver().openAssetFileDescriptor(selectedImage,"r");
			
			bm = BitmapFactory.decodeFileDescriptor(fileDescriptor.getFileDescriptor(), null,
					factory);
			
			int hRatio = (int) Math.ceil(factory.outHeight / (float) higth);
			int wRatio = (int) Math.ceil(factory.outWidth / (float) width);
			
			if (hRatio > 1 || wRatio > 1) {
                 L.i(TAG, "hRatio>1或wRatio>1需要动态设置压缩比"  );
				if (hRatio > wRatio) {
					//判断横向还是竖向比例
					factory.inSampleSize = hRatio;
				} else
					factory.inSampleSize = wRatio;
			}
			factory.inJustDecodeBounds = false;
			L.i(TAG, "设置好宽高之后,创建位图injustDecodeBounds为false"  );
			bm = BitmapFactory.decodeFileDescriptor(fileDescriptor.getFileDescriptor(), null,
					factory);
			
			fileDescriptor.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bm;
	}

	/**
	 * 生成封面 ,宽高都为120
	 * 
	 * @param context
	 * @param selectedImage
	 * @return
	 */
	public static Bitmap getSmallImage(Context context, Uri selectedImage) {
		return readBitmap(context, selectedImage, 120, 120);
	}

	/**
	 * 生成大图 1080,1080
	 * @param context
	 * @param selectedImage
	 * @return
	 */
	public static Bitmap getLargeImage(Context context, Uri selectedImage) {
		return readBitmap(context, selectedImage, 1080, 1080);
	}

	/**
	 * 保存位图
	 * @param 位图
	 * @param 压缩率
	 * @param 保存目录
	 * @return
	 */
	public static File saveImage(Bitmap bitmap, int quality, File dir) {
		OutputStream out = null;
		try {
			File newFile = new File(dir, System.currentTimeMillis() + ".dat");
			out = new FileOutputStream(newFile);
			if (bitmap.compress(Bitmap.CompressFormat.JPEG, quality, out)) {
				return newFile;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	/*****从本地加载,位图  返回位图*/
	public static Bitmap loadFromAsset(Context context, String fileName) {
		Bitmap image = null;
		AssetManager am = context.getResources().getAssets();
		InputStream is = null;
		try {
			is = am.open(fileName);
			image = BitmapFactory.decodeStream(is);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return image;
	}

	/******从文件加载位图,指定宽高*/
	public static Bitmap loadFromFile(Context context, String fileName, int w, int h) {
		return readBitmap(context, Uri.fromFile(new File(fileName)), w, h);
	}

	/********从文件加载位图,不指定宽高*/
	public static Bitmap loadFromFile(Context context, String fileName) {

		Bitmap image = null;
		InputStream is = null;
		try {
			is = new FileInputStream(new File(fileName));
			image = BitmapFactory.decodeStream(is);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return image;
	}
}
