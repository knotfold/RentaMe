package com.example.pcwin.rentame;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MyAdapterImgDepto extends RecyclerView.Adapter<MyAdapterImgDepto.ViewHolder> {
    private List<ListItemsImgDepto> listItems;
    private Context context;

    public MyAdapterImgDepto(List<ListItemsImgDepto> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public MyAdapterImgDepto.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_rv_img_depto, parent, false);
        return new ViewHolder(v);


    }



    @Override
    public void onBindViewHolder(MyAdapterImgDepto.ViewHolder holder, int position) {
        final ListItemsImgDepto listItemsImgDepto = listItems.get(position);
        Picasso.with(context).load(listItemsImgDepto.getImgUrl()).into(holder.imageViewDepto);


       /* holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*Toast.makeText(context, "You clicked" + listItemIglesia.getNombreIglesia(), Toast.LENGTH_LONG).show();

                Intent intent = new Intent(v.getContext(), IglesiaDetails.class);
                Bundle extras = new Bundle();
                extras.putString("EXTRA_IdIglesia", listItemIglesia.getIdIgleisa());
                extras.putString("EXTRA_NombreIglesia", listItemIglesia.getNombreIglesia());
                intent.putExtras(extras);
                v.getContext().startActivity(intent);


            }
        });

        */

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        public ImageView imageViewDepto;


        public ViewHolder(View itemView) {
            super(itemView);
            imageViewDepto = (ImageView) itemView.findViewById(R.id.imageViewDepto);



        }
    }
}
