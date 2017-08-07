package com.example.exmgif;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.exmgif.util.GifImage;
import com.example.exmgif.util.GifImage.GifFrame;

import java.io.InputStream;

public class MainActivity extends Activity implements OnClickListener {

	private ImageView iv_gif;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button btn_play1 = (Button) findViewById(R.id.btn_play1);
        Button btn_play2 = (Button) findViewById(R.id.btn_play2);
        btn_play1.setOnClickListener(this);
        btn_play2.setOnClickListener(this);
		iv_gif = (ImageView) findViewById(R.id.iv_gif);
    }
	
	private Drawable getDraw(int id) {
		return getResources().getDrawable(id);
	}
	
	private void showFrameAnimation() {
		//帧动画需要把每帧图片加入AnimationDrawable队列
		AnimationDrawable animationList = new AnimationDrawable();
		animationList.addFrame(getDraw(R.drawable.flow_p1), 50);
		animationList.addFrame(getDraw(R.drawable.flow_p2), 50);
		animationList.addFrame(getDraw(R.drawable.flow_p3), 50);
		animationList.addFrame(getDraw(R.drawable.flow_p4), 50);
		animationList.addFrame(getDraw(R.drawable.flow_p5), 50);
		animationList.addFrame(getDraw(R.drawable.flow_p6), 50);
		animationList.addFrame(getDraw(R.drawable.flow_p7), 50);
		animationList.addFrame(getDraw(R.drawable.flow_p8), 50);
		//setOneShot为true表示只播放一次，为false表示循环播放
		animationList.setOneShot(false);
		iv_gif.setImageDrawable(animationList);
		animationList.start();
	}

	private void showGifAnimation(int resid) {
		InputStream is = getResources().openRawResource(resid);
		GifImage gifImage = new GifImage();
		int code = gifImage.read(is);
		if (code == GifImage.STATUS_OK) {
			GifFrame[] frameList = gifImage.getFrames();
			AnimationDrawable gifList = new AnimationDrawable();
			for (int i=0; i<frameList.length; i++) {
				//BitmapDrawable用于把Bitmap格式转换为Drawable格式
				BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), frameList[i].image);
				gifList.addFrame(bitmapDrawable, frameList[i].delay);
			}
			gifList.setOneShot(false);
			iv_gif.setImageDrawable(gifList);
			gifList.start();
		} else if (code == GifImage.STATUS_FORMAT_ERROR) {
			Toast.makeText(this, "该图片不是gif格式", Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(this, "gif图片读取失败:"+code, Toast.LENGTH_LONG).show();
		}
	}
	
	@Override
	public void onClick(View v) {
//		if (v.getId() == R.id.btn_play1) {
//			showFrameAnimation();
//		} else if (v.getId() == R.id.btn_play2) {
//			showGifAnimation(R.drawable.timg);
//		}
	}

}
