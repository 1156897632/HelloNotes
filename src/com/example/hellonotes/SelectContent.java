package com.example.hellonotes;

import java.io.File;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore.Images;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.VideoView;

public class SelectContent extends Activity implements OnClickListener {

	private ImageView content_img;
	private VideoView content_video;
	private EditText content_et;
	private Button content_btn_save, content_btn_delete;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.selectcontent);
		content_et = (EditText) findViewById(R.id.content_et);
		content_img = (ImageView) findViewById(R.id.content_img);
		content_video = (VideoView) findViewById(R.id.content_video);
		content_btn_save = (Button) findViewById(R.id.content_btn_save);
		content_btn_delete = (Button) findViewById(R.id.content_btn_delete);
		initView();
	}
	
	private void initView() {
		if(getIntent().getStringExtra(NotesDB.PATH).equals("null")){
			content_img.setVisibility(View.GONE);
		}
		else {
			content_img.setVisibility(View.VISIBLE);
			System.out.println("path:"+getIntent().getStringExtra(NotesDB.PATH));
			Bitmap bitmap = BitmapFactory.decodeFile(getIntent().getStringExtra(NotesDB.PATH));
			bitmap = ThumbnailUtils.extractThumbnail(bitmap, 200, 200,
					ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
			content_img.setImageBitmap(bitmap);
			
		}
		if(getIntent().getStringExtra(NotesDB.VIDEO).equals("null")){
			content_video.setVisibility(View.GONE);
		}
		else {
			content_video.setVisibility(View.VISIBLE);
			content_video.setVideoPath(getIntent().getStringExtra(NotesDB.VIDEO));
			System.out.println("VideoPath: "+getIntent().getStringExtra(NotesDB.VIDEO));
			content_video.start();
		}
		
		String content = "null?";
		content = getIntent().getStringExtra(NotesDB.CONTENT);
		content_et.setText(content);
		content_btn_save.setOnClickListener(this);
		content_btn_delete.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		switch(arg0.getId()){
		case R.id.content_btn_save:
			save();
			break;
		case R.id.content_btn_delete:
			returnn();
			break;
		}
	}

	private void save() {
		finish();
		
	}
	
	private void returnn() {
		finish();
	}
}
