package com.zxtech.ecs.ui.home.qmsmanager.bi;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zxtech.ecs.R;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {


    public List<ListItem> listItems;
    private Context context;

    public MyAdapter(List<ListItem> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_qms_bi_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, final int position) {
        final ListItem listItem = listItems.get(position);

        holder.head.setText(listItem.getHead());
        holder.a1.setText(listItem.getA1());
        holder.a2.setText(listItem.getA2());
        holder.a1_val.setText(Double.toString(listItem.getA1_val()));
        holder.a2_val.setText(Double.toString(listItem.getA2_val()));

        int p = position;
        int k = p % 4;


        if ((listItem.head).equals("处理周期")) {
            holder.head.setBackgroundColor(ContextCompat.getColor(context, R.color.main_aa));
            holder.a1.setTextColor(ContextCompat.getColor(context, R.color.main_aa));
            holder.a1_val.setTextColor(ContextCompat.getColor(context, R.color.main_aa));
            holder.a2.setTextColor(ContextCompat.getColor(context, R.color.main_aa));
            holder.a2_val.setTextColor(ContextCompat.getColor(context, R.color.main_aa));
            //set red image
            holder.imageView.setImageResource(R.mipmap.g1);
        }

        if ((listItem.head).equals("质量成本")) {
            holder.head.setBackgroundColor(ContextCompat.getColor(context, R.color.yellow));
            holder.a1.setTextColor(ContextCompat.getColor(context, R.color.yellow));
            holder.a1_val.setTextColor(ContextCompat.getColor(context, R.color.yellow));
            holder.a2.setTextColor(ContextCompat.getColor(context, R.color.yellow));
            holder.a2_val.setTextColor(ContextCompat.getColor(context, R.color.yellow));
            //set red image
            holder.imageView.setImageResource(R.mipmap.g2);
        }

        if ((listItem.head).equals("逃逸数报表")) {
            holder.head.setBackgroundColor(ContextCompat.getColor(context, R.color.green));
            holder.a1.setTextColor(ContextCompat.getColor(context, R.color.green));
            holder.a1_val.setTextColor(ContextCompat.getColor(context, R.color.green));
            holder.a2.setTextColor(ContextCompat.getColor(context, R.color.green));
            holder.a2_val.setTextColor(ContextCompat.getColor(context, R.color.green));
            //set red image
            holder.imageView.setImageResource(R.mipmap.g3);

        }

        if ((listItem.head).equals("质量问题排行")) {
            holder.head.setBackgroundColor(ContextCompat.getColor(context, R.color.grass_green));
            holder.a1.setTextColor(ContextCompat.getColor(context, R.color.grass_green));
            holder.a1_val.setTextColor(ContextCompat.getColor(context, R.color.grass_green));
            holder.a2.setTextColor(ContextCompat.getColor(context, R.color.grass_green));
            holder.a2_val.setTextColor(ContextCompat.getColor(context, R.color.grass_green));
            //set red image
            holder.imageView.setImageResource(R.mipmap.g4);

        }

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Toast.makeText(context,listItem.getHead(), Toast.LENGTH_LONG).show();
                //  Toast.makeText(context,Integer.toString(position), Toast.LENGTH_LONG).show();
                Context context = v.getContext();
                if (listItem.getHead().equals("处理周期")) {
                    Intent intent = new Intent(context, LT.class);

                    intent.putExtra("head", listItem.head);
                    intent.putExtra("a1", listItem.a1);
                    intent.putExtra("a1val", listItem.a1_val);
                    intent.putExtra("a2", listItem.a2);
                    intent.putExtra("a2val", listItem.a2_val);
                    intent.putExtra("y1", listItem.y2015);
                    intent.putExtra("y2", listItem.y2016);
                    intent.putExtra("y3", listItem.y2017);
                    intent.putExtra("y4", listItem.y2018);

                    context.startActivity(intent);
                }
                /*if (position ==1)
                {
                   Intent intent = new Intent(context, LT.class);
                 context.startActivity(intent);
                }*/

                else {
                    Intent intent = new Intent(context, G1.class);

                    intent.putExtra("head", listItem.head);
                    intent.putExtra("a1", listItem.a1);
                    intent.putExtra("a1val", Double.toString(listItem.getA1_val()));
                    intent.putExtra("a2", listItem.a2);
                    intent.putExtra("a2val", Double.toString(listItem.getA2_val()));
                    intent.putExtra("y1", listItem.y2015);
                    intent.putExtra("y2", listItem.y2016);
                    intent.putExtra("y3", listItem.y2017);
                    intent.putExtra("y4", listItem.y2018);

                    context.startActivity(intent);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView head, a1, a2, a1_val, a2_val, id;
        public LinearLayout linearLayout;
        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);

            head = (TextView) itemView.findViewById(R.id.textView2);
            a1 = (TextView) itemView.findViewById(R.id.textView21);
            a1_val = (TextView) itemView.findViewById(R.id.textView22);
            a2 = (TextView) itemView.findViewById(R.id.textView23);
            a2_val = (TextView) itemView.findViewById(R.id.textView24);


            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout);
            imageView = (ImageView) itemView.findViewById(R.id.imageView3);


        }
    }
}
