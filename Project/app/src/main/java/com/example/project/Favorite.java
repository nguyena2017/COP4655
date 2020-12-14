package com.example.project;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Favorite
{
    private static Favorite instance = new Favorite();
    public ArrayList<String> favoriteList;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    private Favorite()
    {
        favoriteList = new ArrayList<String>();
    }

    public static Favorite getInstance()
    {
        return instance;
    }

    public void add(String company)
    {
        favoriteList.add(company);
    }

    public void delete(String company)
    {
        favoriteList.remove(company);
    }
}
