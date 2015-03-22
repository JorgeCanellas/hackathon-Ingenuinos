package com.example.jorge.myapplication;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {
    private List<Barrio> barrioList;
    private List<PuntoWifi> wifiList;
    public List<Barrio> getBarrioList() {
        return barrioList;
    }

    public void setBarrioList(List<Barrio> barrioList) {
        this.barrioList = barrioList;
    }

    public List<PuntoWifi> getWifiList() {
        return wifiList;
    }

    public void setWifiList(List<PuntoWifi> wifiList) {
        this.wifiList = wifiList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        setBarrioList(loadBarrios());
        setWifiList(loadWifiPoints());
        final TextView resultText = (TextView) findViewById(R.id.editText);
        final Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(getBarrioList() != null && !getBarrioList().isEmpty()) {
                    resultText.setText("");
                    for (Barrio barrio : getBarrioList()) {
                        resultText.append(barrio.getTitle());
                        resultText.append("\n");
                    }
                }
            }
        });
        final Button buttonWifi = (Button) findViewById(R.id.wifiCaidos);
        buttonWifi.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(getWifiList() != null && !getWifiList().isEmpty()) {
                    resultText.setText("");
                    for (PuntoWifi wifiPoint : getWifiList()) {
                        resultText.append(wifiPoint.getEstado());
                        resultText.append("\n");
                    }
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public List<Barrio> loadBarrios() {
        List<Barrio> barrioList = new ArrayList<>(5);
        Gson parser = new Gson();
        JSONObject rawJsonObject;
        String result = getJsonFromRequest(getString(R.string.barriosRequest));
        try{
            rawJsonObject = new JSONObject(result);
            String rawJSON = rawJsonObject.getString("result");
            Type collectionType2 = new TypeToken<List<Barrio>>(){}.getType();
            barrioList = parser.fromJson(rawJSON, collectionType2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return barrioList;
    }

    public List<PuntoWifi> loadWifiPoints() {
        List<PuntoWifi> wifiList = new ArrayList<>(5);
        Gson parser = new Gson();
        JSONObject rawJsonObject;
        String result = getJsonFromRequest(getString(R.string.wifiRequest));
        if(result!= null && result.length() > 0){
            try{
                rawJsonObject = new JSONObject(result);

                String rawJSON = rawJsonObject.getString("result");
                Type collectionType2 = new TypeToken<List<PuntoWifi>>(){}.getType();
                wifiList = parser.fromJson(rawJSON, collectionType2);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return wifiList;
    }


    private static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line;
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }

    private String getJsonFromRequest(String url){
        String result = "";
        try {
            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));

            // receive response as inputStream
            InputStream inputStream = httpResponse.getEntity().getContent();

            // convert inputstream to string
            if (inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
