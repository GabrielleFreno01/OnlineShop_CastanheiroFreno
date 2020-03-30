package com.android.onlineshop_castanheirofreno.ui.item;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.lifecycle.ViewModelProviders;

import com.android.onlineshop_castanheirofreno.R;
import com.android.onlineshop_castanheirofreno.database.entity.CategoryEntity;
import com.android.onlineshop_castanheirofreno.database.entity.ItemEntity;
import com.android.onlineshop_castanheirofreno.ui.BaseActivity;
import com.android.onlineshop_castanheirofreno.ui.home.HomeActivity;
import com.android.onlineshop_castanheirofreno.util.OnAsyncEventListener;

import java.util.ArrayList;
import java.util.List;

public class EditItemActivity extends BaseActivity {



    private Toast toast;

    Spinner spinner;
    ImageButton imageButton;
    Button validateButton;
    EditText etproductName;
    EditText etprice;
    EditText etdescription;

    ItemViewModel viewModel;

    ItemEntity item;

    List<CategoryEntity> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Edit Item");
        navigationView.setCheckedItem(position);

        getLayoutInflater().inflate(R.layout.activity_edit_item, frameLayout);

        initiateView();

        SharedPreferences settings = getSharedPreferences(ItemListActivity.PREFS_ITEM, 0);
        long itemId = settings.getLong(ItemListActivity.PREFS_ITEM, 0);

       ItemViewModel.Factory factory = new ItemViewModel.Factory(getApplication(), itemId, 0L);
        viewModel = ViewModelProviders.of(this, factory).get(ItemViewModel.class);
        viewModel.getItem().observe(this, itemEntity -> {
            if (itemEntity != null) {
                item = itemEntity;
                updateContent();
            }

        });



        validateButton = (Button) findViewById(R.id.btn_edit_product);
        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save(v);
                onBackPressed();

            }
        });
    }


    private void updateContent() {
        if (item != null) {
            etproductName.setText(item.getName());
            etprice.setText(String.valueOf(item.getPrice()));
            etdescription.setText(item.getDescription());
            //spinner.setSelection(item.getIdCategory());

            /*viewModel.getCategories().observe(this, categoriesEntity -> {
                if (categoriesEntity != null) {
                    categories = categoriesEntity;
                    System.out.println(categories.size());
                }

            });


            ArrayList<String> categoriesArray = new ArrayList<String>();
            for (CategoryEntity categoryEntity : categories) {
                categoriesArray.add(categoryEntity.getName());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, categoriesArray);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);*/
        }

    }

    public void save(View view) {
        saveChanges(
                etproductName.getText().toString(),
                Integer.parseInt(etprice.getText().toString()),
                etdescription.getText().toString(),
                //imageButton.getId(),
                spinner.getSelectedItemId()

        );

    }


    private void saveChanges(String name, int price, String description, long idCategory) {
        item.setName(name);
        item.setPrice(price);
        item.setDescription(description);
        //item.setIdImage(idImage);
        item.setIdCategory(idCategory);

        viewModel.updateItem(item, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                setResponse(true);
            }

            @Override
            public void onFailure(Exception e) {
                setResponse(false);
            }
        });
    }

    private void setResponse(Boolean response) {
        if (response) {
            updateContent();
            toast = Toast.makeText(this, getString(R.string.client_edited), Toast.LENGTH_LONG);
            toast.show();
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
            return;
        }
        super.onBackPressed();
        startActivity(new Intent(this, HomeActivity.class));
    }



    private void initiateView() {
        spinner = findViewById(R.id.spinner_category);
        //imageButton = findViewById(R.id.imagebtn_addImage);
        etproductName = findViewById(R.id.input_product_name);
        etprice = findViewById(R.id.input_price);
        etdescription = findViewById(R.id.input_description);

    }
}