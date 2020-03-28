package com.android.onlineshop_castanheirofreno.ui.confirmation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.android.onlineshop_castanheirofreno.R;
import com.android.onlineshop_castanheirofreno.ui.BaseActivity;
import com.android.onlineshop_castanheirofreno.ui.home.HomeActivity;

public class ConfirmationActivity extends BaseActivity {

    private Button goShopping;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Confirmation");
        navigationView.setCheckedItem(position);
        getLayoutInflater().inflate(R.layout.activity_confirmation, frameLayout);

        goShopping = (Button) findViewById(R.id.btn_go);
        goShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goShopping(v);
            }
        });
    }




    public void goShopping (View view){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}