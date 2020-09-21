package com.leela.crops_recognition;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
public class Home_Page extends AppCompatActivity {
    Button button,identify,Market;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_home__page );
       button=findViewById( R.id.button2 );
        Market=findViewById( R.id.button1);
       identify=findViewById( R.id. button);
       button.setOnClickListener( new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent=new Intent( Home_Page.this,Log_in.class );
               startActivity( intent );
           }
       } );
        identify.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                PackageManager manager=getPackageManager();
                try {
                    intent=manager.getLaunchIntentForPackage( "org.CodeByLeela.CropRecognition" );
                    assert intent != null;
                    intent.addCategory( Intent.CATEGORY_LAUNCHER);
                    startActivity( intent );
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } );
        Market.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent( Home_Page.this,eCommerce.class );
                startActivity( intent );
            }
        } );
    }
}