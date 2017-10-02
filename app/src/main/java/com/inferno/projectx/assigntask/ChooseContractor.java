package com.inferno.projectx.assigntask;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.inferno.projectx.BaseFragment;
import com.inferno.projectx.OnclickListener;
import com.inferno.projectx.R;
import com.inferno.projectx.contractors.ContractAdapter;
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


public class ChooseContractor extends BaseFragment {

    View rootView;
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.choose_contractor, container, false);
        context = getActivity();
        progressDialog = new ProgressDialog(context);
        retrofit = new Retrofit.Builder()
                .baseUrl(ServerConstants.SERVER_BASEURL)
                .build();
        networkService = retrofit.create(NetworkService.class);
        contractorListView = (RecyclerView) rootView.findViewById(R.id.addContractorsList);
        mLayoutManager = new LinearLayoutManager(context);
        contractorListView.setLayoutManager(mLayoutManager);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().build();
        realm = Realm.getInstance(realmConfiguration);

        return rootView;
    }
    @Override
    public void onResume() {
        super.onResume();
        //showList();
        getAllResources();
    }

    void showList(){
        try{
            if(!realm.isEmpty()){
                contractorListView.setAdapter(mAdapter);
            }

        }catch (Exception e){
        }
    }

    void getAllResources(){
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
                                Toast.makeText(context,""+contractorArrayList.get(position).getContractorName(),Toast.LENGTH_SHORT).show();
                                Bundle extras = new Bundle();
                                extras.putParcelable(AppConstants.EXTRA_CONTRACTOR,contractorArrayList.get(position));
                                startFrgament(getFragmentManager(),new ChooseWorkers(),false,extras);
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
