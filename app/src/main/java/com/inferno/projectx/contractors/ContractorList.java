package com.inferno.projectx.contractors;

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
import com.inferno.projectx.OnclickListener;
import com.inferno.projectx.R;
import com.inferno.projectx.assigntask.ChooseWorkers;
import com.inferno.projectx.model.ContractorModel;
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

public class ContractorList extends BaseActivity {

    private Context context;
    private Realm realm;
    //private RealmList<ContractorModel> contractorList;
    private RecyclerView contractorListView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ContractAdapter mAdapter;

    private ProgressDialog progressDialog;
    Retrofit retrofit;
    NetworkService networkService;
    private ArrayList<ContractorModel> contractorArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contractor_list);
        context = this;

        progressDialog = new ProgressDialog(context);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        contractorListView = (RecyclerView) findViewById(R.id.contractorList);
        mLayoutManager = new LinearLayoutManager(this);
        contractorListView.setLayoutManager(mLayoutManager);
        retrofit = new Retrofit.Builder()
                .baseUrl(ServerConstants.SERVER_BASEURL)
                .build();
        networkService = retrofit.create(NetworkService.class);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().build();
        realm = Realm.getInstance(realmConfiguration);

       // Log.i("ContractorModel",""+realm.where(ContractorModel.class).count());


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(AddContractor.class,null, AppConstants.VIEW_ADD_CONTRACTOR);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAllContractors();
    }

    void getAllContractors(){

        progressDialog.show();
        try{
            networkService.getAllResources().enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        JSONObject reponseBody = new JSONObject(response.body().string());
                        if(reponseBody.has("contracts")){
                            JSONArray contractors = reponseBody.getJSONArray("contracts");
                            contractorArrayList = new ArrayList<>();
                            for(int i=0;i<contractors.length();i++) {
                                JSONObject contractorObject = contractors.getJSONObject(i);
                                contractorArrayList.add(new ContractorModel(contractorObject.getInt("cid"),
                                        contractorObject.getString("contract_name"), "",
                                        contractorObject.getString("contract_address"), contractorObject.getString("picture")));
                            }
                            mAdapter = new ContractAdapter(context, contractorArrayList, new OnclickListener() {
                                @Override
                                public void onClick(int position) {
                                    Toast.makeText(context,""+contractorArrayList.get(position).getContractorName(),Toast.LENGTH_LONG).show();
                                }
                            });
                            contractorListView.setAdapter(mAdapter);

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
