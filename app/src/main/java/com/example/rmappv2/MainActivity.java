package com.example.rmappv2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Random;

@RequiresApi(api = Build.VERSION_CODES.O)
public class MainActivity extends AppCompatActivity implements Serializable {
    SharedPreferences sharedPreferences;
    Button playButton;
    Button nextActivity;
    ImageView mainImage;
    int arrayAddress =0; // should be changed to -1 if using the pathList approach
    LocalDate lastPlayed = LocalDate.of(1969,07,20);

    //private PathList pathList = new PathList();

    // TO REMOVE - FOR TESTING PURPOSES !!!!
    TextView testBox;
    int arrayAddressTest =-10;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playButton = (Button) findViewById(R.id.playButton);
        nextActivity = (Button) findViewById(R.id.nextActivity);
        mainImage = (ImageView) findViewById(R.id.imageView);
        mainImage.setImageURI(null);

        // The following calls the arrayAddress and lastPlayed from the shared preferences
        sharedPreferences = getSharedPreferences("mySettings",Context.MODE_PRIVATE);
        arrayAddress = sharedPreferences.getInt("saved_address",0);
        String dateSaved = sharedPreferences.getString("saved_date","1969-07-20");
        lastPlayed = LocalDate.parse(dateSaved);

        // TO REMOVE - FOR TESTING PURPOSES !!!!!!
        testBox = (TextView) findViewById(R.id.testView);
    }

    // This method compares a date to the current date
    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean testDateConditions(LocalDate dateTest) {
        LocalDate dnow = LocalDate.now();
        if (dnow.compareTo(dateTest)==0) {
            return true;
        } else {
            return false;
        }
    };

    // This method runs the "Play" Button (tests conditions and then displays an image or video)
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void playAction (View v) {
       //This part tests whether or not a video has been played today (if not, it iterates to return the next image or video)
        if (testDateConditions(lastPlayed)==false) {
            try {
                String[] f = getAssets().list("");
                if (arrayAddress < f.length) {
                    arrayAddress = arrayAddress + 1;
                    lastPlayed = LocalDate.now();
                }
            } catch (IOException e ) { }
        }


        // The following saves the arrayAddress and date for use when app is restarted
        sharedPreferences = getSharedPreferences("mySettings",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("saved_address", arrayAddress);
        editor.putString("saved_date", lastPlayed.toString());
        editor.commit();


        // This part returns the path address from the path array based on the number called
        //String imageName = pathList.getPath(arrayAddress);
        try {
            String [] f = getAssets().list("");
            String imageName = f[arrayAddress];
            // 2 above lines can be deleted and replaced with the greyed imageName string based on pathList.
            // If it works, pathList is no longer really necessary but the max condition needs to be replaced
            InputStream is = getAssets().open(imageName);
            Drawable d = Drawable.createFromStream(is,null);
            mainImage.setImageDrawable(d);
        } catch (IOException e ) { }


        // TO REMOVE - FOR TESTING PURPOSES !!!!!
        sharedPreferences = getSharedPreferences("mySettings",Context.MODE_PRIVATE);
        arrayAddressTest = sharedPreferences.getInt("saved_address",-100);
        testBox.setText(arrayAddressTest+"");
    };

    public void goHistorique (View v) {
        Intent intent = new Intent(this, HistoriqueActivity.class);
        startActivity(intent);
    };

}
