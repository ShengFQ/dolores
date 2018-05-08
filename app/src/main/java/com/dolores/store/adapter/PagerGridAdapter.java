/*
 * Copyright 2017 GcsSloop
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Last modified 2017-09-18 23:47:01
 *
 * GitHub: https://github.com/GcsSloop
 * WeiBo: http://weibo.com/GcsSloop
 * WebSite: http://www.gcssloop.com
 */

package com.dolores.store.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.pdf.PdfDocument;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dolores.store.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PagerGridAdapter extends RecyclerView.Adapter<PagerGridAdapter.MyViewHolder> {
    private Context context;
    public static List<MyData> datas = new ArrayList<MyData>();
    /*public PagerGridAdapter(Context context){
        this.context=context;
        setDefault();
    }*/

    public PagerGridAdapter(){
        setDefault();
    }
    private void setDefault() {
        for (int i = 1; i <= 15; i++) {
            MyData data=new MyData(i+"",R.drawable.icon_office_cam);
            datas.add(data);
        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //    Log.i("GCS", "onCreateViewHolder");
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.layout_pagergrid_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        //   Log.i("GCS","onBindViewHolder = "+position);
        final String title = datas.get(position).title;
        final int resouceid=datas.get(position).resource;
        holder.tv_title.setText(title);
        holder.iv_avatar.setImageResource(resouceid);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "item" + title + " 被点击了", Toast.LENGTH_SHORT).show();
                holder.tv_title.setText("G "+title);
                notifyItemChanged(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class MyData{
        String title;
        int resource;
        public MyData(String title,int resouce){
            this.title=title;
            this.resource=resouce;
        }
    }
    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title;
        ImageView iv_avatar;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            iv_avatar = (ImageView) itemView.findViewById(R.id.iv_avatar);

        }
    }
}
