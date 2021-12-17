package com.agaldanaw.reto9.list;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.agaldanaw.reto9.CreateCompanyActivity;
import com.agaldanaw.reto9.MainActivity;
import com.agaldanaw.reto9.R;
import com.agaldanaw.reto9.model.Company;


import java.util.ArrayList;

public class MyAdapterCompany extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<Company> list;

    public MyAdapterCompany(Context context, int layout, ArrayList<Company> companies)
    {
        this.context = context;
        this.layout = layout;
        this.list = companies;
    }

    @Override
    public int getCount() {
        if(list == null)
            return 0;
        return this.list.size();
    }

    public void deleteCompany(long uid) {
        int idx = -1;
        for(int i = 0;i < list.size(); i++)
            if(list.get(i).Uid == uid)
                idx = i;

        if(idx != -1){
            list.remove(idx);
            notifyDataSetChanged();
        }
    }

    @Override
    public Company getItem(int i) {
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
        String classification = list.get(i).Clasification;
        ((TextView)v.findViewById(R.id.name)).setText(name);
        ((TextView)v.findViewById(R.id.classification)).setText(classification);

        ((Button)v.findViewById(R.id.deleteCompany)).setOnClickListener(new ButtonDeleteCompany(context, list.get(i).Uid, this));
        ((Button)v.findViewById(R.id.updateCompany)).setOnClickListener(new ButtonUpdateCompany(context, list.get(i).Uid));
        ((Button)v.findViewById(R.id.viewCompany)).setOnClickListener(new ButtonViewCompany(context, list.get(i).Uid));

        return v;
    }

    private class ButtonDeleteCompany implements View.OnClickListener {
        private boolean _clicked = false;
        private Context _context;
        private long uidCompany;
        private MyAdapterCompany adapter;

        public ButtonDeleteCompany(Context context, long uidCompany, MyAdapterCompany adapter)
        {
            _context = context;
            this.uidCompany = uidCompany;
            this.adapter = adapter;
        }
        public void onClick(View view) {
            try {
                if(!_clicked)
                {
                    _clicked = true;
                    Dialog dialog = null;
                    AlertDialog.Builder builder = new AlertDialog.Builder(_context);
                    builder.setMessage("Do you want delete this company?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    boolean deleted = MainActivity.helper.deleteCompany(uidCompany);
                                    if(deleted)
                                    {
                                        adapter.deleteCompany(uidCompany);
                                        Toast.makeText(_context, "Deleted!", Toast.LENGTH_LONG).show();
                                    }
                                    else
                                        Toast.makeText(_context, "Error deleting company, try again, please!", Toast.LENGTH_LONG).show();
                                }
                            })
                            .setNegativeButton("No", null);
                    dialog = builder.create();
                    dialog.show();
                }
            }catch(Exception e) { // ignore
            }
            finally {
                _clicked = false;
            }
        }
    }

    private class ButtonUpdateCompany implements View.OnClickListener {
        private boolean _clicked = false;
        private Context _context;
        private long uidCompany;
        public ButtonUpdateCompany(Context context, long uidCompany)
        {
            _context = context;
            this.uidCompany = uidCompany;
        }
        public void onClick(View view) {
            try {
                if(!_clicked)
                {
                    _clicked = true;
                    Intent intent = new Intent(_context, CreateCompanyActivity.class);
                    intent.putExtra("companyId", uidCompany);
                    intent.putExtra("update", true);
                    _context.startActivity(intent);
                }
            }catch(Exception e) { // ignore
            }
            finally {
                _clicked = false;
            }
        }
    }

    private class ButtonViewCompany implements View.OnClickListener {
        private boolean _clicked = false;
        private Context _context;
        private long uidCompany;
        public ButtonViewCompany(Context context, long uidCompany)
        {
            _context = context;
            this.uidCompany = uidCompany;
        }
        public void onClick(View view) {
            try {
                if(!_clicked)
                {
                    _clicked = true;
                    Intent intent = new Intent(_context, CreateCompanyActivity.class);
                    intent.putExtra("companyId", uidCompany);
                    _context.startActivity(intent);
                }
            }catch(Exception e) { // ignore
            }
            finally {
                _clicked = false;
            }
        }
    }
}
