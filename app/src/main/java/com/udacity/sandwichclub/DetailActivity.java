package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private TextView mAlsoKnownAsLabelTextView;
    private TextView mAlsoKnownAsTextView;
    private TextView mIngridientsTextView;
    private TextView mPlaceOfOriginTextView;
    private TextView mDescriptionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        mAlsoKnownAsTextView = (TextView)findViewById(R.id.also_known_tv);
        mIngridientsTextView = (TextView)findViewById(R.id.ingredients_tv);
        mPlaceOfOriginTextView = (TextView)findViewById(R.id.origin_tv);
        mDescriptionTextView = (TextView)findViewById(R.id.description_tv);
        mAlsoKnownAsLabelTextView = (TextView)findViewById(R.id.detail_also_known_as_label_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = null;
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        StringBuilder sb = new StringBuilder();

        for (String s: sandwich.getIngredients()) {
            sb.append(s);
            sb.append("\n");
        }
        mIngridientsTextView.append(sb.length() > 0 ? sb.substring(0, sb.length() - 1): "");

        mPlaceOfOriginTextView.append(sandwich.getPlaceOfOrigin());

        sb.setLength(0);
        for (String s: sandwich.getAlsoKnownAs()) {
            sb.append(s);
            sb.append(",");
        }
        if (sb.length() == 0) {
            mAlsoKnownAsTextView.setVisibility(View.INVISIBLE);
            mAlsoKnownAsLabelTextView.setVisibility(View.INVISIBLE);
        } else {
            mAlsoKnownAsTextView.append(sb.length() > 0 ? sb.substring(0, sb.length() - 1): "");
        }
        mDescriptionTextView.append(sandwich.getDescription());
    }
}
