package com.android.onlineshop_castanheirofreno.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.onlineshop_castanheirofreno.R;
import com.android.onlineshop_castanheirofreno.ui.cart.CartActivity;
import com.android.onlineshop_castanheirofreno.ui.category.CategoryActivity;
import com.android.onlineshop_castanheirofreno.ui.customer.CustomerActivity;
import com.android.onlineshop_castanheirofreno.ui.home.HomeActivity;
import com.android.onlineshop_castanheirofreno.ui.mgmt.LoginActivity;
import com.android.onlineshop_castanheirofreno.ui.mgmt.SettingsActivity;
import com.android.onlineshop_castanheirofreno.ui.orders.OrdersActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final String PREFS_NAME = "SharedPrefs";
    public static final String PREFS_USER = "LoggedIn";
    public static final String PREFS_ITEM = "ItemId";
    public static final String PREFS_CATEGORYID = "CategoryId";

    protected FrameLayout frameLayout;

    protected DrawerLayout drawerLayout;

    protected NavigationView navigationView;

    protected static int position;

    protected Menu options;

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

        options = navigationView.getMenu();
        navigationView.setNavigationItemSelectedListener(this);


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



        if(id==R.id.nav_home || id==R.id.nav_order
                || id==R.id.nav_cart || id==R.id.nav_category
                || id==R.id.nav_account || id==R.id.nav_settings) {
            intent = new Intent(this, HomeActivity.class);
            intent.putExtra("menuId", id);
            navigationView.setCheckedItem(id);
        }

        if (intent != null) {
            intent.setFlags(
                    Intent.FLAG_ACTIVITY_CLEAR_TOP |
                            Intent.FLAG_ACTIVITY_NO_ANIMATION
            );
            startActivity(intent);
        }

        if(id==R.id.nav_logout){
            final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle(getString(R.string.action_logout));
            alertDialog.setCancelable(false);
            alertDialog.setMessage(getString(R.string.logout_msg));
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.action_logout), (dialog, which) -> logout());
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.action_cancel), (dialog, which) -> alertDialog.dismiss());
            alertDialog.show();
            navigationView.setCheckedItem(0);
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

        SharedPreferences.Editor editorCat = getSharedPreferences(BaseActivity.PREFS_CATEGORYID, 0).edit();
        editorCat.remove(BaseActivity.PREFS_CATEGORYID);
        editorCat.apply();

        FirebaseAuth.getInstance().signOut();

        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }
}
