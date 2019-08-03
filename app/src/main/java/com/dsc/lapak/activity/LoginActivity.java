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
import android.widget.TextView;

import com.dsc.lapak.R;
import com.dsc.lapak.database.UserHelper;
import com.dsc.lapak.entity.User;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    EditText editTextEmail, editTextPassword;
    TextInputLayout textInputLayoutEmail, textInputLayoutPassword;
    Button buttonLogin;
    ProgressBar progressBar;
    UserHelper userHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userHelper = new UserHelper(this);
        initCreateAccountTextView();

        editTextEmail = findViewById(R.id.ipt_username);
        editTextPassword = findViewById(R.id.ipt_pass);
        textInputLayoutEmail = findViewById(R.id.layout_username);
        textInputLayoutPassword = findViewById(R.id.layout_password);
        progressBar = findViewById(R.id.progress_bar_login);
        buttonLogin = findViewById(R.id.btn_login);

        editTextEmail.setText("kangparkir@gmail.com");
        editTextPassword.setText("123456");

        // atur klik event tombol login
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Periksa input pengguna apakah sudah benar atau tidak
                if (validate()){
                    // Dapatkan nilai dari bidang EditText
                    String Email = editTextEmail.getText().toString();
                    String Password = editTextPassword.getText().toString();

                    progressBar.setVisibility(View.VISIBLE);

                    // validasi user
                    User currentUser = userHelper.Authenticate(new User(null, null, null, null, Email, Password), getApplicationContext());

                    // cek validasinya berhasil atau ga
                    if(currentUser != null){
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                Intent intent = new Intent();
                                if (userHelper.userLevel.equals("member")) {
                                    intent = new Intent(LoginActivity.this, HomeActivity.class);
                                    intent.putExtra("KEY_ID", userHelper.idUser);
                                    intent.putExtra("KEY_NAME", userHelper.nameUser);
                                    intent.putExtra("KEY_LEVEL", userHelper.userLevel);
                                    intent.putExtra("KEY_BALANCE", userHelper.userBalance);
                                    intent.putExtra("KEY_EMAIL", userHelper.emailUser);
                                    intent.putExtra("KEY_PASSWORD", userHelper.passwordUser);

                                } else if (userHelper.userLevel.equals("mitra")) {
                                    intent = new Intent(LoginActivity.this, MitraActivity.class);
                                    intent.putExtra("KEY_ID", userHelper.idUser);
                                    intent.putExtra("KEY_NAME", userHelper.nameUser);
                                    intent.putExtra("KEY_LEVEL", userHelper.userLevel);
                                    intent.putExtra("KEY_BALANCE", userHelper.userBalance);
                                    intent.putExtra("KEY_EMAIL", userHelper.emailUser);
                                    intent.putExtra("KEY_PASSWORD", userHelper.passwordUser);
                                }
                                startActivity(intent);
                                finish();
                            }
                        }, 3000);
                        // Pengguna Masuk dengan Berhasil Meluncurkan aktivitas layar utama Anda

                    } else {
                        // gagal login
                        progressBar.setVisibility(View.GONE);
                        Snackbar.make(buttonLogin, getString(R.string.login_failed), Snackbar.LENGTH_LONG).show();
                    }
                }
            }
        });

    }
    // metode ini digunakan untuk mengatur teks Buat akun TextView dan klik acara (warna maltipal
    // untuk TextView namun belum didukung dalam Xml jadi saya telah melakukannya secara terprogram)

    private void initCreateAccountTextView(){
        TextView textViewCreateAccount = findViewById(R.id.txt_signup);
        textViewCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    // Metode ini untuk menangani penghentian metode dari Html
//    @SuppressWarnings("deprecation")
//    public static Spanned fromHyml(String html){
//        Spanned result;
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
//            result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
//        } else {
//            result = Html.fromHtml(html);
//        }
//        return result;
//    }

    // Metode ini digunakan untuk memvalidasi input yang diberikan oleh pengguna
    public boolean validate(){
        boolean valid = false;

        // Dapatkan nilai dari bidang EditText
        String Email = editTextEmail.getText().toString();
        String Password = editTextPassword.getText().toString();

        // Menangani validasi untuk Email
        if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
            valid = false;
            textInputLayoutEmail.setError(getString(R.string.error_email));
        } else {
            valid = true;
            textInputLayoutEmail.setError(null);
        }

        // Menangani validasi untuk Password
        if(Password.isEmpty()){
            valid = false;
            textInputLayoutPassword.setError(getString(R.string.error_password_length));
        } else {
            if(Password.length() > 5){
                valid = true;
                textInputLayoutPassword.setError(null);
            } else {
                textInputLayoutPassword.setError(getString(R.string.error_password_length));
            }
        }
        return valid;
    }
}
