package com.agaldanaw.reto9;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.agaldanaw.reto9.list.MyAdapterCompany;
import com.agaldanaw.reto9.list.MyAdapterProduct;
import com.agaldanaw.reto9.model.Company;
import com.agaldanaw.reto9.model.Product;
import com.agaldanaw.reto9.sqlite.SQLiteAdapter;


import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Company> _list;
    private ListView _listView;
    public static SQLiteAdapter helper;
    Spinner spinner;
    EditText nameCompany;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _listView = (ListView)findViewById(R.id.listBoards);
        TextView empylist = (TextView)findViewById(R.id.emptyList);
        _listView.setEmptyView(empylist);

        spinner = (Spinner)findViewById(R.id.classification);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.clas_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        nameCompany = (EditText)findViewById(R.id.nameCompany);

        helper = new SQLiteAdapter(this);

        ((Button)findViewById(R.id.searchCompany)).setOnClickListener(new ButtonSearchCompany(this));
        ((Button)findViewById(R.id.filterCompany)).setOnClickListener(new ButtonClearFilter(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        GetRequest();
    }

    public void GetRequest()
    {
        ArrayList<Company> companies = helper.getCompanieswithoutFilter();
        UpdateList(companies);
    }

    private void UpdateList(ArrayList<Company> companies) {
        _list = new ArrayList<Company>(companies);
        fillListview();
    }

    private void fillListview() {
        MyAdapterCompany adapter = new MyAdapterCompany(this, android.R.layout.simple_list_item_1, _list);
        _listView.setAdapter(adapter);
    }

    private void ShowToast(String message, Context context) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bottom_nav_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.create:
                Intent intent = new Intent(this, CreateCompanyActivity.class);
                startActivity(intent);
                return true;
        }
        return false;
    }

    private class ButtonSearchCompany implements View.OnClickListener {
        private boolean _clicked = false;
        private Context _context;
        public ButtonSearchCompany(Context context)
        {
            _context = context;
        }
        public void onClick(View view) {
            try {
                if(!_clicked)
                {
                    _clicked = true;
                    ArrayList<Company> companies = helper.getCompaniesWithFilter(nameCompany.getText().toString(), spinner.getSelectedItem().toString());
                    UpdateList(companies);
                }
            }catch(Exception e) { // ignore
            }
            finally {
                _clicked = false;
            }
        }
    }

    private class ButtonClearFilter implements View.OnClickListener {
        private boolean _clicked = false;
        private Context _context;
        public ButtonClearFilter(Context context)
        {
            _context = context;
        }
        public void onClick(View view) {
            try {
                if(!_clicked)
                {
                    _clicked = true;
                    GetRequest();
                    nameCompany.setText("");
                    spinner.setSelection(0);
                }
            }catch(Exception e) { // ignore
            }
            finally {
                _clicked = false;
            }
        }
    }

}