package com.inventano.cuworkshop;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.List;

/**
 * Created by HP on 17-02-2018.
 */

public class postAdapter extends RecyclerView.Adapter<postAdapter.PostViewHolder>{
    private Context context;
    private List<Item> items;
    postAdapter(Context context, List<Item> item)
    {
        this.context = context;
        this.items = item;
    }
    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.post_view,parent,false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PostViewHolder holder, int position) {


        final Item item = items.get(position);
        holder.postTitle.setText(item.getTitle());
        Document document = Jsoup.parse(item.getContent());
        holder.postDescription.setText(document.text());
        Elements elements = document.select("img");
        try {
            Glide.with(context).load(elements.get(0).attr("src")).into(holder.postImage);
        }
        catch (Exception e)
        {
            holder.postImage.setImageResource(R.drawable.logo);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("url", item.getUrl());
                context.startActivity(intent);
            }
        });

    }



    @Override
    public int getItemCount() {
        return  items.size();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder
    {
        ImageView postImage;
        TextView postTitle;
        TextView postDescription;

        public PostViewHolder(View itemView) {
            super(itemView);
            postImage = (ImageView) itemView.findViewById(R.id.imageView);
            postTitle = (TextView) itemView.findViewById(R.id.title);
            postDescription = (TextView) itemView.findViewById(R.id.description);
        }
    }
}
