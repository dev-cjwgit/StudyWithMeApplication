package adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import entity.AnssharingEntity;
import kr.ac.koreatech.teamproject.R;

public class AnsbulletSharingMatAdapter extends BaseAdapter implements Serializable, Cloneable {
    public ArrayList<AnssharingEntity> list;

    public AnsbulletSharingMatAdapter() { list = new ArrayList<>();}

    public Object clone(){
        BulletinSharingMaterialsAdapter vo = null;
        try {
            vo = (BulletinSharingMaterialsAdapter) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return vo;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final Context context = viewGroup.getContext();
        AnssharingEntity listItem = list.get(i);

        if(view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.control_anssharing_listview, viewGroup, false);

        }

        TextView title = view.findViewById(R.id.anssharing_anstitle_TextView);
        TextView name = view.findViewById(R.id.anssharing_ansname_TextView);
        TextView date = view.findViewById(R.id.anssharing_ansdate_TextView);
        TextView body = view.findViewById(R.id.anssharing_ansbody_TextView);

        title.setText(listItem.getTitle());
        name.setText(listItem.getName());

        SimpleDateFormat sDate4 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        date.setText(sDate4.format(listItem.getDate()));
        body.setText(listItem.getBody());

        return view;
    }

    @Override
    public int getCount() { return list.size(); }

    public ArrayList<AnssharingEntity> getList() { return this.list; }

    @Override
    public Object getItem(int i) { return list.get(i); }

    @Override
    public long getItemId(int i) { return i; }

    public void clear() {
        list.clear();
        notifyDataSetChanged();
    }

    public void append(AnssharingEntity obj) {
        list.add(obj);
        notifyDataSetChanged();
    }
}
