package com.agaldanaw.reto9.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.agaldanaw.reto9.CreateCompanyActivity;
import com.agaldanaw.reto9.R;
import com.agaldanaw.reto9.model.Company;
import com.agaldanaw.reto9.model.Product;

import java.util.ArrayList;

public class MyAdapterProduct  extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<Product> list;

    public MyAdapterProduct(Context context, int layout, ArrayList<Product> products)
    {
        this.context = context;
        this.layout = layout;
        this.list = products;
    }

    @Override
    public int getCount() {
        if(list == null)
            return 0;
        return this.list.size();
    }

    @Override
    public Product getItem(int i) {
        return this.list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;

        LayoutInflater inflater = LayoutInflater.from(this.context);
        v = inflater.inflate(R.layout.row_company, null);

        String name = list.get(i).Name;
        String classification = ""+list.get(i).Price;
        ((TextView)v.findViewById(R.id.name)).setText(name);
        ((TextView)v.findViewById(R.id.classification)).setText(classification);

        ((Button)v.findViewById(R.id.deleteCompany)).setVisibility(View.GONE);
        ((Button)v.findViewById(R.id.updateCompany)).setVisibility(View.GONE);
        ((Button)v.findViewById(R.id.viewCompany)).setVisibility(View.GONE);

        return v;
    }
}
