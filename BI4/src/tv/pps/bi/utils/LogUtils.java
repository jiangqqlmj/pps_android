package tv.pps.bi.utils;


import android.util.Log;

public class LogUtils {



	  private static boolean showLog = false;

	  public static boolean isShowLog() {
		return showLog;
	}

	public static void setShowLog(boolean showLog) {
		LogUtils.showLog = showLog;
	}

	public static void d(String paramString1, String paramString2)
	  {
	    if (showLog)
	      Log.d(paramString1, paramString2);
	  }

	  public static void e(String paramString1, String paramString2)
	  {
	    if (showLog)
	      Log.e(paramString1, paramString2);
	  }

	  public static void e(String paramString1, String paramString2, Throwable paramThrowable)
	  {
	    if (showLog)
	      Log.e(paramString1, paramString2, paramThrowable);
	  }

	  public static void enableLogging(boolean paramBoolean)
	  {
	    showLog = paramBoolean;
	  }

	  public static void i(String paramString1, String paramString2)
	  {
	    if (showLog)
	      Log.i(paramString1, paramString2);
	  }

	  public static void v(String paramString1, String paramString2)
	  {
	    if (showLog)
	      Log.v(paramString1, paramString2);
	  }

	  public static void w(String paramString1, String paramString2)
	  {
	    if (showLog)
	      Log.w(paramString1, paramString2);
	  }

	  public static void w(String paramString1, String paramString2, Throwable paramThrowable)
	  {
	    if (showLog)
	      Log.w(paramString1, paramString2, paramThrowable);
	  }
	}
	

