package com.example.recognition_crops;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class ListingEverthing extends AppCompatActivity {
    TextView name;
    ImageView images;
    int quantity;
    String Cost;
    int cost;
String Name;
    TextView  price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_listing_everthing );

        name = findViewById( R.id.crops );
        images = findViewById( R.id.image );
        price = findViewById( R.id.cost);
        Intent intent = getIntent();
        name.setText( intent.getStringExtra( "name" ) );
        images.setImageResource( intent.getIntExtra( "image" , 0 ) );
        price.setText( intent.getStringExtra( " price " ) );
        Name=name.getText().toString();

//        Cost=price.getText().toString();
//        if(Cost.matches("\\d+")) //check if only digits. Could also be text.matches("[0-9]+")
//        {
//            cost = Integer.parseInt(Cost);
//            System.out.println("number"+cost);
//        }
//        else
//        {
//            System.out.println("not a valid number");
//        }


    }
    public void decrement(View view) {
        quantity=quantity-1;
        displayquantity(quantity);

    }

    private void displayquantity(int quantity) {
        EditText quantity1 = (EditText) findViewById(R.id.quantityTxt);
        quantity1.setText(""+quantity);
    }
    public void increment(View view) {
        quantity = quantity + 1;
        displayquantity(quantity);
    }
    private void displayMessage(String finalMessage) {
        TextView Message = (TextView) findViewById(R.id.resultTxt);
        Message.setText(""+finalMessage);
    }
    private String createFinalMessage(int price) {  //Boolean Crop ,
        String Message = "Product:"+Name+"\n"+"Total Price $: "+price;
        return  Message;


    }

    private int calculatePrice(Boolean Crops) {
        int price =50;
        if (Crops)
        {
            price =price+2;
        }
        return price*quantity;
    }


    public void orderButton(View view) {

        CheckBox Crops = (CheckBox) findViewById(R.id.chocalate_checkbox);
        Boolean Crop = Crops.isChecked();
        int price = calculatePrice(Crop);
        String finalMessage =createFinalMessage (price);//Crop,
        displayMessage(finalMessage);
    }
}
