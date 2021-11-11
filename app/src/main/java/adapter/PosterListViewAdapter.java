package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

import entity.PosterEntity;
import kr.ac.koreatech.teamproject.R;

public class PosterListViewAdapter extends BaseAdapter implements Serializable, Cloneable {
    public ArrayList<PosterEntity> list;

    public PosterListViewAdapter() {
        list = new ArrayList<>();
    }


    public Object clone() {
        PosterListViewAdapter vo = null;
        try {
            vo = (PosterListViewAdapter) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return vo;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final Context context = viewGroup.getContext();
        PosterEntity listItem = list.get(i);

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.controll_listview_poster, viewGroup, false);
        }
        ImageView bitmap = view.findViewById(R.id.poster_listview_image);
        TextView title = view.findViewById(R.id.poster_listview_title);
        TextView profName = view.findViewById(R.id.poster_listview_profname);
        TextView currPeople = view.findViewById(R.id.poster_listview_people);
        TextView introduce = view.findViewById(R.id.poster_listview_introduce);

        bitmap.setImageBitmap(listItem.getBitmap());
        title.setText(listItem.getTitle());
        profName.setText(listItem.getProfName());
        currPeople.setText(listItem.getCurrPeople() + "명");
        introduce.setText(listItem.getIntroduce());

        return view;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    public ArrayList<PosterEntity> getList() {
        return this.list;
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

    public void append(PosterEntity obj) {
        list.add(obj);
        notifyDataSetChanged();
    }

}