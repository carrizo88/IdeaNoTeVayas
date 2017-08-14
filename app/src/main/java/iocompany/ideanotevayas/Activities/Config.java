package iocompany.ideanotevayas.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import iocompany.ideanotevayas.Adaptadores.ListViewConfigAdapter;
import iocompany.ideanotevayas.R;

public class Config extends AppCompatActivity {

    private ListView listViewConfig;
    private List<String> config;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        showToolbar("Configuraciones",true);

        listViewConfig=(ListView)findViewById(R.id.ListViewConfig);

        config=new ArrayList<>();

        config.add("Nota Rapida");
        config.add("Envio de nota por Email");
        config.add("Almacenamiento de Notas");
        config.add("Buscar Actualizaciones");
        config.add("Sincronizar");
        config.add("Sobre IO");


        listViewConfig.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Aca lo que haga en el click
            }

        });
        // Enlazamos adaptador personalizado
        ListViewConfigAdapter configAdapter = new ListViewConfigAdapter(getApplicationContext(),R.layout.list_view_config,config);
        listViewConfig.setAdapter(configAdapter);


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
