package com.android.onlineshop_castanheirofreno.ui.cart;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.android.onlineshop_castanheirofreno.ConfirmationActivity;
import com.android.onlineshop_castanheirofreno.R;


public class CartFragment extends Fragment {

    private CartViewModel cartViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        cartViewModel =
                ViewModelProviders.of(this).get(CartViewModel.class);
        View root = inflater.inflate(R.layout.fragment_cart, container, false);

        return root;
    }

    public void seeConfirmation (View view) {
        Intent intent = new Intent(getContext(), ConfirmationActivity.class);
        startActivity(intent);
    }
}
