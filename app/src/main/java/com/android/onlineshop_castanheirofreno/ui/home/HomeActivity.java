package com.android.onlineshop_castanheirofreno.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.android.onlineshop_castanheirofreno.R;
import com.android.onlineshop_castanheirofreno.adapter.NewItemsAdapter;
import com.android.onlineshop_castanheirofreno.database.entity.ItemEntity;
import com.android.onlineshop_castanheirofreno.ui.BaseActivity;
import com.android.onlineshop_castanheirofreno.ui.item.ItemDescriptionActivity;
import com.android.onlineshop_castanheirofreno.viewmodel.item.ItemViewModel;
import com.android.onlineshop_castanheirofreno.util.RecyclerViewItemClickListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomeActivity extends BaseActivity {

    private NewItemsAdapter newItemsAdapter;
    private ItemViewModel viewModel;
    private List<ItemEntity> newItems;
    private Timer timer;
    private int current_position = 0;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_home, frameLayout);

        setTitle("Home");
        navigationView.setCheckedItem(position);

        recyclerView = findViewById(R.id.newProducts_recyclerView);
        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        pagerSnapHelper.attachToRecyclerView(recyclerView);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == RecyclerView.SCROLL_STATE_IDLE) {
                    View centerView = pagerSnapHelper.findSnapView(layoutManager);
                    current_position = layoutManager.getPosition(centerView);
                }
            }
        });
        autoScroll();


        newItems = new ArrayList<>();

        newItemsAdapter = new NewItemsAdapter(this, newItems, new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(HomeActivity.this, ItemDescriptionActivity.class);
                intent.setFlags(
                        Intent.FLAG_ACTIVITY_NO_ANIMATION
                );
                intent.putExtra("itemId", newItems.get(position).getIdItem());
                intent.putExtra("idCategory", newItems.get(position).getIdCategory());
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View v, int position) {
                onBackPressed();
            }

        });

        ItemViewModel.Factory factory = new ItemViewModel.Factory(getApplication(), "", "");
        viewModel = ViewModelProviders.of(this, factory).get(ItemViewModel.class);
        viewModel.getNewItems().observe(this, newItemEntity -> {
            if (newItemEntity != null) {
                newItems = newItemEntity;
                newItemsAdapter.setData(newItems);
            }
        });

        recyclerView.setAdapter(newItemsAdapter);

    }

    private void autoScroll(){

        //Scroll automatically the cards
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {

                if(current_position==newItems.size()){
                    current_position = 0 ;
                    recyclerView.smoothScrollToPosition(current_position);
                }else {
                    recyclerView.smoothScrollToPosition(current_position++);
                }
            }
        };

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(runnable);
            }
        }, 2500, 2500);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
            return;
        }
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(getString(R.string.action_logout));
        alertDialog.setCancelable(false);
        alertDialog.setMessage(getString(R.string.logout_msg));
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.action_logout), (dialog, which) -> logout());
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.action_cancel), (dialog, which) -> alertDialog.dismiss());
        alertDialog.show();
    }

}

