package com.liuxe.wanandroid.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.liuxe.wanandroid.R;
import com.liuxe.wanandroid.bean.HotKey;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;

import java.util.List;
import java.util.Random;

public class FlowLayoutAdapter extends TagAdapter<HotKey> {
    public FlowLayoutAdapter(List datas) {
        super(datas);
    }

    @Override
    public View getView(FlowLayout parent, int position, HotKey hotKey) {
        TextView tvHotkey = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hotkey,null);
        tvHotkey.setTextColor(getRandomColor());
        tvHotkey.setText(hotKey.getName());
        return tvHotkey;
    }

    protected int getRandomColor() {
        Random random = new Random();
        int red = random.nextInt(200) + 50;
        int green = random.nextInt(200) + 50;
        int blue = random.nextInt(200) + 50;
        return Color.rgb(red, green, blue);
    }
}
