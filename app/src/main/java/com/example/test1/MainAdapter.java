package com.example.test1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MainAdapter extends BaseAdapter {
    ArrayList<WorkList> list = new ArrayList<>();

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
            convertView = inflater.inflate(R.layout.main_list, parent, false);
        }

        TextView timeText = (TextView) convertView.findViewById(R.id.timeTextView);

        WorkList workList = list.get(position);

        timeText.setText(workList.getwGoTime()+" ~ "+workList.getwOffTime());

        return convertView;
    }

    public void addItemList(String goTime, String offTime){
        WorkList workList = new WorkList();

        workList.setwGoTime(goTime);
        workList.setwOffTime(offTime);

        list.add(workList);

    }
}
