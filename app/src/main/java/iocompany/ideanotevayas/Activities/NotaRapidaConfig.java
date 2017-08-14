package iocompany.ideanotevayas.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;


import java.util.ArrayList;
import java.util.List;

import iocompany.ideanotevayas.Adaptadores.ListViewNotaRapidaConfigAdapter;
import iocompany.ideanotevayas.R;
import iocompany.ideanotevayas.Servicios.AcelerometroNotaRapida;
import iocompany.ideanotevayas.Servicios.GiroscopioNotaRapida;

public class NotaRapidaConfig extends AppCompatActivity {

    private ListView listViewConfig;
    private List<String> NotaRapidaconfig;
    private RadioButton rbAcelerometro,rbGiroscopio;
    private Button configurar;
    private Switch sw1;
    private SharedPreferences prefs;
    private ToggleButton tg2,tg3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nota_rapida_config);

        showToolbar("Nota Rapida",true);
        configurar=(Button) findViewById(R.id.buttonNotaRapidaConfig);
        sw1=(Switch)findViewById(R.id.switchNotaRapidaConfig);
        rbAcelerometro=(RadioButton)findViewById(R.id.radioButtonAcelerometro);
        rbGiroscopio=(RadioButton)findViewById(R.id.radioButtonGiroscopio);

        tg2=(ToggleButton)findViewById(R.id.toggleButton2);
        tg3=(ToggleButton)findViewById(R.id.toggleButton3);

        prefs=getSharedPreferences("Preferences", Context.MODE_PRIVATE);

        getPreferences();
        sw1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPreferences();
            }
        });

        //startServices();
        //toggles
       // startServices2();
        tg2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    //Servicio Acelerometro
                    Intent intentoAcelerometro = new Intent(getApplicationContext(), AcelerometroNotaRapida.class);
                    startService(intentoAcelerometro);
                }
                if(!isChecked){
                    stopService(new Intent(getApplicationContext(), AcelerometroNotaRapida.class));
                }
            }
        });
        tg3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    //Servicio Giroscopio
                    Intent intentoGiroscopio = new Intent(getApplicationContext(), GiroscopioNotaRapida.class);
                    startService(intentoGiroscopio);
                }
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

    private void startServices(){
        if(sw1.isChecked()){
            rbAcelerometro.setVisibility(View.VISIBLE);
            rbGiroscopio.setVisibility(View.VISIBLE);
            if(rbAcelerometro.isChecked()){
                //Servicio Acelerometro
                Intent intentoAcelerometro = new Intent(this, AcelerometroNotaRapida.class);
                startService(intentoAcelerometro);
            }
            if(rbGiroscopio.isChecked()){
                //Servicio Giroscopio
                Intent intentoGiroscopio = new Intent(this, GiroscopioNotaRapida.class);
                startService(intentoGiroscopio);
            }
        }else {
            rbAcelerometro.setVisibility(View.INVISIBLE);
            rbGiroscopio.setVisibility(View.INVISIBLE);
        }
    }
    private void startServices2(){
        if(tg2.isChecked()){
                //Servicio Acelerometro
                Intent intentoAcelerometro = new Intent(this, AcelerometroNotaRapida.class);
                startService(intentoAcelerometro);
            }
        if(tg3.isChecked()){
                //Servicio Giroscopio
                Intent intentoGiroscopio = new Intent(this, GiroscopioNotaRapida.class);
                startService(intentoGiroscopio);
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
