package tv.pps.pad.test;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class PPSPadActivity extends Activity {

	private TextView movie_name, movie_info, equipment_info;
	private Gallery gallery;
	private GridView gridview;
	private SliderNaviLeftBar left_bar;
	private LayoutInflater inflater;
	private ListView listview;
	private GalleryAdapter gAdapter;
	private LinearLayout ll_home, ll_vip, ll_search, ll_history, ll_fav,
			ll_zhuiju, ll_download, ll_ipin, ll_setting;
	private static final String[] channels = { "综合频道", "正在直播", "最新更新", "每日焦点",
			"内地剧场", "港台剧场", "日韩剧场", "欧美剧场", "高清影院", "热门电影", "专题电影", "巨星电影" };
	private static int[] tops = { R.drawable.top1, R.drawable.top2,
			R.drawable.top3, R.drawable.top4, R.drawable.top5, R.drawable.top6,
			R.drawable.top7 };
	private static final String[] channel_counts = { "7952", "37", "854",
			"944", "37003", "26691", "23889", "15293", "6679", "17005", "1367",
			"1530" };
	private static int[] posters = { R.drawable.p1, R.drawable.p2,
			R.drawable.p3, R.drawable.p4, R.drawable.p5, R.drawable.p6,
			R.drawable.p7, R.drawable.p8, R.drawable.p9, R.drawable.p10,
			R.drawable.p11, R.drawable.p12 };
	private static String[] poster_namees = { "神话", "非诚勿扰", "王者之剑", "玩命追踪",
			"轩辕剑-天之痕", "天涯明月刀", "飞虎", "哆啦A梦", "雪之女王", "PPS全播报", "十二星座离奇事件",
			"麦兜" };
	private static String[] movie_infos = { "穿越题材收视神话之作,胡歌白冰跨越千年的爱恋.",
			"2012-07-14期，极品帅哥狂爱夜店惹女生争议.", "肌肉猛男的原始回归,蛮王柯南的奇幻冒险之旅.",
			"票房动作巨星杰森·斯坦森火爆新作,暴力特警血战变态杀人犯!", "古装玄幻大作,胡歌唐嫣剑客美人混战江湖!",
			"天涯明月刀40集全大结局,主角傅红雪结局揭晓.", "魔鬼教官马德钟,与工作狂宣萱日久生情,成就破案最佳拍档." };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		listview = (ListView) findViewById(R.id.listView);
		inflater = LayoutInflater.from(this);
		left_bar = (SliderNaviLeftBar) findViewById(R.id.leftbar);
		ll_home = (LinearLayout) findViewById(R.id.left_imageview_homepage);
		ll_vip = (LinearLayout) findViewById(R.id.left_imageview_vip);
		ll_search = (LinearLayout) findViewById(R.id.left_imageview_search);
		ll_history = (LinearLayout) findViewById(R.id.left_imageview_history);
		ll_fav = (LinearLayout) findViewById(R.id.left_imageview_favourites);
		ll_zhuiju = (LinearLayout) findViewById(R.id.left_imageview_zhuij);
		ll_download = (LinearLayout) findViewById(R.id.left_imageview_download);
		ll_ipin = (LinearLayout) findViewById(R.id.left_imageview_ipd);
		ll_setting = (LinearLayout) findViewById(R.id.left_imageview_setting);
		equipment_info = (TextView) findViewById(R.id.equipment_info);
		gallery = (Gallery) findViewById(R.id.gallery);
		gridview = (GridView) findViewById(R.id.gridview);
		movie_name = (TextView) findViewById(R.id.name);
		movie_info = (TextView) findViewById(R.id.info);
		listview.setAdapter(new ListAdapter());
		gAdapter=new GalleryAdapter();
		gallery.setAdapter(gAdapter);
		gridview.setAdapter(new GridviewAdapter());
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int width = dm.widthPixels;
		int height = dm.heightPixels;
		int density = dm.densityDpi;
		StringBuffer buffer = new StringBuffer("分辨率:" + width + "X" + height
				+ "密度:" + density + "dpi图片目录:");
		switch (density) {
		case DisplayMetrics.DENSITY_DEFAULT:
			buffer.append("drawable-mdpi");
			break;
		case DisplayMetrics.DENSITY_HIGH:
			buffer.append("drawable-hdpi");
			break;
		case 320:
			buffer.append("drawable-xhdpi");
			break;
		case DisplayMetrics.DENSITY_LOW:
			buffer.append("drawable-ldpi");
		default:
			buffer.append("drawable-nodpi");
			break;
		}
		equipment_info.setText(buffer.toString());
		// Toast.makeText(this, ""+width+"X"+height+"密度:"+density+"dpi",
		// Toast.LENGTH_LONG).show();

		gallery.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				movie_name.setText(poster_namees[position]);
				movie_info.setText(movie_infos[position]);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});

		ll_home.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				left_bar.setAnimation(ll_home);
			}
		});
		ll_vip.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				left_bar.setAnimation(ll_vip);
			}
		});
		ll_search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				left_bar.setAnimation(ll_search);
			}
		});
		ll_history.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				left_bar.setAnimation(ll_history);
			}
		});
		ll_fav.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				left_bar.setAnimation(ll_fav);
			}
		});
		ll_zhuiju.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				left_bar.setAnimation(ll_zhuiju);
			}
		});
		ll_download.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				left_bar.setAnimation(ll_download);
			}
		});
		ll_ipin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				left_bar.setAnimation(ll_ipin);
			}
		});
		ll_setting.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				left_bar.setAnimation(ll_setting);
			}
		});


		// 让这些布局有获取焦点的资格
		ll_home.setFocusable(true);
		ll_vip.setFocusable(true);
		ll_search.setFocusable(true);
		ll_history.setFocusable(true);
		ll_fav.setFocusable(true);
		ll_zhuiju.setFocusable(true);
		ll_download.setFocusable(true);
		ll_ipin.setFocusable(true);
		ll_setting.setFocusable(true);
		
		gallery.setUnselectedAlpha(0.3f);//未选中出现阴影
	
		
		gallery.clearFocus();
		listview.clearFocus();
		gridview.clearFocus();
		
		ll_home.requestFocus();
		

	}

	final class ListAdapter extends BaseAdapter {

		private Holder holder;

		@Override
		public int getCount() {
			return channels.length;
		}

		@Override
		public Object getItem(int position) {
			return channels[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup arg2) {
			if (convertView == null) {
				holder = new Holder();
				convertView = inflater.inflate(
						R.layout.homepage_fragment_listview_item, null);
				holder.tv_name = (TextView) convertView
						.findViewById(R.id.listview_textview_channel_name);
				holder.tv_count = (TextView) convertView
						.findViewById(R.id.listview_textview_channel_count);
				convertView.setTag(holder);
			} else {
				holder = (Holder) convertView.getTag();
			}
			holder.tv_name.setText(channels[position]);
			holder.tv_count.setText(channel_counts[position]);
			return convertView;
		}
	}

	class Holder {
		TextView tv_name, tv_count;
	}

	final class GalleryAdapter extends BaseAdapter {
		public int selectedPosition=0;
		@Override
		public int getCount() {
			return tops.length;
		}

		@Override
		public Object getItem(int position) {
			return tops[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			convertView=inflater.inflate(R.layout.gallery_item, null);
			ImageView iv = (ImageView)convertView.findViewById(R.id.gallery_image);
			iv.setImageResource(tops[position]);
			return convertView;
		}

	}

	final class GridviewAdapter extends BaseAdapter {

		private GridHolder gHolder;

		@Override
		public int getCount() {
			return posters.length;
		}

		@Override
		public Object getItem(int position) {
			return posters[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				gHolder = new GridHolder();
				convertView = inflater.inflate(R.layout.gridview_item, null);
				gHolder.image = (ImageView) convertView
						.findViewById(R.id.gridview_image);
				gHolder.name = (TextView) convertView
						.findViewById(R.id.gridview_name);
				convertView.setTag(gHolder);
			} else {
				gHolder = (GridHolder) convertView.getTag();
			}
			gHolder.image.setImageResource(posters[position]);
			gHolder.name.setText(poster_namees[position]);
			return convertView;
		}

	}

	final class GridHolder {
		ImageView image;
		TextView name;
	}

	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		System.out.println("scale:" + scale);
		return (int) (pxValue / scale + 0.5f);
	}

}