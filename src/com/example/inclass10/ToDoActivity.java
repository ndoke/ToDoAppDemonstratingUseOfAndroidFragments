/**
 * Ajay Vijayakumaran Nair
 * Ayang
 * Nachiket Doke
 */
package com.example.inclass10;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class ToDoActivity extends Activity {

	private AlertDialog alertDialog;
	private View alertDView;
	private CustomAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_to_do);
		adapter = new CustomAdapter(this);
		adapter.notifyDataSetChanged();
		ListView listView = (ListView) findViewById(R.id.listView1);
		listView.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.to_do, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_add) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			final LayoutInflater inflater = this.getLayoutInflater();
			alertDView = inflater.inflate(R.layout.custom_alert, null);
			builder.setView(alertDView).setPositiveButton("OK", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int id) {
					String todoItem = ((EditText) alertDView.findViewById(R.id.editTextAD)).getText().toString();
					if (todoItem == null || todoItem.isEmpty()) {
						Toast.makeText(ToDoActivity.this, "Empty text not allowed", Toast.LENGTH_LONG).show();
						return;
					}
					ParseObject toDoObject = new ParseObject("ToDo");
					toDoObject.put("text", todoItem);
					toDoObject.saveInBackground(new SaveCallback() {

						@Override
						public void done(ParseException arg0) {
							adapter.loadObjects();
							Toast.makeText(ToDoActivity.this, "Text is saved", Toast.LENGTH_LONG).show();
						}
					});
				}
			}).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					dialog.cancel();
				}
			}).setTitle("Add an item");
			alertDialog = builder.create();
			alertDialog.show();
			return true;
		} else if (id == R.id.action_logout) {
			ParseUser.logOut();
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
			finish();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}
