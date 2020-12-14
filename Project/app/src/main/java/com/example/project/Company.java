package com.example.project;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Company
{
    public ArrayList<String> id = new ArrayList<String>();
    public ArrayList<String> name = new ArrayList<String>();
    public ArrayList<String> address = new ArrayList<String>();
    public ArrayList<String> city = new ArrayList<>();
    public ArrayList<String> phone = new ArrayList<String>();
    public ArrayList<String> image_url = new ArrayList<>();
    public ArrayList<String> rating = new ArrayList<String>();
    public ArrayList<String> is_closed = new ArrayList<String>();
    public ArrayList<Double> latitude = new ArrayList<Double>();
    public ArrayList<Double> longitude = new ArrayList<Double>();

    public void add(String response)
    {
        this.id.clear();
        this.name.clear();
        this.address.clear();
        this.phone.clear();
        this.rating.clear();
        this.is_closed.clear();
        this.city.clear();
        this.longitude.clear();
        this.latitude.clear();
        this.image_url.clear();

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("businesses");
            for(int i = 0; i < 10; i++)
            {
                JSONObject business = jsonArray.getJSONObject(i);
                JSONObject location = business.getJSONObject("location");
                JSONObject coordinates = business.getJSONObject("coordinates");
                this.id.add(business.getString("id"));
                this.name.add(business.getString("name"));
                this.address.add(location.getString("address1") + ",");
                this.city.add(location.getString("city") + ", " + location.getString("state")
                        + " " + location.getString("zip_code"));
                this.phone.add(business.getString("phone"));
                if(business.getBoolean("is_closed"))
                {
                    this.is_closed.add("Closed");
                }
                else
                {
                    this.is_closed.add("Open");
                }
                double distance = business.getDouble("distance");
                double rating = business.getDouble("rating");
                this.rating.add("Rating: " + Double.toString(rating) + "/5");
                this.latitude.add(coordinates.getDouble("latitude"));
                this.longitude.add(coordinates.getDouble("longitude"));
                this.image_url.add(business.getString("image_url"));
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

    }
}

