// https://developer.android.com/develop/ui/views/layout/recyclerview
package com.example.myapplication.takecar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    ArrayList<Car> carsList;
    private int CAR_IMAGE_WIDTH = 400;
    private int CAR_IMAGE_HEIGHT = 250;
    Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        private final ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.textViewCarListElement);
            imageView = (ImageView) view.findViewById(R.id.ivCar);
        }
        public TextView getTextView() {
            return textView;
        }

        public ImageView getImageView() {
            return imageView;
        }
    }

    public void runCarPage(View view, Car car) {
        Intent intent = new Intent(this.context, CarPageManager.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("car", car);
        context.startActivity(intent);
    }

    ///public MyAdapter(String[] dataSet) {
    //    localDataSet = dataSet;
    //}

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_car_layout, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.itemView.setTag(carsList.get(position).getID());
        viewHolder.getTextView().setText(carsList.get(position).getProducer().toString() + " " + carsList.get(position).getModel());
        Picasso.get().load(carsList.get(position).getPhotosUris().get(0)).resize(CAR_IMAGE_WIDTH, CAR_IMAGE_HEIGHT).into(viewHolder.getImageView());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runCarPage(view, carsList.get(position));
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return carsList.size();
    }

    public MyAdapter(ArrayList<Car> cars, Context context) {
        this.carsList = cars;
        this.context = context;
    }
}

