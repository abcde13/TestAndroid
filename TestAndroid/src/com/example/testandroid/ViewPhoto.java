package com.example.testandroid;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.Menu;
import android.widget.ImageView;

public class ViewPhoto extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		Bitmap bmp = (Bitmap)extras.getParcelable("picTaken");
		ImageView image = new ImageView(this);
		image.setImageBitmap(bmp);
		
		setContentView(R.layout.activity_view_photo);
		setContentView(image);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_photo, menu);
		return true;
	}

}
