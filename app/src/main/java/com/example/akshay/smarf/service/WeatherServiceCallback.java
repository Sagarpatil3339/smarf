package com.example.akshay.smarf.service;

import com.example.akshay.smarf.data.Channel;

/**
 * Created by akshay on 13/12/17.
 */

public interface WeatherServiceCallback  {

    void serviceSuccess(Channel channel);

    void serviceFailure( Exception exeption);
}
