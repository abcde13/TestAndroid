package com.example.testandroid;

import java.util.List;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.os.Build;
import android.provider.MediaStore;


public class DisplayMessageActivity extends Activity {

	TextView a_message;
	TextView l_messages;
	Bitmap mImageBitmap;
	ImageView mImageView;
	
	public static final int TAKING_PIC = 1;
	
	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_message);
		
		SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPref.edit();
		
		//Get the message from the Intent
		Intent intent = getIntent();
		String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
		
		a_message = (TextView)findViewById(R.id.actual_message);
		l_messages = (TextView)findViewById(R.id.message_list);
		
		a_message.setText(message);
		String def = "";
		String k = 	sharedPref.getString(getString(R.string.messages),def);
		message = k + (message+'\n');
		editor.putString(getString(R.string.messages), message);
		editor.commit();
		
		k = sharedPref.getString(getString(R.string.messages),message);
		
		l_messages.setText(k);
		
		
		//Make sure we're running HoneyComb or higher to use ActionBar APIs
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
			//Show the Up button on the ActionBar
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_message, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	protected void onActivityResult(int requestCode,int resultCode,Intent data){
		if(requestCode == TAKING_PIC){
			handleSmallPhoto(data);
			
			Intent viewPhoto = new Intent(this,ViewPhoto.class);
			Bundle extras = new Bundle();
			extras.putParcelable("picTaken", mImageBitmap);
			viewPhoto.putExtras(extras);
			startActivity(viewPhoto);
		}
	}
	
	public void activateCamera(View view){
		takePic(TAKING_PIC);
		
	}
	
	public void takePic(int actionCode){
		Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(takePicture,actionCode);
		
	}
	
	public static boolean isIntentAvailable(Context context, String action) {
	    final PackageManager packageManager = context.getPackageManager();
	    final Intent intent = new Intent(action);
	    List<ResolveInfo> list =
	            packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
	    return list.size() > 0;
	}
	
	private void handleSmallPhoto(Intent intent){
		Bundle extras = intent.getExtras();
		mImageBitmap = (Bitmap) extras.get("data");

	}

}
