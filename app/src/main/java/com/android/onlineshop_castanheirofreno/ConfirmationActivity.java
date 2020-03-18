package com.android.onlineshop_castanheirofreno;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;




public class ConfirmationActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.purchase_confirmation);
    }
    /*
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.purchase_confirmation, container, false);

        return root;
    }*/



    public void goShopping (View view){
        /*Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);*/
        onBackPressed();
    }
}