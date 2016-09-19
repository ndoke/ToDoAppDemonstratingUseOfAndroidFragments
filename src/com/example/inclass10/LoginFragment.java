/**
 * Ajay Vijayakumaran Nair
 * Ayang
 * Nachiket Doke
 */
package com.example.inclass10;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment
 * must implement the {@link LoginFragment.OnFragmentInteractionListener}
 * interface to handle interaction events.
 *
 */
public class LoginFragment extends Fragment {

	private OnFragmentInteractionListener mListener;

	public LoginFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		final View view = inflater.inflate(R.layout.fragment_login, container, false);
		view.findViewById(R.id.buttonLogin).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String email = ((EditText) view.findViewById(R.id.editTextEmail)).getText().toString();
				String pass = ((EditText) view.findViewById(R.id.editTextPassword)).getText().toString();
				if (email == null || email.isEmpty() || pass ==null || pass.isEmpty()) {
					Toast.makeText(getActivity(), "Mandatory login fields cant be empty", Toast.LENGTH_LONG).show();
					return;

				}
				logUserIn(email, pass);
				
			}
		});
		view.findViewById(R.id.buttonCreateNewAccount).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mListener.gotoSignUpFragment();
				
			}
		});
		return view;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mListener = (OnFragmentInteractionListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + " must implement OnFragmentInteractionListener");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}

	/**
	 * This interface must be implemented by activities that contain this
	 * fragment to allow an interaction in this fragment to be communicated to
	 * the activity and potentially other fragments contained in that activity.
	 * <p>
	 * See the Android Training lesson <a href=
	 * "http://developer.android.com/training/basics/fragments/communicating.html"
	 * >Communicating with Other Fragments</a> for more information.
	 */
	public interface OnFragmentInteractionListener {
		// TODO: Update argument type and name
		public void gotoSignUpFragment();
		public void gotoToDoFragment();
	}
	private void logUserIn(String email, String pass) {
		ParseUser.logInInBackground(email, pass, new LogInCallback() {
			public void done(ParseUser user, ParseException e) {
				if (user != null) {
					Log.d(LoginActivity.LOGGING_KEY, "User logged in");
					mListener.gotoToDoFragment();
				} else {
					Log.d(LoginActivity.LOGGING_KEY, "User couldnt b logged in");
					if (e.getCode() == ParseException.OBJECT_NOT_FOUND) {
						Toast.makeText(getActivity(), "Dint find given user in server", Toast.LENGTH_LONG).show();
					}
				}
			}
		});
	}

}
