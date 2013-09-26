package com.pps.bean;

public class UpdateInformation {
	public static int localVersion = 2;// 本地版本
	public static int serverVersion = 3;// 服务器版本
	public static int serverFlag = 1;// 服务器标志
	public static int lastForce = 2;// 之前强制升级版本
	public static String Durl = "http://10.1.35.27:8080/UpdateAppServer/tv.pps.pad.apk";// 升级包获取地址
	public static String MD5 = "";// 升级包获取地址MD5值
	public static String upgradeinfo = "新版本(v1.5.1)更新提示:\n1.修复了列 \" 筛选 \" 无法使用的问题。\n2.UI细节优化\n3.其他若干细节优化";// 升级信息
	public static String downloadDir = "pps/PPStv_update";// 下载目录
 }
