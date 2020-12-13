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
    public ArrayList<String> image_url;
    public ArrayList<String> rating = new ArrayList<String>();
    public ArrayList<String> is_closed = new ArrayList<String>();
    public ArrayList<String> distance = new ArrayList<String>();
    public ArrayList<Double> latitude;
    public ArrayList<Double> longitude;

    public void add(String response)
    {
        this.id.clear();
        this.name.clear();
        this.address.clear();
        this.phone.clear();
        this.rating.clear();
        this.is_closed.clear();
        this.distance.clear();
        this.city.clear();
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("businesses");
            for(int i = 0; i < 10; i++)
            {
                JSONObject business = jsonArray.getJSONObject(i);
                JSONObject location = business.getJSONObject("location");
                this.id.add(business.getString("id"));
                this.name.add(business.getString("name"));
                this.address.add(location.getString("address1") + ",");
                this.city.add(location.getString("city") + ", " + location.getString("state")
                        + " " + location.getString("zip_code"));
                this.phone.add(business.getString("phone"));
                System.out.println(business.get("phone"));
                if(business.getBoolean("is_closed"))
                {
                    this.is_closed.add("Open");
                }
                else
                {
                    this.is_closed.add("Closed");
                }
                double distance = business.getDouble("distance");
                this.distance.add(Double.toString(distance) + "m");
                double rating = business.getDouble("rating");
                this.rating.add("Rating: " + Double.toString(rating) + "/5");
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

    }
}

