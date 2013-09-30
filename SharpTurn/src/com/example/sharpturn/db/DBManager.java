package com.example.sharpturn.db;

import java.util.List;

import com.example.sharpturn.model.SharpModel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBManager {
	private Context mContext;
	private DBHelper mDBHelper;
	private SQLiteDatabase mDatabase;
	public DBManager(Context pContext)
	{
		this.mContext=pContext;
		mDBHelper=new DBHelper(mContext);
	}
	
	/**
	 * 根据传进来的索引进行查询一条数据对象
	 * @param pSharp_id
	 * @return
	 */
	public SharpModel querySharpById(int pSharp_id)
	{
		SharpModel mSharpModel=null;
		mDatabase=mDBHelper.getReadableDatabase();
		Cursor mCursor = mDatabase.query(DBConfig.TABLE_NAME, new String[]{"name","answer"}, "id= ?", new String[]{String.valueOf(pSharp_id)}, null, null,null);
		if(mCursor!=null&&mCursor.getCount()>0)
		{
			mSharpModel=new SharpModel();
			mSharpModel.setName(mCursor.getString(mCursor.getColumnIndex("name")));
			mSharpModel.setAnswer(mCursor.getString(mCursor.getColumnIndex("answer")));
			if(mCursor!=null)
			{
				mCursor.close();
			}
			if(mDatabase!=null)
			{
				mDatabase.close();
			}
			return mSharpModel;
		}
		return null;
	}
	
	/**
	 * 根据传进来的对象进行插入到数据库
	 * @param pSharpModel
	 * @return 插入成功返回true,否则返回false
	 */
	public boolean insertSharp(SharpModel pSharpModel)
	{
		mDatabase=mDBHelper.getWritableDatabase();
		ContentValues mContentValues=new ContentValues();
		mContentValues.put("name", pSharpModel.getName());
		mContentValues.put("answer", pSharpModel.getAnswer());
		long result= mDatabase.insert(DBConfig.TABLE_NAME, null, mContentValues);
		if(mDatabase!=null)
		{
			mDatabase.close();
		}
		if(result!=-1)
		{
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param pLists
	 * @return
	 */
	public boolean insertSharpByList(List<SharpModel> pLists){
		Log.d("jiangqq", ">>>>>>需要插入的数据条数为"+pLists.size());
		for (SharpModel sharpModel : pLists) {
			boolean result = insertSharp(sharpModel);
			if(!result)
			{
				return false;
			}
		}		
		return true;
	}
	 
}
