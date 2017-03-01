package com.example.hellonotes;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends Activity implements OnClickListener,
		OnItemLongClickListener, OnItemClickListener {
	private NotesDB db;
	private SQLiteDatabase dbreader;
	private Button btn_text, btn_img, btn_video;
	private ListView list;
	private Intent intent;
	private Myadapter myadapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		btn_text = (Button) findViewById(R.id.btn_text);
		btn_img = (Button) findViewById(R.id.btn_img);
		btn_video = (Button) findViewById(R.id.btn_video);
		list = (ListView) findViewById(R.id.list);
		list.setOnItemClickListener(this);
		list.setOnItemLongClickListener(this);

		btn_text.setOnClickListener(this);
		btn_img.setOnClickListener(this);
		btn_video.setOnClickListener(this);
		db = new NotesDB(this);
		dbreader = db.getReadableDatabase();
	}

	@Override
	public void onClick(View arg0) {
		intent = new Intent(this, AddContent.class);
		switch (arg0.getId()) {
		case R.id.btn_text:
			intent.putExtra("flag", "1");
			startActivity(intent);
			break;
		case R.id.btn_img:
			intent.putExtra("flag", "2");
			startActivity(intent);
			break;
		case R.id.btn_video:
			intent.putExtra("flag", "3");
			startActivity(intent);
			break;
		}
	}

	private void selectDB() {
		Cursor cursor = dbreader.query(NotesDB.TABLE_NAME, null, null, null,
				null, null, null);
		myadapter = new Myadapter(this, cursor);
		list.setAdapter(myadapter);
	}

	@Override
	protected void onResume() {
		super.onResume();
		selectDB();
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> AdapterView, View View,
			final int arg2, long arg3) {
		new AlertDialog.Builder(this)
				.setTitle("操作选项")
				.setItems(new CharSequence[] { "删除" },
						new AlertDialog.OnClickListener() {

							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								Cursor cursor = dbreader.query(
										NotesDB.TABLE_NAME, null, null, null,
										null, null, null);
								cursor.moveToPosition(arg2);
								switch (arg1) {
								case 0:

									dbreader.delete(
											NotesDB.TABLE_NAME,
											"_id="
													+ cursor.getString(cursor
															.getColumnIndex("_id")),
											null);
									selectDB();
									break;
								}
							}
						}).setNegativeButton("取消", null).show();

		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Cursor cursor = dbreader.query(NotesDB.TABLE_NAME, null, null, null,
				null, null, null);
		cursor.moveToPosition(arg2);
		Intent i = new Intent(MainActivity.this, SelectContent.class);
		i.putExtra(NotesDB.ID,
				cursor.getString(cursor.getColumnIndex(NotesDB.ID)));
		i.putExtra(NotesDB.PATH,
				cursor.getString(cursor.getColumnIndex(NotesDB.PATH)));
		i.putExtra(NotesDB.CONTENT,
				cursor.getString(cursor.getColumnIndex(NotesDB.CONTENT)));
		i.putExtra(NotesDB.VIDEO,
				cursor.getString(cursor.getColumnIndex(NotesDB.VIDEO)));
		startActivity(i);
	}
}
