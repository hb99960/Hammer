package com.example.harshitbatra.hammer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    EditText etusername,etpassword;
    Button btnlogin,btnsignup;
    TextView tvwarning;
    public static final int RC_FOR_SIGNUP = 911;
    public static final String TAG = "Main Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boolean isLogin = checkSharedPreferences();

        etusername = (EditText) findViewById(R.id.etusername);
        etpassword = (EditText) findViewById(R.id.etpassword);
        btnlogin = (Button) findViewById(R.id.btnlogin);
        btnsignup = (Button) findViewById(R.id.btnsignup);



        View.OnClickListener loginClick = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                String username = etusername.getText().toString();
                String password = etpassword.getText().toString();

                if(username.isEmpty() || password.isEmpty())
                {
                    Toast.makeText(MainActivity.this, "Fill All Credentials", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    PostRequest postRequest = new PostRequest("http://hammertime.16mb.com/ht_login.php?",username,password);
                    postRequest.execute("login");
                    postRequest.setTaskDoneListener(new PostRequest.TaskDoneListener()
                    {
                        @Override
                        public String onTaskDoneListener(String str)
                        {
                            tvwarning = (TextView) findViewById(R.id.tvwarning1);
                            tvwarning.setVisibility(View.VISIBLE);

                            Log.d(TAG, "onTaskDoneListener: Response = " + str);
                            if(str.charAt(0) == 't')
                            {
                                tvwarning.setText("Successful");
                                tvwarning.setTextColor(Color.GREEN);

                                String username = etusername.getText().toString();
                                String password = etpassword.getText().toString();

                                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                                SharedPreferences.Editor editor = settings.edit();
                                editor.putString("session_id",str.substring(4));
                                editor.commit();

                                Intent intent = new Intent(MainActivity.this,Home.class);
                                intent.putExtra("username",username);
                                intent.putExtra("password",password);
                                startActivity(intent);

                            }
                            else if(str.charAt(0) == 'N')
                            {
                                tvwarning.setText("No User Found");
                                tvwarning.setTextColor(Color.RED);
                            }
                            else  if(str.charAt(0) == 'W')
                            {
                                tvwarning.setText("Wrong Password");
                                tvwarning.setTextColor(Color.RED);
                            }
                            else
                            {
                                Toast.makeText(MainActivity.this, "Response is = " + str, Toast.LENGTH_SHORT).show();
                            }

                            return null;
                        }
                    });
                }
            }
        };

        View.OnClickListener signupclick = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent goToSignup = new Intent(getApplicationContext(),SignupForm.class);
                startActivityForResult(goToSignup,RC_FOR_SIGNUP);
            }
        };

        ConnectivityManager cMgr = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cMgr.getActiveNetworkInfo();

        if(netInfo != null && netInfo.isConnected())
        {
           /* if(isLogin)
            {

            }
            else
            {*/
                btnlogin.setOnClickListener(loginClick);
                btnsignup.setOnClickListener(signupclick);
            /*}*/
        }
        else
        {
            Toast.makeText(MainActivity.this, "Check Your Internet Connection!", Toast.LENGTH_SHORT).show();
        }



    }

    public boolean checkSharedPreferences()
    {
        SharedPreferences store = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        String session_id = store.getString("session_id","");
        if(session_id.isEmpty())
            return false;
        else
            return true;
    }
}
