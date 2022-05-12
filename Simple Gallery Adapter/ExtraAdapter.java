package shivraman.story.saver;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Div on 24/6/19.
 */


public class ExtraAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<String> image;
    LayoutInflater inflater = null;

    public ExtraAdapter(Context context, ArrayList<String> myimage) {
        this.mContext = context;
        this.image = myimage;
        inflater = LayoutInflater.from(mContext);
    }

    public int getCount() {
        return image.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
       
            convertView = inflater.inflate(R.layout.extra_adapter, null);

        TextView t = (TextView) convertView.findViewById(R.id.text);

        t.setText(image.get(position));

        return convertView;
    }

    public void SetUIRelative(View mview, int WIDTH, int HEIGHT) {
        RelativeLayout.LayoutParams layoutParamsi = new RelativeLayout.LayoutParams(
                mContext.getResources().getDisplayMetrics().widthPixels * WIDTH / 1080,
                mContext.getResources().getDisplayMetrics().widthPixels * HEIGHT / 1080);

        mview.setLayoutParams(layoutParamsi);
    }

}


