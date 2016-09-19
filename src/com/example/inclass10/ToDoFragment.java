/**
 * Ajay Vijayakumaran Nair
 * Ayang
 * Nachiket Doke
 */
package com.example.inclass10;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment
 * must implement the {@link ToDoFragment.OnFragmentInteractionListener}
 * interface to handle interaction events.
 *
 */
public class ToDoFragment extends Fragment {
	private CustomAdapter adapter;
	private OnFragmentInteractionListener mParentActivity;
	private AlertDialog alertDialog;
	private View alertDView;

	public ToDoFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_to_do, container, false);
	}

//	// TODO: Rename method, update argument and hook method into UI event
//	public void onButtonPressed(Uri uri) {
//		if (mListener != null) {
//			mListener.onFragmentInteraction(uri);
//		}
//	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		adapter = new CustomAdapter(getActivity());
		adapter.notifyDataSetChanged();
		ListView listView = (ListView) getView().findViewById(R.id.listView1);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				ParseObject po = adapter.getItem(position);
				mParentActivity.gotoItemDetailsFragment(po);
			}
		});
		super.onViewCreated(view, savedInstanceState);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mParentActivity = (OnFragmentInteractionListener) activity;

		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + " must implement OnFragmentInteractionListener");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mParentActivity = null;
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
		public void gotoItemDetailsFragment(ParseObject po);
	}

	public void showAddItemMenu() {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		final LayoutInflater inflater = getActivity().getLayoutInflater();
		alertDView = inflater.inflate(R.layout.custom_alert, null);
		builder.setView(alertDView).setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				String todoItem = ((EditText) alertDView.findViewById(R.id.editTextAD)).getText().toString();
				if (todoItem == null || todoItem.isEmpty()) {
					Toast.makeText(getActivity(), "Empty text not allowed", Toast.LENGTH_LONG).show();
					return;
				}
				ParseObject toDoObject = new ParseObject("ToDo");
				toDoObject.put("text", todoItem);
				toDoObject.saveInBackground(new SaveCallback() {

					@Override
					public void done(ParseException arg0) {
						adapter.loadObjects();
						Toast.makeText(getActivity(), "Text is saved", Toast.LENGTH_LONG).show();
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
	}
}
