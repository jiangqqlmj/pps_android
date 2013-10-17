package com.pps.db.service;

import java.util.List;

import com.pps.model.SubModel;

/**
 * 列表缓存业务逻辑接口
 * @author jiangqingqing
 *
 */
public interface ChannelListServiceInterface {
    
	/**
	 * 查询数据库中所有的列表的缓存
	 * @return 列表的缓存集合
	 */
	public List<SubModel> queryAllCacheChannel();
	
	/**
	 * 往数据库中插入的单个列表的缓存
	 * @param mChannelModel  单个节目的对象
	 * @return 插入成功返回true 否则返回false
	 */
	public boolean insertSingleChannel(SubModel pSubModel);
	
	/**
	 * 根据传进来的列表的缓存集合，集中插入到数据库中
	 * @param pLists 节目列表的集合
	 * @return 插入成功返回true，否则返回false
	 */
	public boolean insertChannelList(List<SubModel> pLists);
	
	
	/**
	 * 删除数据库中所有的列表缓存
	 * @return
	 */
	public boolean deleteAllCacheChannel();
	
	
	/**
	 * 根据相应的限制条件进行分页查询
	 * @param pLimited  一页显示的条数
	 * @param offest   偏移量
	 * @return  符合条件的列表的集合
	 */
	public List<SubModel> queryChanenlByLimited(int pLimited,int offest);
}
