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

import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    ArrayList<Car> carsList;
    public boolean isRunningTakeList;
    private int CAR_IMAGE_WIDTH = 400;
    private int CAR_IMAGE_HEIGHT = 250;
    Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        private final ImageView imageView;
        private final TextView tvYear;
        private final TextView tvDailyPrice;

        public ViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.textViewCarListElement);
            imageView = (ImageView) view.findViewById(R.id.ivCar);
            tvYear = (TextView) view.findViewById(R.id.textViewCarListElementYear);
            tvDailyPrice = (TextView) view.findViewById(R.id.textViewCarListElementHourlyPrice);
        }
        public TextView getTextView() {
            return textView;
        }

        public ImageView getImageView() {
            return imageView;
        }

        public TextView getTvYear() {
            return tvYear;
        }

        public TextView getTvDailyPrice() {
            return tvDailyPrice;
        }
    }

    public void runCarPage(View view, Car car) {
        Intent intent = new Intent(this.context, CarPageManager.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("car", car);
        context.startActivity(intent);
    }

    public void runEditCarPage(View view, Car car) {
        Intent intent = new Intent(this.context, EditCarPageManager.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("car", car);
        context.startActivity(intent);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_car_layout, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {
        viewHolder.itemView.setTag(carsList.get(position).getID());
        viewHolder.getTextView().setText(carsList.get(position).getProducer().toString() + " " + carsList.get(position).getModel());
        Picasso.get().load(carsList.get(position).getPhotosUris().get(0)).fit().into(viewHolder.getImageView());
        viewHolder.getTvYear().setText(String.valueOf(carsList.get(position).getYear()));
        if (isRunningTakeList) {
            viewHolder.getTvDailyPrice().setText("FROM " + carsList.get(position).getHourlyPrice() + "$/hour");
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    runCarPage(view, carsList.get(position));
                }
            });
        }
        else {
            viewHolder.getTvDailyPrice().setText("PROPERTIES");
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    runEditCarPage(view, carsList.get(position));
                }
            });
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return carsList.size();
    }

    public MyAdapter(ArrayList<Car> cars, boolean isRunningTakeList, Context context) {
        this.carsList = cars;
        this.isRunningTakeList = isRunningTakeList;
        this.context = context;
    }
}

