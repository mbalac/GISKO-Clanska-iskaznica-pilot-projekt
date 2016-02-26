package gisko.milan.ffos.giskoclanskaiskaznica;

import android.os.AsyncTask;
import android.util.Log;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Milan on 9.2.2016..
 */
public class Autoriziraj extends AsyncTask<String, Void, String> {
    Autorizacija context;
    public Autoriziraj(Autorizacija ctx){
        context = ctx;
    }
    @Override
    protected String doInBackground(String... params) {
        Log.d("korak1", "korak1");
        String type = params[0];
        String login_url = "http://oziz.ffos.hr/notfound/PPGCI/login_clan.php";
        if(type.equals("login")){
            try {
                String username = params[1];
                String password = params[2];
                URL url = new URL(login_url);
                Log.d("korak","korak");
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("username", "UTF-8")+"="+URLEncoder.encode(username,"UTF-8")+"&"
                        +URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8");
                Log.d("korak2","korak2");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while((line = bufferedReader.readLine())!=null){
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                Log.d("kraj", "kraj");
                return result;
            } catch (MalformedURLException e){
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("catch", "catch");
            }
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.d("prex", "prex");
    }

    @Override
    protected void onPostExecute(String rezultat) {
        try {
            super.onPostExecute(rezultat);
            //skužiti da li je autoriziran pa pozvati parsirat json pomoću gson

            Gson g = new Gson();
            Clan c = g.fromJson(rezultat,Clan.class);
            Log.d("postx", rezultat);

            if (c.getGreska()==null) {

                context.autoriziran(rezultat);
            } else {
                context.pogreskaLogin();
            }
        }catch (Exception e){
            context.nemaNeta();
        }


    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
