package com.example.doanrasp_biensoxe;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {
    private ImageButton btnQuayLai;
    private Handler handler = new Handler();
    private ListView listViewContent;

    ArrayList<content> arrayList    = new ArrayList<>();
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        AnhXa();

        final CustomAdapter customAdapter =new CustomAdapter(this, R.layout.item_content, arrayList);
        listViewContent.setAdapter(customAdapter);

        mDatabase.child("content").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                content noidung = dataSnapshot.getValue(content.class);
                arrayList.add(noidung);

                Toast.makeText(Main2Activity.this, noidung.getmOrder() + " - "
                        +noidung.getmCode()+ " - " + noidung.getmArriveTime() + " - "
                        +noidung.getmExitTime(),Toast.LENGTH_SHORT ).show();
                customAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                content noidung = dataSnapshot.getValue(content.class);
                arrayList.remove(arrayList.indexOf(noidung));
                Toast.makeText(Main2Activity.this, "Removing: "+ noidung.getmOrder() + " - "
                        +noidung.getmCode()+ " - " + noidung.getmArriveTime() + " - "
                        +noidung.getmExitTime(),Toast.LENGTH_SHORT ).show();
                customAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    void BangNoiDung(final ArrayList<content> arrayList) {
//        content content1 = new content( 1, "D1-43-8683", "12:00 2/4", "14:00 2/4" );
//        content content2 = new content( 2, "D1-97-8888", "13:00 2/4", "17:00 2/4" );
//        content content3 = new content( 3, "D1-43-1086", "19:00 2/4", "18:00 2/4" );
//
//        arrayList.add(content1);
//        arrayList.add(content2);
//        arrayList.add(content3);



    }


    //Anh xa cac nut bam
    private void AnhXa() {
        btnQuayLai = (ImageButton) findViewById(R.id.btnBack);
        listViewContent = (ListView) findViewById(R.id.lv_content);
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    //Nut quay lai
    public void comeBack(View view) {
        if(view.getId() == R.id.btnBack){
            finish();
        }
    }
}
