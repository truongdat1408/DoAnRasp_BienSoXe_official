package com.example.doanrasp_biensoxe;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainManagementActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private PostAdapter adapter;
    private ImageButton btn_add;
    private DatabaseReference DB;
    private ArrayList<Post> listNhanVien;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_management);

        listNhanVien = new ArrayList<Post>();
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DB = FirebaseDatabase.getInstance().getReference();
        Query query = DB.child("DemoStaff").orderByChild("name");

        //Chuyển tất cả dữ liệu lấy trong DataBase từ FireBase thành định dạng class Post
        final FirebaseRecyclerOptions<Post> options =
                new FirebaseRecyclerOptions.Builder<Post>()
                        .setQuery(query, Post.class)
                        .build();

        //Đưa vào bộ chuyển đổi và đổ dữ liệu ra recyclerView
        adapter = new PostAdapter(options, this);
        recyclerView.setAdapter(adapter);

        //Lấy dữ liệu trên DB về để so sánh code khi thực hiện add có đăng kí chưa?

        DB.child("DemoStaff").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Post noidung = dataSnapshot.getValue(Post.class);
                listNhanVien.add(noidung); //Dữ liệu lưu hết vào đây
                Log.d("HELLO", "onChildAdded: " + listNhanVien);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                 Post noidung = dataSnapshot.getValue(Post.class);
                for (Post i: listNhanVien) {
                    if (i.getCode() == noidung.getCode()){
                        listNhanVien.set(listNhanVien.indexOf(i),noidung);
                        Log.d("HELLO", "onChildChanged: " + listNhanVien);
                        break;
                    }
                }
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


        //Image button add event
        btn_add = (ImageButton) findViewById(R.id.imageButton_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialog = DialogPlus.newDialog(MainManagementActivity.this)
                        .setGravity(Gravity.CENTER) // Đưa vào trung tâm màn hình
                        .setMargin(50,0,50,0) //Set kích thước lề cho dialog
                        .setContentHolder(new ViewHolder(R.layout.content_add)) //Use ViewHolder as content holder if you want custom
                        .setExpanded(false)  // Kích hoạt tính năng mở rộng cho Dialog
                        .create(); //Tiến hành tạo Dialog mod trong content
                dialog.show(); // Hiển thị dialog ra khi bấm icon edit

                //Ánh xạ các button với các biến tương ứng
                Button btnAcceptAdd = findViewById(R.id.btn_add_accept);

                //Sự kiện nhấn nút button add sẽ lấy dữ liệu nhập vào được và đưa lên db
                btnAcceptAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final EditText namenv = findViewById(R.id.txt_namenvadd);
                        final EditText codenv = findViewById(R.id.txt_codenvadd);
                        final EditText phonenv = findViewById(R.id.txt_phonenvadd);

                        Map<String,Object> map = new HashMap<>();
                        map.put("name",namenv.getText().toString());
                        map.put("code",codenv.getText().toString());
                        map.put("phone",phonenv.getText().toString());
                        //Kiểm tra có trùng  khóa hay không
                        boolean check = true;
                        for(Post i : listNhanVien){
                            if(i.getCode().toString() == codenv.getText().toString()) {check = false; break;}
                        }
                        //Nếu trùng khóa thì không thực hiện add data
                        if (check == true) {
                            FirebaseDatabase.getInstance().getReference().child("DemoStaff").push()
                                    .setValue(map)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            dialog.dismiss();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(MainManagementActivity.this, "Lỗi đường truyền",Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(MainManagementActivity.this, "Add Thành công",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }else{
                            Toast.makeText(MainManagementActivity.this, "The Code was signed!", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening(); //Nếu có thay đổi nó sẽ tự gọi lại hàm (1 dạng trigger)
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening(); //Nếu thực hiện xong nó tạm thời dừng chương trình này
    }
}
