package com.example.doanrasp_biensoxe;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

public class PostAdapter extends FirebaseRecyclerAdapter<Post, PostAdapter.PastViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    private Context context;
    public PostAdapter(@NonNull FirebaseRecyclerOptions<Post> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull PastViewHolder holder, final int i, @NonNull final Post post) {

        holder.name.setText(post.getName());
        holder.code.setText(post.getCode());
        holder.phone.setText(post.getPhone());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference()
                        .child("DemoStaff")
                        .child(getRef(i).getKey())
                        .setValue(null) //Khi set value bằng null thì child sẽ tự động bị xóa
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(context, "DONE!", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialog = DialogPlus.newDialog(context)
                        .setGravity(Gravity.CENTER) // Đưa vào trung tâm màn hình
                        .setMargin(50,0,50,0) //Set kích thước lề cho dialog
                        .setContentHolder(new ViewHolder(R.layout.content)) //Use ViewHolder as content holder if you want custom
                        .setExpanded(false)  // Kích hoạt tính năng mở rộng cho Dialog
                        .create(); //Tiến hành tạo Dialog mod trong content

                View holderView = (LinearLayout)dialog.getHolderView(); //Set dạng dialog LinearLayout

                // Ánh xạ các id tương ứng với các biến
                final EditText namenv = holderView.findViewById(R.id.txt_namenv);
                final EditText codenv = holderView.findViewById(R.id.txt_codenv);
                final EditText phonenv = holderView.findViewById(R.id.txt_phonenv);

                // Lấy nội dung và đẩy lên textbox trong dialog
                namenv.setText(post.getName());
                codenv.setText(post.getCode());
                phonenv.setText(post.getPhone());

                Button update = holderView.findViewById(R.id.btn_update);

                //Khi bấm nút update sẽ thấy thông tin nội dung từ textbox vừa edit và cập nhật firebase và recyclerView
                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Chuyển thành dạng json: key: value tương ứng các trường và gắn dữ liệu vào map
                        // Lấy dữ liệu từ các ô textbox
                        Map<String,Object> map = new HashMap<>();
                        map.put("name",namenv.getText().toString());
                        map.put("code",codenv.getText().toString());
                        map.put("phone",phonenv.getText().toString());

                        FirebaseDatabase.getInstance().getReference()
                                .child("DemoStaff")
                                .child(getRef(i).getKey()) //Lấy vị trí thứ i và key của vị trí đó ra mà cập nhật lại dữ liệu
                                .updateChildren(map) // Update lại dữ liệu theo map
                                .addOnCompleteListener(new OnCompleteListener<Void>() { //Hoàn thành update sẽ tự động tắt dialog
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        dialog.dismiss();
                                    }
                                });
                    }
                });

                dialog.show(); // Hiển thị dialog ra khi bấm icon edit
            }
        });


    }

    @NonNull
    @Override
    public PastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.post, parent, false);
        return new PastViewHolder(view);
    }

    class PastViewHolder extends RecyclerView.ViewHolder{

        TextView name,code,phone;
        ImageView edit, delete;

        public PastViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_namenv);
            code = itemView.findViewById(R.id.tv_codenv);
            phone = itemView.findViewById(R.id.tv_phonenv);
            edit = itemView.findViewById(R.id.edit);
            delete = itemView.findViewById(R.id.delete);
        }
    }
}
