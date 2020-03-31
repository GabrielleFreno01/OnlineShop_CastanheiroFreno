package com.android.onlineshop_castanheirofreno.ui.item;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProviders;

import com.android.onlineshop_castanheirofreno.R;
import com.android.onlineshop_castanheirofreno.adapter.MyListAdapter;
import com.android.onlineshop_castanheirofreno.database.entity.CategoryEntity;
import com.android.onlineshop_castanheirofreno.database.entity.ItemEntity;
import com.android.onlineshop_castanheirofreno.ui.BaseActivity;
import com.android.onlineshop_castanheirofreno.ui.category.CategoryActivity;
import com.android.onlineshop_castanheirofreno.util.OnAsyncEventListener;
import com.android.onlineshop_castanheirofreno.viewmodel.item.ItemViewModel;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class EditItemActivity extends BaseActivity {


    private Toast toast;

    private Spinner spinner;
    //ImageButton imageButton;
    private Button validateButton;
    private EditText etproductName;
    private EditText etprice;
    private EditText etdescription;

    private ItemViewModel viewModel;

    private ItemEntity item;

    private MyListAdapter<CategoryEntity> adapterCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Edit Item");
        navigationView.setCheckedItem(position);

        getLayoutInflater().inflate(R.layout.activity_edit_item, frameLayout);

        initiateView();

        Intent intent = getIntent();
        long itemId = intent.getLongExtra("itemId", 0L);
        long catId = intent.getLongExtra("idCategory", 0L);

        ItemViewModel.Factory factory = new ItemViewModel.Factory(getApplication(), itemId, catId);
        viewModel = ViewModelProviders.of(this, factory).get(ItemViewModel.class);
        viewModel.getItem().observe(this, itemEntity -> {
            if (itemEntity != null) {
                item = itemEntity;
                updateContent();
            }
        });

        viewModel.getCategories().observe(this, categories -> {
            if (categories != null) {
                updateSpinner(categories);
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

    private void updateSpinner(List<CategoryEntity> categories) {
        adapterCategories.updateData(new ArrayList<>(categories));

    }


    private void updateContent() {
        if (item != null) {
            etproductName.setText(item.getName());
            NumberFormat formatter = new DecimalFormat("#0.00");
            etprice.setText(formatter.format(item.getPrice()));
            etdescription.setText(item.getDescription());
            spinner.setSelection((int) (item.getIdCategory()) - 1);

        }

    }

    public void save(View view) {
        saveChanges(
                etproductName.getText().toString(),
                Double.parseDouble(etprice.getText().toString()),
                etdescription.getText().toString(),
                //imageButton.getId(),
                spinner.getSelectedItemId() + 1

        );

    }


    private void saveChanges(String name, double price, String description, long idCategory) {
        item.setName(name);
        item.setPrice(price);
        item.setDescription(description);
        //item.setIdImage(idImage);
        item.setIdCategory(idCategory);


        //TODO list de category
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
            toast = Toast.makeText(this, "Order edited", Toast.LENGTH_LONG);
            toast.show();
            Intent intent = new Intent(EditItemActivity.this, CategoryActivity.class);
            startActivity(intent);
            finish();
        }
    }



    private void initiateView() {
        //imageButton = findViewById(R.id.imagebtn_addImage);
        etproductName = findViewById(R.id.input_product_name);
        etprice = findViewById(R.id.input_price);
        etdescription = findViewById(R.id.input_description);

        spinner = findViewById(R.id.spinner_category);
        adapterCategories = new MyListAdapter<>(this, R.layout.row_category, new ArrayList<>());
        //ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.category_list, android.R.layout.simple_spinner_item);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterCategories);

    }
}