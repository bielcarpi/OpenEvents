package com.grup8.OpenEvents.model.utils;

import android.annotation.SuppressLint;
import android.widget.ImageView;

import com.grup8.OpenEvents.R;
import com.grup8.OpenEvents.model.OpenEventsApplication;
import com.squareup.picasso.Picasso;

public class ImageHelper {

    public static void bindImageToEvent(String url, ImageView holder){
        bindImage(url, R.drawable.ic_add_event, holder);
    }


    public static void bindImageToUser(String url, ImageView holder){
        bindImage(url, R.drawable.ic_person, holder);
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    private static void bindImage(String url, int drawable, ImageView holder){
        if(url == null || url.trim().isEmpty())
            holder.setImageDrawable(OpenEventsApplication.getAppContext().getDrawable(drawable));
        else
            Picasso.get().load(url).error(drawable).into(holder);
    }
}
