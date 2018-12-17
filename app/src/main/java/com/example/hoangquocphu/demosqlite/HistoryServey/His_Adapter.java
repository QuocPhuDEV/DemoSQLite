package com.example.hoangquocphu.demosqlite.HistoryServey;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hoangquocphu.demosqlite.Answer.An_DBHelper;
import com.example.hoangquocphu.demosqlite.Answer.Answer;
import com.example.hoangquocphu.demosqlite.R;

import java.util.List;

public class His_Adapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Answer> answerList;

    public His_Adapter(Context context, int layout, List<Answer> answerList) {
        this.context = context;
        this.layout = layout;
        this.answerList = answerList;
    }

    @Override
    public int getCount() {
        return this.answerList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    // Khai báo đối tượng của layout item_customer
    static class viewHolder {
        public TextView tvFullName, tvServeyTime;
        public ImageView imgStt;
    }

    // Xử lý View
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        His_Adapter.viewHolder viewHolder = null;
        if (view == null) {

            // Khai báo màn hình
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(layout, null);

            // Ánh xạ đối tượng
            viewHolder = new His_Adapter.viewHolder();
            viewHolder.tvFullName = (TextView) view.findViewById(R.id.tvCustomerHistory);
            viewHolder.tvServeyTime = (TextView) view.findViewById(R.id.tvServeyTime);
            viewHolder.imgStt = (ImageView) view.findViewById(R.id.imgStatus);

            view.setTag(viewHolder);
            view.setTag(R.id.tvCustomerHistory, viewHolder.tvFullName);
            view.setTag(R.id.tvServeyTime, viewHolder.tvServeyTime);
            view.setTag(R.id.imgCustomerHistory, viewHolder.imgStt);
        } else {
            viewHolder = (His_Adapter.viewHolder) view.getTag();
        }

        // Gán giá trị cho các edittext
        An_DBHelper an_dbHelper = new An_DBHelper(context);

        // Kiểm tra trạng thái hoàn thành câu hỏi
        int answerTotal = answerList.get(position).getAn_TotalAnswer();
        if (answerTotal < 10) {
            viewHolder.imgStt.setImageResource(R.drawable.ng);
        } else {
            viewHolder.imgStt.setImageResource(R.drawable.ok);
        }
        viewHolder.tvFullName.setText(answerList.get(position).getAn_Customer());
        viewHolder.tvServeyTime.setText(answerList.get(position).getAn_AnswerTime());
        return view;
    }
}
