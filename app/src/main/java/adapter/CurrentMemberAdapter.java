package adapter;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

import entity.CurrentMemberEntity;
import entity.PosterEntity;
import kr.ac.koreatech.teamproject.R;

public class CurrentMemberAdapter extends BaseAdapter implements Serializable, Cloneable {
    public ArrayList<CurrentMemberEntity> list;

    public CurrentMemberAdapter(){
        list=new ArrayList<>();
    }

    public Object clone(){
        CurrentMemberAdapter vo = null;
        try{
            vo=(CurrentMemberAdapter) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return vo;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup){
        final Context context = viewGroup.getContext();
        CurrentMemberEntity listItem = list.get(i);

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.controll_current_member, viewGroup, false);
        }
        TextView member = view.findViewById(R.id.cur_mem_name);

        member.setText(listItem.getMember());

        return view;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void clear() {
        list.clear();
        notifyDataSetChanged();
    }

    public void append(CurrentMemberEntity obj) {
        list.add(obj);
        notifyDataSetChanged();
    }

}
