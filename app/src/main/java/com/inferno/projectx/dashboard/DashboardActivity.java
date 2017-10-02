package com.inferno.projectx.dashboard;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.inferno.projectx.BaseActivity;
import com.inferno.projectx.R;
import com.inferno.projectx.assigntask.AssignTask;
import com.inferno.projectx.contractors.ContractorList;
import com.inferno.projectx.dailytask.DailytaskList;
import com.inferno.projectx.materials.MaterialList;
import com.inferno.projectx.toolbox.AppConstants;
import com.inferno.projectx.workers.WorkerList;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class DashboardActivity extends BaseActivity {

    private Context context;
    private Realm realm;
    private LinearLayout assign;
    private LinearLayout contract;
    private LinearLayout materials;
    private LinearLayout worker;
    private LinearLayout report;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        context = this;
        assign = (LinearLayout) findViewById(R.id.add_panel);
        contract = (LinearLayout) findViewById(R.id.contract_panel);
        materials = (LinearLayout) findViewById(R.id.material_panel);
        worker = (LinearLayout) findViewById(R.id.workers_panel);
        report = (LinearLayout) findViewById(R.id.reports_panel);

        assign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(AssignTask.class,null, AppConstants.ASSIGN_TASK);
            }
        });
        contract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(ContractorList.class,null, AppConstants.VIEW_CONTRACT_LIST);
            }
        });
        materials.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(MaterialList.class,null, AppConstants.VIEW_MATERIAL_LIST);
            }
        });
        worker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(WorkerList.class,null, AppConstants.VIEW_WORKER_LIST);
            }
        });
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(DailytaskList.class,null, AppConstants.VIEW_DAILYTASK_LIST);
            }
        });
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().build();
        realm = Realm.getInstance(realmConfiguration);

    }
}
