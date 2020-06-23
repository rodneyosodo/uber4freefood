package com.qualis.angelapp.Adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.qualis.angelapp.Model.Food;
import com.qualis.angelapp.R;
import com.qualis.angelapp.ViewHolder.FoodViewHolder;

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
        Food foodItem = foodListItems.get(position);

        ((FoodViewHolder)holder).foodName.setText(foodItem.getFoodName());
        ((FoodViewHolder)holder).foodServing.setText("Serves: " + foodItem.getServing());

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference("ProfilePictures/").child("foodImages");


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

    }

    @Override
    public int getItemCount() {
        return foodListItems.size();
    }
}
