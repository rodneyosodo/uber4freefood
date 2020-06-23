package com.qualis.qfood;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.qualis.qfood.Model.Food;

public class FoodDetailActivity extends AppCompatActivity {

    TextView foodName, foodDietType, foodServing, foodDescription, foodIngredients, foodSpecialNote;


    ImageView food_image;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton fabFood;


    String foodId="";
    FirebaseStorage storage;
    StorageReference storageReference;

    Food currentFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);




        foodName = (TextView)findViewById(R.id.food_name);
        foodDietType = (TextView)findViewById(R.id.food_diet_type);
        foodServing = (TextView)findViewById(R.id.food_serving);
        foodDescription = (TextView)findViewById(R.id.food_description);
        foodIngredients = (TextView)findViewById(R.id.food_ingredients);
        foodSpecialNote = (TextView)findViewById(R.id.food_special_note);

        fabFood = (FloatingActionButton)findViewById(R.id.btnCart);

        fabFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(FoodDetailActivity.this, "Food is now Active. Make your way to the collecting station", Toast.LENGTH_SHORT).show();

            }
        });


        collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);


        currentFood = new Food("2343", "Rice and Beef stew",
                "Non-Vegan",
                "sddfdsfsdfs",
                "sasadsad",
                "Food well prepared in a clean kitchen. The food was served to guests on 6/6/2020 and has a shelf life of two days. Enjoyed best when warmed",
                "Mushrooms, Soy sauce and lots of garlic",
                "4",
                "Has chillies. Not recommended for people with allergies",
                "sasadsad",
                "sddfdsfsdfs",
                "sasadsad",
                "sddfdsfsdfs",
                "Inactive"
        );

        collapsingToolbarLayout.setTitle(currentFood.getFoodName());

        foodName.setText(currentFood.getFoodName());
        foodDietType.setText(currentFood.getDietType());
        foodServing.setText("Serves: " + currentFood.getServing());
        foodDescription.setText(currentFood.getDescription());
        foodIngredients.setText(currentFood.getSpecialIngredients());
        foodSpecialNote.setText(currentFood.getSpecialNote());






    }
}