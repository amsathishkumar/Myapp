package com.example.app1;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.app1.R;

public class MainActivity extends ActionBarActivity {

    TextView tt;
	EditText ed1,ed2 ;
	Button btsave,btsavetoexcel;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tt = (TextView) findViewById(R.id.textsat);
		ed1 = (EditText) findViewById(R.id.name);
		ed2 = (EditText) findViewById(R.id.age);
		 btsave = (Button) findViewById(R.id.save);
		 btsavetoexcel = (Button) findViewById(R.id.savetoexcel);		 
		tt.setText(R.string.sat1);
		clickSave();
		clickSaveToExcel();

	}

	private void clickSaveToExcel() {
		btsavetoexcel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				tt.setText(readFile("sathish"));
				
			}
		});
		
	}

	public void clickSave() {
		btsave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
//				ed2.setFocusable(false);
//				ed1.setFocusable(false);
//
//				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//				imm.hideSoftInputFromWindow(ed2.getWindowToken(), 0);
//
//				btsave.setFocusable(true);
				String s1 = "Name: " + ed1.getText().toString();
				String s2 = "Age: " + ed2.getText().toString();

				saveFile("sathish", s1 + "," + s2 + ";");
				

			}
		});
	}

	public void saveFile(String fileName, String ss) {
		try {
			
			FileOutputStream fos = openFileOutput(fileName,
					Context.MODE_PRIVATE);

			fos.write(ss.getBytes());
			fos.close();
			System.out.println("sathish");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String readFile(String fileName) {
		int c;
		String temp = "";
		try {
			FileInputStream fin = openFileInput("sathish");
			while ((c = fin.read()) != -1) {
				temp = temp + Character.toString((char) c);
			}
			fin.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return temp;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
