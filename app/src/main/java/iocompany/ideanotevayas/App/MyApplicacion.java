package iocompany.ideanotevayas.App;

import android.app.Application;

import java.util.concurrent.atomic.AtomicInteger;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmResults;
import iocompany.ideanotevayas.Modelos.Nota;


/**
 * Created by PC on 05/07/2017.
 */

public class MyApplicacion extends Application {

    public static AtomicInteger NotaID= new AtomicInteger();
    @Override
    public void onCreate() {

        setUpRealmConfig();

        Realm realm = Realm.getDefaultInstance();

        NotaID = getIdByTable(realm, Nota.class);
        realm.close();
    }

    private void setUpRealmConfig() {
        RealmConfiguration config = new RealmConfiguration
                .Builder(getApplicationContext())
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
    }

    private <T extends RealmObject> AtomicInteger getIdByTable(Realm realm, Class<T> anyClass) {
        RealmResults<T> results = realm.where(anyClass).findAll();
        return (results.size() > 0) ? new AtomicInteger(results.max("id").intValue()) : new AtomicInteger();
    }

    /*private <T extends RealmObject> AtomicInteger getIdByNote(Realm realm,Class<T> anyClass){

        RealmResults<T> results = realm.where(anyClass).findAll();
       if(results.size()>0){
          AtomicInteger  t1=new AtomicInteger(results.max("id").intValue());
           return t1;
        }else{
           AtomicInteger t2=new AtomicInteger();
           return t2;
        }*/


}
