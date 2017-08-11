package iocompany.ideanotevayas.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import iocompany.ideanotevayas.R;

public class Config extends AppCompatActivity {

    private ListView listViewConfig;
    private List<String> config;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        showToolbar("Configuraciones",true);

        listViewConfig=(ListView)findViewById(R.id.ListViewConfig);

        config=new ArrayList<String>();
        config.add("Hola");
        /*config.add("Nota Rapida");
        config.add("Envio de nota por Email");
        config.add("Almacenamiento de Notas");
        config.add("Buscar Actualizaciones");
        config.add("Sincronizar");
        config.add("Sobre IO");*/

        adapter= new ArrayAdapter<String>(getApplicationContext(),R.layout.list_view_config,config);

        listViewConfig.setAdapter(adapter);


    }
    public void showToolbar(String title,boolean volverButton){

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbarConfigActivity);
        setSupportActionBar(myToolbar);
        //Titulo y Volver Atras
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(volverButton);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(volverButton);
    }
}
