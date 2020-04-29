package com.t9.excito.Stores;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.t9.excito.Models.ItemListModel;
import com.t9.excito.R;

public class ItemListAdapter extends FirestoreRecyclerAdapter<ItemListModel, ItemListAdapter.NoteHolder> {
    private OnItemClickListener listener;

    public ItemListAdapter(@NonNull FirestoreRecyclerOptions<ItemListModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull NoteHolder holder, int position, @NonNull ItemListModel model) {
        holder.textViewItem.setText(model.getItemName());
        holder.textViewPrice.setText(model.getItemPrice());

    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,
                parent, false);
        return new NoteHolder(v);
    }

    class NoteHolder extends RecyclerView.ViewHolder {
        TextView textViewItem;
        TextView textViewPrice;
        CardView addCart;

        public NoteHolder(View itemView) {
            super(itemView);
            textViewItem = itemView.findViewById(R.id.item_name);
            textViewPrice = itemView.findViewById(R.id.item_price);
            addCart=itemView.findViewById(R.id.add_to_cart);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });
            addCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });


        }
    }
    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
