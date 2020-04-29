package com.t9.excito.Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.t9.excito.Models.Order;
import com.t9.excito.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView productName, price, quantity;

    private StoreListAdapter.OnItemClickListener itemClickListener;


    public TextView getProductName() {
        return productName;
    }



    public CartViewHolder(@NonNull View itemView) {
        super(itemView);
        productName=(TextView)itemView.findViewById(R.id.name);
        quantity=(TextView)itemView.findViewById(R.id.quantity_text_view);
        price=(TextView)itemView.findViewById(R.id.price);
    }

    @Override
    public void onClick(View v) {

    }
}


public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {
    private List<Order> orderList= new ArrayList<>();
    private Context context;

    public CartAdapter(List<Order> orderList, Context context) {
        this.orderList = orderList;
        this.context = context;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View itemView=inflater.inflate(R.layout.order_list_item, parent, false);
        return new CartViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        holder.productName.setText(orderList.get(position).getProductName());
        holder.price.setText(orderList.get(position).getPrice());
        holder.quantity.setText(orderList.get(position).getQuantity());
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }
}
