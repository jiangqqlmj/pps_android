package tv.pps.bi.proto.biz;



/**
 * 
 * @author jiangqingqing
 * @deprecated
 */


public class PoiInfoService {
//	public BMapManager mapManager;// 定义搜索服务类
//	public LocationListener  mLocationListener;
//	public PoiInfoStatistic(Context context){
//		Log.i("billsong","初始化map");
//		mapManager = new BMapManager(context);
//		// init方法的第一个参数需填入申请的APIKey
//		mapManager.init("285B415EBAB2A92293E85502150ADA7F03C777C4", null);
//		  mLocationListener = new LocationListener(){
//			public void onLocationChanged(Location arg0) {
//			
//				if (arg0 != null) {
//					GeoPoint geoPoint = new GeoPoint((int) (arg0.getLatitude() * 1e6),
//							(int) (arg0.getLongitude() * 1e6));
//					MKSearch mMKSearch = new MKSearch();
//					mMKSearch.init(mapManager, new MySearchListener());
//					// 搜索贵州大学校门口附近500米范围的自动取款机
//					mMKSearch.poiSearchNearBy("ATM", geoPoint, 500);
//				}
//			}
//		};
//	
//	}
//	public void getPOI(){
//		if (mapManager != null) {
//			// 开启百度地图API
//			// 注册定位事件，定位后将地图移动到定位点
//			mapManager.getLocationManager().requestLocationUpdates(mLocationListener);
//			mapManager.start();
//		}
//
//		
//		Log.i("billsong","结束map");
//	}
//
//	/**
//	 * * 实现MKSearchListener接口,用于实现异步搜索服务 * @author liufeng
//	 */
//	public class MySearchListener implements MKSearchListener {
//		/** * 根据经纬度搜索地址信息结果 * * @param result 搜索结果 * @param iError 错误号 （0表示正确返回） */
//		@Override
//		public void onGetAddrResult(MKAddrInfo result, int iError) {
//			Log.i("billsong", "onGetAddrResult");
//		}
//
//		/** * 驾车路线搜索结果 * * @param result 搜索结果 * @param iError 错误号 */
//		@Override
//		public void onGetDrivingRouteResult(MKDrivingRouteResult result, int iError) {
//			Log.i("billsong", "onGetDrivingRouteResult");
//		}
//
//		/**
//		 * * POI搜索结果（范围检索、城市POI检索、周边检索） * * @param result 搜索结果 * @param type
//		 * 返回结果类型（11,12,21:poi列表 7:城市列表） * @param iError 错误号（0表示正确返回）
//		 */
//		@Override
//		public void onGetPoiResult(MKPoiResult result, int type, int iError) {
//			Log.i("billsong", "onGetPoiResult");
//			if (result == null) {
//				return;
//			}
//            StringBuffer sb = new StringBuffer();  
//            // 经纬度所对应的位置   
//
//
//            // 判断该地址附近是否有POI（Point of Interest,即兴趣点）   
//            if (null != result.getAllPoi()) {  
//                // 遍历所有的兴趣点信息   
//            	int count = 0;
//                for (MKPoiInfo poiInfo : result.getAllPoi()) {  
//                	count++;
//                    sb.append(count+"##").append("--");  
//                    sb.append("名称：").append(poiInfo.name).append("--");  
//                    sb.append("地址：").append(poiInfo.address).append("--");  
//                    sb.append("经度：").append(poiInfo.pt.getLongitudeE6() / 1000000.0f).append("--");  
//                    sb.append("纬度：").append(poiInfo.pt.getLatitudeE6() / 1000000.0f).append("\n");  
//                }
//                Log.i("billsong", sb.toString());
//            } 
//       
//		}
//
//		/** * 公交换乘路线搜索结果 * * @param result 搜索结果 * @param iError 错误号（0表示正确返回） */
//		@Override
//		public void onGetTransitRouteResult(MKTransitRouteResult result, int iError) {
//		}
//
//		/** * 步行路线搜索结果 * * @param result 搜索结果 * @param iError 错误号（0表示正确返回） */
//		@Override
//		public void onGetWalkingRouteResult(MKWalkingRouteResult result, int iError) {
//		}
//	}
}