package com.inferno.projectx.materials;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.inferno.projectx.BaseActivity;
import com.inferno.projectx.ChooseItem;
import com.inferno.projectx.OnclickListener;
import com.inferno.projectx.R;
import com.inferno.projectx.assigntask.ChooseMaterialAdapter;
import com.inferno.projectx.model.MaterialModel;
import com.inferno.projectx.model.WorkerModel;
import com.inferno.projectx.toolbox.AppConstants;
import com.inferno.projectx.toolbox.NetworkService;
import com.inferno.projectx.toolbox.ServerConstants;
import com.inferno.projectx.workers.WorkerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MaterialList extends BaseActivity {
    private Context context;
    private Realm realm;
    //private RealmList<MaterialModel> materialList;
    private RecyclerView materialListView;
    private RecyclerView.LayoutManager mLayoutManager;
    private MaterialAdapter mAdapter;
    private ProgressDialog progressDialog;
    Retrofit retrofit;
    NetworkService networkService;
    private ArrayList<MaterialModel> materialArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.material_list);
        context = this;
        progressDialog = new ProgressDialog(context);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        retrofit = new Retrofit.Builder()
                .baseUrl(ServerConstants.SERVER_BASEURL)
                .build();
        networkService = retrofit.create(NetworkService.class);

        materialListView = (RecyclerView) findViewById(R.id.materialList);
        mLayoutManager = new LinearLayoutManager(this);
        materialListView.setLayoutManager(mLayoutManager);

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().build();
        realm = Realm.getInstance(realmConfiguration);

        //Log.i("WorkerModel",""+realm.where(WorkerModel.class).count());


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(AddMaterial.class,null, AppConstants.VIEW_ADD_MATERIAL);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        showList();
    }

    void showList(){

        progressDialog.show();
        try{
            networkService.getAllResources().enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        JSONObject reponseBody = new JSONObject(response.body().string());
                        if(reponseBody.has("materials")){
                            JSONArray materials = reponseBody.getJSONArray("materials");
                            materialArrayList = new ArrayList<>();
                            for(int i=0;i<materials.length();i++) {
                                JSONObject materialObject = materials.getJSONObject(i);
                                materialArrayList.add(new MaterialModel(materialObject.getInt("mid"),materialObject.getString("material_name"),
                                        materialObject.getString("material_unit"), materialObject.getString("material_price"),
                                        materialObject.getString("picture"),"0",false));
                            }
                            mAdapter = new MaterialAdapter(context, materialArrayList, new OnclickListener() {
                                @Override
                                public void onClick(int position) {
                                    Toast.makeText(context, "" + materialArrayList.get(position).getMaterialName(), Toast.LENGTH_LONG).show();
                                }
                            });
                            materialListView.setAdapter(mAdapter);
                            progressDialog.dismiss();
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
