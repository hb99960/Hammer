package com.example.harshitbatra.hammer;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Home extends AppCompatActivity
{
    public static final String TAG = "LOG-Home";
    TextView tvhellouser;
    ImageView ivhimage, ivUpperLeft, ivUpperRight, ivBottomLeft, ivBottomRight;
    String username, password, name, phone, email, address, city, JSONString;
    private ListView drawerListView;
    private ArrayAdapter<String> drawerAdapter;


    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private String mActivityTitle;
    DrawerLayout drawer;

    // RecyclerView recyclerView1,recyclerView2,recyclerView3;
    ArrayList <MarketData> arrayData = new ArrayList<MarketData>();

    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mActivityTitle = getTitle().toString();
        ivUpperLeft = (ImageView) findViewById(R.id.ivUpperLeft);
        ivUpperRight = (ImageView) findViewById(R.id.ivUpperRight);
        ivBottomLeft = (ImageView) findViewById(R.id.ivBottomLeft);
        ivBottomRight = (ImageView) findViewById(R.id.ivBottomRight);


        Log.d(TAG, "onCreate: mActivityTitle = " + mActivityTitle);
        // sendPostRequest();


        setupDrawer();

        sp = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


/////////////////////////////---Getting data from splash Screen------//////////////////////////////////////////////


        Intent receivedIntent = getIntent();
        username = receivedIntent.getStringExtra("username");
        password = receivedIntent.getStringExtra("password");
        name = receivedIntent.getStringExtra("name");
        phone = receivedIntent.getStringExtra("phone");
        email = receivedIntent.getStringExtra("email");
        address = receivedIntent.getStringExtra("address");
        city = receivedIntent.getStringExtra("city");
        //JSONString = receivedIntent.getStringExtra("data");
        //arrayData = receivedIntent.getParcelableArrayListExtra("data");

       /* try
        {
            arrayData = getArrayFromJSONString(JSONString);
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
        Log.d(TAG, "onCreate: ArrayList = " + arrayData);*/

        /*if( !arrayData.isEmpty() )
        {
            updateRecyclerView(arrayData);
        }*/


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        ivUpperRight.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(Home.this, Market.class);
                startActivity(i);
            }
        });
        ivBottomLeft.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
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
        });
        ivBottomRight.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                alertMessage();
            }
        });



        Log.d(TAG, "onCreate: username = " + username + " password = " + password + " name = " + name +
                " phone = " + phone + " email = " + email + " address = " + address + " city = " + city);

        drawer = (DrawerLayout) findViewById(R.id.drawerLayout);

        tvhellouser = (TextView) findViewById(R.id.tvhellouser);
        tvhellouser.setText("Hello " + username);
        //tvhellouser.setTextColor(Color.GREEN);

        drawerListView = (ListView) findViewById(R.id.navList);
        String[] drawerArray = {"Profile", "Market", "Your Items", "Logout", "Contact", "About Us"};
        drawerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, drawerArray);
        drawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {


                if (position == 0)
                {

                    //view.setBackgroundColor(Color.parseColor("#3b5998"));
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
                else if (position == 1)
                {
                   // view.setBackgroundColor(Color.parseColor("#3b5998"));
                    Intent toMarket = new Intent(Home.this, Market.class);
                    toMarket.putExtra("loginuser", username);
                    startActivity(toMarket);
                }
                else if (position == 2)
                {
                   // view.setBackgroundColor(Color.parseColor("#3b5998"));
                    Intent i = new Intent(Home.this, YourItems.class);
                    i.putExtra("username", username);
                    startActivity(i);
                }
                else if (position == 3)
                {
                   // view.setBackgroundColor(Color.parseColor("#3b5998"));
                    alertMessage();
                }
                /*view.setBackgroundColor(Color.TRANSPARENT);*/
                else if (position == 4)
                {
                    String number = "9417422346";
                    Uri call = Uri.parse("tel:" + number);
                    Intent surf = new Intent(Intent.ACTION_DIAL, call);
                    startActivity(surf);
                }
                else if (position == 5)
                {
                    // About us.
                    Intent i = new Intent(Home.this, AboutUs.class);
                    startActivity(i);
                }
            }
        });
        drawerListView.setAdapter(drawerAdapter);
    }

    public ArrayList<MarketData> getArrayFromJSONString(String str) throws JSONException
    {

        String encodedImage = null, item = null, currentBid = null, time = null;
        String username = null, tag = null, itemdescription = null;
        String address = null, city = null, phone = null, email = null, lastBid = null;
        JSONArray myList = new JSONArray(str);
        MarketData data = new MarketData(item, encodedImage, currentBid, time, username, tag, itemdescription, address, city, phone, email, lastBid);
        for (int i = 0; i < myList.length(); i++)
        {
            JSONObject jsonObject = (JSONObject) myList.get(i);

            encodedImage = jsonObject.optString("encodedImage");
            item = jsonObject.optString("item");
            currentBid = jsonObject.optString("currentbid");
            time = jsonObject.optString("time");
            username = jsonObject.optString("username");
            tag = jsonObject.optString("tag");
            itemdescription = jsonObject.optString("itemdescription");
            address = jsonObject.optString("address");
            city = jsonObject.optString("city");
            phone = jsonObject.optString("phone");
            email = jsonObject.optString("email");
            lastBid = jsonObject.optString("lastbid");

            Log.d(TAG, "onTaskDoneListener: item = " + item + "city = " + city + "lastbid = " + lastBid);
            data.setArrayData(item, encodedImage, currentBid, time, username, tag, itemdescription, address, city, phone, email, lastBid);

        }

        return data.getArrayData();
    }


    public void sendPostRequest()
    {
        PostRequest postRequest = new PostRequest("http://hammertime.16mb.com/market.php");
        postRequest.execute("market");
        postRequest.setTaskDoneListener(new PostRequest.TaskDoneListener()
                                        {
                                            @Override
                                            public String onTaskDoneListener(String str) throws JSONException
                                            {
                                                String encodedImage = null, item = null, currentBid = null, time = null;
                                                String username = null, tag = null, itemdescription = null;
                                                String address = null, city = null, phone = null, email = null, lastBid = null;
                                                JSONArray myList = new JSONArray(str);
                                                MarketData data = new MarketData(item, encodedImage, currentBid, time, username, tag, itemdescription, address, city, phone, email, lastBid);
                                                for (int i = 0; i < myList.length(); i++)
                                                {
                                                    JSONObject jsonObject = (JSONObject) myList.get(i);

                                                    encodedImage = jsonObject.optString("encodedImage");
                                                    item = jsonObject.optString("item");
                                                    currentBid = jsonObject.optString("currentbid");
                                                    time = jsonObject.optString("time");
                                                    username = jsonObject.optString("username");
                                                    tag = jsonObject.optString("tag");
                                                    itemdescription = jsonObject.optString("itemdescription");
                                                    address = jsonObject.optString("address");
                                                    city = jsonObject.optString("city");
                                                    phone = jsonObject.optString("phone");
                                                    email = jsonObject.optString("email");
                                                    lastBid = jsonObject.optString("lastbid");

                                                    Log.d(TAG, "onTaskDoneListener: item = " + item + "city = " + city + "lastbid = " + lastBid);
                                                    data.setArrayData(item, encodedImage, currentBid, time, username, tag, itemdescription, address, city, phone, email, lastBid);

                                                }
                                                arrayData = data.getArrayData();
                                                updateRecyclerView(arrayData);
                                                return null;
                                            }
                                        }

        );

    }

    public void updateRecyclerView(ArrayList<MarketData> array)
    {

        /*recyclerView1 = (RecyclerView) findViewById(R.id.recycler_view1);
        recyclerView2 = (RecyclerView) findViewById(R.id.recycler_view2);
        recyclerView3 = (RecyclerView) findViewById(R.id.recycler_view3);*/

        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(array);
        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        /*RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.LayoutManager layoutManager3 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);*/
       /* recyclerView1.setLayoutManager(layoutManager1);
        recyclerView1.setAdapter(myAdapter);*/
        /*recyclerView2.setLayoutManager(layoutManager2);
        recyclerView2.setAdapter(myAdapter);
        recyclerView3.setLayoutManager(layoutManager3);
        recyclerView3.setAdapter(myAdapter);*/
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder
    {


        public ImageView imageView;
        public RecyclerViewHolder(View itemView)
        {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.recycler_image);
        }


    }

    public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder>
    {

        public ArrayList<MarketData> arrayList;

        public RecyclerViewAdapter(ArrayList<MarketData> arrayList )
        {
            this.arrayList = arrayList;
        }

        @Override
        public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            LayoutInflater li = getLayoutInflater();
            View itemView = li.inflate(R.layout.layout_recycler_view,null);
            RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(itemView);

            return recyclerViewHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerViewHolder holder, int position)
        {
            MarketData ob = arrayList.get(position);
            Bitmap bitmap = getBitmap(ob.getMarketEncodedImage());
            Log.d(TAG, "onBindViewHolder: bitmap = " + bitmap);
            holder.imageView.setImageBitmap(bitmap);
        }

        @Override
        public int getItemCount()
        {

            Log.d(TAG, "getItemCount: arrayList = " + arrayList + "size = " + arrayList.size() );
            return arrayList.size();
        }

    }

    public Bitmap getBitmap(String str)
    {
        byte[] encodedByte = Base64.decode(str, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(encodedByte, 0, encodedByte.length);
        return bitmap;
    }
    public void alertMessage()
    {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                switch (which)
                {
                case DialogInterface.BUTTON_POSITIVE:
                    SharedPreferences.Editor editor = sp.edit();
                    editor.remove("username");
                    editor.remove("password");
                    editor.remove("address");
                    editor.remove("city");
                    editor.remove("phone");
                    editor.remove("email");
                    editor.remove("name");
                    editor.apply();
                    finish();
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are You Sure?")
                .setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();

    }
    public void setupDrawer()
    {

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close)
        {
            public void onDrawerOpened(View drawerView)
            {
                Log.d(TAG, "onDrawerOpened: username = " + username);
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Hello " + username);
                invalidateOptionsMenu();
            }

            public void onDrawerClosed(View view)
            {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }
    public void goToSell(View v)
    {
        Intent i = new Intent(Home.this, SellItem.class);
        i.putExtra("username", username);
        startActivity(i);
    }
    public void onBackPressed()
    {

        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        // Activate the navigation drawer toggle

        if (id == R.id.action_sell)
        {
            Intent i = new Intent(Home.this, SellItem.class);
            i.putExtra("username", username);
            startActivity(i);
        }
        else if (id == R.id.action_refresh)
        {
            Toast.makeText(Home.this, "Done!", Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.action_check_updates)
        {
            Toast.makeText(Home.this, "The Latest updates have already been installed", Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.action_logout)
        {
            alertMessage();
        }

        if (mDrawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

}