package com.qualis.angelapp.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.qualis.angelapp.Interface.ItemClickListener;
import com.qualis.angelapp.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class FoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView foodName, foodServing;
    public CircleImageView foodImage;

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public FoodViewHolder(View itemView) {
        super(itemView);

        foodName = (TextView) itemView.findViewById(R.id.food_name);
        foodServing = (TextView) itemView.findViewById(R.id.food_serving);

        foodImage = (CircleImageView) itemView.findViewById(R.id.food_image);

        itemView.setOnClickListener(this);
    }



    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view,getAdapterPosition(),false);

    }
}
