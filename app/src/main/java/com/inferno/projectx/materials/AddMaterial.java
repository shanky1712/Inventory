package com.inferno.projectx.materials;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.inferno.projectx.R;
import com.inferno.projectx.model.ContractorModel;
import com.inferno.projectx.model.MaterialModel;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class AddMaterial extends AppCompatActivity {

    private Context context;
    private EditText name;
    private EditText  unit;
    private EditText  price;
    private ImageView imageView;
    private Button save;
    private Realm realm;
    private MaterialModel materialModel;
    private int lastRowId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_material);

        context = this;


        name = (EditText) findViewById(R.id.name);
        unit = (EditText) findViewById(R.id.units);
        price = (EditText) findViewById(R.id.price);
        imageView = (ImageView) findViewById(R.id.image);
        save = (Button) findViewById(R.id.save);

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().build();
        realm = Realm.getInstance(realmConfiguration);



        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //validate fields here
                /*try{
                    if(!realm.isEmpty()){
                        lastRowId = !realm.isEmpty()?realm.where(MaterialModel.class).max("nid").intValue():0;
                    }
                }catch (Exception e){
                    lastRowId = 0;
                }
                materialModel = new MaterialModel(lastRowId+1,name.getText().toString(),
                        unit.getText().toString(),price.getText().toString(),"imageView");
                if(isValid() && null != materialModel){
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            realm.insertOrUpdate(materialModel);
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
        if(name.getText().toString().isEmpty() || unit.getText().toString().isEmpty() || price.getText().toString().isEmpty())
            return false;
        else
            return true;
    }

}
