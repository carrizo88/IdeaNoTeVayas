package iocompany.ideanotevayas.Modelos;


import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;
import iocompany.ideanotevayas.App.MyApplicacion;

/**
 * Created by PC on 05/07/2017.
 */

public class Nota extends RealmObject {

    @PrimaryKey
    private int id;
    @Required
    private String descripcion;
    @Required
    private Date creadaElFecha;

    private boolean favorito;

    public Nota(){
        //Constructor que pide realm
    }

    public Nota(String descripcion,boolean favorito){
        this.id= MyApplicacion.NotaID.incrementAndGet();
        this.descripcion =descripcion;
        this.creadaElFecha = new Date();
        this.favorito=favorito;

    }

    public int getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getCreadaElFecha() {
        return creadaElFecha;
    }

    public boolean isFavorito() {return favorito;}

    public void setFavorito(boolean favorito) {this.favorito = favorito;}
}
