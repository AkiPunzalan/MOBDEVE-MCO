package com.mobdeve.mco.ViewHolders;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.core.widget.ImageViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.mobdeve.mco.R;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DailyViewHolder extends RecyclerView.ViewHolder{

    ImageView ivColor, ivCheck, ivAlarm;
    TextView tvName, tvTime;
    TextView tvMon, tvTue, tvWed, tvThu, tvFri, tvSat, tvSun;

    public DailyViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);

        ivColor = itemView.findViewById(R.id.iv_daily_color);
        ivCheck = itemView.findViewById(R.id.iv_daily_check);
        ivAlarm = itemView.findViewById(R.id.iv_daily_alarm);

        tvName = itemView.findViewById(R.id.tv_daily_name);
        tvTime = itemView.findViewById(R.id.tv_daily_time);

        tvMon = itemView.findViewById(R.id.tv_daily_mon);
        tvTue = itemView.findViewById(R.id.tv_daily_tue);
        tvWed = itemView.findViewById(R.id.tv_daily_wed);
        tvThu = itemView.findViewById(R.id.tv_daily_thu);
        tvFri = itemView.findViewById(R.id.tv_daily_fri);
        tvSat = itemView.findViewById(R.id.tv_daily_sat);
        tvSun = itemView.findViewById(R.id.tv_daily_sun);
    }

    public void setColor(String color){
        ImageViewCompat.setImageTintList(ivColor, ColorStateList.valueOf(Color.parseColor(color)));
    }

    public void setName(String name){
        tvName.setText(name);
    }

    public void setTime(LocalDateTime time){
        String formatedtime;
        LocalDateTime today = LocalDateTime.now();

        if(time == null){
            ivAlarm.setVisibility(View.GONE);
            tvTime.setVisibility(View.GONE);
        }
        else if(!(today.getYear() == time.getYear() && today.getMonth() == time.getMonth() && today.getDayOfMonth() == time.getDayOfMonth())){
            formatedtime = "starts at " + time.format(DateTimeFormatter.ofPattern("EE, hh:mm a"));
            tvTime.setText(formatedtime);
        } else {
            formatedtime = "starts at " + time.format(DateTimeFormatter.ofPattern("hh:mm a"));
            tvTime.setText(formatedtime);
        }
    }

    @SuppressLint("ResourceAsColor")
    public void setCheck(boolean complete, String color){

        if(complete)
            ImageViewCompat.setImageTintList(ivCheck, ColorStateList.valueOf(Color.parseColor(color)));
    }

    public void setDaysoftheWeek(boolean[] days, String color){
        
        if(days[0]) tvMon.setTextColor(Color.parseColor(color));
        if(days[1]) tvTue.setTextColor(Color.parseColor(color));
        if(days[2]) tvWed.setTextColor(Color.parseColor(color));
        if(days[3]) tvThu.setTextColor(Color.parseColor(color));
        if(days[4]) tvFri.setTextColor(Color.parseColor(color));
        if(days[5]) tvSat.setTextColor(Color.parseColor(color));
        if(days[6]) tvSun.setTextColor(Color.parseColor(color));
    }
}
