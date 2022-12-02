package com.ass2.project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    EditText email,password;
    ImageView login;

    private static final String url ="http://192.168.100.82/loginsignup/get.php";

    TextView signIn;
    TextView create_one;
    TextView forgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signIn = findViewById(R.id.signIn);
        create_one = findViewById(R.id.idcreateone);
        forgotPassword = findViewById(R.id.mainActivityForgotPassword);

        email = findViewById(R.id.idemail);
        password = findViewById(R.id.idpwd);

        login = findViewById(R.id.idlogin);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this,home_page.class));
            }
        });
        create_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this,signup.class));
            }
        });
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this,ForgotPassword.class));
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getdata();
                Toast.makeText(Login.this, "loginned", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Login.this,MainActivity.class));
            }
        });
    }

    private void getdata() {

        final String e =email.getText().toString().trim();
        final String p =password.getText().toString().trim();


        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //volley sy jo response araha woh store kar rhy iss mein
                email.setText("");
                password.setText("");
                Toast.makeText(Login.this,response.toString(), Toast.LENGTH_LONG).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Login.this,error.toString(), Toast.LENGTH_LONG).show();

            }
        }){

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param = new HashMap<String,String>();

                //yeh decide horaha ke jo php file mei values receive horin woh yeh hain
                param.put("email",e);
                param.put("password",p);


                return param;
            }
        };

        RequestQueue queue= Volley.newRequestQueue(Login.this);
        queue.add(request);
    }
}