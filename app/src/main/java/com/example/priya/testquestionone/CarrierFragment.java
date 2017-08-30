package com.example.priya.testquestionone;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

/**
 * Created by priya on 24/8/17.
 */

public class CarrierFragment extends Fragment {

    private final String TAG = getClass().getSimpleName();

    public static TwitterLoginButton btnSignIn;
    TwitterSession session;

    String token, secret;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.carrier_fragment,container,false);
        btnSignIn=(TwitterLoginButton) view.findViewById(R.id.btn_signIn);
//
//        TwitterConfig config = new TwitterConfig.Builder(getContext())
//                .logger(new DefaultLogger(Log.DEBUG))
//               .twitterAuthConfig(new TwitterAuthConfig(getString(R.string.CONSUMER_KEY),getString(R.string.CONSUMER_SECRET)))
//                .debug(true)
//                .build();
//        Twitter.initialize(getContext());


        TwitterConfig config = new TwitterConfig.Builder(getContext())
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig(getString(R.string.CONSUMER_KEY),getString(R.string.CONSUMER_SECRET)))
                .debug(true)
                .build();
        Twitter.initialize(config);



        btnSignIn.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                Log.e("UserId", result.data.getUserId() + "");
                Log.e("UserName", result.data.getUserName() + "");

                session = TwitterCore.getInstance().getSessionManager().getActiveSession();
                TwitterAuthToken authToken = session.getAuthToken();
                token = authToken.token;
                secret = authToken.secret;
                requestEmail();
            }

            private void requestEmail() {
                TwitterAuthClient authClient = new TwitterAuthClient();
                authClient.requestEmail(session, new Callback<String>() {
                    @Override
                    public void success(Result<String> result) {
                        // Do something with the result, which provides the email address
                        Log.e(TAG, ""+result);
                    }

                    @Override
                    public void failure(TwitterException exception) {
                        // Do something on failure

                        Log.e(TAG, ""+exception);
                    }
                });
            }

            @Override
            public void failure(TwitterException exception) {
                Toast.makeText(getContext(), "Failure", Toast.LENGTH_SHORT).show();
            }
        });




        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Clicked","Clicked");
//                twitterLogin();

                MainActivity.isTwitter_login = true;
                MainActivity.isFacebook_login = false;

                TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
                if (session != null){
                    TwitterAuthToken authToken = session.getAuthToken();
                    String token = authToken.token;
                    String secret = authToken.secret;
                }


            }
        });
        return view;
    }
    void twitterLogin() {






    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

}