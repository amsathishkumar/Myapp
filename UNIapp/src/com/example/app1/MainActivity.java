package com.example.app1;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;




import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app1.R;

public class MainActivity extends ActionBarActivity {

	TextView tt;
	EditText ed0, ed1, ed2;
	Button btsave, btview, btupdate, btdelete, btmail;
	DBhelper db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		db = new DBhelper(this);
		tt = (TextView) findViewById(R.id.textsat);
		ed0 = (EditText) findViewById(R.id.ids);
		ed1 = (EditText) findViewById(R.id.name);
		ed2 = (EditText) findViewById(R.id.age);
		btsave = (Button) findViewById(R.id.save);
		btview = (Button) findViewById(R.id.view);
		btupdate = (Button) findViewById(R.id.btUpdate);
		btdelete = (Button) findViewById(R.id.btDelete);
		btmail = (Button) findViewById(R.id.btmail);
		tt.setText(R.string.sat1);

		clickSave();
		showAll();
		updateValue();
		deleteValue();
		if (android.os.Build.VERSION.SDK_INT > 9) {
		    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		    StrictMode.setThreadPolicy(policy);
		}
		sendmail();

	}

	private void sendmail() {
		btmail.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.v("status", "" + isOnline());
				// SMTP server information
		        String host = "smtp.gmail.com";
		        String port = "587";
		        String mailFrom = "amsathishkumar@gmail.com";
		        String password = "gmailsathish";
		 
		        // outgoing message information
		        String mailTo = "smuniapp@cisco.com";
		        String subject = "Hello my friend";
		 
		        // message contains HTML markups
		        String message = "<i>Greetings!</i><br>";
		        message += "<b>Wish you a nice day!</b><br>";
		        message += "<font color=red>Duke</font>";
		 

		 
		        try {
		           sendHtmlEmail(host, port, mailFrom, password, mailTo,
		                    subject, message);
		            System.out.println("Email sent.");
		        } catch (Exception ex) {
		            System.out.println("Failed to sent email.");
		            ex.printStackTrace();
		        }

			}
		});

	}

	public void sendHtmlEmail(String host, String port,
            final String userName, final String password, String toAddress,
            String subject, String message) throws AddressException,
            MessagingException {
 
        // sets SMTP server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
 
        // creates a new session with an authenticator
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        };
 
        Session session = Session.getInstance(properties, auth);
 
        // creates a new e-mail message
        Message msg = new MimeMessage(session);
 
        msg.setFrom(new InternetAddress(userName));
        InternetAddress[] toAddresses = { new InternetAddress(toAddress) };
        msg.setRecipients(Message.RecipientType.TO, toAddresses);
        msg.setSubject(subject);
        msg.setSentDate(new Date());
        // set plain text message
        msg.setContent(message, "text/html");
 
        // sends the e-mail
        Transport.send(msg);
 
    }
	public boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}
		return false;
	}

	private void deleteValue() {
		btdelete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int s0 = Integer.parseInt(ed0.getText().toString());
				boolean inserted = db.deleteData(s0);
				if (inserted)
					Toast.makeText(MainActivity.this, "Data deleted",
							Toast.LENGTH_LONG).show();
				else
					Toast.makeText(MainActivity.this, "Data not delete",
							Toast.LENGTH_LONG).show();

			}
		});

	}

	private void updateValue() {
		btupdate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int s0 = Integer.parseInt(ed0.getText().toString());
				String s1 = ed1.getText().toString();
				String s2 = ed2.getText().toString();
				boolean inserted = db.updateData(s0, s1, Integer.parseInt(s2));

				if (inserted)
					Toast.makeText(MainActivity.this, "Data Update",
							Toast.LENGTH_LONG).show();
				else
					Toast.makeText(MainActivity.this, "Data not updated",
							Toast.LENGTH_LONG).show();

			}
		});

	}

	private void showAll() {
		// TODO Auto-generated method stub
		btview.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Cursor res = db.getallData();
				StringBuffer sb = new StringBuffer();
				while (res.moveToNext()) {
					sb.append("ID" + res.getInt(0) + "\n");
					sb.append("NAME" + res.getString(1) + "\n");
					sb.append("AGE" + res.getInt(2) + "\n\n");

				}
				showMessage("student", sb.toString());

			}
		});

	}

	public void showMessage(String Title, String msg) {

		AlertDialog.Builder ab = new AlertDialog.Builder(this);
		ab.setTitle(Title);
		ab.setMessage(msg);
		ab.show();

	}

	public void clickSave() {
		btsave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int s0 = Integer.parseInt(ed0.getText().toString());
				String s1 = ed1.getText().toString();
				String s2 = ed2.getText().toString();

				// saveFile("sathish", s1 + "," + s2 + ";");
				boolean inserted = db.insertData(s0, s1, Integer.parseInt(s2));
				if (inserted)
					Toast.makeText(MainActivity.this, "Data inserted",
							Toast.LENGTH_LONG).show();
				else
					Toast.makeText(MainActivity.this, "Data not inserted",
							Toast.LENGTH_LONG).show();

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
