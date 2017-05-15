package com.mao.guli.Adapter;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mao.guli.Bean.Teacher;
import java.util.List;
import com.loopj.android.image.SmartImageView;
import com.mao.guli.R;

/**
 * Created by mao on 2017/5/12.
 */
public class TeacherAdapter extends BaseAdapter {

    private List<Teacher>teachers;
    private LayoutInflater mInflater=null;

    public TeacherAdapter(List<Teacher> teachers, Context context) {
        this.teachers = teachers;
        mInflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return teachers.size();
    }

    @Override
    public Object getItem(int position) {
        return teachers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        DeviceViewHolder holder=new DeviceViewHolder();
        if(convertView==null){
            convertView=mInflater.inflate(R.layout.item_teacher,parent,false);
            holder.teacherIcon=(SmartImageView)convertView.findViewById(R.id.img_icon);
            holder.teacherName=(TextView)convertView.findViewById(R.id.teacher_name);
            holder.teacherDepartment=(TextView)convertView.findViewById(R.id.teacher_department);
            convertView.setTag(holder);
        }else {
            holder=(DeviceViewHolder)convertView.getTag();
        }
        holder.teacherIcon.setImageUrl(teachers.get(position).getImgUrl());
        holder.teacherName.setText(teachers.get(position).getTeacherName());
        holder.teacherDepartment.setText(teachers.get(position).getTeacherDepartment());

        return convertView;
    }
    class DeviceViewHolder{
        private SmartImageView teacherIcon;
        private TextView teacherName ;
        private TextView teacherDepartment;
    }
}
