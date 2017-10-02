package com.inferno.projectx.dailytask;

/**
 * Created by shankar on 10/1/17.
 */
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
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
import com.inferno.projectx.assigntask.ChooseWorkersAdapter;
import com.inferno.projectx.contractors.AddContractor;
import com.inferno.projectx.contractors.ContractAdapter;
import com.inferno.projectx.model.ContractorModel;
import com.inferno.projectx.model.DailytaskModel;
import com.inferno.projectx.toolbox.AppConstants;
import com.inferno.projectx.toolbox.NetworkService;
import com.inferno.projectx.toolbox.ServerConstants;

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

public class DailytaskList extends BaseActivity {
    private Context context;
    private Realm realm;
    private RecyclerView dailytaskListView;
    private RecyclerView.LayoutManager mLayoutManager;
    private DailytaskAdapter mAdapter;
    Retrofit retrofit;
    NetworkService networkService;
    private ArrayList<DailytaskModel> dailytaskArrayList;

    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dailytask_list);
        context = this;

        progressDialog = new ProgressDialog(context);
        retrofit = new Retrofit.Builder()
                .baseUrl(ServerConstants.SERVER_BASEURL)
                .build();
        networkService = retrofit.create(NetworkService.class);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dailytaskListView = (RecyclerView) findViewById(R.id.dailytaskList);
        mLayoutManager = new LinearLayoutManager(this);
        dailytaskListView.setLayoutManager(mLayoutManager);

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().build();
        realm = Realm.getInstance(realmConfiguration);
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
                        if(reponseBody.has("daily_work")){
                            JSONArray workers = reponseBody.getJSONArray("daily_work");
                            dailytaskArrayList = new ArrayList<>();
                            for(int i=0;i<workers.length();i++) {
                                JSONObject workerObject = workers.getJSONObject(i);
                                JSONArray allWorkers = workerObject.getJSONArray("who");
                                for(int j=0;j<allWorkers.length();j++) {
                                    Log.i("Workers", allWorkers.get(j).toString());
                                }
                                JSONArray allMaterials = workerObject.getJSONArray("materials");
                                for(int j=0;j<allMaterials.length();j++) {
                                    JSONObject eachMaterials = allMaterials.getJSONObject(j);
                                    Log.i("matId", eachMaterials.getString("id"));
                                    Log.i("Qty", eachMaterials.getString("qty"));
                                    Log.i("Unit", eachMaterials.getString("unit"));
                                }
                                dailytaskArrayList.add(new DailytaskModel(workerObject.getInt("wid"),workerObject.getInt("cid"),
                                        workerObject.getString("date"),false));
                            }
                            mAdapter = new DailytaskAdapter(context, dailytaskArrayList, new OnclickListener() {
                                @Override
                                public void onClick(int position) {
                                    Toast.makeText(context,""+dailytaskArrayList.get(position).getWid(),Toast.LENGTH_LONG).show();
                                }
                            });
                            dailytaskListView.setAdapter(mAdapter);
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
