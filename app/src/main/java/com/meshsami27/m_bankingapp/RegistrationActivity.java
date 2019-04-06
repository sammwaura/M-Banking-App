package com.meshsami27.m_bankingapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {
    public  static final String TAG = RegistrationActivity.class.getSimpleName();

    @BindView(R.id.emailEditText)
    EditText mEmailEditText;
    @BindView(R.id.mobileNumberEditText)
    EditText mMobileNumberEditText;
    @BindView(R.id.accountNumberEditText)
    EditText mAccountNumberEditText;
    @BindView(R.id.IDPassportNumberEditText)
    EditText mIDPassportNumberEditText;
    @BindView(R.id.editTextFirstName)
    EditText mFirtsNameEditText;
    @BindView(R.id.editTextSurname)
    EditText mSurnameEditText;
    @BindView(R.id.SignIn_btn)
    EditText mSignInBtn;

    private ProgressDialog mAuthProgressDialog;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();
        createAuthStateListener();
        createAuthProgressDialog();

        mSignInBtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if (view == mSignInBtn){
            Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

    }

    private void createAuthProgressDialog() {
        mAuthProgressDialog = new ProgressDialog(this);
        mAuthProgressDialog.setTitle("Loading...");
        mAuthProgressDialog.setMessage("Authenticating with Firebase...");
        mAuthProgressDialog.setCancelable(false);
    }

    private void createNewUser(){
        final String name = mFirtsNameEditText.getText().toString().trim();
        final String surname = mSurnameEditText.getText().toString().trim();
        final String email = mEmailEditText.getText().toString().trim();
        final String mobilenumber = mMobileNumberEditText.getText().toString().trim();
        final String accountNumber = mAccountNumberEditText.getText().toString().trim();
        final String IDPassportNumber = mIDPassportNumberEditText.getText().toString().trim();

        boolean validEmail = isValidEmail(email);
        boolean validName = isValidName(name);
        boolean validSurname = isValidSurname(surname);
        boolean validMobileNumber = isValidMobileNumber(mobilenumber);
        boolean validAccountNumber = isValidAccountNumber(accountNumber);
        boolean validIDPassportNumber = isValidIDPassportNumber(IDPassportNumber);
        if (!validEmail || !validName || !validSurname || !validMobileNumber || !validAccountNumber || !validIDPassportNumber) return;

        mAuthProgressDialog.show();

        mAuth.createUserWithEmailAndPassword(email, mobilenumber)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        mAuthProgressDialog.dismiss();
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Authentication successfull");
                        } else {
                            Toast.makeText(RegistrationActivity.this, "Authentication Failed!",
                                Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void createAuthStateListener(){
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user!= null) {
                    Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                    intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private boolean isValidEmail(String email){
        boolean isGoodEmail = (email != null && Patterns.EMAIL_ADDRESS.matcher(email).matches());
        if (!isGoodEmail){
            mEmailEditText.setError("Please enter a valid email address");
            return false;
        }
        return isGoodEmail;
    }

    private boolean isValidName(String name){
        if (name.equals("")) {
            mFirtsNameEditText.setError("Please enter your name");
            return false;
        }
        return true;

    }

    private boolean isValidSurname(String name){
        if (name.equals("")) {
            mSurnameEditText.setError("Please enter your Surname");
            return false;
        }
        return true;

    }

    private boolean isValidMobileNumber(String mobilenumber){
        if (mobilenumber.equals("")) {
            mMobileNumberEditText.setError("Please enter your Mobile Number");
            return false;
        }
        return true;
    }

    private boolean isValidAccountNumber(String accountnumber){
        if (accountnumber.equals("")) {
            mAccountNumberEditText.setError("Please enter your AccountNumber");
            return false;
        }
        return true;
    }

    private boolean isValidIDPassportNumber(String IDPassportNumber){
        if (IDPassportNumber.equals("")){
            mIDPassportNumberEditText.setError("Please enter your IDPassportNumber");
            return false;
        }
        return true;
    }
}
