package com.example.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;


public class WeatherForecast extends Activity {

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);
        Log.i("Weather Forecast Debug", "Create Activity");
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        new ForcastQuery().execute("Weather forecast");
    }

    public class ForcastQuery extends AsyncTask<String, Integer, String> {
        String min;
        String max;
        String currentTemperature;
        Bitmap currentWeather;
        String weatherWeb = "http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric";

        public boolean fileExistance(String fname) {
            File file = getBaseContext().getFileStreamPath(fname);
            return file.exists();
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL(weatherWeb);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser parser = factory.newPullParser();
                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                InputStream input = conn.getInputStream();
                parser.setInput(input, "UTF-8");

                int eventType = parser.getEventType();
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if (eventType == XmlPullParser.START_TAG) {
                        if (parser.getName().equals("temperature")) {
                            currentTemperature = parser.getAttributeValue(null, "value");
                            publishProgress(25);
                            min = parser.getAttributeValue(null, "min");
                            publishProgress(50);
                            max = parser.getAttributeValue(null, "max");
                            publishProgress(75);
                        }
                        if (parser.getName().equals("weather")) {
                            String iconName = parser.getAttributeValue(null, "icon");

                            if (!fileExistance(iconName + ".png")) {
                                Log.i("Weather Forecast", "File not found! Start Download!");
                                URL imageURL = new URL("http://openweathermap.org/img/w/" + iconName + ".png");
                                HttpURLConnection connection = null;
                                connection = (HttpURLConnection) imageURL.openConnection();
                                connection.setReadTimeout(10000);
                                connection.setConnectTimeout(15000);
                                connection.setRequestMethod("GET");
                                connection.setDoInput(true);
                                connection.connect();
                                Bitmap image = BitmapFactory.decodeStream(connection.getInputStream());
                                currentWeather = image;

                                FileOutputStream outputStream = openFileOutput(iconName + ".png", Context.MODE_PRIVATE);
                                image.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                                outputStream.flush();
                                outputStream.close();
                            } else {
                                Log.i("Weather Forecast", "File found! Load file!");
                                FileInputStream fis = null;
                                fis = openFileInput(iconName + ".png");
                                currentWeather = BitmapFactory.decodeStream(fis);
                            }
                        }
                    }

                    eventType = parser.next();
                }
            } catch (XmlPullParserException e) {
                Log.i("Weather Forecast Debug", "XPP Error");
            } catch (ProtocolException e) {
                Log.i("Weather Forecast Debug", "Protocol Error");
            } catch (IOException e) {
                Log.i("Weather Forecast Debug", "IO Error");
            }
            return null;
        }

        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(values[0]);
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ImageView imageView = findViewById(R.id.currentWeather);
            imageView.setImageBitmap(currentWeather);
            TextView currentTemp = findViewById(R.id.currentTemperature);
            currentTemp.setText("Current Temperature: "+currentTemperature);
            TextView minTemp = findViewById(R.id.minTemperature);
            minTemp.setText("Minimum Temperature: "+min);
            TextView maxTemp = findViewById(R.id.maxTemperature);
            maxTemp.setText("Maximum Temperature: "+max);
            progressBar.setVisibility(View.INVISIBLE);
        }


    }
}
