/**
 * Ajay Vijayakumaran Nair
 * Ayang
 * Nachiket Doke
 */
package com.example.inclass10;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.DeleteCallback;
import com.parse.ParseException;
import com.parse.ParseObject;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class ItemDetailsFragment extends Fragment {
	ItemDetailsFrListener mParentActivity;
	ParseObject object;

	public ItemDetailsFragment(ParseObject po) {
		this.object = po;
	}

	public ItemDetailsFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_item_details, container, false);
		view.findViewById(R.id.buttonDelete).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				object.deleteInBackground(new DeleteCallback() {

					@Override
					public void done(ParseException arg0) {
						mParentActivity.replaceItemDetailsFrWithToDo();
					}
				});

			}
		});
		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		TextView tv = (TextView) getActivity().findViewById(R.id.textViewToDoText);
		tv.setText(object.getString("text"));
		super.onViewCreated(view, savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onAttach(Activity activity) {
		try {
			mParentActivity = (ItemDetailsFrListener) activity;

		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + " must implement OnFragmentInteractionListener");
		}
		super.onAttach(activity);
	}

	interface ItemDetailsFrListener {
		public void replaceItemDetailsFrWithToDo();
	}
}
