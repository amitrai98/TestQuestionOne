package com.example.priya.testquestionone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.priya.testquestionone.pojo.Response;
import com.example.priya.testquestionone.rest.ApiClient;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity{
    Button btnBuyer, btnCarrier;

    LoginButton loginButton;
    CallbackManager callbackManager;
    String userName, gender, location, dob, userId, profile, email = "priya123@gmail.com";
    final static String social_type = "fb";
    final static String devicetoken = "123456";
    final static String devicetype = "Android";
    final static String lat = "30.88";
    final static String log = "89.00";
    final static String lastname = "fbUser";

    public static boolean isTwitter_login = false;
    public static boolean isFacebook_login = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginButton = (LoginButton)findViewById(R.id.loginButton);
        btnBuyer = (Button) findViewById(R.id.btnBuyer);
        btnCarrier = (Button) findViewById(R.id.btnCarrier);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        BuyerFragment buyerFragment = new BuyerFragment();
        fragmentTransaction.add(R.id.container, buyerFragment);
        fragmentTransaction.commit();
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        AppEventsLogger.activateApp(this);
        btnBuyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                BuyerFragment buyerFragment = new BuyerFragment();
                fragmentTransaction.replace(R.id.container, buyerFragment);
                fragmentTransaction.commit();
            }
        });
        btnCarrier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                CarrierFragment carrierFragment = new CarrierFragment();
                fragmentTransaction.replace(R.id.container, carrierFragment);
                fragmentTransaction.commit();
            }
        });
        loginButton.setReadPermissions(Arrays.asList("public_profile,email,user_birthday,user_location"));
        callbackManager = CallbackManager.Factory.create();
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(MainActivity.this, "Login Successfully", Toast.LENGTH_LONG).show();
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.v("Main", response.toString());
                                setProfileToView(object);
                                hitApi();
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,email,gender,birthday,location,name");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Log.e("error", error.toString());
            }
        });
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (isFacebook_login)
            callbackManager.onActivityResult(requestCode, resultCode, data);

        if (isTwitter_login)
            CarrierFragment.btnSignIn.onActivityResult(requestCode, resultCode, data);
    }

    private void setProfileToView(JSONObject jsonObject) {
        try {
            userName = jsonObject.getString("name");
            Log.e("Name", (jsonObject.getString("name")));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            email = jsonObject.getString("email");
            Log.e("email", (jsonObject.getString("email")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            gender = jsonObject.getString("gender");
            Log.e("gender", (jsonObject.getString("gender")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            JSONObject jsonobject_location = jsonObject.getJSONObject("location");
            location = jsonobject_location.getString("name");
            Log.e("Location", location);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            Log.e("DOB", (jsonObject.getString("birthday")));
            dob = jsonObject.getString("birthday");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            Log.e("USER ID", (jsonObject.getString("id")));
            userId = jsonObject.getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            profile = jsonObject.getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    void hitApi() {
        ApiClient.getApiService().insertUser(userId, social_type, devicetoken, devicetype, email, lat, log, userName, lastname)
                .enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                        Log.e("message", response.body() + "");
                        String message = response.body().getMessage();
                        String status = response.body().getStatus();
                        Log.e("message", message);
                        Log.e("message", status);
                        if (status.equals("success") && message.equals("No mobile number found. Redirect to signup page.")) {
                            Intent intent = new Intent(MainActivity.this, UserDetails.class);
                            intent.putExtra("userName", userName);
                            intent.putExtra("gender", gender);
                            intent.putExtra("location", location);
                            intent.putExtra("dob", dob);
                            intent.putExtra("userId", userId);
                            intent.putExtra("email", email);
                            intent.putExtra("profile", profile);
                            startActivity(intent);
                        } else
                            Log.e("MESSAGE", "HIT FAILS...");
                    }
                    @Override
                    public void onFailure(Call<Response> call, Throwable t) {

                    }
                });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        LoginManager.getInstance().logOut();
    }
}
