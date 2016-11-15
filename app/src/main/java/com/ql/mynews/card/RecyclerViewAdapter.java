package com.ql.mynews.card;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ql.mynews.R;
import com.ql.mynews.bitmap.DownImage;
import com.ql.mynews.bitmap.ImageLoader;
import com.ql.mynews.utils.NewsOtherBean;
import com.ql.mynews.web.WebViewActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qinli on 2015/6/13.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.NewsViewHolder> {

    private List<NewsOtherBean> newses = new ArrayList<NewsOtherBean>();
    private Context context;
    public ImageLoader imageLoader; // 用来下载图片的类，后面有介绍

    public RecyclerViewAdapter(List<NewsOtherBean> newses, Context context) {
        this.newses = newses;
        this.context = context;
        imageLoader = new ImageLoader(context);
    }


    //自定义ViewHolder类
    static class NewsViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        ImageView news_photo;
        TextView news_title;
        TextView news_desc;
        TextView date;
        Button share;
        Button readMore;

        public NewsViewHolder(final View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            news_photo = (ImageView) itemView.findViewById(R.id.news_photo);
            news_title = (TextView) itemView.findViewById(R.id.news_title);
            news_desc = (TextView) itemView.findViewById(R.id.news_desc);
            date = (TextView) itemView.findViewById(R.id.date);
            share = (Button) itemView.findViewById(R.id.btn_share);
            readMore = (Button) itemView.findViewById(R.id.btn_more);
            //设置TextView背景为半透明
            news_title.setBackgroundColor(Color.argb(40, 0, 0, 0));

        }


    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.news_item, viewGroup, false);
        NewsViewHolder nvh = new NewsViewHolder(v);
        return nvh;
    }

    @Override
    public void onBindViewHolder(final NewsViewHolder personViewHolder, int i) {
        final int j = i;

//        personViewHolder.news_photo.setImageResource(newses.get(i).getPhotoId());
        personViewHolder.news_title.setText(newses.get(i).getTitle());
        personViewHolder.news_desc.setText(newses.get(i).getAuthor_name());
        personViewHolder.date.setText(newses.get(i).getDate());
        // 接口回调的方法，完成图片的读取;
        DownImage downImage = new DownImage(newses.get(j).getThumbnail_pic_s03()
                .toString());
        downImage.loadImage(new DownImage.ImageCallBack() {

            @Override
            public void getDrawable(Drawable drawable) {
                personViewHolder.news_photo.setImageDrawable(drawable);
            }
        });


        //为btn_share btn_readMore cardView设置点击事件
        personViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WebViewActivity.class);
                intent.putExtra("url", newses.get(j).getUrl());
                context.startActivity(intent);
            }
        });

        personViewHolder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
                intent.putExtra(Intent.EXTRA_TEXT, newses.get(j).getTitle());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(Intent.createChooser(intent, newses.get(j).getTitle()));
            }
        });

        personViewHolder.readMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WebViewActivity.class);
                intent.putExtra("News", newses.get(j).getUrl());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return newses.size();
    }
}
