package com.t9.excito;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.t9.excito.Adapters.StoreListAdapter;
import com.t9.excito.Models.StoreListModel;

public class RestaurantsList extends AppCompatActivity {

    private StoreListAdapter adapter;
    String TAG="Departments";
    String hospital_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants_list);

        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference notebookRef = db.collection("Stores");
        Query query = notebookRef.orderBy("storeName", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<StoreListModel> options = new FirestoreRecyclerOptions.Builder<StoreListModel>()
                .setQuery(query, StoreListModel.class)
                .build();

        adapter = new StoreListAdapter(options);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_restaurants);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new StoreListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                Log.d(TAG, "onItemClick: item clicked 1");
                StoreListModel note = documentSnapshot.toObject(StoreListModel.class);
                String id = documentSnapshot.getId();
                String path = documentSnapshot.getReference().getPath();
                Toast.makeText(RestaurantsList.this,
                        "Position: " + position + " ID: " + id, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }


}