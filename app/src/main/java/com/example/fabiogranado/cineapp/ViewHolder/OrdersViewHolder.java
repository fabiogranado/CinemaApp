package com.example.fabiogranado.cineapp.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.fabiogranado.cineapp.Interface.ItemClickListener;
import com.example.fabiogranado.cineapp.R;

public class OrdersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{



        public TextView txtOrderName, txtOrderPrice,  txtOrderDate, txtOrderNumber;
        private ItemClickListener itemClickListener;


    public OrdersViewHolder(View itemView) {
        super(itemView);


        txtOrderName = itemView.findViewById(R.id.order_name);
        txtOrderPrice = itemView.findViewById(R.id.order_price);
        txtOrderDate = itemView.findViewById(R.id.order_date);

    }

    @Override
    public void onClick(View view) {

        itemClickListener.onClick(view, getAdapterPosition(), false);

    }

    public void setItemClickListener(ItemClickListener itemClickListener){

        this.itemClickListener = itemClickListener;

    }
}
