package com.ptzlabs.wc;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ObjectAdapter<String> extends ArrayAdapter<String> {

	private ArrayList<String> titles;
	private ArrayList<JSONObject> objects;

	private Context context;
	private int rowlayout;
	
	static final int color = Color.argb(50,0,0,50);

	/* here we must override the constructor for ArrayAdapter
	 * the only variable we care about now is ArrayList<Item> objects,
	 * because it is the list of objects we want to display.
	 */
	public ObjectAdapter(Context context, int textViewResourceId, ArrayList<JSONObject> objects,
			ArrayList<String> titles) {
		super(context, textViewResourceId, titles);
		this.objects = objects;
		this.titles = titles;
		this.context = context;
		this.rowlayout = textViewResourceId;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		/* Log.d("madeit", "attempting to print");
		if (convertView != null)  {

			if (convertView instanceof TextView) {
				((TextView) convertView).setText("blargl");
			}
			convertView.setTag("hi");
			Log.d("madeit2", "attempting to print");
		}
 */
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(this.rowlayout, parent, false);
		//TextView textView = (TextView) rowView.;

		/* // ImageView imageView = (ImageView) rowView.findViewById(R.id.icon); */
		if (rowView != null) {
			JSONObject o = getFreeObject();
			try {
				((TextView) rowView).setText((CharSequence) o.get("name"));
				rowView.setTag(o);

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		rowView.setBackgroundColor(color);
		return rowView;
	}
	
	private JSONObject getFreeObject() {
		if (this.objects != null) {
			JSONObject o = objects.get(0);
			objects.remove(o);
			return o;
		}
		return null;
	}

}
