package com.example.asynctaskassignment;

import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class forecastTask extends AsyncTask<String,String,String> {

    @Override
    protected String doInBackground(String... strings) {
        String s = "https://api.openweathermap.org/data/2.5/weather?q="+strings[0]+"&appid=fae7190d7e6433ec3a45285ffcf55c86";

        InputStream inputStream;
        try {

            URL url = new URL(s);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            inputStream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line = "";

            while ((line = reader.readLine())!=null){
                stringBuilder.append(line+ " \n");
            }


            reader.close();
            inputStream.close();

            return stringBuilder.toString();

        } catch (Exception e) {
            Log.i("background task",e.toString());
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        String temp="",Humidity="",rain="",windSpeed="",windDegree="";
        try {
            JSONObject object = new JSONObject(s);
            JSONObject object1  = object.getJSONObject("main");
            temp = object1.getString("temp");
            Humidity = object1.getString("humidity");
            rain = object.getJSONObject("clouds").getString("all");
            windSpeed = object.getJSONObject("wind").getString("speed");
            windDegree = object.getJSONObject("wind").getString("deg");

        } catch (Exception e) {
            Log.i("json",e.toString());
        }
        CityFragment.tvTemp.setText(CityFragment.tvTemp.getText()+temp);
        CityFragment.tvHumidity.setText(CityFragment.tvHumidity.getText()+Humidity);
        CityFragment.tvRainChances.setText(CityFragment.tvRainChances.getText()+rain);
        CityFragment.tvWind.setText(CityFragment.tvWind.getText()+"Speed:"+windSpeed+" Deg:"+windDegree);

    }
}
