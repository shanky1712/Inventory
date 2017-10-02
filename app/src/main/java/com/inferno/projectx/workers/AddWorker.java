package com.inferno.projectx.workers;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.inferno.projectx.R;
import com.inferno.projectx.model.ContractorModel;
import com.inferno.projectx.model.WorkerModel;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;

public class AddWorker extends AppCompatActivity {

    private Context context;
    private EditText name;
    private EditText  phone;
    private EditText  address;
    private ImageView imageView;
    private Button save;
    private Realm realm;
    private WorkerModel workerModel;
    private int lastRowId;


    private RealmResults realmResults;
    //private RealmList<WorkerModel> workerList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_worker);

        context = this;


        name = (EditText) findViewById(R.id.name);
        phone = (EditText) findViewById(R.id.phone);
        address = (EditText) findViewById(R.id.address);
        imageView = (ImageView) findViewById(R.id.image);
        save = (Button) findViewById(R.id.save);

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().build();
        realm = Realm.getInstance(realmConfiguration);

        //workerList = new RealmList<>();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //validate fields here
               /* try{
                    if(!realm.isEmpty()){
                        lastRowId = !realm.isEmpty()?realm.where(WorkerModel.class).max("nid").intValue():0;
                    }
                }catch (Exception e){
                    lastRowId = 0;
                }
                workerModel = new WorkerModel(lastRowId+1,name.getText().toString(),
                        phone.getText().toString(),address.getText().toString(),"imageView");//set imagepath here
                if(isValid() &&  null != workerModel){
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            realm.insertOrUpdate(workerModel);
                            finish();
                        }
                    });
                }else{
                    Toast.makeText(context,"Fields are empty",Toast.LENGTH_LONG).show();
                }*/
            }
        });



    }

    boolean isValid(){
        if(name.getText().toString().isEmpty() || phone.getText().toString().isEmpty() || address.getText().toString().isEmpty())
            return false;
        else
            return true;
    }
}
