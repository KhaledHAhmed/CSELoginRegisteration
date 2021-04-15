package com.example.cselogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class Register extends AppCompatActivity {

//    private EditText First_Name_value;
    private FirebaseAuth mAuth;

    EditText First_Name_value, Last_Name_value, Email_value, Username_value, Password_value, PhoneNumber;
    Button Register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

//        EditText First_Name_value, Last_Name_value, Email_value, Username_value, Password_value, PhoneNumber;
//        Button Register;

//        First_Name_value = (EditText) findViewById(R.id.FirstName);
//        Last_Name_value = (EditText) findViewById(R.id.LasttName);
//        Email_value = (EditText) findViewById(R.id.Email);
//        Username_value = (EditText) findViewById(R.id.Username);
//        Password_value = (EditText) findViewById(R.id.password);
//        PhoneNumber = (EditText) findViewById(R.id.PhoneNumber);

        Register = (Button) findViewById(R.id.Register);


        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RegesterUser();

            }
        });

    }

    private void RegesterUser() {

        String FirstName, LastName, Username, Password, Email, Phonenumber;

        First_Name_value = (EditText) findViewById(R.id.FirstName);
        Last_Name_value = (EditText) findViewById(R.id.LasttName);
        Email_value = (EditText) findViewById(R.id.Email);
        Username_value = (EditText) findViewById(R.id.Username);
        Password_value = (EditText) findViewById(R.id.password);
        PhoneNumber = (EditText) findViewById(R.id.PhoneNumber);


        FirstName = First_Name_value.getText().toString();
        LastName = Last_Name_value.getText().toString().trim();
        Username = Username_value.getText().toString();
        Password = Password_value.getText().toString();
        Email = Email_value.getText().toString();
        Phonenumber = PhoneNumber.getText().toString();

        if ( FirstName.isEmpty() )
        {
            First_Name_value.setError("First Name is Required!");
            First_Name_value.requestFocus();
            return;
        }

        if ( LastName.isEmpty() )
        {
            Last_Name_value.setError("Last Name is Required!");
            Last_Name_value.requestFocus();
            return;
        }

        if ( Phonenumber.isEmpty() )
        {
            PhoneNumber.setError("Username is Required!");
            PhoneNumber.requestFocus();
            return;
        }

        if ( Email.isEmpty() )
        {
            Email_value.setError("Email is Required!");
            Email_value.requestFocus();
            return;
        }

        if ( !Patterns.EMAIL_ADDRESS.matcher(Email).matches() )
        {
            Email_value.setError("Please provide valid email");
            Email_value.requestFocus();
            return;
        }

        if ( Username.isEmpty() )
        {
            Username_value.setError("Username is Required!");
            Username_value.requestFocus();
            return;
        }

        if ( Password.isEmpty() )
        {
            Password_value.setError("Password is Required!");
            Password_value.requestFocus();
            return;
        }

        if ( Password.length() < 8 )
        {
            Password_value.setError("Min Password length should be 8 characters");
            Password_value.requestFocus();
            return;
        }

            // progressBar
        mAuth.createUserWithEmailAndPassword(Email,Password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if ( task.isSuccessful() )
                            {
                                User user = new User (FirstName, LastName, Username, Password, Email, Phonenumber);

                                FirebaseDatabase.getInstance().getReference( "Users")
                                        .child( FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue( user ).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if ( task.isSuccessful() )
                                        {
                                            Toast toast = Toast.makeText(getApplicationContext(), "User has been registered successfully!", Toast.LENGTH_LONG);
                                            toast.show();
                                            Intent downloadIntent = new Intent(getApplicationContext(), MainActivity.class);
                                            startActivity(downloadIntent);
                                        }
                                        else
                                        {
                                            // unsuccessful login
                                            Toast toast = Toast.makeText(getApplicationContext(), "Failed to register try again ", Toast.LENGTH_LONG);
                                            toast.show();
                                        }

                                    }
                                });
                            }
                            else
                            {
                                // unsuccessful login
                                Toast toast = Toast.makeText(getApplicationContext(), "Failed to register try again ", Toast.LENGTH_LONG);
                                toast.show();
                            }
                        }
                    });





//        if (  Password.length() > 8 )
//        {
//            // successful login
//            Toast toast = Toast.makeText(getApplicationContext(), "Succsee", Toast.LENGTH_SHORT);
//            toast.show();
//            Intent downloadIntent = new Intent(getApplicationContext(), MainActivity.class);
//            startActivity(downloadIntent);
//        }
//        else
//        {
//            // unsuccessful login
//            Toast toast = Toast.makeText(getApplicationContext(), "UnSuccsee: Password must be at least 8 charecter", Toast.LENGTH_SHORT);
//            toast.show();
//        }


    }
}