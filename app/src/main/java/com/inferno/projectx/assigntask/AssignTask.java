package com.inferno.projectx.assigntask;

import android.os.Bundle;

import com.inferno.projectx.BaseActivity;
import com.inferno.projectx.R;

public class AssignTask extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assign_task);
        startFrgament(getSupportFragmentManager(),new ChooseContractor(),false,null);
    }
}
