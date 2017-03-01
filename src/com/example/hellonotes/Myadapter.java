package com.example.hellonotes;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.media.Image;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;

public class Myadapter extends BaseAdapter {

	private Context context;
	private Cursor cursor;
	private LinearLayout layout;
	private TextView tv_list_content, tv_list_time;
	private ImageView list_img;
	private ImageView list_video;

	public Myadapter(Context context, Cursor cursor) {
		this.context = context;
		this.cursor = cursor;
	}

	@Override
	public int getCount() {
		return cursor.getCount();
	}

	@Override
	public Object getItem(int arg0) {
		return cursor.getPosition();
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		LayoutInflater inflater = LayoutInflater.from(context);
		layout = (LinearLayout) inflater.inflate(R.layout.cell, null);
		tv_list_content = (TextView) layout.findViewById(R.id.list_content);
		tv_list_time = (TextView) layout.findViewById(R.id.list_time);
		list_img = (ImageView) layout.findViewById(R.id.list_img);
		list_video = (ImageView) layout.findViewById(R.id.list_video);

		cursor.moveToPosition(arg0);

		// System.out.println("~~~~~~~~~~~~~~~~");
		// System.out.println("position:"+arg0);
		String id = cursor.getString(cursor.getColumnIndex("_id"));
		// System.out.println("id:"+id);

		String content = cursor.getString(cursor.getColumnIndex("content"));
		// System.out.println("content:"+content);

		String time = cursor.getString(cursor.getColumnIndex("time"));
		// System.out.println("time:"+time);

		String uri = cursor.getString(cursor.getColumnIndex("path"));
		// System.out.println("time:"+time);

		String urivideo = cursor.getString(cursor.getColumnIndex("video"));
		// System.out.println("time:"+time);

		tv_list_content.setText(content);
		tv_list_time.setText(time);
		if(!uri.equals("null")){
		list_img.setImageBitmap(getImageThumbnail(uri, 200, 200));
		}
		if(!urivideo.equals("null")){
		list_video.setImageBitmap(getVideoThumbanail(urivideo, 200, 200,
				MediaStore.Images.Thumbnails.MICRO_KIND));
		}
		return layout;
	}

	public Bitmap getImageThumbnail(String uri, int width, int height) {
		Bitmap bitmap = null;
		Options options = new Options();
		options.inJustDecodeBounds = true;
		bitmap = BitmapFactory.decodeFile(uri, options);
		options.inJustDecodeBounds = false;
		int bewidth = options.outWidth / width;
		int beheight = options.outHeight / height;
		int be = 1;
		if (bewidth < beheight) {
			be = bewidth;
		} else {
			be = beheight;

		}
		if (be <= 0) {
			be = 1;
		}
		options.inSampleSize = be;
		bitmap = BitmapFactory.decodeFile(uri, options);
		bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
				ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
		return bitmap;
	}

	private Bitmap getVideoThumbanail(String uri, int width, int height,
			int kind) {
		Bitmap bitmap = null;
		bitmap = ThumbnailUtils.createVideoThumbnail(uri, kind);
		bitmap = ThumbnailUtils.extractThumbnail(bitmap, 200, 200,
				ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
		return bitmap;

	}
}
