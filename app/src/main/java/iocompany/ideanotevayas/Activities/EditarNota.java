package iocompany.ideanotevayas.Activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import io.realm.Realm;
import iocompany.ideanotevayas.Modelos.Nota;
import iocompany.ideanotevayas.R;

public class EditarNota extends AppCompatActivity {

    private EditText descripcion;

    private FloatingActionButton cancelar,favorito,editar;
    private Realm realm;
    private  int notaID;
    private Nota nota;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_nota);

        showToolbar("Edita la Nota",true);

        descripcion=(EditText)findViewById(R.id.DescripcionNotaEditNota);

        realm=Realm.getDefaultInstance();
        if(getIntent().getExtras()!=null){
            notaID=getIntent().getExtras().getInt("id");
        }
        nota = realm.where(Nota.class).equalTo("id",notaID).findFirst();
        //Edit Text
        descripcion.setText(nota.getDescripcion());
        //Posicion del cursor en el edit text al final!
        descripcion.setSelection(descripcion.getText().length());

        editar=(FloatingActionButton)findViewById(R.id.EditNota);
        cancelar=(FloatingActionButton)findViewById(R.id.cancelarNotaEditNota);
        favorito=(FloatingActionButton)findViewById(R.id.FavoritoEnEditNota);
        if(!nota.isFavorito()){
            favorito.setImageResource(R.drawable.ic_favorito);
        }else{
            favorito.setImageResource(R.drawable.ic_favorito_full);
        }

        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nuevaDescripcion=descripcion.getText().toString();
                editNota(nuevaDescripcion,nota);
                finish();
            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                verNota(nota);
            }
        });
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
    public void editNota(String descripcionNueva,Nota nota){

            realm.beginTransaction();
            nota.setDescripcion(descripcionNueva);
            realm.copyToRealmOrUpdate(nota);
            realm.commitTransaction();

    }
    private void verNota(Nota nota){
        Intent intento = new Intent(getApplicationContext(), VerNota.class);
        int id = nota.getId();
        intento.putExtra("id", id);
        startActivity(intento);
    }
    public void showToolbar(String title,boolean volverButton){

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbarEditarNota);
        setSupportActionBar(myToolbar);
        //Titulo y Volver Atras
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(volverButton);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(volverButton);
    }
}
