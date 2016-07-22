package com.example.harshitbatra.hammer;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
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
    String username,password,url;
    TaskDoneListener taskdone;
    String name,phone,email,address,city,repassword;

    public static final String TAG = "response";

    public void setTaskDoneListener(TaskDoneListener listener)
    {
        taskdone = listener;
    }

    public PostRequest(String url,String username, String password)
    {
        this.url = url;
        this.username = username;
        this.password = password;
    }
    public PostRequest( String url,String name,String username,String phone,String email,String address,String city,String password,String repassword)
    {
        this.url = url;
        this.name = name;
        this.username = username;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.city = city;
        this.password = password;
        this.repassword = repassword;
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
    }

    public String makeConnectionLogin()
    {
        HashMap<String,String> hashMap = new HashMap<>(2);
        hashMap.put("username",username);
        hashMap.put("password",password);
        return performPostCall(url,hashMap);
    }
    public String makeConnectionSignup()
    {
        HashMap<String,String> hashMap = new HashMap<>(8);
        hashMap.put("name",name);
        hashMap.put("username",username);
        hashMap.put("phone",phone);
        hashMap.put("email",email);
        hashMap.put("address",address);
        hashMap.put("city",city);
        hashMap.put("password",password);
        hashMap.put("repassword",repassword);
        return performPostCall(url,hashMap);

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
        }
        catch (Exception e)
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
        if(params[0].equals("login"))
        {
            Log.d(TAG, "doInBackground: result 1 = " + result);
            result =  makeConnectionLogin();
            Log.d(TAG, "doInBackground: result 2 = " + result);
        }
        else if(params[0].equals("signup"))
        {
           // Log.d(TAG, "doInBackground: enter if");
            result = makeConnectionSignup();
          //  Log.d(TAG, "doInBackground: result = " + result);
        }
        return result;
    }

    @Override
    protected void onPostExecute(String s)
    {
        super.onPostExecute(s);
        taskdone.onTaskDoneListener(s);
    }

    public interface TaskDoneListener
    {
        String onTaskDoneListener(String str);
    }

}
