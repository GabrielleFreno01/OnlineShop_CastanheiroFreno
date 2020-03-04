package com.android.onlineshop_castanheirofreno;


import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListActivity extends Activity {

    ListView categoryList;
    String[] categories = {"Audio", "Cameras", "Computers", "Gaming", "Smartphones", "TVs"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view_category);

        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.list_view_category, categories);

        categoryList = (ListView) findViewById(R.id.mobile_list);
        categoryList.setAdapter(adapter);
    }
}