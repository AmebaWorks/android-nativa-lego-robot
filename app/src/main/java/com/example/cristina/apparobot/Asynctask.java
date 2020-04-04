package com.example.cristina.apparobot;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Cristina on 16/05/2018.
 */

public abstract class Asynctask extends AsyncTask<Object,String,Object>{

    boolean stop = false;
    InputStreamReader in;
    Asynctask(InputStreamReader in)
    {
        this.in = in;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        BufferedReader br = new BufferedReader(in);
        String lineToRead = "";
        while(!stop)
        {

            try {
                lineToRead = br.readLine();
                publishProgress(lineToRead.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected abstract void onProgressUpdate(String... values);
}
