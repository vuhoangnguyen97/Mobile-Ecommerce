package com.example.legia.mobileweb.AdapterSanPham;

import android.content.Context;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

public class SlidingAdapter extends PagerAdapter {

    Context context;
    int arrHinh[];

    public SlidingAdapter(Context context, int arrHinh[]){
        this.arrHinh = arrHinh;
        this.context = context;
    }


    @Override
    public int getCount() {
        return arrHinh.length;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == ((View) arg1);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void destroyItem(View arg0, int arg1, Object arg2) {
        ((ViewPager) arg0).removeView((View) arg2);
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        ImageView view = new ImageView(context);
        Picasso.get().load(arrHinh[position])
                .fit().into(view);

        ((ViewPager) collection).addView(view, 0);
        return view;
    }

    @Override
    public Parcelable saveState() {
        return null;
    }


}
