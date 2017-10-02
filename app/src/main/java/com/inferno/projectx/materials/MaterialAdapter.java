package com.inferno.projectx.materials;

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
import com.inferno.projectx.model.MaterialModel;
import com.inferno.projectx.toolbox.ServerConstants;

import java.util.ArrayList;

import io.realm.RealmList;

/**
 * Created by saravana.subramanian on 8/30/17.
 */

public class MaterialAdapter extends RecyclerView.Adapter<MaterialAdapter.ViewHolder> {

    private ArrayList<MaterialModel> materialList;
    private Context context;
    OnclickListener onClickListener;

    public MaterialAdapter(Context context, ArrayList<MaterialModel>  materialList, OnclickListener onClickListener) {
        this.materialList = materialList;
        this.context = context;
        this.onClickListener = onClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        private CardView mItemContainer;
        private TextView materialName;
        private ImageView image;
        private TextView units;
        private TextView price;

        private ViewHolder(View itemView) {
            super(itemView);

            mItemContainer = (CardView) itemView.findViewById(R.id.item_container);
            materialName = (TextView) itemView.findViewById(R.id.name);
            units = (TextView) itemView.findViewById(R.id.units);
            price = (TextView) itemView.findViewById(R.id.price);
            image = (ImageView)itemView.findViewById(R.id.image);
        }
    }

    @Override
    public MaterialAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.material_list_item, parent, false);
        return new MaterialAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final MaterialAdapter.ViewHolder holder, final int position) {

        if(null != materialList){
            Log.i("Material Image",ServerConstants.SERVER_ROOTURL+materialList.get(position).getMaterialPicture().substring(1));
            holder.materialName.setText(materialList.get(position).getMaterialName());
            holder.units.setText(materialList.get(position).getMaterialUnit());
            holder.price.setText(materialList.get(position).getMaterialUnitPrice());
            Glide.with(context)
                    .load(ServerConstants.SERVER_ROOTURL+materialList.get(position).getMaterialPicture().substring(1))
                    .into(holder.image);
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
        if(null != materialList)
            return materialList.size();
        else
            return 0;

    }
}
