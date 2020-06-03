package com.example.recognition_crops;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class eCommerce extends AppCompatActivity {
    Button buy, sell;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_e_commerce );
        buy = findViewById( R.id.buy );
        sell = findViewById( R.id.sell );
        sell.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                PackageManager manager=getPackageManager();
                try {
                    intent=manager.getLaunchIntentForPackage( "com.example.codebyleela.selling" );
                    assert intent != null;
                    intent.addCategory( Intent.CATEGORY_LAUNCHER);
                    startActivity( intent );
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } );
        buy.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( eCommerce.this ,
                        buyActivity.class );
                startActivity( intent );
            }
        } );

    }



}

