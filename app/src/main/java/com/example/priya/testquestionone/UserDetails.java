package com.example.priya.testquestionone;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.priya.testquestionone.pojo.Response;
import com.example.priya.testquestionone.rest.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;

import static com.example.priya.testquestionone.MainActivity.devicetoken;
import static com.example.priya.testquestionone.MainActivity.devicetype;
import static com.example.priya.testquestionone.MainActivity.lat;
import static com.example.priya.testquestionone.MainActivity.log;
import static com.example.priya.testquestionone.MainActivity.social_type;

public class UserDetails extends AppCompatActivity {
    EditText etFirstName, etLastName, etEmail, etPwd, etCpwd, etPhone;
    String id, name, gender, location, dob, firstname, lastname, email,profile,phone;
    Button btnRegister;
    Spinner spinner;
    final static String usertype="1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        etFirstName = (EditText) findViewById(R.id.etFirstName);
        etLastName = (EditText) findViewById(R.id.etLastName);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPwd = (EditText) findViewById(R.id.etPwd);
        etCpwd = (EditText) findViewById(R.id.etCpwd);
        etPhone = (EditText) findViewById(R.id.etPhone);
        btnRegister=(Button)findViewById(R.id.btnRegister);
        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter country = ArrayAdapter.createFromResource(this, R.array.country_code, android.R.layout.simple_spinner_item);
        country.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(country);

        id = getIntent().getStringExtra("userId");
        name = getIntent().getStringExtra("userName");
        gender = getIntent().getStringExtra("gender");
        location = getIntent().getStringExtra("location");
        dob = getIntent().getStringExtra("dob");
        email = getIntent().getStringExtra("email");
        profile = getIntent().getStringExtra("profile");
        String[] splited = name.split("\\s+");
        firstname = splited[0];
        lastname = splited[1];
        etFirstName.setText(firstname);
        etLastName.setText(lastname);
        etEmail.setText(email);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                 hitApi();
                }
            }
        });
    }
    private boolean validate() {
        String email = etEmail.getText().toString();
        String pwd = etPwd.getText().toString();
       String cPwd = etCpwd.getText().toString();
       phone= etPhone.getText().toString();
        if (TextUtils.isEmpty(etFirstName.getText().toString())) {
            etFirstName.setError("Plz Enter Your First Name");
        } else if (TextUtils.isEmpty(etLastName.getText().toString())) {
            etLastName.setError("Plz Enter Your Last Name");
        } else if (TextUtils.isEmpty(etEmail.getText().toString())) {
            etEmail.setError("Plz Enter you Email");
        } else if (!email.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
            etEmail.setError("Invalid Email");
        } else if (TextUtils.isEmpty(etPwd.getText().toString())) {
            etPwd.setError("Please Fill Field");
        } else if (TextUtils.isEmpty(etCpwd.getText().toString())) {
            etCpwd.setError("Please Fill Field");
        } else if (!pwd.equals(cPwd)) {
            etCpwd.setError("ReEnter Your Password");
        } else if (TextUtils.isEmpty(etPhone.getText().toString())) {
            etPhone.setError("Please Fill Field");
        } else if (etPhone.length() < 10) {
            etPhone.setError("Invalid Phone Number");
        }
        else{
            return true;
        }
        return true;
    }
    void hitApi(){
        ApiClient.getApiServiceSinUp().insertUser(firstname,lastname,etEmail.getText().toString(),devicetype,devicetoken,lat,log,phone,usertype,id,social_type,profile)
                .enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                        Log.e("message",response.body()+"");
                        String message=response.body().getMessage();
                        String status=response.body().getStatus();
                        Log.e("message",message);
                        Log.e("message",status);
                        if(status.equals("success")){
                            Log.e("SUCCESS","SUCCESS");
                        }
                        else
                            Log.e("MESSAGE","API HIT FAILS...");
                    }@Override
                    public void onFailure(Call<Response> call, Throwable t) {

                    }
                });
    }
}