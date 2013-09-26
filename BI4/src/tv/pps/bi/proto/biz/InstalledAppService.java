package tv.pps.bi.proto.biz;

import java.util.ArrayList;
import java.util.List;

import tv.pps.bi.db.DBAPPManager;
import tv.pps.bi.proto.model.App;
import tv.pps.bi.proto.model.AppActivity;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * 获取APP信息  
 * @author jiangqingqing
 * 
 */
public class InstalledAppService {
	private Context mContext;
  public InstalledAppService(Context pContext) {
	  this.mContext=pContext;
	
  }
  
  /**
	 * 查询安装app的基本信息 Appinstalled_app = 8; 安装哪些app 以及app的基本信息，使用情况
	 * @return  APP信息对象的集合
	 */
  public List<App> getUserInstalled_app()
  {
	    PackageManager packM= mContext.getPackageManager();
		List<PackageInfo> packageInfos 	=  packM.getInstalledPackages(0);
		List<App> appinfos 		= new ArrayList<App>();
		DBAPPManager db 					= DBAPPManager.getDBManager(mContext);
		for (int i = 0; i < packageInfos.size(); i++) {
			PackageInfo temp 			= packageInfos.get(i);
			ApplicationInfo appInfo 	= temp.applicationInfo;
			App app_info			= new App();
			String appName 				= temp.applicationInfo.loadLabel(mContext.getPackageManager()).toString();
			String packagename      	= temp.packageName;
			if ((appInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
				List<AppActivity> appdatas 	= db.getData(packagename);//通过appname获取app的使用情况
				app_info.setName(appName);// 应用名称
				app_info.setVersion(temp.versionName);// 版本名称
				app_info.setActivity(appdatas);// app的使用情况list集合
				appinfos.add(app_info);
			}
		}
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	  return appinfos;
  }
}
