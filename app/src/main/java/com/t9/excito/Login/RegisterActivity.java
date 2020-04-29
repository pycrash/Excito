package com.t9.excito.Login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.orhanobut.hawk.Hawk;
import com.t9.excito.Home.HomeActivity;
import com.t9.excito.R;

import java.util.HashMap;
import java.util.Map;


public class RegisterActivity extends AppCompatActivity {

    String name, phoneNumber, email;
    EditText name_edit_text, phone_editText, email_editText;
    Button btnRegister;
    RelativeLayout rootLayout;
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        rootLayout=(RelativeLayout)findViewById(R.id.rootLayout_register);
        phoneNumber=getIntent().getStringExtra("phoneNumber");
        phone_editText=(EditText)findViewById(R.id.editTextMobile);
        phone_editText.setEnabled(false);
        phone_editText.setText(phoneNumber);

        name_edit_text=(EditText)findViewById(R.id.editTextName);
        name_edit_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkFields();
            }

            @Override
            public void afterTextChanged(Editable s) {
                checkFields();
            }
        });
        email_editText=(EditText)findViewById(R.id.editTextEmail);
        email_editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkFields();
            }

            @Override
            public void afterTextChanged(Editable s) {
                checkFields();
            }
        });

        btnRegister = (Button)findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = email_editText.getText().toString();
                name = name_edit_text.getText().toString();
                Map<String, Object> User = new HashMap<>();
                User.put("name",name);
                User.put("email",email);
                User.put("phoneNumber",phoneNumber);
                DocumentReference dbUsers = db.collection("Users").document(phoneNumber);
                dbUsers.set(User).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(RegisterActivity.this,"Account Created",Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Snackbar.make(rootLayout,e.getMessage(),Snackbar.LENGTH_LONG).show();
                        Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
                Hawk.init(RegisterActivity.this).build();
                Hawk.put("name", name);
                Hawk.put("phoneNumber",phoneNumber);
                Hawk.put("email",email);

                Intent intent=new Intent(RegisterActivity.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        });
    }

void checkFields(){
        if(name_edit_text.length()>3 && isValidEmail(email_editText.getText().toString().trim())){
            btnRegister.setEnabled(true);
        }
        else {
            btnRegister.setEnabled(false);
        }
}
    public static boolean isValidEmail(CharSequence target) {
        return target != null && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}
