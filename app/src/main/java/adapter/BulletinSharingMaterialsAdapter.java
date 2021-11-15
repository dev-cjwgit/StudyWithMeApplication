package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import entity.BulletinSharingMaterialsEntity;
import kr.ac.koreatech.teamproject.R;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class BulletinSharingMaterialsAdapter extends BaseAdapter implements Serializable, Cloneable {
    public ArrayList<BulletinSharingMaterialsEntity> list;

    public BulletinSharingMaterialsAdapter(){
        list = new ArrayList<>();
    }

    public Object clone() {
        BulletinSharingMaterialsAdapter vo = null;
        try{
            vo = (BulletinSharingMaterialsAdapter) super.clone();
        } catch (CloneNotSupportedException e){
            e.printStackTrace();
        }
        return vo;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final Context context = viewGroup.getContext();
        BulletinSharingMaterialsEntity listItem = list.get(i);

        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.controll_bulletin_sharing_materials, viewGroup, false);
        }
        TextView title = view.findViewById(R.id.sharingmat_title_textView);
        TextView name = view.findViewById(R.id.question_name_textView);
        TextView date = view.findViewById(R.id.question_date_textView);

        title.setText(listItem.getTitle());
        name.setText(listItem.getName());

        SimpleDateFormat sDate3 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        date.setText(sDate3.format(listItem.getDate()));

        return view;
    }

    @Override
    public int getCount() { return list.size(); }

    public ArrayList<BulletinSharingMaterialsEntity> getList() { return this.list; }

    @Override
    public Object getItem(int i) { return list.get(i); }

    @Override
    public long getItemId(int i) { return i; }

    public void clear() {
        list.clear();
        notifyDataSetChanged();
    }

    public void append(BulletinSharingMaterialsEntity obj) {
        list.add(obj);
        notifyDataSetChanged();
    }
}
