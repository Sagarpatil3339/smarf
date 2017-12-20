package com.example.akshay.smarf;

import android.animation.ObjectAnimator;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.akshay.smarf.data.Channel;
import com.example.akshay.smarf.service.WeatherServiceCallback;
import com.example.akshay.smarf.service.YahooWeatherService;

public class MainActivity extends AppCompatActivity implements WeatherServiceCallback {

    private ImageView weatherIconImageView;
    private TextView temperatureTextView;
    private TextView locationTextView;
    private TextView conditionTextView;
    private ProgressBar progressBar;
    private ObjectAnimator progressAnimator;

    private YahooWeatherService service;

    private NotificationHelper mNotificationHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weatherIconImageView= (ImageView)findViewById(R.id.weatherIconImageView);
        temperatureTextView= (TextView)findViewById(R.id.temperatureTextView);
        locationTextView= (TextView)findViewById(R.id.locationTextView);
        conditionTextView= (TextView)findViewById(R.id.conditionTextView);
        progressBar= (ProgressBar)findViewById(R.id.progressBar);

        service = new YahooWeatherService(this);
        progressBar.setVisibility(View.VISIBLE);
        progressAnimator = ObjectAnimator.ofFloat(progressBar,"progress", 0.0f,1.0f);
        progressAnimator.setDuration(7000);
        progressAnimator.start();

        service.refreshWeather("New York, NY");

        mNotificationHelper= new NotificationHelper(this);
    }

    @Override
    public void serviceSuccess(Channel channel) {
        progressBar.setVisibility(View.INVISIBLE);

        int resourceId= getResources().getIdentifier("drawable/icon_"+channel.getItem().getCondition().getCode(), null, getPackageName());
        Drawable weatherIconDrawable= getResources().getDrawable(resourceId, null);
        weatherIconImageView.setImageDrawable(weatherIconDrawable);
        temperatureTextView.setText(channel.getItem().getCondition().getTemperature()+"\u00B0"+channel.getUnits().getTemperature());
        conditionTextView.setText(channel.getItem().getCondition().getDescription());
        locationTextView.setText(service.getLocation());

        int temp= channel.getItem().getCondition().getTemperature();
        if(temp<15){
            // notification class
            sendOnChannel1("Wear Your Scarf", "The Temperature outside is "+channel.getItem().getCondition().getTemperature()+"\u00B0"+channel.getUnits().getTemperature());
        }
    }

    public void sendOnChannel1(String title, String message){
        NotificationCompat.Builder nb= mNotificationHelper.getChannel1Notification(title, message);
        mNotificationHelper.getManager().notify(18729, nb.build());
    }

    @Override
    public void serviceFailure(Exception exeption) {
        progressBar.setVisibility(View.INVISIBLE);
        Toast.makeText(this, exeption.getMessage(), Toast.LENGTH_LONG).show();


    }


}
