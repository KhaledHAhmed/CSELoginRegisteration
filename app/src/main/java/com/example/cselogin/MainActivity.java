package com.example.cselogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.content.Intent;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText Username_value,Password_value;
    Button Login;
    Button Register;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        Login = (Button) findViewById(R.id.Login);
        Register = (Button) findViewById(R.id.Register);
        progressBar = (ProgressBar) findViewById(R.id.ProgressBar);


        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userlogin();
                

            }
        });

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent downloadIntent = new Intent(getApplicationContext(), Register.class);
                startActivity(downloadIntent);
            }
        });


    }

    private void userlogin() {


        Username_value = (EditText) findViewById(R.id.Username);
        Password_value = (EditText) findViewById(R.id.password);

        String Username, Password;

        Username = Username_value.getText().toString().trim();
        Password = Password_value.getText().toString().trim();

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

        // some validation like Email check and length of the password

       // progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(Username,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if ( task.isSuccessful() )
                {
                    Toast toast = Toast.makeText(getApplicationContext(), "Succsee", Toast.LENGTH_LONG);
                    toast.show();
                    Intent downloadIntent = new Intent(getApplicationContext(), HomeScreen.class);
                    startActivity(downloadIntent);
                }
                else
                {
                    // unsuccessful login
                    Toast toast = Toast.makeText(getApplicationContext(), "Wrong Username or Password, Try again", Toast.LENGTH_LONG);
                    toast.show();
                }

            }
        });


//        if ( Username.matches("ABC") &&  Password.matches("123") )
//        {
//            // successful login
//            Toast toast = Toast.makeText(getApplicationContext(), "Succsee", Toast.LENGTH_LONG);
//            toast.show();
//            Intent downloadIntent = new Intent(getApplicationContext(), HomeScreen.class);
//            downloadIntent.putExtra( "Username", Username);
//            startActivity(downloadIntent);
//        }
//        else
//        {
//            // unsuccessful login
//            Toast toast = Toast.makeText(getApplicationContext(), "Wrong Username or Password, Try again", Toast.LENGTH_LONG);
//            toast.show();
//        }

    }
}