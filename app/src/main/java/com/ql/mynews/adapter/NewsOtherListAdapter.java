package com.ql.mynews.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ql.mynews.bitmap.ImageLoader;
import com.ql.mynews.bitmap.DownImage;
import com.ql.mynews.utils.NewsOtherBean;

import java.util.ArrayList;
import java.util.List;

import static com.ql.mynews.R.*;

public class NewsOtherListAdapter extends BaseAdapter {
    private Context context;
    private List<NewsOtherBean> list = new ArrayList<NewsOtherBean>();
    public ImageLoader imageLoader; // 用来下载图片的类，后面有介绍

    public NewsOtherListAdapter(Context context, List<NewsOtherBean> list) {
        this.context = context;
        this.list = list;
        imageLoader = new ImageLoader(context);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        if (convertView == null) {
            viewHolder holder = new viewHolder();
            view = LayoutInflater.from(context).inflate(layout.newsall_item,
                    null, false);
            holder.title = (TextView) view
                    .findViewById(id.title);
            holder.date = (TextView) view
                    .findViewById(id.date);
            holder.img = (ImageView) view
                    .findViewById(id.img);
            view.setTag(holder);
        } else {
            view = convertView;
        }
        NewsOtherBean news = list.get(position);
        final viewHolder holder = (viewHolder) view.getTag();
        holder.title.setText(news.getTitle());
        holder.date.setText(news.getDate());
//        holder.img.setImageDrawable(context.getResources().getDrawable(R.mipmap.ic_launcher));
        // 接口回调的方法，完成图片的读取;
        DownImage downImage = new DownImage(list.get(position).getThumbnail_pic_s03()
                .toString());
        downImage.loadImage(new DownImage.ImageCallBack() {

            @Override
            public void getDrawable(Drawable drawable) {
                holder.img.setImageDrawable(drawable);
            }
        });
        return view;
    }

    class viewHolder {
        TextView title;
        TextView date;
        ImageView img;
    }
}
