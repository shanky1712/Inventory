package com.inferno.projectx.assigntask;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.inferno.projectx.ChooseItem;
import com.inferno.projectx.R;
import com.inferno.projectx.model.MaterialModel;
import com.inferno.projectx.toolbox.ServerConstants;

import java.util.ArrayList;

import io.realm.Realm;

/**
 * Created by saravana.subramanian on 9/14/17.
 */

public class ChooseMaterialAdapter extends RecyclerView.Adapter<ChooseMaterialAdapter.ViewHolder> {

    private ArrayList<MaterialModel> materialList;
    private Context context;
    ChooseItem onClickListener;
    OnEditTextChanged onEditTextChanged;
    public interface OnEditTextChanged {
        void onTextChanged(int position, String charSeq);
    }

    public ChooseMaterialAdapter(Context context, ArrayList<MaterialModel>  materialList, ChooseItem onClickListener,OnEditTextChanged onEditTextChanged) {
        this.materialList = materialList;
        this.context = context;
        this.onClickListener = onClickListener;
        this.onEditTextChanged = onEditTextChanged;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        private CardView mItemContainer;
        private TextView materialName;
        private ImageView image;
        private TextView units;
        private TextView price;
        private EditText unitsSelected;

        private ViewHolder(View itemView) {
            super(itemView);

            mItemContainer = (CardView) itemView.findViewById(R.id.item_container);
            materialName = (TextView) itemView.findViewById(R.id.name);
            units = (TextView) itemView.findViewById(R.id.availableunits);
            price = (TextView) itemView.findViewById(R.id.price);
            image = (ImageView)itemView.findViewById(R.id.image);
            unitsSelected = (EditText)itemView.findViewById(R.id.units);
        }
    }

    @Override
    public ChooseMaterialAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.choose_material_list_item, parent, false);
        return new ChooseMaterialAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ChooseMaterialAdapter.ViewHolder holder, final int position) {

        if(null != materialList){
            holder.materialName.setText(materialList.get(position).getMaterialName());
            holder.units.setText(materialList.get(position).getMaterialUnit());
            holder.price.setText(materialList.get(position).getMaterialUnitPrice());
            //holder.image.setImageDrawable(null);
            Glide.with(context)
                    .load(ServerConstants.SERVER_ROOTURL+materialList.get(position).getMaterialPicture().substring(1))
                    .into(holder.image);
        }else{
            //
        }
        holder.mItemContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            materialList.get(position).setMaterialSelected(!materialList.get(position).isMaterialSelected());
            holder.itemView.setBackgroundColor(materialList.get(position).isMaterialSelected() ? Color.YELLOW : Color.WHITE);

            onClickListener.onItemClicked(position,materialList.get(position).isMaterialSelected());
            }
        });
        holder.unitsSelected.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                onEditTextChanged.onTextChanged(position, charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

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
