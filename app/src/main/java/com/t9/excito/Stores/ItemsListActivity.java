package com.t9.excito.Stores;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.api.LogDescriptor;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.firestore.Query;
import com.t9.excito.Database.Database;
import com.t9.excito.Models.ItemListModel;
import com.t9.excito.Models.Order;
import com.t9.excito.R;

public class ItemsListActivity extends AppCompatActivity {

    private ItemListAdapter adapter;;
    ImageView back, call,email, directions;
    TextView storename, area;
    String phoneNumber, emailAddress, Area, store;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_list);
        store=getIntent().getStringExtra("storeName");
        phoneNumber=getIntent().getStringExtra("phoneNumber");
        emailAddress=getIntent().getStringExtra("email");
        Area=getIntent().getStringExtra("area");
        Toast.makeText(ItemsListActivity.this, store,Toast.LENGTH_LONG).show();
        Log.d("ItemList", "onCreate: "+store);
        call=(ImageView)findViewById(R.id.phone);
        email=(ImageView)findViewById(R.id.email);
        directions=(ImageView)findViewById(R.id.directions);

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:<"+phoneNumber+">"));
                startActivity(intent);             }
        });

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ItemsListActivity.this,emailAddress,Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Intent.ACTION_SEND);
                String[] strTo = { emailAddress };
                intent.putExtra(Intent.EXTRA_EMAIL, strTo);
                intent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
                intent.putExtra(Intent.EXTRA_TEXT, "Body");
                intent.setType("message/rfc822");
                intent.setPackage("com.google.android.gm");
                startActivity(intent);
            }
        });
        directions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri mapUri = Uri.parse("geo:0,0?q=" + Uri.encode(Area));
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, mapUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });



        setUpRecyclerView();



    }
    private void setUpRecyclerView() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference notebookRef = db.collection("Stores").document(store).collection("Items");
        Query query = notebookRef.orderBy("itemName", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<ItemListModel> options = new FirestoreRecyclerOptions.Builder<ItemListModel>()
                .setQuery(query, ItemListModel.class)
                .build();

        adapter = new ItemListAdapter(options);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_items);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new ItemListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                Log.d("Home", "onItemClick: item clicked 1");
                ItemListModel note = documentSnapshot.toObject(ItemListModel.class);
                String id = documentSnapshot.getId();
                String path = documentSnapshot.getReference().getPath();
                Toast.makeText(ItemsListActivity.this,
                        "Position: " + position + " ID: " + id, Toast.LENGTH_LONG).show();
            }
        });
        new Database(getBaseContext()).addToCart(new Order());
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

