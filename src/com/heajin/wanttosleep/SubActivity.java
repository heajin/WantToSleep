package com.heajin.wanttosleep;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SubActivity extends Activity implements OnClickListener {
	PreMusic background = null;
	TextView countSheep;
	Button startBtn;
	Button pauseBtn;
	Boolean isPaused;
	Boolean clickAble = true;
	int count = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sub);

		countSheep = (TextView) findViewById(R.id.countsheep);
		background = new PreMusic(this, R.raw.jajang1);
		Button startBtn = (Button) findViewById(R.id.startbtn);
		Button pauseBtn = (Button) findViewById(R.id.pausebtn);
		startBtn.setOnClickListener(this);
		pauseBtn.setOnClickListener(this);

	}

	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			countSheep.setText(count + "마리");
			ImageView img_animation = (ImageView) findViewById(R.id.image1);
			img_animation.setVisibility(View.VISIBLE);
			TranslateAnimation animation = new TranslateAnimation(-600.0f,
					1500.0f, 150.0f, 150.0f); // new
												// TranslateAnimation(xFrom,xTo,
												// yFrom,yTo)
			animation.setDuration(16000); // animation duration // animation
											// repeat count
			animation.setRepeatMode(1); // repeat animation (left to right,
										// right to
										// left )
			// animation.setFillAfter(true);

			img_animation.startAnimation(animation); // start animation
			count++;

			img_animation.setVisibility(View.INVISIBLE);
			if (isPaused == false)
				mHandler.sendEmptyMessageDelayed(0, 13000);
		}
	};

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		View temp;
		if (v.getId() == R.id.startbtn) {
			if (clickAble == true) {
				background.play();
				Toast.makeText(this, "양 세기를 시작합니다", Toast.LENGTH_SHORT).show();
				temp = (View) findViewById(R.id.image1);
				mHandler.sendEmptyMessage(0);
				isPaused = false;
				clickAble = false;
			}
		} else if (v.getId() == R.id.pausebtn) {
			if (clickAble == false) {
				background.stop();
				Toast.makeText(this, "일시 정지", Toast.LENGTH_SHORT).show();
				temp = (View) findViewById(R.id.image1);
				temp.setVisibility(View.INVISIBLE);
				isPaused = true;
				clickAble = true;
			}
		}

	}

	DialogInterface.OnClickListener dlistener = new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			if (which == Dialog.BUTTON_POSITIVE) {
				finish();
			}
		}
	};

	public void onBackPressed() {
		// TODO Auto-generated method stub
		AlertDialog alert = new AlertDialog.Builder(SubActivity.this)
				.setTitle("종료확인").setMessage("양을 그만 세시겠습니까?")
				.setIcon(R.drawable.sheepbutton)
				.setPositiveButton("네", dlistener)
				.setNegativeButton("아니오", dlistener).show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sub, menu);
		return true;
	}

	protected void onPause() {
		super.onPause();
		background.stop();
		isPaused = true;
	}

	protected void onResume() {
		super.onResume();
		background.play();
		isPaused = false;
	}

}

class PreMusic {
	MediaPlayer mp = null;

	public PreMusic(Context con, int id) {
		mp = MediaPlayer.create(con, id);
	}

	public void play() {
		mp.seekTo(0);
		mp.start();
		mp.setOnCompletionListener(new OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer arg0) {
				// TODO Auto-generated method stub
				mp.start();
			}

		});
	}

	public void stop() {
		mp.seekTo(0);
		mp.pause();

	}
}