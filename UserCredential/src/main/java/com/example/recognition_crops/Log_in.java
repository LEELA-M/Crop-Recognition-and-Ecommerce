package com.example.recognition_crops;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.List;

public class Log_in extends AppCompatActivity {
    Button button;
    EditText Email,Password;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog loadingBar;
    private String parentDBname="Users";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_log_in );
        firebaseAuth=FirebaseAuth.getInstance();
        Email=findViewById( R.id.editText2 );
        //phone =findViewById( R.id.editText2 );
        Password=findViewById( R.id.editText3 );
    button=findViewById( R.id. button5);
    loadingBar=new ProgressDialog( this );
//    button.setOnClickListener( new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            loginUser();
//        }
//    } );
    button.setOnClickListener( new View.OnClickListener() {
        @Override
        public void onClick(View v) {
                    if(Email.getText().toString().isEmpty()){
                       Email.setError( "Please enter valid email" );
                    }
            if(Password.getText().toString().isEmpty()){
                Password.setError( "Please enter valid email" );
            }
            firebaseAuth.signInWithEmailAndPassword( Email.getText().toString(),
                    Password.getText().toString() ).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener( new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                   if(firebaseAuth.getCurrentUser().isEmailVerified()){
                                       Toast.makeText( Log_in.this,"Logging...",Toast.LENGTH_LONG ).show();

                                       startActivity( new Intent( Log_in.this,Home_Page.class ) );
                                    }
                                   else {
                                       Toast.makeText( Log_in.this,task.getException().getMessage(),Toast.LENGTH_LONG ).show();

                                   }
                                }
                                else {
                                    Toast.makeText( Log_in.this,"Please verify your email",Toast.LENGTH_LONG ).show();
                                }
                            }

                        } );
                    }else {
                        Toast.makeText( Log_in.this,task.getException().getMessage(),Toast.LENGTH_LONG ).show();

                    }

                }
            } );

        }
    } );
   }

//    private void loginUser() {
//        String pnumber=phone.getText().toString();
//        String password=Password.getText().toString();
//        if(TextUtils.isEmpty( pnumber )){
//            Toast.makeText( this,"please enter your number",Toast.LENGTH_SHORT).show();
//        }
//        else if(TextUtils.isEmpty( password )){
//            Toast.makeText( this,"please enter yourpassword ",Toast.LENGTH_SHORT).show();
//        }
//        else{
//            loadingBar.setTitle( "Login Account" );
//            loadingBar.setMessage( "Please wait" );
//            loadingBar.setCanceledOnTouchOutside( false );
//            loadingBar.show();
//
//            AllowAccess(pnumber,password);
//        }
//    }
//
//    private void AllowAccess(final String pnumber , final String password) {
//        final DatabaseReference Rootref;
//        Rootref= FirebaseDatabase.getInstance().getReference();
//
//        Rootref.addListenerForSingleValueEvent( new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//    if(dataSnapshot.child( parentDBname ).child( pnumber ).exists()){
//            User userdata=dataSnapshot.child( parentDBname ).child( pnumber ).getValue(User.class);
//            if(userdata.getPhone().equals( pnumber )){
//                if(userdata.getPassword().equals( password )){
//                    Toast.makeText( Log_in.this,"Logging successfully",Toast.LENGTH_SHORT).show();
//                    loadingBar.dismiss();
//                    Intent intent=new Intent( Log_in.this,Home_Page.class );
//                    startActivity( intent);
//                }
//            }
//    }
//    else{
//        Toast.makeText( Log_in.this,"First Create Account",Toast.LENGTH_SHORT).show();
//loadingBar.dismiss();
//    }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//            }
//        } );{
//
//        }
//  }
}
