package com.example.test1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class DepartmentAdapter extends BaseAdapter {
    ArrayList<MemberList> list = new ArrayList<>();

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
            convertView = inflater.inflate(R.layout.department_list, parent, false);
        }

        TextView timeText = (TextView) convertView.findViewById(R.id.departmentTextView);

        MemberList memberList = list.get(position);

        timeText.setText("\t\t\t"+memberList.getposition()+"\t\t\t\t"+memberList.getname()+"\t\t\t\t\t\t"+memberList.getusertel()+"\t\t\t\t\t\t"+memberList.getemail());

        return convertView;
    }

    public void addItemList(String po, String na, String tel, String em){
        MemberList memberList = new MemberList();

        memberList.setposition(po);
        memberList.setname(na);
        memberList.setusertel(tel);
        memberList.setemail(em);

        list.add(memberList);

    }
}
