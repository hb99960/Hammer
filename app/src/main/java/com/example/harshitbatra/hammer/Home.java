package com.example.harshitbatra.hammer;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class Home extends AppCompatActivity
{
    public static final String TAG = "LOG-Home";
    TextView tvhellouser;
    ImageView ivhimage;
    String username, password, name, phone, email, address, city;
    private ListView drawerListView;
    private ArrayAdapter<String> drawerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent receivedIntent = getIntent();

        username = receivedIntent.getStringExtra("username");
        password = receivedIntent.getStringExtra("password");
        name = receivedIntent.getStringExtra("name");
        phone = receivedIntent.getStringExtra("phone");
        email = receivedIntent.getStringExtra("email");
        address = receivedIntent.getStringExtra("address");
        city = receivedIntent.getStringExtra("city");

        Log.d(TAG, "onCreate: username = " + username + " password = " + password + " name = " + name +
                " phone = " + phone + " email = " + email + " address = " + address + " city = " + city);
        tvhellouser = (TextView) findViewById(R.id.tvhellouser);
        tvhellouser.setText("Hello " + username);
        tvhellouser.setTextColor(Color.GREEN);

        drawerListView = (ListView) findViewById(R.id.navList);
        String[] drawerArray = {"Home", "Profile", "Market", "Your Items", "Contact", "About Us"};
        drawerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, drawerArray);
        drawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                if (position == 1)
                {
                    Intent sendToHome = new Intent(Home.this, Profile.class);

                    Bundle bundle = new Bundle();
                    bundle.putString("username", username);
                    bundle.putString("password", password);
                    bundle.putString("name", name);
                    bundle.putString("address", address);
                    bundle.putString("city", city);
                    bundle.putString("email", email);
                    bundle.putString("phone", phone);
                    sendToHome.putExtras(bundle);
                    startActivity(sendToHome);

                }
                if (position == 2)
                {
                    Intent toMarket = new Intent(Home.this, Market.class);
                    toMarket.putExtra("loginuser", username);
                    startActivity(toMarket);

                }
                if (position == 3)
                {
                    Intent i = new Intent(Home.this, YourItems.class);
                    i.putExtra("username", username);
                    startActivity(i);
                }
            }
        });
        drawerListView.setAdapter(drawerAdapter);
    }

    public void goToSell(View v)
    {
        Intent i = new Intent(Home.this, SellItem.class);
        i.putExtra("username", username);
        startActivity(i);
    }

   /* public void show(View v)
    {
        PostRequest postRequest = new PostRequest("http://hammertime.16mb.com/individual.php",username);
        postRequest.execute("home");

        postRequest.setTaskDoneListener(new PostRequest.TaskDoneListener()
        {
            @Override
            public String onTaskDoneListener(String str) throws JSONException
            {
                Log.d(TAG, "onTaskDoneListener: String = " + str);
                if(! str.equals("Failed") )
                {

                    byte[] encodedByte = Base64.decode(str,Base64.DEFAULT);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(encodedByte,0, encodedByte.length);
                    ivhimage = (ImageView) findViewById(R.id.ivhImage);
                    ivhimage.setImageBitmap(bitmap);
                    Toast.makeText(Home.this, "I am done", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(Home.this, "Failed", Toast.LENGTH_SHORT).show();
                }
                return null;
            }
        });
    }*/
}
