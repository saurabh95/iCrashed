package com.example.contextlist;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class home extends Activity {

	TextView heading;
	Button start,stop,numbers;
	RadioButton high,bumpy;
	Double threshold=42.0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		
		heading=(TextView)findViewById(R.id.head);
		Typeface mFont = Typeface.createFromAsset(getAssets(), "fonts/Terra.ttf");
		start=(Button) findViewById(R.id.start);
		stop=(Button)findViewById(R.id.stop);
		numbers=(Button)findViewById(R.id.numsel);
		high=(RadioButton)findViewById(R.id.high);
		bumpy=(RadioButton)findViewById(R.id.low);
		heading.setTypeface(mFont);
		
		numbers.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getBaseContext(),MainActivity.class);
				startActivity(intent);
				
			}
			
		});
		
		high.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
				onRadioButtonClicked(v);
				// TODO Auto-generated method stub
				
			}
		});
		bumpy.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onRadioButtonClicked(v);
				// TODO Auto-generated method stub
				
			}
		});
		start.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d("start","fuck");
				Toast.makeText(getApplicationContext(), "Tracking on", Toast.LENGTH_LONG).show();
				Intent intent=new Intent(getBaseContext(),speedit.class);
				intent.putExtra("threshold", threshold);
				startService(intent);	
			}
		});
		stop.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Intent intent=new Intent(getBaseContext(),MainActivity.class);
				//intent.putExtra("threshold", threshold);
				Toast.makeText(getApplicationContext(), "Tracking off", Toast.LENGTH_LONG).show();
				stopService(new Intent(getBaseContext(),speedit.class));
			}
		});
		
	}
	public void onRadioButtonClicked(View view) {
	    // Is the button now checked?
	    boolean checked = ((RadioButton) view).isChecked();
	    
	    // Check which radio button was clicked
	    switch(view.getId()) {
	        case R.id.low:
	            if (checked)
	            	
	            	threshold=4E-8;
	            break;
	        case R.id.high:
	            if (checked)
	            {
	            	Log.d("alla", "challa");
	            	threshold=2.5E-8;
	            }
	            break;
	    }
	}
	

}
