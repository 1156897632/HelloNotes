package com.example.hellonotes;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.VideoView;

public class AddContent extends Activity implements OnClickListener {
	private String value;
	private Button btn_save, btn_delet;
	private EditText ettext;
	private ImageView c_img;
	private VideoView c_video;
	private NotesDB db;
	private SQLiteDatabase databasewrite;
	private File phonefile = null, videofile = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addcontent);
		value = getIntent().getStringExtra("flag");

		System.out.println("addContent start");
		btn_save = (Button) findViewById(R.id.btn_save);
		btn_delet = (Button) findViewById(R.id.btn_delet);
		ettext = (EditText) findViewById(R.id.ettext);
		c_img = (ImageView) findViewById(R.id.c_img);
		c_video = (VideoView) findViewById(R.id.c_video);
		btn_save.setOnClickListener(this);
		btn_delet.setOnClickListener(this);
		db = new NotesDB(this);
		databasewrite = db.getWritableDatabase();
		initView();
	}

	private void initView() {
		if (value.equals("1")) {
			c_img.setVisibility(View.GONE);
			c_video.setVisibility(View.GONE);
		}
		if (value.equals("2")) {
			c_img.setVisibility(View.VISIBLE);
			c_video.setVisibility(View.GONE);
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			phonefile = new File(Environment.getExternalStorageDirectory()
					.getAbsoluteFile() + "/" + getTime() + " .jpg");
			intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(phonefile));
			startActivityForResult(intent, 1);
		}
		if (value.equals("3")) {
			c_img.setVisibility(View.GONE);
			c_video.setVisibility(View.VISIBLE);
			Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
			videofile = new File(Environment.getExternalStorageDirectory()
					.getAbsoluteFile() + "/" +  " .mp4");
			intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(videofile));
			startActivityForResult(intent, 2);
		}
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.btn_save:
			addDB();
			System.out.println("addDB");
			System.out.println("~~~~~~~~~~~~~");
			finish();
			break;
		case R.id.btn_delet:
			finish();

			break;
		}
	}

	private void addDB() {
		ContentValues values = new ContentValues();
		values.put(NotesDB.CONTENT, ettext.getText().toString());
		System.out.println(ettext.getText().toString());

		values.put(NotesDB.TIME, getTime());
		System.out.println(getTime());

		values.put(NotesDB.PATH, phonefile + "");
		// System.out.println("absolut img path");
		System.out.println("phonefile:" + phonefile + "");

		values.put(NotesDB.VIDEO, videofile+"");
//		System.out.println(videofile+"");
		System.out.println("VideoPath: "+videofile+"");

		databasewrite.insert(NotesDB.TABLE_NAME, null, values);
		System.out.println("insert");

	}

	private String getTime() {
		String curTime = "";
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyyƒÍMM‘¬dd»’ HH:mm:ss");
		Date date = new Date();
		curTime = dateFormat.format(date);
		return curTime;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1) {
			Bitmap bitmap = BitmapFactory.decodeFile(phonefile + "");
			// Options options = new Options();
			// options.inJustDecodeBounds = true;
			// bitmap = BitmapFactory.decodeFile(phonefile + "", options);
			// options.inJustDecodeBounds = false;
			// options.inSampleSize = 1;
			// bitmap = BitmapFactory.decodeFile(phonefile + "", options);
			bitmap = ThumbnailUtils.extractThumbnail(bitmap, 500, 500,
					ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
			c_img.setImageBitmap(bitmap);
			System.out.println("set addcontent img");
		}
		if(requestCode == 2){
			c_video.setVideoURI(Uri.fromFile(videofile));
			c_video.start();
		}
	}
}
