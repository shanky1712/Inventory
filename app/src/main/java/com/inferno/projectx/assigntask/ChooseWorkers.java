package com.inferno.projectx.assigntask;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.inferno.projectx.BaseFragment;
import com.inferno.projectx.ChooseItem;
import com.inferno.projectx.OnclickListener;
import com.inferno.projectx.R;
import com.inferno.projectx.contractors.ContractAdapter;
import com.inferno.projectx.model.ContractorModel;
import com.inferno.projectx.model.WorkerModel;
import com.inferno.projectx.toolbox.AppConstants;
import com.inferno.projectx.toolbox.NetworkService;
import com.inferno.projectx.toolbox.ServerConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ChooseWorkers extends BaseFragment {

    private View rootView;
    private Context context;
    private Realm realm;
    //private RealmList<WorkerModel> workerList;
    private RecyclerView workerListView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ChooseWorkersAdapter mAdapter;
    private ProgressDialog progressDialog;
    //private RealmList<WorkerModel> selectedWorkers;

    Retrofit retrofit;
    NetworkService networkService;
    private ArrayList<WorkerModel> workerArrayList;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.choose_workers, container, false);
        context = getActivity();
        setHasOptionsMenu(true);
        progressDialog = new ProgressDialog(context);

        retrofit = new Retrofit.Builder()
                .baseUrl(ServerConstants.SERVER_BASEURL)
                .build();
        networkService = retrofit.create(NetworkService.class);
        workerListView = (RecyclerView) rootView.findViewById(R.id.addworkersList);
        mLayoutManager = new LinearLayoutManager(context);
        workerListView.setLayoutManager(mLayoutManager);

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().build();
        realm = Realm.getInstance(realmConfiguration);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        //showList();
        getWrokerList();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        // TODO Add your menu entries here
        inflater.inflate(R.menu.chooseworker, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.done:
                if(!getSelectedWorkers(workerArrayList).isEmpty()) {
                    Bundle extras = new Bundle();
                    extras.putParcelableArrayList(AppConstants.EXTRA_WORKER_LIST, getSelectedWorkers(workerArrayList));
                    extras.putParcelable(AppConstants.EXTRA_CONTRACTOR, (Parcelable) getArguments().get(AppConstants.EXTRA_CONTRACTOR));
                    startFrgament(getFragmentManager(), new ChooseMaterial(), false, extras);
                    break;
                }

        }
        return true;

    }

    void getWrokerList(){
        progressDialog.show();
        try{
            networkService.getAllResources().enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        JSONObject reponseBody = new JSONObject(response.body().string());
                        if(reponseBody.has("workers")){
                            JSONArray workers = reponseBody.getJSONArray("workers");
                            workerArrayList = new ArrayList<>();
                            for(int i=0;i<workers.length();i++) {
                                JSONObject workerObject = workers.getJSONObject(i);
                                workerArrayList.add(new WorkerModel(workerObject.getInt("uid"),workerObject.getInt("rid"),
                                        workerObject.getString("Name"), workerObject.getString("phone"),
                                        workerObject.getString("Age"),workerObject.getString("address"),
                                        workerObject.getString("picture"),false));
                            }
                            mAdapter = new ChooseWorkersAdapter(context, workerArrayList, new ChooseItem() {
                                @Override
                                public void onItemClicked(int position, boolean isSelected) {
                                    workerArrayList.get(position).setWorkerSelected(isSelected);
                                    Toast.makeText(context,""+workerArrayList.get(position).getWorkerName(),Toast.LENGTH_LONG).show();
                                }

                            });
                            workerListView.setAdapter(mAdapter);
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

    ArrayList<WorkerModel> getSelectedWorkers(ArrayList<WorkerModel> workerList){
        ArrayList<WorkerModel> selectedWorkersList = new ArrayList<>();
        for(WorkerModel workerModel:workerList){
            if (workerModel.isWorkerSelected())
                selectedWorkersList.add(workerModel);
        }
        return  selectedWorkersList;
    }

}
