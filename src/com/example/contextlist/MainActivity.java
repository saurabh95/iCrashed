package com.example.contextlist;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.provider.ContactsContract;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	String num;
	EditText num1,num2,num3;
	Button sel1,sel2,sel3,save;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		final SharedPreferences saved_numbers=getSharedPreferences("numbers",0);
		
		
		num1=(EditText)findViewById(R.id.num1);
		num2=(EditText)findViewById(R.id.num2);
		num3=(EditText)findViewById(R.id.num3);
		
		sel1=(Button)findViewById(R.id.select1);
		sel2=(Button)findViewById(R.id.select2);
		sel3=(Button)findViewById(R.id.select3);
		save=(Button)findViewById(R.id.save);
		
		SharedPreferences.Editor editor = saved_numbers.edit();
		if(saved_numbers.contains("no1")==false){
			editor.putString("no1", "");
		}
		else
			num1.setText(saved_numbers.getString("no1", ""));
		if(saved_numbers.contains("no2")==false){
			editor.putString("no2", "");
		}
		else
			num2.setText(saved_numbers.getString("no2", ""));
		if(saved_numbers.contains("no3")==false){
			editor.putString("no3", "");
		}
		else
			num3.setText(saved_numbers.getString("no3", ""));
		editor.commit();
		
		sel1.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				pickNumber(1);
			}
			
		});
		
		sel2.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				pickNumber(2);
			}
			
		});
		
		sel3.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				pickNumber(3);
			}
			
		});
		
		save.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				SharedPreferences.Editor editor = saved_numbers.edit();
				if(num1.getText().toString()!=""){
					editor.putString("no1", num1.getText().toString());
				}
				if(num2.getText().toString()!=""){
					editor.putString("no2", num2.getText().toString());
				}
				if(num3.getText().toString()!=""){
					editor.putString("no3", num3.getText().toString());
				}
				editor.commit();
				Intent intent = new Intent(getBaseContext(),home.class);
				startActivity(intent);
			}
		});
	}
	
	private void pickNumber(int index){
		Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
		  startActivityForResult(intent, index);
	}
	
	 @Override
	 public void onActivityResult(int reqCode, int resultCode, Intent data) {
	 super.onActivityResult(reqCode, resultCode, data);

	   if (resultCode == Activity.RESULT_OK) {

	     Uri contactData = data.getData();
	     @SuppressWarnings("deprecation")
		Cursor c =  managedQuery(contactData, null, null, null, null);
	     if (c.moveToFirst()) {


	         String id =c.getString(c.getColumnIndexOrThrow(BaseColumns._ID));

	         String hasPhone =c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

	           if (hasPhone.equalsIgnoreCase("1")) {
	          Cursor phones = getContentResolver().query( 
	                       ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null, 
	                       ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ id, 
	                       null, null);
	             phones.moveToFirst();
	              num = phones.getString(phones.getColumnIndex("data1"));
	              if(reqCode == 1){
	            	  num1.setText(num);
	              }
	              else if(reqCode==2){
	            	  num2.setText(num);
	              }
	              else
	            	  num3.setText(num);
	           }
	         String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
	         Toast.makeText(getApplicationContext(), name, Toast.LENGTH_SHORT).show();

	     }
	   }
	 }

}
