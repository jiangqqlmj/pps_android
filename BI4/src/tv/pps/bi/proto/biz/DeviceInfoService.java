package tv.pps.bi.proto.biz;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import tv.pps.bi.proto.model.DeviceInfo;
import tv.pps.bi.utils.LogUtils;
import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.view.WindowManager;

/**
 * 获取手机设备信息
 * 
 * @author jiangqingqing
 * @time 2013/09/05 17:13
 */
public class DeviceInfoService {

	public final static String TAG = "DeviceInfo";
	private Context mContext;

	public DeviceInfoService(Context pContext) {
		this.mContext = pContext;
	}

	@SuppressWarnings("deprecation")
	public DeviceInfo getUserDevice_info() {

		DeviceInfo deviceInfo = new DeviceInfo();
		TelephonyManager tm = (TelephonyManager) mContext
				.getSystemService(Context.TELEPHONY_SERVICE);
		WindowManager mWindowManager = (WindowManager) mContext
				.getSystemService(Context.WINDOW_SERVICE);
		String imei = tm.getDeviceId();
		if(null!=imei){
			
		deviceInfo.setImei(imei);// 手机串号
		}
		String imsi = tm.getSubscriberId();
		if (null != imsi) {
			deviceInfo.setImsi(imsi);
		}

		String manufacturer = Build.MANUFACTURER;// 制造商
		deviceInfo.setManufacturer(manufacturer);
		String model = Build.MODEL;// 类型
		deviceInfo.setModel(model);

		int width = mWindowManager.getDefaultDisplay().getWidth();
		int height = mWindowManager.getDefaultDisplay().getHeight();
		String screen_resolution = width + "*" + height;
		deviceInfo.setScreen_resolution(screen_resolution);// 分辨率
		String os_version = Build.VERSION.RELEASE;// Build.VERSION.SDK_INT+ "";
		deviceInfo.setOs_version(os_version);
		/** case1 手机root 返回 root case 2 手机没有root 设置为"" */
		if (isRoot()) {
			deviceInfo.setOs_custermize("root");
		} else {
			deviceInfo.setOs_custermize("");
		}
		return deviceInfo;
	}

	/**
	 * 判断手机是否root
	 * 
	 * @return
	 */
	public static boolean isRoot() {
		boolean isRoot = false;
		String sys = System.getenv("PATH");
		ArrayList<String> commands = new ArrayList<String>();
		String[] path = sys.split(":");
		for (int i = 0; i < path.length; i++) {
			String commod = "ls -l " + path[i] + "/su";
			commands.add(commod);
		}
		ArrayList<String> res = run("/system/bin/sh", commands);
		String response = "";
		for (int i = 0; i < res.size(); i++) {
			response += res.get(i);
		}
		String root = "-rwsr-sr-x root     root";
		if (response.contains(root)) {
			isRoot = true;
		}
		return isRoot;

	}

	/**
	 * 
	 * @param shell
	 * @param commands
	 * @return
	 */
	public static ArrayList<String> run(String shell, ArrayList<String> commands) {
		ArrayList<String> output = new ArrayList<String>();
		Process process = null;
		try {
			process = Runtime.getRuntime().exec(shell);

			BufferedOutputStream shellInput = new BufferedOutputStream(
					process.getOutputStream());
			BufferedReader shellOutput = new BufferedReader(
					new InputStreamReader(process.getInputStream()));

			for (String command : commands) {
				LogUtils.i(TAG, "command: " + command);
				shellInput.write((command + " 2>&1\n").getBytes());
			}

			shellInput.write("exit\n".getBytes());
			shellInput.flush();

			String line;
			while ((line = shellOutput.readLine()) != null) {
				LogUtils.i(TAG, "result: " + line);
				output.add(line);
			}

			process.waitFor();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			process.destroy();
		}
		return output;
	}
}
