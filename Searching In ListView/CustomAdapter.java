package com.example.instantchatforwp;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class CustomAdapter extends BaseAdapter {
    Context context;
    ArrayList<CountryModel> mydata;
    String[] countryNames, names;
    LayoutInflater inflter;
    //    private CustomAdapter.ItemFilter mFilter = new CustomAdapter.ItemFilter();
    ArrayList<CountryModel> filtydata0;

    public CustomAdapter(Context applicationContext, ArrayList<CountryModel> mydatas, String[] names) {
        this.context = applicationContext;
        this.mydata = mydatas;
//        this.countryNames = countryNames;
        this.names = names;
        this.filtydata0 = new ArrayList<CountryModel>();
        this.filtydata0.addAll(mydata);
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return mydata.size();
    }

    @Override
    public CountryModel getItem(int i) {
        return mydata.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Nullable
    @Override
    public CharSequence[] getAutofillOptions() {
        return new CharSequence[0];
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.custom_spinner_items, null);
        ImageView icon = (ImageView) view.findViewById(R.id.imageView);
        ImageView flagb1 = (ImageView) view.findViewById(R.id.flagb1);
        TextView C_code = (TextView) view.findViewById(R.id.textView);
        TextView cname = (TextView) view.findViewById(R.id.name);
        LinearLayout items = (LinearLayout) view.findViewById(R.id.items);
        RelativeLayout.LayoutParams layoutP4 = new RelativeLayout.LayoutParams(
                context.getResources().getDisplayMetrics().widthPixels * 106 / 1080,
                context.getResources().getDisplayMetrics().heightPixels * 76 / 1920);

        icon.setLayoutParams(layoutP4);
        flagb1.setLayoutParams(layoutP4);

        LinearLayout.LayoutParams layoutP5 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                context.getResources().getDisplayMetrics().heightPixels * 120 / 1920);

        items.setLayoutParams(layoutP5);
        CountryModel cm = mydata.get(i);

        icon.setImageResource(cm.getM_flag());
        C_code.setText("+" + cm.getM_code());
        cname.setText(cm.getM_name());

        return view;
    }


    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        mydata.clear();
        if (charText.length() == 0) {
            mydata.addAll(filtydata0);
        } else {
            for (CountryModel wp : filtydata0) {
                if (wp.getM_name().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    mydata.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

}