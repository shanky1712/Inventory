package com.inferno.projectx.dailytask;

/**
 * Created by shankar on 10/1/17.
 */

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.inferno.projectx.OnclickListener;
import com.inferno.projectx.R;
import com.inferno.projectx.model.DailytaskModel;
import com.inferno.projectx.toolbox.ServerConstants;
import com.inferno.projectx.dailytask.DailytaskAdapter;

import java.util.ArrayList;
public class DailytaskAdapter extends RecyclerView.Adapter<DailytaskAdapter.ViewHolder> {
    private ArrayList<DailytaskModel> dailytaskList;
    private Context context;
    OnclickListener onClickListener;

    public DailytaskAdapter(Context context, ArrayList<DailytaskModel>  dailytaskList, OnclickListener onClickListener) {
        this.dailytaskList = dailytaskList;
        this.context = context;
        this.onClickListener = onClickListener;
    }
    @Override
    public DailytaskAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dailytask_list_item, parent, false);
        return new DailytaskAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(DailytaskAdapter.ViewHolder holder, final int position) {

        if(null != dailytaskList){
            holder.name.setText(dailytaskList.get(position).getDate());
            /*Glide.with(context)
                    .load(ServerConstants.SERVER_ROOTURL+dailytaskList.get(position).getWorkerImageURL().substring(1))
                    .into(holder.image);*/
        }else{
            //
        }
        holder.mItemContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(null != dailytaskList)
            return dailytaskList.size();
        else
            return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView mItemContainer;
        private TextView name;
        private ImageView image;
        private TextView address;

        private ViewHolder(View itemView) {
            super(itemView);

            mItemContainer = (CardView) itemView.findViewById(R.id.item_container);
            name = (TextView) itemView.findViewById(R.id.name);
            address = (TextView) itemView.findViewById(R.id.address);
            image = (ImageView)itemView.findViewById(R.id.image);
        }
    }
}
