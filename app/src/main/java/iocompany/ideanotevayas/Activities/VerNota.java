package iocompany.ideanotevayas.Activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import io.realm.Realm;
import iocompany.ideanotevayas.Modelos.Nota;
import iocompany.ideanotevayas.R;

public class VerNota extends AppCompatActivity {

    private TextView descripcion;

    private FloatingActionButton editar,favorito;
    private Realm realm;
    private  int notaID;
    private Nota nota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_nota);

        showToolbar("Ver Nota",true);


        descripcion=(TextView) findViewById(R.id.DescripcionNotaVerNota);

        realm=Realm.getDefaultInstance();
        if(getIntent().getExtras()!=null){
            notaID=getIntent().getExtras().getInt("id");
        }
        nota = realm.where(Nota.class).equalTo("id",notaID).findFirst();
        descripcion.setText(nota.getDescripcion());

        editar=(FloatingActionButton)findViewById(R.id.EditNotaEnVerNota);


        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editNota(nota);
                finish();
            }
        });
        favorito=(FloatingActionButton)findViewById(R.id.FavoritoEnVerNota);
        if(!nota.isFavorito()){
            favorito.setImageResource(R.drawable.ic_favorito);
        }else{
            favorito.setImageResource(R.drawable.ic_favorito_full);
        }

        favorito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realm.beginTransaction();
                if(!nota.isFavorito()){
                    nota.setFavorito(true);
                    favorito.setImageResource(R.drawable.ic_favorito_full);
                }else{
                    nota.setFavorito(false);
                    favorito.setImageResource(R.drawable.ic_favorito);
                }

                realm.copyToRealmOrUpdate(nota);
                realm.commitTransaction();
            }
        });

    }
    private void editNota(Nota nota){
        Intent intento = new Intent(getApplicationContext(), EditarNota.class);
        int id = nota.getId();
        intento.putExtra("id", id);
        startActivity(intento);
    }
    public void showToolbar(String title,boolean volverButton){

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbarVerNota);
        setSupportActionBar(myToolbar);
        //Titulo y Volver Atras
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(volverButton);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(volverButton);
    }
}
