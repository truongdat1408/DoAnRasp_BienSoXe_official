package com.example.doanrasp_biensoxe;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {
    private ImageButton btnQuayLai;
    private Handler handler = new Handler();
    private ListView listViewContent;
    private Button buttonClearHistory;

    ArrayList<Content> arrayList = new ArrayList<>();
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        AnhXa();

        final CustomAdapter customAdapter =new CustomAdapter(this, R.layout.item_content, arrayList);
        listViewContent.setAdapter(customAdapter);
        mDatabase.child("Content").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                Content noidung = dataSnapshot.getValue(Content.class);
                Log.d("CHECK NOIDUNG", "onChildAdded: "+noidung.toString());
                arrayList.add(noidung);
                Log.d("CHECK ARRAY", "onChildAdded: "+arrayList.get(arrayList.size()-1).toString());
                // Log.d("CHECK ARRAY", "onChildAdded: "+arrayList.get(arrayList.size()-1).toString());
                Log.d("Check INDEXREMOVE", "onChildRemoved: "+findmOrderInArray(noidung));
                Toast.makeText(Main2Activity.this, noidung.getmOrder() + " - "
                        +noidung.getmCode()+ " - " + noidung.getmArriveTime() + " - "
                        +noidung.getmName(),Toast.LENGTH_SHORT ).show();
                customAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//                Content noidung = dataSnapshot.getValue(Content.class);
//                Log.d("Check NOIDUNG", "onChildRemoved: "+ noidung.toString());
//                Log.d("CHECK ARRAY", "onChildAdded: "+arrayList.get(arrayList.size()-1).toString());
//                Log.d("Check INDEXREMOVE", "onChildRemoved: "+findmOrderInArray(noidung));
//                arrayList.remove(arrayList.indexOf(noidung));
//                Toast.makeText(Main2Activity.this, "Removing: "+ noidung.getmOrder() + " - "
//                        +noidung.getmCode()+ " - " + noidung.getmArriveTime() + " - "
//                        +noidung.getmName(),Toast.LENGTH_SHORT ).show();
//                customAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        buttonClearHistory = (Button) findViewById(R.id.btn_clear_history);
        buttonClearHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Main2Activity.this, R.style.Theme_AppCompat_Light_Dialog_Alert);
                builder.setTitle("Are you sure CLEAR ALL HISTORY?");
                builder.setMessage("Your choice: ");
                builder.setIcon(android.R.drawable.ic_dialog_alert);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        arrayList.clear();
                        customAdapter.notifyDataSetChanged();
                        Toast.makeText(Main2Activity.this, "DONE!", Toast.LENGTH_SHORT).show();
                        FirebaseDatabase.getInstance().getReference()
                                .child("Content")
                                .setValue(null) //Khi set value bằng null thì child sẽ tự động bị xóa
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                    }
                                });
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Không có gì cả
                    }
                });

                builder.show();
            }
        });

    }

    private int findmOrderInArray(Content content) {
        for (int i=0;i<arrayList.size()-1;i++) {
            Log.d("CHECK FORRRRRRR", "findmOrderInArray: "+arrayList.get(i).getmOrder());
            if (content.getmOrder()== arrayList.get(i).getmOrder()) return i;
        }
        return -1;
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

    //Nut Management
    public void management(View view){
        if(view.getId() == R.id.btn_management){
            Intent intent = new Intent(Main2Activity.this, MainManagementActivity.class);
            startActivity(intent);
        }
    }

}

/*Change Event
*
* Content noidung = dataSnapshot.getValue(Content.class);
                for (Content i: arrayList) {
                    if (i.getmCode() == noidung.getmCode()){
                        i.setmExitTime(noidung.getmName());
                        Toast.makeText(Main2Activity.this, "Update: "+ noidung.getmOrder() + " - "
                                +noidung.getmCode()+ " - " + noidung.getmArriveTime() + " - "
                                +noidung.getmName(),Toast.LENGTH_SHORT ).show();
                        break;
                    }
                }
                customAdapter.notifyDataSetChanged();
* */