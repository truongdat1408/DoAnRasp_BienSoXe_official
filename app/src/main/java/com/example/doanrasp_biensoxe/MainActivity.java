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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
        mDatabase       = FirebaseDatabase.getInstance().getReference();

        TAG = "Test";
        //TaiKhoan tk = new TaiKhoan("admin", "admin");
        //mDatabase.child("Account").push().setValue(tk);

        mDatabase.child("Account").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d(TAG, "onCreate: " + dataSnapshot.getValue(TaiKhoan.class).matKhau);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    private void DangKy(){

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
                String user = edtTenDangNhap.getText().toString().trim();
                String pass = edtMatKhau.getText().toString();


                if(user.equals("admin") && pass.equals("admin")){

                    //Đăng nhập thành công
                    Toast.makeText(MainActivity.this, "Successfull login!",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(MainActivity.this, "Your login failed!",Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(MainActivity.this, "Please enter enough information!",Toast.LENGTH_SHORT).show();
            }
        }
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
