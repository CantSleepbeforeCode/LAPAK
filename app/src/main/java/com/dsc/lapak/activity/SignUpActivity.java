package com.dsc.lapak.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.dsc.lapak.R;
import com.dsc.lapak.database.UserHelper;
import com.dsc.lapak.entity.User;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

public class SignUpActivity extends AppCompatActivity {

    TextInputLayout layoutName, layoutEmail, layoutPassword;
    EditText inputName, inputEmail, inputPassword;
    Button buttonRegister;
    UserHelper userHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        userHelper = new UserHelper(this);

        layoutName = findViewById(R.id.layout_name);
        layoutEmail = findViewById(R.id.layout_email);
        layoutPassword = findViewById(R.id.layout_password_signup);
        inputName = findViewById(R.id.input_name);
        inputEmail = findViewById(R.id.input_email);
        inputPassword = findViewById(R.id.input_password);
        buttonRegister = findViewById(R.id.button_register);
        final ProgressBar progressBar = findViewById(R.id.progress_bar);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    String txtName = inputName.getText().toString().trim();
                    String txtEmail = inputEmail.getText().toString().trim();
                    String txtPassword = inputPassword.getText().toString().trim();
                    progressBar.setVisibility(View.VISIBLE);


//                    Cek kalo datanya udah ada atau ga

                    if (!userHelper.isEmailExists(txtEmail, getApplicationContext())) {
                        userHelper.insert(new User(null, txtName, "member", "0", txtEmail, txtPassword));
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                Intent login = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(login);
                            }
                        }, 3000);
                    } else {
                        progressBar.setVisibility(View.GONE);
                        inputName.setText("");
                        inputEmail.setText("");
                        inputPassword.setText("");
                        Snackbar.make(buttonRegister, getString(R.string.already_exist), Snackbar.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    public boolean validate() {
        boolean valid = false;
        int score = 1;

        String txtName = inputName.getText().toString();
        String txtEmail = inputEmail.getText().toString();
        String txtPassword = inputPassword.getText().toString();

//        cek namanya
        if (txtName.isEmpty()) {
            valid = false;
            inputName.setError(getString(R.string.error_name));
        } else {
            if (txtName.length() > 2) {
                valid = true;
                inputName.setError(null);
            } else {
                score = 0;
                inputName.setError(getString(R.string.error_length_name));
            }
        }

//        cek emailnya
        if (!Patterns.EMAIL_ADDRESS.matcher(txtEmail).matches()) {
            valid = false;
            inputEmail.setError(getString(R.string.error_email));
        } else {
            if (score == 0) {
                valid = false;
            } else {
                valid = true;
            }
            inputEmail.setError(null);
        }

//        cek password
        if (txtPassword.isEmpty()) {
            valid = false;
            inputPassword.setError(getString(R.string.error_password_length));
        } else {
            if (txtPassword.length() < 6) {
                valid = false;
                inputPassword.setError(getString(R.string.error_password_length));
            } else {
                if (score == 0) {
                    valid = false;
                } else {
                    valid = true;
                }
                inputPassword.setError(null);
            }
        }
        return valid;
    }
}
