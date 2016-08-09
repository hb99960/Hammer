package com.example.harshitbatra.hammer;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Harshit Batra on 20-07-2016.
 */
public class PostRequest extends AsyncTask<String, Void, String>
{
    public static final String TAG = "LOG-PostRequest";
    ProgressDialog progressDialog;
    String username, password, url;
    TaskDoneListener taskdone;
    String name, phone, email, address, city;
    String encodedImage, itemName, itemDescription, time, tag;
    String currentBid;
    Context context;
    String loginuser;
    int newbid;

    public PostRequest(String url)
    {
        this.url = url;
    }

    public PostRequest(String url, String username, String password)
    {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public PostRequest(String url, String name, String username, String phone, String email, String address, String city, String password)
    {
        this.url = url;
        this.name = name;
        this.username = username;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.city = city;
        this.password = password;
    }

    public PostRequest(String url, String username, String itemName, String itemDescription, String currentBid,
                       String time, String tag, String loginuser, int newbid, int x)
    {
        this.url = url;
        this.username = username;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.currentBid = currentBid;
        this.time = time;
        this.tag = tag;
        this.loginuser = loginuser;
        this.newbid = newbid;
    }

    public PostRequest(String url, String username, String encodedImage,
                       String itemName, String itemDescription, String currentBid,
                       String time, String tag, int code)
    {
        this.url = url;
        this.username = username;
        this.encodedImage = encodedImage;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.currentBid = currentBid;
        this.time = time;
        this.tag = tag;
        Log.d(TAG, "PostRequest: Encoded Image = " + encodedImage);
    }

    public PostRequest(String url, String username)
    {
        this.url = url;
        this.username = username;
    }

    public PostRequest(String url, String username, String name, String description, String currentBid, String time, String tag)
    {
        this.url = url;
        this.username = username;
        itemName = name;
        itemDescription = description;
        this.currentBid = currentBid;
        this.time = time;
        this.tag = tag;
    }

    public void setTaskDoneListener(TaskDoneListener listener)
    {
        taskdone = listener;
    }

    public void setContext(Context context)
    {
        this.context = context;
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();

        if (context != null)
        {
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

    }

    public String makeConnectionForBid()
    {
        HashMap<String, String> hashMap = new HashMap<>(8);
        hashMap.put("username",username);
        hashMap.put("itemname", itemName);
        hashMap.put("itemdescription", itemDescription);
        hashMap.put("bid", currentBid);
        hashMap.put("time", time);
        hashMap.put("tag", tag);
        hashMap.put("loginuser", loginuser);
        hashMap.put("currentvalue", String.valueOf(newbid));
        Log.d(TAG, "onCreate: username = " + username + "\ntag = " + tag + "\ndesc = " + itemDescription +
                "\naddress = " + address + "\ncity = " + city + "\nbid = " + currentBid + "\nemail = " + "\nloginUser = " + loginuser +
                "\nitemName = " + itemName + "\ntime = " + time + "\ncurrent value = " + newbid
                + "\nurl = " + url);
        return performPostCall(url,hashMap);
        //"URL",username,itemName,desc,bid,time,tag,loginUser,currentValue

    }

    public String makeConnectionLogin()
    {
        HashMap<String, String> hashMap = new HashMap<>(2);
        hashMap.put("username", username);
        hashMap.put("password", password);

        return performPostCall(url, hashMap);
    }

    public String makeConnectionHome()
    {
        HashMap<String, String> hashMap = new HashMap<>(1);
        hashMap.put("username", username);

        return performPostCall(url, hashMap);
    }

    public String makeConnectionSignup()
    {
        HashMap<String, String> hashMap = new HashMap<>(7);
        hashMap.put("name", name);
        hashMap.put("username", username);
        hashMap.put("phone", phone);
        hashMap.put("email", email);
        hashMap.put("address", address);
        hashMap.put("city", city);
        hashMap.put("password", password);

        return performPostCall(url, hashMap);
    }

    public String makeConnectionSell()
    {
        Log.d(TAG, "makeConnectionSell: current bid = " + currentBid);

        HashMap<String, String> hashMap = new HashMap<>(7);
        hashMap.put("username", username);
        hashMap.put("encodedImage", encodedImage);
        hashMap.put("itemName", itemName);
        hashMap.put("itemDescription", itemDescription);
        hashMap.put("currentBid", currentBid);
        hashMap.put("time", time);
        hashMap.put("tag", tag);
        Log.d(TAG, "makeConnectionSell: current bid = " + currentBid);
        return performPostCall(url, hashMap);
    }

    public String makeConnectionSell2()
    {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("username", username);
        hashMap.put("itemName", itemName);
        hashMap.put("itemDescription", itemDescription);
        hashMap.put("currentBid", currentBid);
        hashMap.put("time", time);
        hashMap.put("tag", tag);

        return performPostCall(url, hashMap);
    }

    public String makeConnectionMarket()
    {
        HashMap<String, String> hashMap = new HashMap<>();
        return performPostCall(url, hashMap);
    }


    public String performPostCall(String requestURL, HashMap<String, String> postDataParams)
    {

        URL url;
        String response = "";
        try
        {
            url = new URL(requestURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);


            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));

            writer.flush();
            writer.close();
            os.close();
            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK)
            {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null)
                {
                    response += line;
                }
            }
            else
            {
                response = "";
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        Log.d(TAG, "performPostCall: response = " + response);
        return response;
    }

    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException
    {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet())
        {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        Log.e("dsasd", "getPostDataString: " + result);
        return result.toString();
    }


    @Override
    protected String doInBackground(String[] params)
    {
        String result = "";
        if (params[0].equals("login"))
        {
            Log.d(TAG, "doInBackground: result 1 = " + result);
            result = makeConnectionLogin();
            Log.d(TAG, "doInBackground: result 2 = " + result);
        }
        else if (params[0].equals("signup") || params[0].equals("profile"))
        {
            // Log.d(TAG, "doInBackground: enter if");
            result = makeConnectionSignup();
            //  Log.d(TAG, "doInBackground: result = " + result);
        }
        else if (params[0].equals("Sell"))
        {
            result = makeConnectionSell();
        }
        else if (params[0].equals("home"))
        {
            result = makeConnectionHome();
        }
        else if (params[0].equals("market"))
        {
            result = makeConnectionMarket();
        }
        else if (params[0].equals("SellViaFtp"))
        {
            result = makeConnectionSell2();
        }
        else if (params[0].equals("ChangeBid"))
        {
            result = makeConnectionForBid();
        }

        Log.d(TAG, "doInBackground: result = " + result);
        return result;
    }

    @Override
    protected void onPostExecute(String s)
    {
        super.onPostExecute(s);
        if (progressDialog != null)
        {
            progressDialog.dismiss();
        }

        try
        {
            Log.d(TAG, "onPostExecute: s = " + s);
            taskdone.onTaskDoneListener(s);
        } catch (JSONException e)
        {
            e.printStackTrace();
        }

    }

    public interface TaskDoneListener
    {
        String onTaskDoneListener(String str) throws JSONException;
    }

}
