package com.agaldanaw.reto9.sqlite;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.agaldanaw.reto9.model.Company;
import com.agaldanaw.reto9.model.Product;

import java.util.ArrayList;
import java.util.List;

public class SQLiteAdapter {

    SQLHelper myhelper;

    public SQLiteAdapter(Context context)
    {
        myhelper = new SQLHelper(context);
    }

    public long insertCompany(Company company)
    {
        SQLiteDatabase dbb = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myhelper.NAME_COMPANY, company.Name);
        contentValues.put(myhelper.URL_COMPANY, company.Url);
        contentValues.put(myhelper.PHONE_COMPANY, company.Phone);
        contentValues.put(myhelper.EMAIL, company.Email);
        contentValues.put(myhelper.CLASIFICATION, company.Clasification);
        long id = dbb.insert(myhelper.TABLE_NAME_COMPANY, null , contentValues);
        return id;
    }


    public long insertProduct(Product product)
    {
        SQLiteDatabase dbb = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myhelper.NAME_PRODUCT, product.Name);
        contentValues.put(myhelper.PRICE, product.Price);
        contentValues.put(myhelper.FK_ID_COMPANY, product.UidCompany);
        long id = dbb.insert(myhelper.TABLE_NAME_PRODUCT, null , contentValues);
        return id;
    }

    @SuppressLint("Range")
    public ArrayList<Company> getCompanieswithoutFilter()
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myhelper.UID, myhelper.NAME_COMPANY,
                myhelper.URL_COMPANY, myhelper.PHONE_COMPANY,
                myhelper.EMAIL, myhelper.CLASIFICATION};


        Cursor cursor = db.query(myhelper.TABLE_NAME_COMPANY, columns, null,null,null,null,null);
        ArrayList<Company> companies = new ArrayList<Company>();
        while (cursor.moveToNext())
        {

            int uid =cursor.getInt(cursor.getColumnIndex(myhelper.UID));
            String name =cursor.getString(cursor.getColumnIndex(myhelper.NAME_COMPANY));
            String url =cursor.getString(cursor.getColumnIndex(myhelper.URL_COMPANY));
            String phone =cursor.getString(cursor.getColumnIndex(myhelper.PHONE_COMPANY));
            String email =cursor.getString(cursor.getColumnIndex(myhelper.EMAIL));
            String clasification =cursor.getString(cursor.getColumnIndex(myhelper.CLASIFICATION));
            Company company = new Company(uid, name, url, phone, email, clasification);
            companies.add(company);
        }
        return companies;
    }

    @SuppressLint("Range")
    public ArrayList<Company> getCompaniesWithFilter(String nameFilter, String clasificationFilter)
    {
        if(clasificationFilter.equals("Select Classification"))
            clasificationFilter = null;

        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myhelper.UID, myhelper.NAME_COMPANY,
                myhelper.URL_COMPANY, myhelper.PHONE_COMPANY,
                myhelper.EMAIL, myhelper.CLASIFICATION};

        String filter = "";
        String[] args = new String[2];
        if(nameFilter != null && !nameFilter.isEmpty())
        {
            filter = myhelper.NAME_COMPANY + " LIKE ?";
            args[0] = "%" + nameFilter + "%";
        }

        if(clasificationFilter != null && !clasificationFilter.isEmpty())
        {
            if(filter.isEmpty())
            {
                filter = myhelper.CLASIFICATION + " LIKE ?";
                args = new String[1];
                args[0] = "%" + clasificationFilter + "%";
            }
            else
            {
                filter += "AND " + myhelper.CLASIFICATION + " LIKE ?";
                args[1] = "%" + clasificationFilter + "%";
            }
        }
        else
        {
            if(args[0] != null)
            {
                String tmp = args[0];
                args = new String[1];
                args[0] = tmp;
            }
        }

        Cursor cursor = db.query(myhelper.TABLE_NAME_COMPANY, columns, filter, args,null,null,null);
        ArrayList<Company> companies = new ArrayList<Company>();
        while (cursor.moveToNext())
        {

            int uid =cursor.getInt(cursor.getColumnIndex(myhelper.UID));
            String name =cursor.getString(cursor.getColumnIndex(myhelper.NAME_COMPANY));
            String url =cursor.getString(cursor.getColumnIndex(myhelper.URL_COMPANY));
            String phone =cursor.getString(cursor.getColumnIndex(myhelper.PHONE_COMPANY));
            String email =cursor.getString(cursor.getColumnIndex(myhelper.EMAIL));
            String clasification =cursor.getString(cursor.getColumnIndex(myhelper.CLASIFICATION));
            Company company = new Company(uid, name, url, phone, email, clasification);
            companies.add(company);
        }
        return companies;
    }

    @SuppressLint("Range")
    public ArrayList<Product> getProducts(long companyUid)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myhelper.UID, myhelper.NAME_PRODUCT,
                myhelper.PRICE, myhelper.FK_ID_COMPANY};

        String filter = myhelper.FK_ID_COMPANY + " = ?";
        Cursor cursor =db.query(myhelper.TABLE_NAME_PRODUCT, columns,filter, new String[]{""+companyUid},null,null,null);
        ArrayList<Product> products = new ArrayList<Product>();
        while (cursor.moveToNext())
        {

            long uid = cursor.getInt(cursor.getColumnIndex(myhelper.UID));
            String name =cursor.getString(cursor.getColumnIndex(myhelper.NAME_PRODUCT));
            double price =cursor.getDouble(cursor.getColumnIndex(myhelper.PRICE));
            long fk_id_company =cursor.getInt(cursor.getColumnIndex(myhelper.FK_ID_COMPANY));
            Product product = new Product(uid, name, fk_id_company, price);
            products.add(product);
        }
        return products;
    }

    @SuppressLint("Range")
    public Company getCompany(long companyUid)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myhelper.UID, myhelper.NAME_COMPANY,
                myhelper.URL_COMPANY, myhelper.PHONE_COMPANY,
                myhelper.EMAIL, myhelper.CLASIFICATION};

        String filter = myhelper.UID + " = ?";
        Cursor cursor =db.query(myhelper.TABLE_NAME_COMPANY, columns,filter, new String[]{""+companyUid},null,null,null);
        while (cursor.moveToNext())
        {

            int uid =cursor.getInt(cursor.getColumnIndex(myhelper.UID));
            String name =cursor.getString(cursor.getColumnIndex(myhelper.NAME_COMPANY));
            String url =cursor.getString(cursor.getColumnIndex(myhelper.URL_COMPANY));
            String phone =cursor.getString(cursor.getColumnIndex(myhelper.PHONE_COMPANY));
            String email =cursor.getString(cursor.getColumnIndex(myhelper.EMAIL));
            String clasification =cursor.getString(cursor.getColumnIndex(myhelper.CLASIFICATION));
            Company company = new Company(uid, name, url, phone, email, clasification);
            return company;
        }
        return null;
    }

    public boolean deleteCompany(long uidCompany)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] whereArgs ={""+uidCompany};
        int count =db.delete(myhelper.TABLE_NAME_COMPANY ,myhelper.UID+" = ?",whereArgs);

        //DELETE PRODUCTS
        db.delete(myhelper.TABLE_NAME_PRODUCT, myhelper.FK_ID_COMPANY + "= ?", whereArgs);
        return  count > 0;
    }

    public boolean deleteProduct(long uidProduct)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] whereArgs ={""+uidProduct};
        int count = db.delete(myhelper.TABLE_NAME_PRODUCT, myhelper.UID + "= ?", whereArgs);
        return  count > 0;
    }

    public boolean updateProduct(long uidProduct, Product newProduct)
    {
        SQLiteDatabase dbb = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myhelper.NAME_PRODUCT, newProduct.Name);
        contentValues.put(myhelper.PRICE, newProduct.Price);
        String[] whereArgs= {""+uidProduct};
        int count = dbb.update(myhelper.TABLE_NAME_PRODUCT, contentValues, myhelper.UID+" = ?", whereArgs );
        return count > 0;
    }

    public boolean updateCompany(long uidCompany, Company newCompany)
    {
        SQLiteDatabase dbb = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myhelper.NAME_COMPANY, newCompany.Name);
        contentValues.put(myhelper.URL_COMPANY, newCompany.Url);
        contentValues.put(myhelper.PHONE_COMPANY, newCompany.Phone);
        contentValues.put(myhelper.EMAIL, newCompany.Email);
        contentValues.put(myhelper.CLASIFICATION, newCompany.Clasification);
        String[] whereArgs= {""+uidCompany};
        int count = dbb.update(myhelper.TABLE_NAME_COMPANY, contentValues, myhelper.UID+" = ?", whereArgs );
        return count > 0;
    }

    static class SQLHelper extends SQLiteOpenHelper
    {
        private static final String DATABASE_NAME = "softwareCompany";
        private static final String TABLE_NAME_COMPANY = "company";
        private static final int DATABASE_Version = 1;
        private static final String UID="_id";
        private static final String NAME_COMPANY = "Name";
        private static final String URL_COMPANY= "Uri";
        private static final String PHONE_COMPANY= "Phone";
        private static final String EMAIL= "Email";
        private static final String CLASIFICATION = "Clasification";

        private static final String TABLE_NAME_PRODUCT = "product";
        private static final String NAME_PRODUCT = "Name";
        private static final String PRICE= "Price";
        private static final String FK_ID_COMPANY= "id_company";

        private static final String CREATE_TABLE_COMPANY = "CREATE TABLE "+TABLE_NAME_COMPANY+
                " ("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+NAME_COMPANY+" VARCHAR(255) ,"+URL_COMPANY+" VARCHAR(255) ,"
                +PHONE_COMPANY+" VARCHAR(255) ,"
                +EMAIL+" VARCHAR(255) ,"+ CLASIFICATION+" VARCHAR(225));";

        private static final String CREATE_TABLE_PRODUCT= "CREATE TABLE "+TABLE_NAME_PRODUCT+
                " ("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+FK_ID_COMPANY+" INTEGER ,"
                +NAME_PRODUCT+" VARCHAR(255) ,"+ PRICE+" REAL);";

        private static final String DROP_TABLE_PRODUCT ="DROP TABLE IF EXISTS "+TABLE_NAME_PRODUCT;
        private static final String DROP_TABLE_COMPANY ="DROP TABLE IF EXISTS "+TABLE_NAME_COMPANY;

        private Context context;

        public SQLHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_Version);
            this.context=context;
        }

        public void onCreate(SQLiteDatabase db) {

            try {
                db.execSQL(CREATE_TABLE_COMPANY);
                db.execSQL(CREATE_TABLE_PRODUCT);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                Log.e("UpgradeDB", "upagraded");
                db.execSQL(DROP_TABLE_PRODUCT);
                db.execSQL(DROP_TABLE_COMPANY);
                onCreate(db);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
