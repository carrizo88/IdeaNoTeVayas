package iocompany.ideanotevayas.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import iocompany.ideanotevayas.Adaptadores.ListViewNotaRapidaConfigAdapter;
import iocompany.ideanotevayas.R;

public class NotaRapidaConfig extends AppCompatActivity {

    private ListView listViewConfig;
    private List<String> NotaRapidaconfig;
    private Button configurar;
    private Switch sw1;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nota_rapida_config);

        showToolbar("Nota Rapida",true);
        configurar=(Button) findViewById(R.id.buttonNotaRapidaConfig);
        sw1=(Switch)findViewById(R.id.switchNotaRapidaConfig);

        prefs=getSharedPreferences("Preferences", Context.MODE_PRIVATE);

        getPreferences();
        sw1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPreferences();
            }
        });

        configurar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intento1 = new Intent(getApplicationContext(), NotaRapidaSet.class);
                startActivity(intento1);
            }
        });



/*
        listViewConfig=(ListView)findViewById(R.id.ListViewNotaRapidaConfig);

        NotaRapidaconfig=new ArrayList<>();

        NotaRapidaconfig.add("Nota Rapida");
        //NotaRapidaconfig.add("Envio de nota por Email");

        ListViewNotaRapidaConfigAdapter notaRapidaAdapter = new ListViewNotaRapidaConfigAdapter(getApplicationContext(),R.layout.activity_nota_rapida_config,NotaRapidaconfig);
        listViewConfig.setAdapter(notaRapidaAdapter);
  */

    }
    private void setPreferences(){
        SharedPreferences.Editor editor = prefs.edit();
        if(sw1.isChecked()==true){
            editor.putBoolean("NotaRapida",true);
            editor.apply();
        }else{
            editor.putBoolean("NotaRapida",false);
            editor.apply();
        }
    }

    private void getPreferences() {
        boolean NT =prefs.getBoolean("NotaRapida",false);
        if(NT==true){
            sw1.setChecked(true);
        }else {
            sw1.setChecked(false);
        }
    }
    private void showToolbar(String title,boolean volverButton){

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbarNotaRapidaConfigActivity);
        setSupportActionBar(myToolbar);
        //Titulo y Volver Atras
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(volverButton);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(volverButton);
    }
}
