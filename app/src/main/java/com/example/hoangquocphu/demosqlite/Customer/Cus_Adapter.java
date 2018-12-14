package com.example.hoangquocphu.demosqlite.Customer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.hoangquocphu.demosqlite.R;

import java.util.List;

public class Cus_Adapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Customer> customerList;

    public Cus_Adapter(Context context, int layout, List<Customer> customerList) {
        this.context = context;
        this.layout = layout;
        this.customerList = customerList;
    }

    @Override
    public int getCount() {
        return this.customerList.size();
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
        public TextView tvFullName, tvAddress;
    }

    // Xử lý View
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        Cus_Adapter.viewHolder viewHolder = null;
        if (view == null) {

            // Khai báo màn hình
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(layout, null);

            // Ánh xạ đối tượng
            viewHolder = new Cus_Adapter.viewHolder();
            viewHolder.tvFullName = (TextView) view.findViewById(R.id.tvCustomer);
            viewHolder.tvAddress = (TextView) view.findViewById(R.id.tvAddress);


            view.setTag(viewHolder);
            view.setTag(R.id.tvCustomer, viewHolder.tvFullName);
            view.setTag(R.id.tvAddress, viewHolder.tvAddress);
        } else {
            viewHolder = (Cus_Adapter.viewHolder) view.getTag();
        }

        // Gán giá trị cho các edittext
        viewHolder.tvFullName.setText(customerList.get(position).getFullName());
        viewHolder.tvAddress.setText(customerList.get(position).getAddress());
        return view;
    }
}
