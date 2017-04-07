package com.maneater.util;

import android.util.Log;

public class LogUtils {
	private static final boolean LOG_SWITCH = false;//����

	public static void logMethod(Object msg) {
		if (LOG_SWITCH) {
			StackTraceElement stackTrace = new Throwable().getStackTrace()[1];
			d("method", stackTrace.getFileName() + ":" + stackTrace.getMethodName() + ":" + msg);
		}
	}

	public static void v(String tag, String msg) {
		if (LOG_SWITCH)
			Log.v(tag, msg);
	}

	public static void i(String tag, String msg) {
		if (LOG_SWITCH)
			Log.i(tag, msg);
	}

	public static void d(String tag, String msg) {
		if (LOG_SWITCH)
			Log.d(tag, msg);
	}

	public static void w(String tag, String msg) {
		if (LOG_SWITCH)
			Log.w(tag, msg);
	}

	public static void e(String tag, Object obj) {
		if (LOG_SWITCH) {
			Log.e(tag, obj + "");
		}
	}

}
