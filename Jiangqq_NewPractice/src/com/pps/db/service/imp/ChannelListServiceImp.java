package com.pps.db.service.imp;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.pps.db.DBConfig;
import com.pps.db.DBHelper;
import com.pps.db.service.ChannelListServiceInterface;
import com.pps.model.SubModel;

/**
 * 进行列表缓存与分页处理
 * @author jiangqingqing
 * @time 2013/09/11 14:17
 */
public class ChannelListServiceImp implements ChannelListServiceInterface {

	private DBHelper dbHelper;
	private SQLiteDatabase dbDatabase;
 
	private List<SubModel> mLists;
	private SubModel mModel;
	public ChannelListServiceImp(Context pContext) {
		dbHelper = new DBHelper(pContext);
	}
	
	/*
	 * (non-Javadoc) 返回所有的列表集合
	 * @see com.pps.db.service.ChannelListServiceInterface#queryAllCacheChannel()
	 */
	@Override
	public List<SubModel> queryAllCacheChannel() {
		
		dbDatabase=dbHelper.getWritableDatabase();
		Cursor mCursor= dbDatabase.query(DBConfig.TABLE_NAME, DBConfig.COLUMNS, null, null, null, null,
				null);
		if(mCursor!=null&&mCursor.getCount()>=0)
		{
			mLists=new ArrayList<SubModel>();
			while(mCursor.moveToNext())
			{
				mModel=new SubModel();
				mModel.setImg(mCursor.getString(mCursor.getColumnIndex(DBConfig.CHANNEL_IMG)));
				mModel.setName(mCursor.getString(mCursor.getColumnIndex(DBConfig.CHANNEL_NAME)));
				mModel.setTp(mCursor.getString(mCursor.getColumnIndex(DBConfig.CHANNEL_TP)));
				mModel.setOn(mCursor.getInt(mCursor.getColumnIndex(DBConfig.CHANNEL_ON)));
				mModel.setVm(mCursor.getFloat(mCursor.getColumnIndex(DBConfig.CHANNEL_VM)));
				mModel.setId(mCursor.getInt(mCursor.getColumnIndex(DBConfig.CHANNEL_SUBID)));
				mLists.add(mModel);
			}
		}
		close();
		return mLists;
	}
		
	/*
	 * (non-Javadoc)  进行列表的分页查询   
	 * @prams pLimited 每页进行显示的列表数量
	 * @prams offest 进行查询的偏移量
	 * @see com.pps.db.service.ChannelListServiceInterface#queryChanenlByLimited(int, int)
	 */
	public List<SubModel> queryChanenlByLimited(int pLimited,int offest)
	{
		List<SubModel> mLists = null;
		String sqlStr="select * from channel_table order by channel_id limit ? offset ?";
		dbDatabase = dbHelper.getReadableDatabase();
		Cursor mCursor= dbDatabase.rawQuery(sqlStr, new String[]{String.valueOf(pLimited),String.valueOf(offest)});
	    if(mCursor!=null&&mCursor.getCount()>0){
	    	mLists=new ArrayList<SubModel>(mCursor.getCount());
	    	while(mCursor.moveToNext()){
	    		mModel=new SubModel();
				mModel.setImg(mCursor.getString(mCursor.getColumnIndex(DBConfig.CHANNEL_IMG)));
				mModel.setName(mCursor.getString(mCursor.getColumnIndex(DBConfig.CHANNEL_NAME)));
				mModel.setTp(mCursor.getString(mCursor.getColumnIndex(DBConfig.CHANNEL_TP)));
				mModel.setOn(mCursor.getInt(mCursor.getColumnIndex(DBConfig.CHANNEL_ON)));
				mModel.setVm(mCursor.getFloat(mCursor.getColumnIndex(DBConfig.CHANNEL_VM)));
				mModel.setId(mCursor.getInt(mCursor.getColumnIndex(DBConfig.CHANNEL_SUBID)));
				mLists.add(mModel);
	    	}
	    	close();
	    }
		return mLists;
	}
	
	
	/*
	 * (non-Javadoc)  插入单个节目的对象
	 * @see com.pps.db.service.ChannelListServiceInterface#insertSingleChannel(com.pps.model.SubModel)
	 */
	@Override 
	public boolean insertSingleChannel(SubModel pSubModel) {
		dbDatabase = dbHelper.getWritableDatabase();
		ContentValues mValues=new ContentValues(1);
		mValues.put(DBConfig.CHANNEL_IMG, pSubModel.getImg());
		mValues.put(DBConfig.CHANNEL_NAME, pSubModel.getName());
		mValues.put(DBConfig.CHANNEL_TP, pSubModel.getTp());
		mValues.put(DBConfig.CHANNEL_ON, pSubModel.getOn());
		mValues.put(DBConfig.CHANNEL_VM, pSubModel.getVm());
		mValues.put(DBConfig.CHANNEL_SUBID, pSubModel.getId());
        long result= dbDatabase.insert(DBConfig.TABLE_NAME, null, mValues);	
        close();
		if(result!=-1)
		{
			return true;
		}
		return false;
	}
    	
	/*
	 * (non-Javadoc) 一次性插入节目的集合
	 * @see com.pps.db.service.ChannelListServiceInterface#insertChannelList(java.util.List)
	 */
	@Override  
	public boolean insertChannelList(List<SubModel> pLists) {
        for (SubModel mSubModel : pLists) {
        	 if(!insertSingleChannel(mSubModel))
        	 {
        		return false;
        	 }
		}
		return true;
	}
	
	
	/*
	 * (non-Javadoc) 删除数据库中所有的列表
	 * @see com.pps.db.service.ChannelListServiceInterface#deleteAllCacheChannel()
	 */
	@Override  
	public boolean deleteAllCacheChannel() {
        dbDatabase=dbHelper.getWritableDatabase();
        int result=dbDatabase.delete(DBConfig.TABLE_NAME, null, null);
        close();
        if(result!=0)
        {
        	return true;
        }
		return false;
	}
	/**
	 * 关闭数据库对象
	 */
	private void close()
	{
		if(dbDatabase!=null)
		{
			dbDatabase.close();
		}
	}
	
	
}
