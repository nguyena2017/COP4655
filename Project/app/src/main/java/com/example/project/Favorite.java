package com.example.project;

import com.example.project.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class Favorite
{
    private static Favorite instance = new Favorite();
    public ArrayList<String> favoriteList;
    HomeFragment homeFragment = new HomeFragment();

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

    public void clear()
    {
        favoriteList.clear();
    }


}
