package com.example.cristina.apparobot;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements CustomAdapter.CustomListener {

    static final int REQUEST_ENABLE_BT = 1;
    static final int TAKE_A_RESULT = 2;

    BufferedWriter outBT;
    InputStreamReader inBT;

    //path
    String path;
    ImageButton up;
    ImageButton down;
    ImageButton right;
    ImageButton left;
    ImageButton increaseVolume;
    ImageButton decreaseVolume;
    ImageButton exit;
    ImageButton pause;

    //parking
    ImageView delantera;
    ImageView trasera;
    ImageButton upPark ;
    ImageButton downPark ;
    ImageButton rightPark ;
    ImageButton leftPark ;
    ImageButton backPark ;
    ImageButton pausePark ;

    //main
    ImageButton run;
    ImageButton backPath;
    Asynctask at;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        main();
        bluetoothConection();
    }

    public void main(){
        setContentView(R.layout.activity_main);

        //Initialize main buttons
        up = findViewById(R.id.buttonUp);
        down = findViewById(R.id.buttonDown);
        right = findViewById(R.id.buttonRight);
        left = findViewById(R.id.buttonLeft);
        increaseVolume = findViewById(R.id.buttonMasVol);
        decreaseVolume = findViewById(R.id.buttonMenosVol);
        exit = findViewById(R.id.buttonExit);
        pause = findViewById(R.id.buttonPause);

        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                up();
            }
        });
        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                down();
            }
        });
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                right();
            }
        });
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                left();
            }
        });
        decreaseVolume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    outBT.write("DecreaseVolume\r\n");
                    outBT.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        increaseVolume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    outBT.write("IncreaseVolume\r\n");
                    outBT.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    outBT.write("Exit\r\n");
                    outBT.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                finish();
            }
        });
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pause();
            }
        });
    }

    public void up(){
        try {
            outBT.write("Up\r\n");
            outBT.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void down(){
        try {
            outBT.write("Down\r\n");
            outBT.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void right(){
        try {
            outBT.write("Right\r\n");
            outBT.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void left(){
        try {
            outBT.write("Left\r\n");
            outBT.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void pause(){
        try {
            outBT.write("Pause\r\n");
            outBT.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void parking(){
        setContentView(R.layout.activity_parking);

        delantera = (ImageView) findViewById(R.id.imgDelantero);
        trasera = (ImageView) findViewById(R.id.imgTrasero);
        upPark = findViewById(R.id.Park);
        downPark = findViewById(R.id.buttonDownPark);
        rightPark = findViewById(R.id.buttonRightPark);
        leftPark = findViewById(R.id.buttonLeftPark);
        backPark = findViewById(R.id.buttonBackPark);
        pausePark = findViewById(R.id.buttonPausePark);

        upPark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                up();
            }
        });
        downPark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               down();
            }
        });
        rightPark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                right();
            }
        });
        leftPark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                left();
            }
        });

        pausePark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               pause();
            }
        });
        backPark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pause();

                try {
                    outBT.write("Parking\r\n");
                    outBT.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                main();
            }
        });
    }

    public CustomAdapter adapter;
    private ArrayList<Directions> directions;
    private ListView listView;
    private FloatingActionButton fbutton;
    public void path(){

        setContentView(R.layout.activity_path);

        directions = new ArrayList<Directions>();
        directions.add(new Directions());

        adapter = new CustomAdapter(this, R.layout.custom_layout, directions);
        adapter.notifyDataSetChanged();
        adapter.setCustomListener(MainActivity.this);
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);

        fbutton = (FloatingActionButton) findViewById(R.id.fbutton);
        run = findViewById(R.id.ButtonRun);
        backPath = findViewById(R.id.buttonBackPath);
        run.setEnabled(false);


        fbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDirection();
            }
        });

        run.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    //Create the matrix with the directions and seconds
                    String [][]arrayOfPath;
                    arrayOfPath = new String[directions.size()][2];
                    for(int i = 0; i < directions.size(); i++) {
                        arrayOfPath[i][1] = directions.get(i).getDirection();
                        arrayOfPath[i][0] = directions.get(i).getSeconds();
                    }

                    @SuppressLint("StaticFieldLeak")
                    AsynctaskPath startPath = new AsynctaskPath(arrayOfPath, outBT)
                    {
                        @Override
                        protected void onPostExecute(Object array) {
                            pause();
                            main();
                        }

                        @Override
                        protected void onProgressUpdate(String... values) {
                        }
                    };
                    startPath.executeOnExecutor(Asynctask.THREAD_POOL_EXECUTOR);
            }
        });

        backPath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pause();
                main();
            }
        });
    }

    public boolean isNull(String editable)
    {
        if(editable != null && !editable.equals(""))
        {
            return false;
        }
        return true;
    }

    @SuppressLint("StaticFieldLeak")
    public void bluetoothConection(){
        //Bluetooth
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter != null) {
            //Check if is enabled
            if (!mBluetoothAdapter.isEnabled()) {
                //Activate bluetooth
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }
            //Connect with the robot
            BluetoothDevice mmDevice = mBluetoothAdapter.getRemoteDevice("00:16:53:08:E5:00");
            BluetoothSocket mmSocket = null;
            try {
                mmSocket = mmDevice.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
                mmSocket.connect();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //writer
            if(mmSocket != null) {
                try {
                    outBT = new BufferedWriter(new OutputStreamWriter(mmSocket.getOutputStream()));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    inBT = new InputStreamReader(mmSocket.getInputStream());
                    //initialize asynctask to be reading all time
                    at = new Asynctask(inBT) {
                        @Override
                        protected void onProgressUpdate(String... values) {
                            //update the images of parking
                            if(values[0].equals("1"))
                            {
                                delantera.setImageResource(R.drawable.arriba1);
                            }
                            else if(values[0].equals("2"))
                            {
                                delantera.setImageResource(R.drawable.arriba2);
                            }
                            else if(values[0].equals("3"))
                            {
                                delantera.setImageResource(R.drawable.arriba3);
                                pause();
                            }
                            else if(values[0].equals("4"))
                            {
                                trasera.setImageResource(R.drawable.abajo3);
                                pause();
                            }
                            else
                            {
                                delantera.setImageResource(R.drawable.distance);
                                trasera.setImageResource(R.drawable.distanceback);
                            }
                        }
                    };
                    at.execute();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //Menu toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu)    {
        //Inflate the menu; this adds items to the action bar if it is present
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_park:
                try {
                    outBT.write("Pause\r\n");
                    outBT.flush();
                    outBT.write("Parking\r\n");
                    outBT.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                parking();
                return true;
            case R.id.action_path:
                try {
                    outBT.write("Pause\r\n");
                    outBT.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                path();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == TAKE_A_RESULT) {
            if(resultCode == RESULT_OK)
            {
                path = data.getStringExtra("path");
            }
        }
    }

    public void showDialoog(final int positionOfTextview){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.select_dialog_singlechoice);
        arrayAdapter.add("Up");
        arrayAdapter.add("Down");
        arrayAdapter.add("Right");
        arrayAdapter.add("Left");

        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String strName = arrayAdapter.getItem(which);

                if(strName.equals("Up")){
                    directions.get(positionOfTextview).setDirection("Up");
                }
                if(strName.equals("Down")){
                    directions.get(positionOfTextview).setDirection("Down");
                }

                if(strName.equals("Right")){
                    directions.get(positionOfTextview).setDirection("Right");
                }

                if(strName.equals("Left")){
                    directions.get(positionOfTextview).setDirection("Left");
                }

                adapter.notifyDataSetChanged();
            }
        });
        builder.show();
    }

    @Override
    public void onClickListener(int position) {
        showDialoog(position);
        checkButton();
    }

    @Override
    public void onChangeSeconds(int position, String newSec) {
        directions.get(position).setSecond(newSec);
        checkButton();
    }

    private void addDirection()
    {
        directions.add(new Directions());
        adapter.notifyDataSetChanged();
    }

    private void checkButton()
    {
        for(int i = 0; i < directions.size(); i++)
        {
           if( isNull(directions.get(i).getSeconds()) || isNull(directions.get(i).getDirection())
                    || directions.get(i).getDirection().equals("CHOICE"))
           {
               run.setEnabled(false);
               return;
           }
        }
        run.setEnabled(true);
    }
}