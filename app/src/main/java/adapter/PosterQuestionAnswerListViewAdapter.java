package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import entity.PosterQuestionAnswerEntity;
import entity.PosterQuestionEntity;
import kr.ac.koreatech.teamproject.R;

public class PosterQuestionAnswerListViewAdapter extends BaseAdapter implements Serializable, Cloneable {
    public ArrayList<PosterQuestionAnswerEntity> list;

    public PosterQuestionAnswerListViewAdapter() {
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
        PosterQuestionAnswerEntity listItem = list.get(i);

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.controll_question_answer_listview, viewGroup, false);
        }

        TextView title = view.findViewById(R.id.questionAnswer_title_textView);
        TextView name = view.findViewById(R.id.questionAnswer_name_textView);
        TextView date = view.findViewById(R.id.questionAnswer_date_textView);
        TextView body = view.findViewById(R.id.questionAnswer_body_textView);


        title.setText(listItem.getTitle());
        name.setText(listItem.getName());

        SimpleDateFormat sDate2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        date.setText(sDate2.format(listItem.getDate()));
        body.setText(listItem.getBody());

        return view;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    public ArrayList<PosterQuestionAnswerEntity> getList() {
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

    public void append(PosterQuestionAnswerEntity obj) {
        list.add(obj);
        notifyDataSetChanged();
    }

}