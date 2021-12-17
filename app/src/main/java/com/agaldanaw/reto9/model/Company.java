package com.agaldanaw.reto9.model;

public class Company {
    public long Uid;
    public String Name;
    public String Url;
    public String Phone;
    public String Email;
    public String Clasification;

    public Company(long uid, String name,
                   String url, String phone, String email, String clasification)
    {
        Uid = uid;
        Name = name;
        Url = url;
        Phone = phone;
        Email = email;
        Clasification = clasification;
    }
}
