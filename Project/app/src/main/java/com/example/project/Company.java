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
    public ArrayList<String> phone = new ArrayList<String>();
    public ArrayList<String> image_url;
    public ArrayList<String> is_closed;
    public ArrayList<String> distance;
    public ArrayList<Double> latitude;
    public ArrayList<Double> longitude;

    public void add(String response)
    {
        this.id.clear();
        this.name.clear();
        this.address.clear();
        this.phone.clear();
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("businesses");
            for(int i = 0; i < 5; i++)
            {
                JSONObject business = jsonArray.getJSONObject(i);
                JSONObject location = business.getJSONObject("location");
                this.id.add(business.getString("id"));
                this.name.add(business.getString("name"));
                this.address.add(location.getString("address1"));
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
                this.distance.add(Double.toString(distance) + "m");
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

    }
}

