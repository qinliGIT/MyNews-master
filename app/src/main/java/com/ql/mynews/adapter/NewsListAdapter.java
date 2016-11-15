package com.ql.mynews.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ql.mynews.R;
import com.ql.mynews.bitmap.ImageLoader;
import com.ql.mynews.bitmap.DownImage;
import com.ql.mynews.utils.NewsAllBean;

import java.util.ArrayList;
import java.util.List;

public class NewsListAdapter extends BaseAdapter {
    private Context context;
    private List<NewsAllBean> list = new ArrayList<NewsAllBean>();
    public ImageLoader imageLoader; // 用来下载图片的类，后面有介绍

    public NewsListAdapter(Context context, List<NewsAllBean> list) {
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
            view = LayoutInflater.from(context).inflate(R.layout.newsall_item,
                    null, false);
            holder.title = (TextView) view
                    .findViewById(R.id.title);
            holder.img = (ImageView) view
                    .findViewById(R.id.img);
            view.setTag(holder);
        } else {
            view = convertView;
        }
        NewsAllBean news = list.get(position);
        final viewHolder holder = (viewHolder) view.getTag();
        holder.title.setText(news.getTitle());
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
        ImageView img;
    }
}
