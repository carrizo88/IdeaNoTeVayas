package iocompany.ideanotevayas.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import iocompany.ideanotevayas.R;

public class Configuraciones extends AppCompatActivity {

    private ListView listViewConfiguraciones;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuraciones);

        showToolbar("Configuraciones",true);

        listViewConfiguraciones=(ListView)findViewById(R.id.ListViewConfiguraciones);

        //Datos a Mostrar
        List<String> configuraciones= new ArrayList<>();
        configuraciones.add("Nota Rapida");
        configuraciones.add("Envio de nota por Email");
        configuraciones.add("Almacenamiento de Notas");
        configuraciones.add("Buscar Actualizaciones");
        configuraciones.add("Sincronizar");
        configuraciones.add("Sobre IO");

        //Adaptador
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.list_view_basico,configuraciones);

        listViewConfiguraciones.setAdapter(adapter);


    }
    public void showToolbar(String title,boolean volverButton){

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbarConfiguraciones);
        setSupportActionBar(myToolbar);
        //Titulo y Volver Atras
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(volverButton);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(volverButton);
    }
}
