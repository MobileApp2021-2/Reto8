package com.agaldanaw.reto9.model;

public class Product {
    public long Uid;
    public String Name;
    public double Price;
    public long UidCompany;

    public Product(long uid, String name,
                   long uidCompany, Double price)
    {
        Uid = uid;
        Name = name;
        Price = price;
        UidCompany = uidCompany;
    }
}
