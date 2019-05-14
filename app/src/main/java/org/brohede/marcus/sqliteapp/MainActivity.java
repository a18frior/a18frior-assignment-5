package org.brohede.marcus.sqliteapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.icu.text.IDNA;
import android.location.Location;
import android.media.Image;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import android.support.v7.widget.Toolbar;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.jar.Attributes;

import static org.brohede.marcus.sqliteapp.R.layout.activity_main;
import static org.brohede.marcus.sqliteapp.R.layout.listview;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG =
            MainActivity.class.getSimpleName();

    ArrayList<Mountain> berg2 = new ArrayList();
    ArrayList<HashMap<String, String>> berg = new ArrayList<HashMap<String, String>>();
    private SimpleAdapter adapter2;
    private SimpleAdapter imageAdapter;
    MountainReaderDbHelper db = new MountainReaderDbHelper(this);
    String image;






    private class FetchData extends AsyncTask<Void, Bitmap, String> {






        @Override
        protected String doInBackground(Void... params) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;


            String jsonStr = null;

            try {
                // Construct the URL for the Internet service
                URL url = new URL("http://wwwlab.iit.his.se/brom/kurser/mobilprog/dbservice/admin/getdataasjson.php?type=brom");

                // Create the request to the PHP-service, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {

                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {

                    return null;
                }
                jsonStr = buffer.toString();
                return jsonStr;
            } catch (IOException e) {
                Log.e("PlaceholderFragment", "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in
                // attempting to parse it.
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("Network error", "Error closing stream", e);
                    }
                }
            }
        }




        protected void onPostExecute(String o) {

            super.onPostExecute(o);
            String s1 = o
                    .replace("\"{\\\"img", "{\\\"img")
                    .replace("\\\"}\"", "\\\"}")
                    .replace("\\\"", "\"");

            Log.d("Jennas log", s1);

            try {

                Log.d("jennas log", "okej1");
                JSONArray mountains = new JSONArray(s1);
                Log.d("jennas log", "okej2");

                for (int i = 0; i < mountains.length(); i++) {
                    JSONObject json1 = mountains.getJSONObject(i);
                    JSONObject auxdata = json1.getJSONObject("auxdata");
                    String Img_url = auxdata.getString("img");
                    Log.d("jennas log",Img_url);
                    String InfoUrl = auxdata.getString("url");
                    Log.d("jennas log", InfoUrl);
                    Log.d("img", Img_url);
                    Log.d("img", InfoUrl);


                    String Location = json1.getString("location");
                    String Name = json1.getString("name");
                    Log.d("jennas log", "" + Name);
                    String Height = json1.getString("size");
                    Log.d("jennas log", "" + Height);
                    Log.d("Insert:", "Inserting .");

                    Mountain m = new Mountain(Name, Location, Height, Img_url, InfoUrl);
                    Log.d("Insert:", "new." + m.toString());

                    if (db.getMountain(Name) == null) {
                        db.addmountain(m);
                    }
                }
                Log.d("andras", "" + db.getShopsCount());
            } catch (Exception e) {
                Log.d("jennas log", "E:" + e.getMessage());
            }


            Log.d("jennas log", "berg is ok");


            berg2 = db.getAllMountains();

            /*for(int i = 0; i < db.getDBSSize(); i++){
                berg2.add(db.getmountain(i));
            }*/

            Log.d("Frida Rockar5", "" + berg2.size());
            for (int i = 0; i < berg2.size(); i++) {
                String log = "_" + i + "_" + "Name:" + berg2.get(i).getName() + ",Location:" + berg2.get(i).getLocation() + "Height" + berg2.get(i).getHeight()+berg2.get(i).getImg_url()+berg2.get(i).getInfoUrl();
// Writing shops to log
                Log.d("frida6: : ", log);
            }


            HashMap<String, String> item;




            for (int i = 0; i < berg2.size(); i++) {


                item = new HashMap<>();
                item.put("line1", berg2.get(i).getName());
                item.put("line2", berg2.get(i).getLocation());
                item.put("line3", berg2.get(i).getHeight());








                berg.add(item);

            }
            for (HashMap<String, String> berg : berg) {
                Log.d("Mountain224: : ", berg.get("line1"));
            }

            adapter2 = new SimpleAdapter(MainActivity.this, berg,
                    R.layout.listview,
                    new String[]{"line1", "line2", "line3"},
                    new int[]{R.id.line_a, R.id.line_b, R.id.line_c}
            );

            ListView lista= findViewById(R.id.fridaslist);
            lista.setAdapter(adapter2);













            lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Toast.makeText(getApplicationContext(), berg2.get(position).info(), Toast.LENGTH_LONG).show();
                }
            });



        }}
    private class DownloadImageFromInternet extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        public DownloadImageFromInternet(ImageView imageView) {
            this.imageView = imageView;

        }

        protected Bitmap doInBackground(String... urls) {
            String imageURL = urls[0];
            Bitmap bimage = null;
            try {
                InputStream in = new java.net.URL(imageURL).openStream();
                bimage = BitmapFactory.decodeStream(in);

            } catch (Exception e) {
                Log.e("Error Message", e.getMessage());
                e.printStackTrace();
            }
            return bimage;
        }

        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);





        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new FetchData().execute();






    }







    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == R.id.action_sorth) {
            berg.clear();
            berg2.clear();

            berg2 = db.getAllMountains();
            Collections.sort(berg2, new MountainHeigtComparator());
            HashMap<String, String> item2;
            for (int i = 0; i < berg2.size(); i++) {
                item2 = new HashMap<>();
                item2.put("line1", berg2.get(i).getName());
                item2.put("line2", berg2.get(i).getLocation());
                item2.put("line3", berg2.get(i).getHeight());
                berg.add(item2);

            }
            adapter2 = new SimpleAdapter(MainActivity.this, berg,
                    R.layout.listview,
                    new String[]{"line1", "line2", "line3"},
                    new int[]{R.id.line_a, R.id.line_b, R.id.line_c}
            );

            ListView lista= findViewById(R.id.fridaslist);
            lista.setAdapter(adapter2);




        }



        if (id == R.id.action_sortn) {
            berg.clear();
            berg2.clear();
            berg2 = db.getAllMountains();
            Collections.sort(berg2, new MountainNameComparator());
            HashMap<String, String> item2;
            for (int i = 0; i < berg2.size(); i++) {
                item2 = new HashMap<>();
                item2.put("line1", berg2.get(i).getName());
                item2.put("line2", berg2.get(i).getLocation());
                item2.put("line3", berg2.get(i).getHeight());
                berg.add(item2);

            }
            adapter2 = new SimpleAdapter(MainActivity.this, berg,
                    R.layout.listview,
                    new String[]{"line1", "line2", "line3"},
                    new int[]{R.id.line_a, R.id.line_b, R.id.line_c}
            );

            ListView lista= findViewById(R.id.fridaslist);
            lista.setAdapter(adapter2);


        }

        return super.onOptionsItemSelected(item);
    }

 }





