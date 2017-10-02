package com.inferno.projectx.workers;

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
import com.inferno.projectx.model.WorkerModel;
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

public class WorkerList extends BaseActivity {

    private Context context;
    private Realm realm;
    //private RealmList<WorkerModel> workerList;
    private RecyclerView workerListView;
    private RecyclerView.LayoutManager mLayoutManager;
    private WorkerAdapter mAdapter;
    Retrofit retrofit;
    NetworkService networkService;
    private ArrayList<WorkerModel> workerArrayList;

    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.worker_list);
        context = this;

        progressDialog = new ProgressDialog(context);
        retrofit = new Retrofit.Builder()
                .baseUrl(ServerConstants.SERVER_BASEURL)
                .build();
        networkService = retrofit.create(NetworkService.class);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        workerListView = (RecyclerView) findViewById(R.id.workerList);
        mLayoutManager = new LinearLayoutManager(this);
        workerListView.setLayoutManager(mLayoutManager);

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().build();
        realm = Realm.getInstance(realmConfiguration);

        //Log.i("WorkerModel",""+realm.where(WorkerModel.class).count());


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(AddWorker.class,null, AppConstants.VIEW_ADD_WORKER);
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
                            mAdapter = new WorkerAdapter(context, workerArrayList, new OnclickListener() {
                                @Override
                                public void onClick(int position) {
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
}
