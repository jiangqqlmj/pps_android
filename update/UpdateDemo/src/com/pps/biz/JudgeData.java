package com.pps.biz;

import android.os.AsyncTask;

/**
 * 进行获取软件的版本信息->进行相应的升级
 * @author jiangqingqing
 * @time 2013/08/06 11:41
 */
public class JudgeData {
    
	public JudgeData()
	{
		
		new JudgeInformation().execute();
		
	}
	
	
	
	/**
	 * 后台异步任务类，去服务器中获取软件的版本信息与升级信息
	 * @author jiangqingqing
	 *
	 */
	private class JudgeInformation extends AsyncTask<Void, Integer, Integer>
	{

		@Override
		protected Integer doInBackground(Void... params) {
			return null;
		}

		@Override
		protected void onPreExecute() {
			
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(Integer result) {
			
			super.onPostExecute(result);
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			
			super.onProgressUpdate(values);
		}
		
	}
}
