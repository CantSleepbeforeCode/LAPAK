package com.dsc.lapak.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.dsc.lapak.R;
import com.dsc.lapak.entity.Area;

import java.util.ArrayList;

public class AreaAdapter extends BaseAdapter {
    private final Context context;
    private ArrayList<Area> areas;

    public AreaAdapter(Context context) {
        this.context = context;
        areas = new ArrayList<>();
    }

    public void setArea(ArrayList<Area> area) {
        this.areas = area;
    }

    @Override
    public int getCount() {
        return areas.size();
    }

    @Override
    public Object getItem(int i) {
        return areas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_area, viewGroup, false);
        }
        ViewHolder viewHolder = new ViewHolder(view);
        Area area = (Area) getItem(i);
//        viewHolder.bind(area);
        return view;
    }

    private class ViewHolder {
        private TextView txtTitle, txtCar, txtMotorcycle;
        private ImageView imgCover;

        ViewHolder(View view) {

        }
    }
}
