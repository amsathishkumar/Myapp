package com.sat.info;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sat.db.DBhelper;
import com.sat.mail.GMailhelper;
import com.sat.rest.ResHelper;

public class MainActivity extends Activity {

	// public static final String MyPREFERENCES = "satPrefs";

	TextView tt;
	EditText ed0, ed1, ed2, edmail;
	Button btsave, btview, btupdate, btdelete, btmail, btclear;
	DBhelper db;

	SharedPreferences sharedpreferences;

	public static final String Id = "idKey";
	public static final String Name = "nameKey";
	public static final String Age = "ageKey";
	public static final String Email = "emailKey";
	public static int numMessages = 0;
	public static int notificationID = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		// for gmail
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}

		db = new DBhelper(this);

		tt = (TextView) findViewById(R.id.textsat);
		ed0 = (EditText) findViewById(R.id.ids);
		ed1 = (EditText) findViewById(R.id.name);
		ed2 = (EditText) findViewById(R.id.age);
		edmail = (EditText) findViewById(R.id.editrmail);

		btsave = (Button) findViewById(R.id.btsave);
		btview = (Button) findViewById(R.id.btview);
		btupdate = (Button) findViewById(R.id.btUpdate);
		btdelete = (Button) findViewById(R.id.btDelete);
		btmail = (Button) findViewById(R.id.btmail);
		btclear = (Button) findViewById(R.id.btclear);

		sharedpreferences = getSharedPreferences(
				getResources().getString(R.string.perf_name_shared),
				Context.MODE_PRIVATE);
		if (sharedpreferences.getAll().size() > 0) {
			try {
				tt.setText("Sat Prefered"
						+ sharedpreferences.getAll().toString());

				ed1.setText(sharedpreferences.getString(Name, null));
				ed0.setText(String.valueOf(sharedpreferences.getInt(Id, 0)));

				ed2.setText(String.valueOf(sharedpreferences.getInt(Age, 0)));
				edmail.setText(sharedpreferences.getString(Email, null));
			} catch (Exception e) {
				Log.v("shared", "error");
			}

		} else
			tt.setText(R.string.sat1);

		clickSave();
		showAll();
		updateValue();
		deleteValue();
		clearAll();
		sendmail();
	}

	private void clearAll() {
		btclear.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				clear();

			}
		});

	}

	private void clear() {
		ed0.setText("");
		ed1.setText("");
		ed2.setText("");
		edmail.setText("");
	}

	private void sendmail() {
		btmail.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent in = new Intent(MainActivity.this, SatActivity.class);

				startActivity(in);
				Log.v("status", "" + isOnline());

				// outgoing message information
				// String mailTo = edmail.getText().toString();
				String mailTo = sharedpreferences.getString(Email, null);

				String subject = "Hello my friend";

				// message contains HTML markups
				String message = "<i>Greetings!</i><br>";
				message += "<b>Wish you a nice day!</b><br>";
				message += "<font color=red>From Sathish Kumar</font>";

				try {
					GMailhelper gh = new GMailhelper();
					gh.sendHtmlEmail(getResources().getString(R.string.mailto),
							getResources().getString(R.string.mailtopwd),
							mailTo, subject, message);
					System.out.println("Email sent.");
				} catch (Exception ex) {
					System.out.println("Failed to sent email.");
					ex.printStackTrace();
				}

			}
		});

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
				String s3 = edmail.getText().toString();

				boolean inserted = db.updateData(s0, s1, Integer.parseInt(s2),
						s3);

				if (inserted) {
					Toast.makeText(MainActivity.this, "Data Update",
							Toast.LENGTH_LONG).show();
					//Notify("You've received new message", s3);
					displayNotification();
				} else
					Toast.makeText(MainActivity.this, "Data not updated",
							Toast.LENGTH_LONG).show();

			}
		});

	}

	private void Notify(String notificationTitle, String notificationMessage) {
		NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		@SuppressWarnings("deprecation")
		Notification notification = new Notification(R.drawable.ic_launcher,
				"New Message", System.currentTimeMillis());
		Intent notificationIntent = new Intent(this, NotificationView.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
				notificationIntent, 0);

		notification.setLatestEventInfo(MainActivity.this, notificationTitle,
				notificationMessage, pendingIntent);
		notificationManager.notify(9999, notification);
	}

	protected void displayNotification() {
		Log.i("Start", "notification");

		/* Invoking the default notification service */
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				this);

		mBuilder.setContentTitle("New Message");
		mBuilder.setContentText("You've received new message."+notificationID);
		mBuilder.setTicker("New Message Alert!");
		mBuilder.setSmallIcon(R.drawable.ic_launcher);

		/* Increase notification number every time a new notification arrives */
		mBuilder.setNumber(++numMessages);

		/* Add Big View Specific Configuration */
		NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();

		String[] events = new String[6];
		events[0] = new String("This is first line....");
		events[1] = new String("This is second line...");
		events[2] = new String("This is third line...");
		events[3] = new String("This is 4th line...");
		events[4] = new String("This is 5th line...");
		events[5] = new String("This is 6th line...");

		// Sets a title for the Inbox style big view
		inboxStyle.setBigContentTitle("Big Title Details:");

		// Moves events into the big view
		for (int i = 0; i < events.length; i++) {
			inboxStyle.addLine(events[i]);
		}

		mBuilder.setStyle(inboxStyle);

		/* Creates an explicit intent for an Activity in your app */
		Intent resultIntent = new Intent(this, NotificationView.class);

		TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
		stackBuilder.addParentStack(NotificationView.class);

		/* Adds the Intent that starts the Activity to the top of the stack */
		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,
				PendingIntent.FLAG_UPDATE_CURRENT);
		NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

		mBuilder.setContentIntent(resultPendingIntent);
		mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

		/* notificationID allows you to update the notification later on. */
		mNotificationManager.notify(notificationID, mBuilder.build());
	}

	private void showAll() {
		btview.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				// Cursor res = db.getallData();
				// StringBuffer sb = new StringBuffer();
				// while (res.moveToNext()) {
				// sb.append("ID   :" + res.getInt(0) + "\n");
				// sb.append("NAME :" + res.getString(1) + "\n");
				// sb.append("AGE  :" + res.getInt(2) + "\n\n");
				// sb.append("EMAIL:" + res.getString(3) + "\n\n");
				//
				// }
				// showMessage("student", sb.toString());
				ResHelper reshelp = new ResHelper();
				LinkedHashMap<String, String> vals = new LinkedHashMap<String, String>();
				vals = reshelp.satjson1();

				System.out.println("satjson" + vals);
				ed0.setText(String.valueOf(vals.get("id")));
				ed1.setText(vals.get("name"));
				ed2.setText(String.valueOf(vals.get("age")));
				edmail.setText(vals.get("mail"));

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
				String s3 = edmail.getText().toString();
				// saveFile("sathish", s1 + "," + s2 + ";");
				boolean inserted = db.insertData(s0, s1, Integer.parseInt(s2),
						s3);

				SharedPreferences.Editor editor = sharedpreferences.edit();

				editor.putInt(Id, s0);
				editor.putString(Name, s1);
				editor.putInt(Age, Integer.parseInt(s2));
				editor.putString(Email, s3);
				editor.commit();
				Toast.makeText(MainActivity.this, "Thanks", Toast.LENGTH_LONG)
						.show();

				if (inserted) {
					Toast.makeText(MainActivity.this, "Data inserted",
							Toast.LENGTH_LONG).show();
					// clearAll();
				} else
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
