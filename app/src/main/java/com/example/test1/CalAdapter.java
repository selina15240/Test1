package com.example.test1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CalAdapter extends BaseAdapter {

    ArrayList<DailyList> list = new ArrayList<DailyList>();

    public CalAdapter(){

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Context context = parent.getContext();

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.cal_list, parent, false);
        }

        TextView txtText = (TextView) convertView.findViewById(R.id.txtTextView);
        TextView timeText = (TextView) convertView.findViewById(R.id.timeTextView);

        DailyList clist = list.get(position);

        txtText.setText("\t"+clist.getsText());
        timeText.setText("\t\t\t"+clist.getsStartTime()+" ~ "+clist.getsEndTime());

        return convertView;
    }

    public void addItemToList(String text, String startTime, String endTime){
        DailyList clist = new DailyList();

        clist.setsText(text);
        clist.setsStartTime(startTime);
        clist.setsEndTime(endTime);

        list.add(clist);

    }
}
