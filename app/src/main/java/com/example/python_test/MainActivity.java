package com.example.python_test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView paste ;
    Button button ;
    EditText Current , date ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        paste = findViewById(R.id.paste);
        button = findViewById(R.id.button);

        Current = findViewById(R.id.etX);
        date = findViewById(R.id.etY);

        // "context" must be an Activity, Service or Application object from your app.
        if (! Python.isStarted())
        {
            Python.start(new AndroidPlatform(this));
        }


        Python py = Python.getInstance();
        PyObject pyobj = py.getModule("myscript");

//        PyObject obj = pyobj.callAttr("main");
//
//        paste.setText(obj.toString());


        ArrayList<String> list_Current=new ArrayList<>();
        list_Current=getListFromLocal("Current");
        ArrayList<String> list_date=new ArrayList<>();
        list_date=getListFromLocal("date");

//        Toast.makeText(MainActivity.this, "size"+list_Current.size(), Toast.LENGTH_SHORT).show();


        ImageView image = (ImageView) findViewById(R.id.imageView);

        image.post(new Runnable() {
            @Override
            public void run()
            {


//                PyObject np = py.getModule("numpy");
//                PyObject anchors_final = np.callAttr("array", anchors[0]);
//                anchors_final = np.callAttr("expand_dims", anchors_final, 0);
//                for (int i=1; i < anchors.length; i++){
//                    PyObject temp_arr = np.callAttr("expand_dims", anchors[i], 0);
//                    anchors_final = np.callAttr("append", anchors_final, temp_arr, 0);
//                }


                int[] myNum = {2,3 ,4,5 ,6 ,7, 8, 9, 10 , 11 , 14, 15 , 17 , 21,22 ,23};

                double[] myNum2 = {1279.0 ,1280.6 ,1281.7,1283.1 ,1284.1 ,1285.8, 1286.6,1288.0 ,1289.2,
                        1290.2 ,1294.7 , 1295.8 , 1298.0 , 1299.2 ,1300.2, 1301.9};

                byte[] byteArray =  pyobj.callAttr("plot", myNum , myNum2 ).toJava(byte[].class);

                Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

                image.setImageBitmap(Bitmap.createScaledBitmap(bmp, image.getWidth(), image.getHeight(), false));

            }
        });




        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<String> listSave_current=new ArrayList<>();
                listSave_current.add(Current.getText().toString());
                Saved_data_Current( listSave_current );

                ArrayList<String> listSave_date=new ArrayList<>();
                listSave_date.add(date.getText().toString());
                Saved_data_date( listSave_date );


                ArrayList<String> list_Current=new ArrayList<>();
                list_Current=getListFromLocal("Current");
                ArrayList<String> list_date=new ArrayList<>();
                list_date=getListFromLocal("date");


                ArrayList<String> finalList_Current = list_Current;
                ArrayList<String> finalList_date = list_date;

                image.post(new Runnable() {
                    @Override
                    public void run()
                    {
                        PyObject array1 = PyObject.fromJava(finalList_Current);
                        PyObject array2 = PyObject.fromJava( finalList_date );

                        byte[] byteArray =  pyobj.callAttr("plot",array1 , array2).toJava(byte[].class);

                        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

                        image.setImageBitmap(Bitmap.createScaledBitmap(bmp, image.getWidth(), image.getHeight(), false));

                    }
                });

            }
        });





    }

    private void Saved_data_Current( ArrayList<String> list )
    {
        SharedPreferences prefs = getSharedPreferences("AppName", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString("Current", json);
        editor.apply();     // This line is IMPORTANT !!!


    }

    private void Saved_data_date(  ArrayList<String> list ) {

        SharedPreferences prefs = getSharedPreferences("AppName", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson( list );
        editor.putString("date", json);
        editor.apply();     // This line is IMPORTANT !!!


    }


    public ArrayList<String> getListFromLocal(String key)
    {
        SharedPreferences prefs = getSharedPreferences("AppName", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        return gson.fromJson(json, type);

    }


}