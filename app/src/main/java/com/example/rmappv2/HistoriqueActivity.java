package com.example.rmappv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;

public class HistoriqueActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    int imageMax;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historique);

        LinearLayout gallery = findViewById(R.id.gallery);
        LayoutInflater inflater = LayoutInflater.from(this);

        sharedPreferences = getSharedPreferences("mySettings",Context.MODE_PRIVATE);
        imageMax = sharedPreferences.getInt("saved_address",4);

        for (int i = 1; i<=imageMax; i++) {
            try {
                String[] f = getAssets().list("");
                View view = inflater.inflate(R.layout.item,gallery,false);
                ImageView imageView = view.findViewById(R.id.imageView2);

                String imageName = f[i];
                InputStream is = getAssets().open(imageName);
                Drawable d = Drawable.createFromStream(is,null);
                imageView.setImageDrawable(d);

                gallery.addView(view);

            } catch (IOException e ) { }
        }
    }

    public void goMain (View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    };

}
