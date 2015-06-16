package com.example.popuandviewpagedemo.adapter;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.popuandviewpagedemo.R;
import com.example.popuandviewpagedemo.bean.NewsBean;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;

public class NewsAdapter extends BaseAdapter {
	List<NewsBean> news_list = new ArrayList<NewsBean>();
	LayoutInflater inflater;
	private Context context;
	private ImageLoader loaderUtil;

	public NewsAdapter(Context context, List<NewsBean> news_list) {
		this.news_list = news_list;
		this.inflater = LayoutInflater.from(context);
	}

	public void onDateChange(List<NewsBean> news_list) {
		this.news_list = news_list;
		this.notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return news_list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return news_list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	ViewHolder holder;

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.news_item, null);
			holder.tv_title = (TextView) convertView
					.findViewById(R.id.tv_title);
			holder.tv_inputtime = (TextView) convertView
					.findViewById(R.id.tv_inputtime);
			holder.imgthumb = (ImageView) convertView
					.findViewById(R.id.iv_thumb);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		NewsBean entity = news_list.get(position);
		// º”‘ÿœ‘ æÕ¯¬ÁÕº∆¨

		// -----------------------------------------

		String urlCover = entity.getThumb();
		ImageSize mImageSize = new ImageSize(100, 100);

		ImageLoader.getInstance().loadImage(urlCover, mImageSize,
				new SimpleImageLoadingListener() {

					@Override
					public void onLoadingComplete(String imageUri, View view,
							Bitmap loadedImage) {
						super.onLoadingComplete(imageUri, view, loadedImage);
						holder.imgthumb.setImageBitmap(loadedImage);
					}

				});

		// -----------------------------------

	//	ImageLoader.getInstance().displayImage(urlCover, holder.imgthumb);

		holder.tv_title.setText(entity.getTitle());
		holder.tv_inputtime.setText(entity.getInputtime());

		return convertView;
	}

	class ViewHolder {
		ImageView imgthumb;
		TextView tv_title;
		TextView tv_inputtime;

	}
}
