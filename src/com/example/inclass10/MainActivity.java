/**
 * Ajay Vijayakumaran Nair
 * Ayang
 * Nachiket Doke
 */
package com.example.inclass10;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.inclass10.ItemDetailsFragment.ItemDetailsFrListener;
import com.parse.ParseObject;
import com.parse.ParseUser;

public class MainActivity extends Activity implements ToDoFragment.OnFragmentInteractionListener,
		ItemDetailsFrListener, LoginFragment.OnFragmentInteractionListener,
		SignUpFragment.OnFragmentInteractionListener {
	public static final String ITEM_DETAILS_FR = "itemDetailsF";
	public static final String TODO_FR = "todoF";
	public static final String SIGNUP_FR = "signupF";
	public static final String LOGIN_FR = "loginF";
	public static final String SPLASH_FR = "splashF";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		getFragmentManager().beginTransaction().add(R.id.container, new SplashFragment(), SPLASH_FR).commit();

		
		findViewById(R.id.container).postDelayed(new Runnable() {
			
			@Override
			public void run() {
				if (ParseUser.getCurrentUser() == null) {
					getFragmentManager().beginTransaction()
					.replace(R.id.container, new LoginFragment(), LOGIN_FR).commit();
				} else {
					getFragmentManager().beginTransaction()
					.replace(R.id.container, new ToDoFragment(), TODO_FR).commit();
				}
			}
		}, 5000);
		
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
			ToDoFragment tf = (ToDoFragment) getFragmentManager().findFragmentByTag(TODO_FR);
			if (tf != null && tf.isAdded()) {
				tf.showAddItemMenu();
			} else {
				Toast.makeText(this, "This menu is accessible only to ToDoFragment", Toast.LENGTH_LONG).show();
				return true;
			}
			return true;
		} else if (id == R.id.action_logout) {
			ParseUser.logOut();
			getFragmentManager().beginTransaction().replace(R.id.container, new LoginFragment(), LOGIN_FR).commit();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void gotoItemDetailsFragment(ParseObject po) {
		getFragmentManager().beginTransaction().replace(R.id.container, new ItemDetailsFragment(po), ITEM_DETAILS_FR)
				.addToBackStack(null).commit();
	}

	public void replaceItemDetailsFrWithToDo() {
		/*
		 * getFragmentManager().beginTransaction() .replace(R.id.container, new
		 * ToDoFragment(), TODO_FR) .commit();
		 */
		getFragmentManager().popBackStack();
	}

	@Override
	public void gotoSignUpFragment() {
		getFragmentManager().beginTransaction().replace(R.id.container, new SignUpFragment(), SIGNUP_FR).commit();
	}

	@Override
	public void replaceSigUpWithToDo() {
		getFragmentManager().beginTransaction().replace(R.id.container, new ToDoFragment(), TODO_FR).commit();
	}

	@Override
	public void gotoToDoFragment() {
		getFragmentManager().beginTransaction().replace(R.id.container, new ToDoFragment(), TODO_FR).commit();
	}

	@Override
	public void cancelFromSignUpClicked() {
		getFragmentManager().beginTransaction().replace(R.id.container, new LoginFragment(), LOGIN_FR).commit();
		
	}
}
