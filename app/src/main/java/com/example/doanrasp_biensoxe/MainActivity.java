package com.example.doanrasp_biensoxe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.gms.tasks.OnCompleteListener;

public class MainActivity extends AppCompatActivity {
    EditText edtTenDangNhap, edtMatKhau;
    Button btnlogin, btnExit;
    DatabaseReference mDatabase;
    String TAG;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnhXa();
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    //Ánh xạ các nút -> Biến
    private void AnhXa() {
        edtTenDangNhap  = (EditText) findViewById(R.id.edtUser);
        edtMatKhau      = (EditText) findViewById(R.id.edtPass);
        btnlogin        = (Button) findViewById(R.id.buttonLogin);
        btnExit         = (Button) findViewById(R.id.buttonExit);
        mAuth           = FirebaseAuth.getInstance();
    }

    public void dangNhap(View view) {
        if(view.getId() == R.id.buttonLogin){
            if(edtTenDangNhap.getText().length() != 0 && edtMatKhau.getText().length() != 0){
                login();
            }else{
                Toast.makeText(MainActivity.this, "Please enter enough information!",Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void login(){
        mAuth = FirebaseAuth.getInstance();
        String email = edtTenDangNhap.getText().toString().trim();
        String password = edtMatKhau.getText().toString();
        //Function kiem tra auth trên Firebase nếu có tài khoản thì đăng nhập vào
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(MainActivity.this, "Successfull login!",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(MainActivity.this, "Your login failed!",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void nutThoat(View view) {
        if(view.getId() == R.id.buttonExit){
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.Theme_AppCompat_Light_Dialog_Alert);
            builder.setTitle("Are you sure EXIT the app?");
            builder.setMessage("Your choice: ");
            builder.setIcon(android.R.drawable.ic_dialog_alert);
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //Hàm thoát app
                    System.exit(1);
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //Không có gì cả
                }
            });
            //Hiển thị bảng thông báo
            builder.show();
        }
    }
}
