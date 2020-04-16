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

    private ItemEntity oldItem;
    private ItemEntity newItem = new ItemEntity();
    private List<CategoryEntity> categoriesList;

    private boolean categoryChanged = false;
    private MyListAdapter<CategoryEntity> adapterCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Edit Item");
        navigationView.setCheckedItem(position);

        getLayoutInflater().inflate(R.layout.activity_edit_item, frameLayout);

        initiateView();

        Intent intent = getIntent();
        String itemId = intent.getStringExtra("itemId");
        String catId = intent.getStringExtra("idCategory");


        ItemViewModel.Factory factory = new ItemViewModel.Factory(getApplication(), itemId, catId);
        viewModel = ViewModelProviders.of(this, factory).get(ItemViewModel.class);
        viewModel.getItem().observe(this, itemEntity -> {
            if (itemEntity != null) {
                oldItem = itemEntity;
                updateContent();
            }
        });

        categoriesList = new ArrayList<>();
        viewModel.getCategories().observe(this, categories -> {
            if (categories != null) {
                categoriesList = categories;
                updateSpinner(categories);
            }
        });

        validateButton = (Button) findViewById(R.id.btn_edit_product);
        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save(v);

            }
        });
    }

    private void updateSpinner(List<CategoryEntity> categories) {
        adapterCategories.updateData(new ArrayList<>(categories));
        int position = 0;
        for (int i=0; i<categories.size(); i++) {
            CategoryEntity cat = categories.get(i);
            if(cat.getIdCategory().equals(oldItem.getIdCategory())) {
                position = i;
                break;
            }
        }
        spinner.setSelection(position);

    }


    private void updateContent() {
        if (oldItem != null) {
            etproductName.setText(oldItem.getName());
            NumberFormat formatter = new DecimalFormat("#0.00");
            etprice.setText(formatter.format(oldItem.getPrice()));
            etdescription.setText(oldItem.getDescription());
        }

    }

    public void save(View view) {
        saveChanges(
                etproductName.getText().toString(),
                Double.parseDouble(etprice.getText().toString()),
                etdescription.getText().toString(),
                //imageButton.getId(),
                (CategoryEntity)spinner.getSelectedItem()
        );


    }


    private void saveChanges(String name, double price, String description, CategoryEntity category) {
        newItem.setIdItem(oldItem.getIdItem());
        newItem.setName(name);
        newItem.setPrice(price);
        newItem.setDescription(description);
        //item.setIdImage(idImage);
        newItem.setIdCategory(category.getIdCategory());
        viewModel.updateItem(newItem, oldItem, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                if (!newItem.getIdCategory().equals(oldItem.getIdCategory()))
                    categoryChanged = true;
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
            toast = Toast.makeText(this, "Item edited", Toast.LENGTH_LONG);
            toast.show();
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