package com.agaldanaw.reto9;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

import java.util.ArrayList;

public class CreateCompanyActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ArrayList<Product> _list;
    private ListView _listView;
    Spinner spinner;
    MyAdapterProduct adapterList;
    EditText nameProduct;
    EditText priceProduct;

    EditText nameCompany;
    EditText urlcompany;
    EditText phoneCompany;
    EditText emailCompany;

    long companyId;
    boolean update = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_company);

        Intent intent = getIntent();
        companyId = intent.getLongExtra("companyId", 0);
        update = intent.getBooleanExtra("update", false);

        spinner = (Spinner)findViewById(R.id.classification);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.clas_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        _listView = (ListView)findViewById(R.id.listProducts);
        TextView empylist = (TextView)findViewById(R.id.emptyList);
        _listView.setEmptyView(empylist);
        adapterList = new MyAdapterProduct(this, android.R.layout.simple_list_item_1, _list);
        _listView.setAdapter(adapterList);

        nameProduct = (EditText)findViewById(R.id.nameProduct);
        priceProduct = (EditText)findViewById(R.id.productPrice);

        nameCompany = (EditText)findViewById(R.id.nameCompany);
        urlcompany = (EditText)findViewById(R.id.urlCompany);
        phoneCompany = (EditText)findViewById(R.id.phoneCompanyCompany);
        emailCompany = (EditText)findViewById(R.id.emailcompany);

        ((Button)findViewById(R.id.createProduct)).setOnClickListener(new ButtonAddProduct(this));
        ((Button)findViewById(R.id.createCompany)).setOnClickListener(new ButtonCreateCompany(this));
        ((Button)findViewById(R.id.updateCompany)).setOnClickListener(new ButtonUpdateCompany(this));

        _list = new ArrayList<Product>();

        if(this.companyId != 0 )
        {
            nameProduct.setVisibility(View.GONE);
            priceProduct.setVisibility(View.GONE);
            ((Button)findViewById(R.id.createProduct)).setVisibility(View.GONE);
            ((Button)findViewById(R.id.createCompany)).setVisibility(View.GONE);
            ((TextView)findViewById(R.id.titlePriceProduct)).setVisibility(View.GONE);
            ((TextView)findViewById(R.id.titleNameProduct)).setVisibility(View.GONE);
            _list = new ArrayList<Product>(MainActivity.helper.getProducts(this.companyId));
            adapterList = new MyAdapterProduct(this, android.R.layout.simple_list_item_1, _list);
            _listView.setAdapter(adapterList);
            Company company = MainActivity.helper.getCompany(this.companyId);

            if(company != null)
            {
                nameCompany.setText(company.Name);
                urlcompany.setText(company.Url);
                phoneCompany.setText(company.Phone);
                emailCompany.setText(company.Email);
                spinner.setSelection(getIdxSelectionSpinner(company.Clasification));

                if(!update)
                {
                    spinner.setEnabled(false);
                    nameCompany.setEnabled(false);
                    emailCompany.setEnabled(false);
                    phoneCompany.setEnabled(false);
                    urlcompany.setEnabled(false);
                    //((Button)findViewById(R.id.updateCompany)).setVisibility(View.VISIBLE);
                }
            }

        }
    }

    private int getIdxSelectionSpinner(String classification)
    {
        if(classification.equals("Consultancy"))
            return 1;
        if(classification.equals("Custom Development"))
            return 2;
        if(classification.equals("Software Factory"))
            return 3;
        return 0;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String str = (String)spinner.getItemAtPosition(position);
        Toast.makeText(this, str, Toast.LENGTH_LONG).show();
    }

    private class ButtonAddProduct implements View.OnClickListener {
        private boolean _clicked = false;
        private Context _context;
        public ButtonAddProduct(Context context)
        {
            _context = context;
        }
        public void onClick(View view) {
            try {
                if(!_clicked)
                {
                    _clicked = true;
                    Product product = new Product(0, nameProduct.getText().toString(), 0, Double.parseDouble(priceProduct.getText().toString()));
                    _list.add(product);
                    adapterList = new MyAdapterProduct(_context, android.R.layout.simple_list_item_1, _list);
                    _listView.setAdapter(adapterList);
                    nameProduct.setText("");
                    priceProduct.setText("");
                }
            }catch(Exception e) { // ignore
            }
            finally {
                _clicked = false;
            }
        }
    }

    private class ButtonCreateCompany implements View.OnClickListener {
        private boolean _clicked = false;
        private Context _context;
        public ButtonCreateCompany(Context context)
        {
            _context = context;
        }
        public void onClick(View view) {
            try {
                if(!_clicked)
                {
                    _clicked = true;
                    //crear compania, agregarla, agregar los productos y finalizar
                    Company company = new Company(0, nameCompany.getText().toString(), urlcompany.getText().toString(),
                            phoneCompany.getText().toString(), emailCompany.getText().toString(), spinner.getSelectedItem().toString());

                    long idcompany = MainActivity.helper.insertCompany(company);
                    for (Product p: _list ) {
                        p.UidCompany = idcompany;
                        MainActivity.helper.insertProduct(p);
                    }

                    Toast.makeText(_context, "Company created!",Toast.LENGTH_SHORT).show();
                    finish();
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
        public ButtonUpdateCompany(Context context)
        {
            _context = context;
        }
        public void onClick(View view) {
            try {
                if(!_clicked)
                {
                    _clicked = true;
                    //crear compania, agregarla, agregar los productos y finalizar
                    Company company = new Company(0, nameCompany.getText().toString(), urlcompany.getText().toString(),
                            phoneCompany.getText().toString(), emailCompany.getText().toString(), spinner.getSelectedItem().toString());

                    boolean updated = MainActivity.helper.updateCompany(companyId,company);
                    if(updated)
                        Toast.makeText(_context, "Company updated!",Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(_context, "Error updating company, try again, please",Toast.LENGTH_SHORT).show();

                    finish();
                }
            }catch(Exception e) { // ignore
            }
            finally {
                _clicked = false;
            }
        }
    }
}