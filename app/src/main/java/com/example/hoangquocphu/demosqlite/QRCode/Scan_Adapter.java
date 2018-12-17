package com.example.hoangquocphu.demosqlite.QRCode;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hoangquocphu.demosqlite.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Scan_Adapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Scan> scanResult;

    public int stt = -1;

    public Scan_Adapter(Context context, int layout, List<Scan> scanResult) {
        this.context = context;
        this.layout = layout;
        this.scanResult = scanResult;
    }

    @Override
    public int getCount() {
        return scanResult.size();
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
        public TextView tvSTT, tvMaHang, tvIDScan;
    }

    // Xử lý View
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        Scan_Adapter.viewHolder viewHolder = null;

        if (view == null) {

            // Khai báo màn hình
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(layout, null);

            // Ánh xạ đối tượng
            viewHolder = new Scan_Adapter.viewHolder();
            viewHolder.tvSTT = (TextView) view.findViewById(R.id.tvSTT);
            viewHolder.tvMaHang = (TextView) view.findViewById(R.id.tvMaHang);
            viewHolder.tvIDScan = (TextView) view.findViewById(R.id.tvIDScan);

            view.setTag(viewHolder);
            view.setTag(R.id.tvSTT, viewHolder.tvSTT);
            view.setTag(R.id.tvMaHang, viewHolder.tvMaHang);
            view.setTag(R.id.tvIDScan, viewHolder.tvIDScan);
        } else {
            viewHolder = (Scan_Adapter.viewHolder) view.getTag();
        }

        // Gán giá trị
        viewHolder.tvSTT.setText(String.valueOf(scanResult.get(position).getIdScan()));
        viewHolder.tvMaHang.setText(scanResult.get(position).getMaHang());
        viewHolder.tvIDScan.setText(scanResult.get(position).getSoID());
        return view;
    }
}
