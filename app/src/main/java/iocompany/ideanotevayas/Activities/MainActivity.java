package iocompany.ideanotevayas.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import iocompany.ideanotevayas.Adaptadores.NotaAdaptador;
import iocompany.ideanotevayas.Modelos.Nota;
import iocompany.ideanotevayas.R;

public class MainActivity extends AppCompatActivity implements RealmChangeListener<RealmResults<Nota>> {

    private FloatingActionButton btnAddFloating;
    private Realm realm;
    private RealmResults<Nota> notas;
    private NotaAdaptador adaptador;
    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showToolbar("Idea No Te Vayas",false);



        btnAddFloating =(FloatingActionButton)findViewById(R.id.BtnAddFloating2);
        btnAddFloating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CrearNota.class);
                startActivity(intent);
            }
        });

        //Base de datos
        realm = Realm.getDefaultInstance();
        notas = realm.where(Nota.class).findAll();
        notas.addChangeListener(this);
        //Adaptador
        adaptador = new NotaAdaptador(this,notas,R.layout.lista_de_notas);
        listView =(ListView)findViewById(R.id.ListViewNotas);
        listView.setAdapter(adaptador);
        //listView.setOnItemClickListener((AdapterView.OnItemClickListener) this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                verNota(notas.get(position));
            }
        });
        registerForContextMenu(listView);


    }

    @Override
    public void onChange(RealmResults<Nota> element) {
        adaptador.notifyDataSetChanged();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_toolbar_main_activity,menu);
        return super.onCreateOptionsMenu(menu);
    }

    //Menu de la Toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       switch (item.getItemId()){
           case R.id.configuraciones:
               nuevaActivity();
               break;
           case R.id.Almacenamiento:
               Intent intento22= new Intent(this,VerNota.class);
               startActivity(intento22);
               break;
           default:
               return super.onOptionsItemSelected(item);
       }
       return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        menu.setHeaderTitle("Nota Numero:"+notas.get(info.position).getId());
        getMenuInflater().inflate(R.menu.context_menu_item_main_activity, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()) {
            case R.id.delete_nota:
                deleteNota(notas.get(info.position));
                return true;
            case R.id.edit_nota:
                editNota(notas.get(info.position));
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
    private void deleteNota(Nota nota) {
        realm.beginTransaction();
        nota.deleteFromRealm();
        realm.commitTransaction();
    }
    private void editNota(Nota nota){
        Intent intento = new Intent(getApplicationContext(), EditarNota.class);
        int id = nota.getId();
        intento.putExtra("id", id);
        startActivity(intento);
    }
    private void verNota(Nota nota){
        Intent intento = new Intent(getApplicationContext(), VerNota.class);
        int id = nota.getId();
        intento.putExtra("id", id);
        startActivity(intento);
    }
    //Icono,Titulo y Volver Atras TOOLBAR
    public void showToolbar(String title,boolean volverButton){

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbarMainActivity);
        setSupportActionBar(myToolbar);
        myToolbar.setNavigationIcon(R.mipmap.ic_icono1);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayShowHomeEnabled(volverButton);
       // myToolbar.setOverflowIcon(getDrawable(R.drawable.ic_menu_toolbar));
        myToolbar.setOverflowIcon(getResources().getDrawable(R.drawable.ic_menu_toolbar2));
    }
    public void nuevaActivity(){
        Intent intentoConfig = new Intent(this, Configuraciones.class);
        startActivity(intentoConfig);
    }

}
