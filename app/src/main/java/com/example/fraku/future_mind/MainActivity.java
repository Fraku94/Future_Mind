package com.example.fraku.future_mind;

import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();

    private String OrderID, Title, ImageUrl, WebUrl, ModificationDate, Description;

    private Long lastCheckedMillis;

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mDataAdapter;
    private RecyclerView.LayoutManager mDataLayoutMenager;
    private WebView mWebView;
    private boolean mTwoPane;

    private DatabaseHandlerData databaseData;
    private DatabaseHandlerSettings databaseSettings;

    private NetworkInfo netInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTwoPane = false;

        //Przypisanie funkicji odswiezania
        swipeRefreshLayout = findViewById(R.id.swipeContainer);
        if (swipeRefreshLayout.getTag().equals("s")){
            mTwoPane = true;
        }

        swipeRefreshLayout.setRefreshing(true);

        databaseData = new DatabaseHandlerData(this);
        databaseSettings = new DatabaseHandlerSettings(this);

        //Ustawienie RecycleView
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));

        //Ustawienie Adaptera oraz LayoutMenagera.
        mDataLayoutMenager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mDataLayoutMenager);
        mDataAdapter = new DataAdapter(MainActivity.this,getData(), mTwoPane);
        mRecyclerView.setAdapter(mDataAdapter);

        //Sprawdzenie dostępu do internetu
        isOnline();

        //Sprawdzenie daty odswieżenia
        getDate();

        //Inicjacja odswiezania
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                isOnline();

                if (netInfo != null) {

                    clear();
                    new GetCategory().execute();

                    mDataAdapter = new DataAdapter(MainActivity.this,getData(), mTwoPane);
                    mRecyclerView.setAdapter(mDataAdapter);

                }else if (netInfo == null){
                    Toast.makeText(MainActivity.this,"Brak połączenia z interentem",Toast.LENGTH_SHORT).show();

                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });
    }

    private void getDate() {

        Calendar cal = Calendar.getInstance();
        cal.clear(Calendar.HOUR);
        cal.clear(Calendar.HOUR_OF_DAY);
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);
        long now = cal.getTimeInMillis();

        Cursor resDate = databaseSettings.getDateSettings();

        if (resDate.getCount() == 0){

            databaseSettings.addSettings(now);
            isOnline();
            if (netInfo != null){
                new GetCategory().execute();
            }else{
                Toast.makeText(MainActivity.this,"Brak połączenia z interentem, włącz internet i zresetuj aplikacje",Toast.LENGTH_LONG).show();
            }
        }else if(resDate.moveToPosition(0)) {

            lastCheckedMillis = (resDate.getLong(0));

            long diffMillis = now - lastCheckedMillis;

            if( diffMillis >= (3600000  * 24) ) {
                databaseSettings.addSettings(now);

                isOnline();

                if (netInfo != null){

                    new GetCategory().execute();

                }else{
                    Toast.makeText(MainActivity.this,"Brak połączenia z interentem, włącz internet i zresetuj aplikacje",Toast.LENGTH_LONG).show();
                }

            } else {

                // too early

                Cursor resData = databaseData.getAllData();

                while (resData.moveToNext()) {
                    DataObject object = new DataObject(resData.getString(0), resData.getString(1),
                            resData.getString(2), resData.getString(3), resData.getString(4), resData.getString(5));
                    //Metoda dodawania do Objektu
                    resoult.add(object);
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    //Czyszczenie
    private void clear() {
        int size = this.resoult.size();
        this.resoult.clear();
        mDataAdapter.notifyItemRangeChanged(0, size);
    }

    //Przeslanie do Adaptera Rezultatow
    private ArrayList<DataObject> resoult = new ArrayList<DataObject>();

    private List<DataObject> getData() {
        // new GetCategory().execute();
        return resoult;
    }

    private class GetCategory extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            if (netInfo != null) {

                databaseData.onClear();

                HttpHandler sh = new HttpHandler();
                //Link
                String url = "https://www.futuremind.com/recruitment-task";
                String jsonStr = sh.makeServiceCall(url);

                if (jsonStr != null) {
                    try {
                        JSONArray Data = new JSONArray(jsonStr);

                        //Przejscie do JSONObject
                        JSONObject resultObject = (JSONObject) Data.get(0);

                        // Pobieranie
                        for (int i = 0; i < Data.length(); i++) {
                            JSONObject c = Data.getJSONObject(i);

                            Description = c.getString("description");

                            ImageUrl = c.getString("image_url");

                            ModificationDate = c.getString("modificationDate");

                            OrderID = c.getString("orderId");

                            Title = c.getString("title");

                            List<String> extractedUrls = extractUrls(Description);

                            for (String WebAdresUrl : extractedUrls)
                            {
                                WebUrl = WebAdresUrl;
                            }

                            Description = Description.substring(0,
                                    ((Description.length()-WebUrl.length())));


                            Log.e("wczytanie", "Description:   " + Description);
                            Log.e("wczytanie", "WebUrl:   " + WebUrl);
                            Log.e("wczytanie", "ImageUrl:   " + ImageUrl);
                            Log.e("wczytanie", "ModificationDate:   " + ModificationDate);
                            Log.e("wczytanie", "OrderID:   " + OrderID);
                            Log.e("wczytanie", "Title:   " + Title);
                            Log.e("wczytanie", "-----------------------------------------------------" );

                            DataObject object = new DataObject(OrderID, ModificationDate, Description, Title, ImageUrl, WebUrl);

                            databaseData.addData(new DataObject(OrderID, ModificationDate, Description, Title, ImageUrl, WebUrl));

                            //Metoda dodawania do Objektu
                            resoult.add(object);
                        }
                    } catch (final JSONException e) {
                        Log.e(TAG, "Json parsing error: " + e.getMessage());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(),
                                        "Json parsing error: " + e.getMessage(),
                                        Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                } else {
                    Log.e(TAG, "Couldn't get json from server.");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Couldn't get json from server. Check LogCat for possible errors!",
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }else if (netInfo == null){

                Cursor res = databaseData.getAllData();

                while (res.moveToNext()) {
                    DataObject object = new DataObject(res.getString(0), res.getString(1), res.getString(2), res.getString(3), res.getString(4), res.getString(5));

                    //Metoda dodawania do Objektu
                    resoult.add(object);
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            //Metoda notujaca zmiany (Wywoluje zapisanie zmiennych)
            mDataAdapter.notifyDataSetChanged();

            //Zatrzymanie animacji wyszukiwania
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    public static List<String> extractUrls(String text)
    {
        List<String> containedUrls = new ArrayList<String>();
        String urlRegex = "((https?|ftp|gopher|telnet|file):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
        Pattern pattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE);
        Matcher urlMatcher = pattern.matcher(text);

        while (urlMatcher.find())
        {
            Log.e("containedUrls", "containedUrls:   " + containedUrls);
            containedUrls.add(text.substring(urlMatcher.start(0),
                    urlMatcher.end(0)));

            Log.e("containedUrls", "containedUrls:   " + containedUrls + "  "+ containedUrls.toString().length());
        }
        return containedUrls;
    }
}