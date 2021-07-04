package com.example.test1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class PayAdapter extends BaseAdapter {

    ArrayList<WorkList> list = new ArrayList<>();

    public PayAdapter(){

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
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.pay_list, parent, false);
        }

        TextView payText = (TextView) convertView.findViewById(R.id.payTextView);

        WorkList clist = list.get(position);

        payText.setText(clist.getwDate());

        return convertView;
    }

    public void addItemToPay(String date){
        WorkList plist = new WorkList();

        plist.setwDate(date);

        list.add(plist);

    }

}
