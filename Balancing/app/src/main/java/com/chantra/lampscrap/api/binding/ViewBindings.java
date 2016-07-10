package com.chantra.lampscrap.api.binding;

import android.databinding.BindingAdapter;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.CardView;
import android.view.View;

/**
 * Created by phearom on 3/30/16.
 */
public class ViewBindings {
    @BindingAdapter("bgColor")
    public static void setBackgroundColor(View view, int color) {
        if (null != view)
            view.setBackgroundColor(color);
    }

    @BindingAdapter("cardBackgroundColor")
    public static void setBackgroundColor(CardView view, int color) {
        if (null != view)
            view.setCardBackgroundColor(color);
    }

//    @BindingAdapter("bindSrc")
//    public static void setImage(AppCompatImageView view, String url) {
//        if (null != view)
//            Glide.with(view.getContext()).load(url).into(view);
//    }
//
//    @BindingAdapter("bindSrc")
//    public static void setImage(AppCompatImageView view, int icon) {
//        if (null != view)
//            Glide.with(view.getContext()).load(icon).into(view);
//    }

    @BindingAdapter("bindRes")
    public static void setImageRes(AppCompatImageView view, Integer icon) {
        if (null != view)
            view.setImageResource(icon);
    }
}
