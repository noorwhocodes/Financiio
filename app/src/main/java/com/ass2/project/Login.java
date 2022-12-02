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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Login extends AppCompatActivity {
    EditText email,password;

    private static final String url ="http://192.168.18.40/project/get_login.php";

    TextView signIn;
    TextView create_one;
    TextView forgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signIn = findViewById(R.id.signInTVLogIn);
        create_one = findViewById(R.id.createAccountTVLogIn);
        forgotPassword = findViewById(R.id.mainActivityForgotPassword);

        email = findViewById(R.id.emailETLogin);
        password = findViewById(R.id.passwordETLogin);


        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!email.getText().toString().trim().equals("") &&
                        !password.getText().toString().trim().equals("")) {
                    getdata();
                } else{
                    email.forceLayout();
                    password.forceLayout();
                    Toast.makeText(Login.this, "Please Input All Fields", Toast.LENGTH_SHORT).show();
                }
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
    }

    private void getdata() {

        final String e =email.getText().toString().trim();
        final String p =password.getText().toString().trim();


        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //volley sy jo response araha woh store kar rhy iss mein
                Toast.makeText(Login.this, "Logged In", Toast.LENGTH_SHORT).show();
                Toast.makeText(Login.this,response.toString(), Toast.LENGTH_LONG).show();

                ArrayList<String> resultResponse=stringTok(response.toString(), ",");
                if(resultResponse.get(1).equals("\"resultcode\":1")) {
                    startActivity(new Intent(Login.this, home_page.class));
                    finish();
                }

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

    private ArrayList<String> stringTok(String s, String delim){
        StringTokenizer stringTokenizer = new StringTokenizer(s, delim);
        ArrayList<String> arrayList=new ArrayList<String>();
        while (stringTokenizer.hasMoreTokens()) {
            arrayList.add(stringTokenizer.nextToken());
        }
        return arrayList;
    }
}