package com.t9.excito.Stores;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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
import com.t9.excito.R;

public class StoresActivity extends AppCompatActivity {

    private StoreListAdapter adapter;
    String type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);

        type=getIntent().getStringExtra("type");
        setUpRecyclerView();
    }
    private void setUpRecyclerView() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference notebookRef = db.collection("Stores");
        Query query = notebookRef.orderBy("storeName", Query.Direction.DESCENDING).whereEqualTo("type", type);

        FirestoreRecyclerOptions<StoreListModel> options = new FirestoreRecyclerOptions.Builder<StoreListModel>()
                .setQuery(query, StoreListModel.class)
                .build();

        adapter = new StoreListAdapter(options);

        RecyclerView recyclerView = findViewById(R.id.view_all_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new StoreListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                Log.d("Home", "onItemClick: item clicked 1");
                StoreListModel note = documentSnapshot.toObject(StoreListModel.class);
                String id = documentSnapshot.getId();
                String area=documentSnapshot.getString("area");
                String phone=documentSnapshot.getString("phoneNumber");
                String email=documentSnapshot.getString("email");

                String path = documentSnapshot.getReference().getPath();
                Toast.makeText(StoresActivity.this,
                        "Position: " + position + " ID: " + id+area+phone+email, Toast.LENGTH_LONG).show();
                Intent intent=new Intent(StoresActivity.this, ItemsListActivity.class);
                intent.putExtra("storeName",id);
                intent.putExtra("area",area);
                intent.putExtra("phoneNumber",phone);
                intent.putExtra("email",email);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onStart () {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop () {
        super.onStop();
        adapter.stopListening();
    }
}
