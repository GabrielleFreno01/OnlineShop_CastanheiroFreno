package com.android.onlineshop_castanheirofreno.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.android.onlineshop_castanheirofreno.R;
import com.android.onlineshop_castanheirofreno.ui.cart.CartActivity;
import com.android.onlineshop_castanheirofreno.ui.category.CategoryActivity;
import com.android.onlineshop_castanheirofreno.ui.customer.CustomerActivity;
import com.android.onlineshop_castanheirofreno.ui.home.HomeActivity;
import com.android.onlineshop_castanheirofreno.ui.item.ItemListActivity;
import com.android.onlineshop_castanheirofreno.ui.mgmt.LoginActivity;
import com.android.onlineshop_castanheirofreno.ui.mgmt.SettingsActivity;
import com.android.onlineshop_castanheirofreno.ui.orders.OrdersActivity;
import com.google.android.material.navigation.NavigationView;

public class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final String PREFS_NAME = "SharedPrefs";
    public static final String PREFS_USER = "LoggedIn";
    public static final String PREFS_ITEM= "ItemId";


    protected FrameLayout frameLayout;

    protected DrawerLayout drawerLayout;

    protected NavigationView navigationView;


    protected static int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        frameLayout = findViewById(R.id.flContent);

        drawerLayout = findViewById(R.id.base_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.base_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
            return;
        }
        BaseActivity.position = 0;
        super.onBackPressed();
    }




    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == BaseActivity.position) {
            drawerLayout.closeDrawer(GravityCompat.START);
            return false;
        }
        BaseActivity.position = id;
        Intent intent = null;

        navigationView.setCheckedItem(id);

        if (id == R.id.nav_home) {
            intent = new Intent(this, HomeActivity.class);
        } else if (id == R.id.nav_order) {
            intent = new Intent(this, OrdersActivity.class);
        } else if (id == R.id.nav_cart) {
            intent = new Intent(this, CartActivity.class);
        } else if (id == R.id.nav_category) {
            intent = new Intent(this, CategoryActivity.class);
        } else if (id == R.id.nav_logout) {
            logout();
        }else if (id == R.id.nav_account) {
            intent = new Intent(this, CustomerActivity.class);
        }
        else if (id == R.id.nav_settings) {
            intent = new Intent(this, SettingsActivity.class);
        }
        if (intent != null) {
            intent.setFlags(
                    Intent.FLAG_ACTIVITY_NO_ANIMATION
            );
            startActivity(intent);
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void logout() {
        SharedPreferences.Editor editor = getSharedPreferences(BaseActivity.PREFS_USER, 0).edit();
        editor.remove(BaseActivity.PREFS_USER);
        editor.apply();

        SharedPreferences.Editor editorItem = getSharedPreferences(BaseActivity.PREFS_ITEM, 0).edit();
        editorItem.remove(BaseActivity.PREFS_ITEM);
        editorItem.apply();

        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }
}
