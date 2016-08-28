package com.example.harshitbatra.hammer;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignupForm extends Activity
{
    public static final String TAG = "signup";

    EditText etname, etusername, etphone, etemail, etaddress, etcity, etpassword, etrepassword;
    Button btncreate;
    TextView tvwarning;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_form);

        etname = (EditText) findViewById(R.id.etname);
        etusername = (EditText) findViewById(R.id.etusername);
        etphone = (EditText) findViewById(R.id.etphone);
        etemail = (EditText) findViewById(R.id.etemail);
        etaddress = (EditText) findViewById(R.id.etaddress);
        etcity = (EditText) findViewById(R.id.etcity);
        etpassword = (EditText) findViewById(R.id.etpassword);
        etrepassword = (EditText) findViewById(R.id.etrepassword);
       /* tvwarning = (TextView) findViewById(R.id.tvwarnings);*/
        /*tvwarning.setVisibility(View.INVISIBLE);*/

        btncreate = (Button) findViewById(R.id.btncreate);

        btncreate.setOnClickListener(new Createclick());
    }

    public class Createclick implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {

            if (etname.getText().toString().isEmpty() || etusername.getText().toString().isEmpty() ||
                    etphone.getText().toString().isEmpty() || etemail.getText().toString().isEmpty() ||
                    etaddress.getText().toString().isEmpty() || etpassword.getText().toString().isEmpty() ||
                    etrepassword.getText().toString().isEmpty() || etcity.getText().toString().isEmpty())
            {
                Toast.makeText(SignupForm.this, "All Fields are Required!", Toast.LENGTH_SHORT).show();
            }
            else if (!etpassword.getText().toString().equals(etrepassword.getText().toString()))
            {
                Toast.makeText(SignupForm.this, "Passwords are not Same!", Toast.LENGTH_SHORT).show();
            }
            else
            {
                String name = etname.getText().toString();
                String username = etusername.getText().toString();
                String phone = etphone.getText().toString();
                String email = etemail.getText().toString();
                String address = etaddress.getText().toString();
                String city = etcity.getText().toString();
                String password = etpassword.getText().toString();
                String repassword = etrepassword.getText().toString();

                PostRequest signup = new PostRequest("http://www.hammertime.16mb.com/ht_signup.php",
                        name, username, phone, email, address, city, password);
                signup.setContext(SignupForm.this);
                signup.execute("signup");

                signup.setTaskDoneListener(new PostRequest.TaskDoneListener()
                {
                    @Override
                    public String onTaskDoneListener(String response)
                    {
                        Log.d(TAG, "onTaskDoneListener: Output from php = " + response);
                        tvwarning.setVisibility(View.VISIBLE);
                        if (response.charAt(0) == 'E')
                        {
                            //Log.d(TAG, "onTaskDoneListener: response = " + response);
                            Toast.makeText(SignupForm.this, "Username is Existed", Toast.LENGTH_SHORT).show();

                            /*tvwarning.setText("Username is Existed");
                            tvwarning.setTextColor(Color.RED);*/

                        }
                        else if (response.charAt(0) == 'C')
                        {
//                            Log.d(TAG, "onTaskDoneListener: response = " + response);
                            Toast.makeText(SignupForm.this, "Successfully Created", Toast.LENGTH_SHORT).show();
                            /*tvwarning.setText("Successfully Created");
                            tvwarning.setTextColor(Color.GREEN);
*/
                        }
                        else
                        {
//                            Log.d(TAG, "onTaskDoneListener: response = " + response);
                            Toast.makeText(SignupForm.this, "Error in Database Connection", Toast.LENGTH_SHORT).show();
                           /* tvwarning.setText("Error in Database Connection");
                            tvwarning.setTextColor(Color.RED);*/
                        }
                        return null;
                    }
                });

            }


        }
    }
}
