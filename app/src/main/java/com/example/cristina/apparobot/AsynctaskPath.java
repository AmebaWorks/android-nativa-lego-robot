package com.example.cristina.apparobot;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.IOException;

/**
 * Created by USUARIO on 23/05/2018.
 */

public abstract class AsynctaskPath extends AsyncTask<Object,String,Object> {

    String[][] path;
    BufferedWriter outputStream;
    AsynctaskPath(String[][] array, BufferedWriter out) {
        path = array;
        outputStream = out;
    }

    @Override
    protected abstract void onPostExecute(Object array);

    @Override
    protected Object doInBackground(Object[] objects)
    {
        for(int i = 0; i < path.length ; i++)
        {
            try {
                outputStream.write(path[i][1]+"\r\n");
                outputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(Integer.parseInt(path[i][0])*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected abstract void onProgressUpdate (String...values);
}