package com.t9.excito.Login;



import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.orhanobut.hawk.Hawk;
import com.t9.excito.Home.HomeActivity;
import com.t9.excito.R;

import org.w3c.dom.Text;

import java.util.concurrent.TimeUnit;


public class LoginActivity extends AppCompatActivity {
    EditText phoneNumber,otp;
    private String verificationId;
    TextView resendOTP,changeNumber;
    Button btnSendOTP,btnLogin;
    String phone, mobile;
    public static final String TAG="LoginActivity";
    private ProgressBar progressBarCircle;
    private TextView textViewTime;
    private CountDownTimer countDownTimer;
    RelativeLayout countdown;
    String name, email;
    LinearLayout resendLayout;
    ProgressBar progressBar;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    PhoneAuthProvider.ForceResendingToken token;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    RelativeLayout rootLayout;
    private long timeCountInMilliSeconds = 60000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        phoneNumber=(EditText)findViewById(R.id.editTextPhone);
        btnSendOTP=(Button)findViewById(R.id.sendOtpButton);
        btnLogin=(Button)findViewById(R.id.checkOTP);
        otp=(EditText)findViewById(R.id.editTextOTP);
        resendOTP=(TextView) findViewById(R.id.resendOTP);
        rootLayout = findViewById(R.id.rootLayout);
        countdown=(RelativeLayout)findViewById(R.id.countdown);
        resendLayout=(LinearLayout)findViewById(R.id.linear_layout_resend_OTP);
        resendOTP.setEnabled(false);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        changeNumber=(TextView)findViewById(R.id.change_phone_number);


        // method call to initialize the views
        initViews();
        // method call to initialize the listeners
       countdownTimerMethod();
        phoneNumber.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

        phoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (phoneNumber.length() > 9) {
                    btnSendOTP.setText("Send OTP");
                    Log.d(TAG, "onTextChanged:Phone length greater than 9, enabling btnContinue");
                    btnSendOTP.setEnabled(true);
                } else {
                    Log.d(TAG, "onTextChanged: btnContinue disabled as Phone length not greater than 9");
                    btnSendOTP.setEnabled(false);
                    btnSendOTP.setText("Enter a valid number");
                    Log.d(TAG, "onTextChanged: changing the background tint to red");
                    phoneNumber.getBackground().setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(LoginActivity.this, R.color.red), PorterDuff.Mode.SRC_ATOP));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (phoneNumber.length() > 9) {
                    Log.d(TAG, "afterTextChanged: phone length greater than 9");
                    Log.d(TAG, "afterTextChanged: enabling btnContinue");
                    btnSendOTP.setEnabled(true);
                    btnSendOTP.setText("Send OTP");
                    Log.d(TAG, "afterTextChanged: setting background tint to color primary");
                    phoneNumber.getBackground().setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(LoginActivity.this, R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP));

                }
            }
        });
        otp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (otp.length() > 5) {
                    Log.d(TAG, "onTextChanged:Phone length greater than 9, enabling btnContinue");
                    btnLogin.setEnabled(true);
                } else {
                    Log.d(TAG, "onTextChanged: btnContinue disabled as Phone length not greater than 9");
                    btnLogin.setEnabled(false);
                    Log.d(TAG, "onTextChanged: changing the background tint to red");
                    otp.getBackground().setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(LoginActivity.this, R.color.red), PorterDuff.Mode.SRC_ATOP));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (otp.length() > 5) {
                    Log.d(TAG, "afterTextChanged: phone length greater than 9");
                    Log.d(TAG, "afterTextChanged: enabling btnContinue");
                    btnLogin.setEnabled(true);
                    Log.d(TAG, "afterTextChanged: setting background tint to color primary");
                    otp.getBackground().setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(LoginActivity.this, R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP));

                }
            }
        });
        btnSendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resendLayout.setVisibility(View.VISIBLE);
                startCountDownTimer();
                progressBarCircle.setMax((int) timeCountInMilliSeconds / 1000);
                progressBarCircle.setProgress((int) timeCountInMilliSeconds / 1000);

                phoneNumber.setEnabled(false);
                changeNumber.setVisibility(View.VISIBLE);
                btnSendOTP.setVisibility(View.GONE);
                btnLogin.setVisibility(View.VISIBLE);
                resendOTP.setVisibility(View.VISIBLE);
                otp.setVisibility(View.VISIBLE);
                otp.requestFocus();
                InputMethodManager inputMethodManager=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED,InputMethodManager.HIDE_IMPLICIT_ONLY);
                phone=phoneNumber.getText().toString().trim();
                mobile="+91"+phone;
                Snackbar.make(rootLayout, "OTP sent successfully to "+mobile,Snackbar.LENGTH_LONG).show();
                sendVerificationCode(mobile);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnLogin.setEnabled(false);
                progressBar.setVisibility(View.VISIBLE);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(otp.getWindowToken(), 0);
                Log.d(TAG, "btnContinue onClick : started , calling verifyCode method");
                String code = otp.getText().toString().trim();

                verifyCode(code);
            }
        });
        resendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resendLayout.setVisibility(View.VISIBLE);
                startCountDownTimer();
                progressBarCircle.setMax((int) timeCountInMilliSeconds / 1000);
                progressBarCircle.setProgress((int) timeCountInMilliSeconds / 1000);

                resendOTP.setTextColor(ContextCompat.getColor(LoginActivity.this, R.color.red));
                resendOTP.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                Log.d(TAG, "resendOTP started: calling resendVerificationCode method");
                resendVerificationCode(mobile, token);
                Snackbar.make(rootLayout, "OTP resent successfully", Snackbar.LENGTH_LONG).show();
            }
        });
        changeNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePhoneNumber();
            }
        });
    }

    private void changePhoneNumber() {
        changeNumber.setVisibility(View.GONE);
        resendLayout.setVisibility(View.GONE);
        otp.setVisibility(View.GONE);
        phoneNumber.setText("");
        phoneNumber.setEnabled(true);
        btnLogin.setVisibility(View.GONE);
        btnSendOTP.setVisibility(View.VISIBLE);
        phoneNumber.requestFocus();
    }

    private void countdownTimerMethod() {
    }

    private void verifyCode(String code) {
        Log.d(TAG, "verifyCode: Starting method verify code");
        try {
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
            Log.d(TAG, "verifyCode: Code correct Calling signInWithCredential method");
            signInWithCredential(credential);
        } catch (Exception e) {
            Log.d(TAG, "verifyCode: Incorrect Verification Code"+e);
            Snackbar.make(rootLayout, "Incorrect Verification Code"+e, Snackbar.LENGTH_INDEFINITE).show();
            progressBar.setVisibility(View.GONE);

            Toast toast = Toast.makeText(this, "Verification Code is wrong", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }
    private void signInWithCredential(PhoneAuthCredential credential) {
        Log.d(TAG, "signInWithCredential: started");
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                          Log.d(TAG, "signInWIthCredential : Searching for snapshot of the " + mobile + "in collection " + "Users");
                            DocumentReference docIdRef = db.collection("Users").document(mobile);
                            docIdRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                                    @Override
                                                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                                        if (documentSnapshot.exists()) {
                                                                            Log.d(TAG, "signInWIthCredential: Document snapshot exists");
                                                                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                                                            name = documentSnapshot.getString("name");
                                                                            email = documentSnapshot.getString("email");
                                                                            btnLogin.setEnabled(true);
                                                                            progressBar.setVisibility(View.GONE);
                                                                            Log.d(TAG, "signInWIthCredential: Getting Document Snapshots from Firebase");
                                                                            Log.d(TAG, "signInWIthCredential: retrieved " + name + "" + email);
                                                                            intent.putExtra("phone", mobile);
                                                                            intent.putExtra("email", email);
                                                                            intent.putExtra("name", name);
                                                                            Hawk.init(LoginActivity.this).build();
                                                                            Hawk.put("name",name);
                                                                            Hawk.put("email",email);
                                                                            Hawk.put("phoneNumber",mobile);

                                                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                                            Log.d(TAG, "signInWIthCredential: Starting activity ProfileExists and clearing off the previous tasks");
                                                                            startActivity(intent);
                                                                        } else {
                                                                            Log.d(TAG, "signInWIthCredential: Document snapshot does not exists");
                                                                            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                                                                            intent.putExtra("phoneNumber", mobile);
                                                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                                            progressBar.setVisibility(View.GONE);
                                                                            btnLogin.setEnabled(true);
                                                                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                                                            imm.hideSoftInputFromWindow(otp.getWindowToken(), 0);
                                                                            Log.d(TAG, "signInWIthCredential: Starting activity ProfileCreation and clearing off the previous tasks");
                                                                            startActivity(intent);
                                                                        }
                                                                    }

                                                                }

                            );

                        }
                           else {
                            Snackbar.make(rootLayout, "Something Went Wrong", Snackbar.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                            btnLogin.setEnabled(true);
                            Log.d(TAG, "signInWIthCredential: Somethong went wrong" + task.getException().getMessage());
                            Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void sendVerificationCode(String number) {
        Log.d(TAG, "sendVerificationCode: started");
        Log.d(TAG, "sendVerificationCode: sending code to" + number);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallBack);
    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            token = forceResendingToken;
            verificationId = s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            Log.d(TAG, "onVerificationCompleted: Checking that code is correct or not");
            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                otp.setText(code);
                Log.d(TAG, "onVerificationCompleted: Sending OTp for checking to verifyCode methos");
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Log.d(TAG, "onVerificationFailed: Failed" + e.getMessage());
            Snackbar.make(rootLayout, "Incorrect Verification Code", Snackbar.LENGTH_LONG).show();
            Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };

    private void resendVerificationCode(String phoneNumber,
                                        PhoneAuthProvider.ForceResendingToken token) {
        Log.d(TAG, "resendVerificationCode: resending OTP to " + phoneNumber);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallBack,         // OnVerificationStateChangedCallbacks
                token);             // ForceResendingToken from callbacks
    }

    /**
     * method to initialize the views
     */
    private void initViews() {
        progressBarCircle = (ProgressBar) findViewById(R.id.progressBarCircle);
        textViewTime = (TextView) findViewById(R.id.textViewTime);

    }

    /**
     * method to start count down timer
     */
    private void startCountDownTimer() {

        countDownTimer = new CountDownTimer(timeCountInMilliSeconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                textViewTime.setText(hmsTimeFormatter(millisUntilFinished));

                progressBarCircle.setProgress((int) (millisUntilFinished / 1000));

            }

            @Override
            public void onFinish() {

                textViewTime.setText(hmsTimeFormatter(timeCountInMilliSeconds));
                // call to initialize the progress bar values
                setProgressBarValues();
                resendOTP.setEnabled(true);
                countdown.setVisibility(View.GONE);
                resendOTP.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                resendOTP.setTextColor(ContextCompat.getColor(LoginActivity.this, R.color.colorPrimary));

            }

        }.start();
        countDownTimer.start();
    }


    private void setProgressBarValues() {

        progressBarCircle.setMax((int) timeCountInMilliSeconds / 1000);
        progressBarCircle.setProgress((int) timeCountInMilliSeconds / 1000);
    }


    /**
     * method to convert millisecond to time format
     *
     * @param milliSeconds
     * @return HH:mm:ss time formatted string
     */
    private String hmsTimeFormatter(long milliSeconds) {

        String hms = String.format("%02d",
                TimeUnit.MILLISECONDS.toSeconds(milliSeconds));

        return hms;


    }

}
