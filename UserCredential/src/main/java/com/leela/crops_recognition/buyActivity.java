package com.leela.crops_recognition;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class buyActivity extends AppCompatActivity {
    GridView gridView;

    String[] CropNames = {"Rice","Cabbage","Chill","Brinjal","Maize","Pumpkin","Sorghum","Bitter"};
    int[] CropImages = {R.drawable.rice,R.drawable.cabbage,R.drawable.chilli,R.drawable.eggplant,R.drawable.maize,R.drawable.pumpkin,R.drawable.sorghum,R.drawable.bitter};

    String[] price={"60","30","40","20","50","50","35","40"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_buy );
        gridView = findViewById(R.id.gridview);
        CustomAdapter customAdapter = new CustomAdapter();
        gridView.setAdapter(customAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(getApplicationContext(),fruitNames[i],Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(),ListingEverthing.class);
                intent.putExtra("name",CropNames[i]);
                intent.putExtra("image",CropImages[i]);
                intent.putExtra("Cost", price[i]);
                startActivity(intent);

            }
        });
    }

    private class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return CropImages.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i , View convertView , ViewGroup parent) {
            View view=getLayoutInflater().inflate( R.layout.rowdata,null );
            TextView name = view.findViewById(R.id.crops);
            ImageView image = view.findViewById(R.id.images);
            TextView Cost = view.findViewById(R.id.cost);
            name.setText(CropNames[i]);
            image.setImageResource(CropImages[i]);
            Cost.setText(price[i]);
            return view;

        }


    }
}
