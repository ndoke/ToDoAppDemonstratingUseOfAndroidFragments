/**
 * Ajay Vijayakumaran Nair
 * Ayang
 * Nachiket Doke
 */
package com.example.inclass10;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

public class CustomAdapter extends ParseQueryAdapter<ParseObject> {

	public CustomAdapter(Context context) {
		super(context, new ParseQueryAdapter.QueryFactory<ParseObject>() {

			@Override
			public ParseQuery<ParseObject> create() {
				ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("ToDo");
				query.orderByAscending("createdAt");
				return query;
			}
		});
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getItemView(ParseObject object, View v, ViewGroup parent) {
		if (v == null) {
		    v = View.inflate(getContext(), R.layout.list_item_view, null);
		  }
		 super.getItemView(object, v, parent);
		 ((TextView)v.findViewById(R.id.textViewLv)).setText(object.getString("text"));
		 return v;
	}

}
