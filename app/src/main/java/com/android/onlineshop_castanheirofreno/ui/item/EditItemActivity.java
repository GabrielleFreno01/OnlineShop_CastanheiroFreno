package com.android.onlineshop_castanheirofreno.ui.item;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProviders;

import com.android.onlineshop_castanheirofreno.R;
import com.android.onlineshop_castanheirofreno.database.entity.ItemEntity;
import com.android.onlineshop_castanheirofreno.ui.BaseActivity;

public class EditItemActivity extends BaseActivity {

    private Toast toast;

    Spinner spinner;
    ImageButton imageButton;
    Button validateButton;
    EditText etproductName;
    EditText etprice;
    EditText etquantity;
    EditText etdescription;

    ItemViewModel viewModel;

    ItemEntity item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Edit Item");
        navigationView.setCheckedItem(position);

        getLayoutInflater().inflate(R.layout.activity_edit_item, frameLayout);

        initiateView();

        SharedPreferences settings = getSharedPreferences(ItemListActivity.PREFS_ITEM, 0);
        long itemid = settings.getLong(ItemListActivity.PREFS_ITEM, 0);

       /* ItemViewModel.Factory factory = new ItemViewModel.Factory(getApplication(), user);
        viewModel = ViewModelProviders.of(this, factory).get(ItemViewModel.class);
        viewModel.getCustomer().observe(this, customerEntity -> {
            if (customerEntity != null) {
                client = customerEntity;
                updateContent();
            }
        });
*/
        validateButton = (Button) findViewById(R.id.btn_save);
        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save(v);

            }
        });
    }

    public void save(View view) {
        saveChanges(
                etproductName.getText().toString(),
                Integer.parseInt(etprice.getText().toString()),
                Integer.parseInt(etquantity.getText().toString()),
                etdescription.getText().toString(),
                imageButton.getId(),
                spinner.getSelectedItemId()

        );

    }


    private void saveChanges(String name, int price, int quantity, String description, long idImage, long idCategory) {
        item.setName(name);
        item.setPrice(price);
        item.setQuantity_in_stock(quantity);
        item.setDescription(description);
        item.setIdImage(idImage);
        item.setIdCategory(idCategory);
        }


    private void initiateView() {
        spinner = findViewById(R.id.spinner_category);
        imageButton = findViewById(R.id.imagebtn_addImage);
        etproductName = findViewById(R.id.input_product_name);
        etprice = findViewById(R.id.input_price);
        etquantity = findViewById(R.id.input_quantity);
        etdescription = findViewById(R.id.input_description);

    }
}