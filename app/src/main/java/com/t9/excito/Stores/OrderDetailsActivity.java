package com.t9.excito.Stores;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ListView;

import com.google.api.Distribution;
import com.t9.excito.Adapters.CartAdapter;
import com.t9.excito.Database.Database;
import com.t9.excito.Models.Order;
import com.t9.excito.R;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    List<Order> cart=new ArrayList<>();
    CartAdapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        recyclerView=(RecyclerView)findViewById(R.id.order_details_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        loadCart();


    }

    private void loadCart() {
        cart=new Database(this).getCarts();
        cartAdapter=new CartAdapter(cart,this);
        recyclerView.setAdapter(cartAdapter);


    }
}
