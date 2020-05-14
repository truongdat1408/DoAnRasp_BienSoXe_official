package com.example.doanrasp_biensoxe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<content> {
    private Context context;
    private int resource;
    private List<content> arrContent;
    public CustomAdapter(@NonNull Context context, int resource, @NonNull List<content> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.arrContent = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolders viewHolders;

        if(convertView == null){
            convertView         = LayoutInflater.from(context)
                    .inflate(R.layout.item_content, parent, false);
            viewHolders = new ViewHolders();

            viewHolders.tv_order = (TextView) convertView.findViewById(R.id.tv_order);
            viewHolders.tv_code = (TextView) convertView.findViewById(R.id.tv_code);
            viewHolders.tv_arrive_time = (TextView) convertView.findViewById(R.id.tv_arrive_time);
            viewHolders.tv_exit_time = (TextView) convertView.findViewById(R.id.tv_exit_time);

            convertView.setTag(viewHolders);
        }else{
            viewHolders = (ViewHolders) convertView.getTag();
        }
        //Trả về mảng vị trí đó
        content noidung = arrContent.get(position);

        //Lấy nội dung đưa vào giao diện
        viewHolders.tv_order.setText(String.valueOf(noidung.getmOrder()));
        viewHolders.tv_code.setText(noidung.getmCode());
        viewHolders.tv_arrive_time.setText(noidung.getmArriveTime());
        viewHolders.tv_exit_time.setText(noidung.getmExitTime());

        return convertView;
    }

    public class  ViewHolders{
        TextView tv_order;
        TextView tv_code;
        TextView tv_arrive_time;
        TextView tv_exit_time;
    }
}
