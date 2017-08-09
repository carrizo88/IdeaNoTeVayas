package iocompany.ideanotevayas.Activities;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import io.realm.Realm;
import iocompany.ideanotevayas.Modelos.Nota;
import iocompany.ideanotevayas.R;

public class CrearNota extends AppCompatActivity {

    private EditText descripcion;

    private FloatingActionButton cancelar,favorito,crear;
    private Realm realm;
    private boolean fav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_nota);

        showToolbar("Crea una Nueva Nota",true);

        descripcion=(EditText)findViewById(R.id.DescripcionNota);

        //Crear DB REALM
        realm=Realm.getDefaultInstance();


        crear=(FloatingActionButton)findViewById(R.id.guardarNota);
        favorito=(FloatingActionButton)findViewById(R.id.FavoritoEnCrearNota);
        cancelar=(FloatingActionButton)findViewById(R.id.cancelarNota);

        crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String descripcionNota=descripcion.getText().toString().trim();
                if(descripcionNota.length()>0){
                    crearNuevaNota(descripcionNota,fav);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), "Debe colocarle una descripcion a la Nota", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        favorito.setImageResource(R.drawable.ic_favorito);

        favorito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!fav){
                    favorito.setImageResource(R.drawable.ic_favorito_full);
                    fav=true;
                }else{
                    favorito.setImageResource(R.drawable.ic_favorito);
                }
            }
        });
    }

    //CRUD ACTIONS
    //Crear una Nota Nueva
    private void crearNuevaNota(String contenido,boolean fav) {
        realm.beginTransaction();
        Nota nota= new Nota(contenido,fav);
        realm.copyToRealm(nota);
        realm.commitTransaction();
    }
    public void showToolbar(String title,boolean volverButton){

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbarCrearNota);
        setSupportActionBar(myToolbar);
        //Titulo y Volver Atras
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(volverButton);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(volverButton);
    }

}
