/**
 * Ajay Vijayakumaran Nair
 * Ayang
 * Nachiket Doke
 */
package com.example.inclass10;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment
 * must implement the {@link SignUpFragment.OnFragmentInteractionListener}
 * interface to handle interaction events.
 *
 */
public class SignUpFragment extends Fragment {

	private OnFragmentInteractionListener mListener;
	EditText edName, edEmail, edPwrd, edRpwrd;
	String uName, uEmail, UPwrd, URpwrd;

	public SignUpFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		final View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
		view.findViewById(R.id.buttonSignup).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				edName = (EditText) view.findViewById(R.id.editTextUserName);
				edEmail = (EditText) view.findViewById(R.id.editTextEmail);
				edPwrd = (EditText) view.findViewById(R.id.editTextPassword);
				edRpwrd = (EditText) view.findViewById(R.id.editTextPasswordConfirm);

				uName = edName.getText().toString();
				uEmail = edEmail.getText().toString();
				UPwrd = edPwrd.getText().toString();
				URpwrd = edRpwrd.getText().toString();

				if (!UPwrd.equals(URpwrd)) {
					Toast.makeText(getActivity(), "Passwords don't match!!", Toast.LENGTH_LONG).show();
					return;
				}
				if (uName.isEmpty() || uEmail.isEmpty() || UPwrd.isEmpty() || URpwrd.isEmpty()) {
					Toast.makeText(getActivity(), "Fill all the fileds!!", Toast.LENGTH_LONG).show();
					return;
				} else {

					ParseUser user = new ParseUser();
					user.setUsername(uEmail);
					user.setEmail(uEmail);
					user.setPassword(UPwrd);
					user.put("name", uName);

					user.signUpInBackground(new SignUpCallback() {

						@SuppressLint("ShowToast")
						@Override
						public void done(ParseException e) {
							if (e == null) {
								Toast.makeText(getActivity(), "Account creation successful!!", Toast.LENGTH_LONG)
										.show();
								mListener.replaceSigUpWithToDo();
							} else {
								if (e.getCode() == ParseException.USERNAME_TAKEN)
									Toast.makeText(getActivity(), "Email already in use!!", Toast.LENGTH_LONG).show();
							}
						}
					});

				}

			}
		});
		view.findViewById(R.id.buttonCancel).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mListener.cancelFromSignUpClicked();

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
		public void replaceSigUpWithToDo();
		public void cancelFromSignUpClicked();
	}

}
