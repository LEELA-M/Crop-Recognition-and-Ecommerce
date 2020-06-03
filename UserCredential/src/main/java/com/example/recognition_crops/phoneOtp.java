package com.example.recognition_crops;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class phoneOtp extends AppCompatActivity {
    public static final String TAG = "TAG";
    FirebaseAuth fAuth;
    EditText pnumber,codeenter;
    Button next;
    ProgressBar pbar;
    TextView state;
    CountryCodePicker picker;
    String verify;
    PhoneAuthProvider.ForceResendingToken Token;
    Boolean verficationInProgress=false;
    Button register;
    EditText name,password,Email;
    FirebaseDatabase database;
    DatabaseReference ref;
    User user;
    private ProgressDialog loadingBar;
    //long id=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_phone_otp );
        fAuth=FirebaseAuth.getInstance();
        pnumber=findViewById( R.id.phone );
        codeenter=findViewById( R.id.codeEnter );
        pbar=findViewById( R.id.progressBar );
        next=findViewById( R.id.nextBtn );
        state=findViewById( R.id.state );
        picker=findViewById( R.id.ccp );
        name=findViewById( R.id.editText5 );
        Email=findViewById( R.id.editText4 );
        password=findViewById( R.id.editText7 );
//        gender=findViewById( R.id.editText);
        database=FirebaseDatabase.getInstance();
        user=new User();
        ref=database.getReference().child( "User" );

        register=findViewById( R.id.reg );
//        register.setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                createAccount();
//            }
//        } );

        register.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Long pass=Long.parseLong( password.getText().toString() );
                user.setPassword( pass );
                String ph="+"+picker.getSelectedCountryCode()+pnumber.getText().toString();
                user.setPhone( ph );
                String email=Email.getText().toString();
                user.setEmail( email );
                user.setName( name.getText().toString() );
                ref.child(( "User" ) ).setValue( user );
                //id=id+1;
                ref.push().setValue( user );
                fAuth.createUserWithEmailAndPassword( Email.getText().toString(),
                        password.getText().toString() ).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            fAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener( new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                    Toast.makeText( phoneOtp.this,"Registered sucessfully.Please check email for verification",Toast.LENGTH_LONG ).show();
                                    Email.setText( "" );
                                    password.setText( "" );
                                    }
                                    else {
                                        Toast.makeText( phoneOtp.this,task.getException().getMessage(),Toast.LENGTH_LONG ).show();
                                       }
                                }

                            } );
                        }else {
                            Toast.makeText( phoneOtp.this,task.getException().getMessage(),Toast.LENGTH_LONG ).show();

                        }

                    }
                } );
                Intent intent=new Intent( phoneOtp.this,Log_in.class );
                    startActivity( intent );
            }
        } );
        next.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!verficationInProgress)
                {
                if(!pnumber.getText().toString().isEmpty() && pnumber.getText().toString().length()==10)
                {
                    String ph="+"+picker.getSelectedCountryCode()+pnumber.getText().toString();


                    Log.d( TAG ,
                            "onClick: Phone No"+ph);
                    pbar.setVisibility( View.VISIBLE );
                    state.setText( "Sending OTP..." );
                    state.setVisibility( View.VISIBLE );
                    requestOTP(ph);
                }
                else{
                    pnumber.setError( "Phone number is not valid" );
                }
                }
                else{
                   String userOtp=codeenter.getText().toString();
                   if(!userOtp.isEmpty()&&userOtp.length()==6)
                   {
                       PhoneAuthCredential credential=PhoneAuthProvider.getCredential( verify,userOtp );
                       verifyAuth(credential);
                   }
                   else{
                       codeenter.setError( "Invalid OTP" );
                   }
                }
            }
        } );
    }

//    private void createAccount() {
//        String Inname=name.getText().toString();
//        String phone=pnumber.getText().toString();
//        String Pass=password.getText().toString();
//        if(TextUtils.isEmpty( Inname )){
//            Toast.makeText( this,"please enter your name",Toast.LENGTH_SHORT).show();
//        }
//        else if(TextUtils.isEmpty( phone )){
//            Toast.makeText( this,"please enter your phone ",Toast.LENGTH_SHORT).show();
//        } else if(TextUtils.isEmpty( Pass )){
//            Toast.makeText( this,"please enter your password ",Toast.LENGTH_SHORT).show();
//        }
//        else {
//            loadingBar.setTitle( "Create Account" );
//            loadingBar.setMessage( "Please wait" );
//            loadingBar.setCanceledOnTouchOutside( false );
//            loadingBar.show();
//            ValidateNumber(Inname,Email,phone,Pass);
//        }
//
//        }
//
//    private void ValidateNumber(final String inname , final EditText email , final String phone , final String pass) {
//        final DatabaseReference Rootref;
//        Rootref= FirebaseDatabase.getInstance().getReference();
//        Rootref.addListenerForSingleValueEvent( new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if(!(dataSnapshot.child("Users").child(phone).exists())){
//                    HashMap<String,Object> UserData=new HashMap<>();
//                    UserData.put( "name",inname);
//                    UserData.put( "email",email);
//                    UserData.put( "phone",phone);
//                    UserData.put( "password",pass);
//                 Rootref.child( "Users").child( phone ).updateChildren( UserData).addOnCompleteListener(
//                         new OnCompleteListener<Void>() {
//                             @Override
//                             public void onComplete(@NonNull Task<Void> task) {
//                                    if(task.isSuccessful()){
//                                        Toast.makeText( phoneOtp.this,"Account Created sucessfully ",Toast.LENGTH_SHORT).show();
//                                        loadingBar.dismiss();
//
//                                        Intent intent=new Intent( phoneOtp.this,Log_in.class );
//                                        startActivity( intent);
//                                    }
//                                    else{
//                                        loadingBar.dismiss();
//                                        Toast.makeText( phoneOtp.this,"Try Again Later ",Toast.LENGTH_SHORT).show();
//
//                                    }
//                             }
//                         }
//                 );
//                }
//                else
//                {
//                    Toast.makeText( phoneOtp.this," Create Account Already exist",Toast.LENGTH_SHORT).show();
//                    loadingBar.dismiss();
//
//                    Intent intent=new Intent( phoneOtp.this,Log_in.class );
//                    startActivity( intent);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        } );
//
//    }
//

    private void verifyAuth(PhoneAuthCredential credential) {
        fAuth.signInWithCredential( credential ).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText( phoneOtp.this,"Authentication Successfull" ,Toast.LENGTH_SHORT).show();
                    next.setText( "Verified" );
                }
                else {
                    Toast.makeText( phoneOtp.this,"Authentication Failed" ,Toast.LENGTH_SHORT).show();
                }
            }
        } );
    }

    private void requestOTP(String ph) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber( ph ,
                60L ,
                TimeUnit.SECONDS ,
                this ,
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onCodeSent(@NonNull String s , @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent( s ,
                                forceResendingToken );
                        pbar.setVisibility( View.GONE );
                        //state.setText( "Sending OTP..." );
                        state.setVisibility( View.GONE );
                        codeenter.setVisibility( View.VISIBLE );
                        verify=s;
                        Token=forceResendingToken;
                        next.setText( "Verify" );

                        verficationInProgress=true;

                    }

                    @Override
                    public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
                        super.onCodeAutoRetrievalTimeOut( s );
                    }

                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        Toast.makeText( phoneOtp.this,"Cannot send. please try again!!"+e.getMessage(),Toast.LENGTH_SHORT ).show();
                    }
                } );
    }

}
