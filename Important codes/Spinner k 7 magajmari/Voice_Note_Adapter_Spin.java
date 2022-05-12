package cm.voice.note;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


	
public class Voice_Note_Adapter_Spin extends BaseAdapter {

	private Context context;
	private ArrayList<String> data;
	private String selectedItemColor;
	private String itemColor;

	public Voice_Note_Adapter_Spin(Context context, ArrayList<String> data, String selectedItemColor, String itemColor) {
	    this.context = context;
	    this.data = data;
	    this.selectedItemColor = selectedItemColor;
	    this.itemColor = itemColor;
	}

	public Voice_Note_Adapter_Spin(Context context, ArrayList<String> data, String label, String selectedItemColor, String itemColor) {
	    this.context = context;
	    this.data = data;
	    this.data = new ArrayList<String>(data.size()+1);//new String[data.length + 1];
	    this.data.add(0, label);
	    this.data.remove(1);
	    for (int i = 1; i <= data.size(); i++) {
	    	this.data.add(i, data.get(i-1));
	    	this.data.remove(i+1);
//		        this.data[i] = data[i - 1];
	    }
	    this.selectedItemColor = selectedItemColor;
	    this.itemColor = itemColor;
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
	    ViewHolder holder;
	    if (convertView == null) {
	        holder = new ViewHolder();
	        convertView = LayoutInflater.from(context).inflate(R.layout.voice_note_spinner_selected_item, parent, false);
	        holder.txtSpinnerSelected = (TextView) convertView.findViewById(R.id.txtSpinnerItem);
	        convertView.setTag(holder);
	    } else {
	        holder = (ViewHolder) convertView.getTag();
	    }
	    holder.txtSpinnerSelected.setTextColor(Color.parseColor(itemColor));
	    holder.txtSpinnerSelected.setText(data.get(position));
	    return convertView;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	    ViewHolder holder;
	    if (convertView == null) {
	        holder = new ViewHolder();
	        convertView = LayoutInflater.from(context).inflate(R.layout.voice_note_spinner_drop_down_item, parent, false);
	        holder.txtSpinnerItem = (TextView) convertView.findViewById(R.id.txtSpinnerItem);
	        convertView.setTag(holder);
	    } else {
	        holder = (ViewHolder) convertView.getTag();
	    }
	    holder.txtSpinnerItem.setTextColor(Color.parseColor(selectedItemColor));
	    holder.txtSpinnerItem.setText(data.get(position));
	    return convertView;
	}

	@Override
	public int getCount() {
	    return data.size();
	}

	@Override
	public Object getItem(int position) {
	    return data.get(position);
	}

	@Override
	public long getItemId(int position) {
	    return position;
	}

	static class ViewHolder {
	    TextView txtSpinnerSelected;
	    TextView txtSpinnerItem;
	}
}
