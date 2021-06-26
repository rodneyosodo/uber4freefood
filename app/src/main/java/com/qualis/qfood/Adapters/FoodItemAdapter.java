package com.qualis.qfood.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.qualis.qfood.FoodDetailActivity;
import com.qualis.qfood.Interface.ItemClickListener;
import com.qualis.qfood.MainActivity;
import com.qualis.qfood.Model.Food;
import com.qualis.qfood.R;
import com.qualis.qfood.ViewHolder.FoodViewHolder;

import java.util.List;

public class FoodItemAdapter extends RecyclerView.Adapter  {


    FirebaseStorage storage;
    StorageReference storageReference;

    private Context context;
    private List<Food> foodListItems;

    public FoodItemAdapter(Context context, List<Food> foodListItems) {
        this.context = context;
        this.foodListItems = foodListItems;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.food_item, parent, false);
        return new FoodViewHolder(view);

    }


    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        final Food foodItem = foodListItems.get(position);

        ((FoodViewHolder)holder).foodName.setText(foodItem.getFoodName());
        ((FoodViewHolder)holder).foodServing.setText("Serves: " + foodItem.getServing());

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference("FoodImages/").child(foodItem.getFoodImageId() +".jpg");


        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String imgURL = uri.toString();
                Glide.with(context).load(imgURL).into(((FoodViewHolder)holder).foodImage);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });

        ((FoodViewHolder)holder).setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {

                Intent foodDetail=  new Intent(context, FoodDetailActivity.class);
                Bundle extras = new Bundle();
                extras.putString("FoodId", foodListItems.get(position).getID() + "");
                foodDetail.putExtras(extras);
                context.startActivity(foodDetail);

            }
        });

    }

    @Override
    public int getItemCount() {
        return foodListItems.size();
    }
}
