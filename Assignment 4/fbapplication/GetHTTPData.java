package com.example.akshaykumars.fbapplication;

import android.os.AsyncTask;
import android.util.Log;

import com.example.akshaykumars.fbapplication.interfaces.OnTaskCompleted;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Akshay Kumar S on 4/12/2017.
 */

public class GetHTTPData extends AsyncTask<String, Void, JSONObject> {
    public static final String CLASS_NAME = "GetHTTPData.java";
    private OnTaskCompleted listener;
    private String type = null;
    public GetHTTPData(OnTaskCompleted listener, String type){
        this.listener = listener;
        this.type = type;
    }

    protected JSONObject doInBackground(String... urls) {
        JSONObject json = null;
        StringBuffer response = null;
        try {
            URL url = new URL(urls[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            // optional default is GET
            con.setRequestMethod("GET");
            con.setConnectTimeout(30000);
            con.setReadTimeout(30000);
           con.connect();

            Log.d(CLASS_NAME,"GET request sent to PHP script");

            BufferedReader in = new BufferedReader(
                   new InputStreamReader(con.getInputStream()));
            String inputLine;
            response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            json = new JSONObject(response.toString());
        } catch (Exception e) {


            Log.e(CLASS_NAME,"Exception occurred "+e.getMessage());
        }
        return json;
    }

    protected void onPostExecute(JSONObject result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
        if(this.type.equalsIgnoreCase("TabbedActivity")) {
            ((TabbedActivity) listener).OnSuccess(result);
        } else if(this.type.equalsIgnoreCase("DetailsTab")){
            ((DetailsTab) listener).OnSuccess(result);
        }


    }

}