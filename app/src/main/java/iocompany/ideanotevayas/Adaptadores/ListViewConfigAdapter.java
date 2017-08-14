package iocompany.ideanotevayas.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import iocompany.ideanotevayas.R;

/**
 * Created by PC on 13/08/2017.
 */

public class ListViewConfigAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<String> lista;

    public ListViewConfigAdapter(Context context,int layout, List<String> lista ){
        this.context=context;
        this.layout =layout;
        this.lista=lista;
    }

    @Override
    public int getCount() { return this.lista.size(); }

    @Override
    public Object getItem(int position){ return this.lista.size(); }

    @Override
    public long getItemId(int id) { return id; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        //Este metodo se utiliza para hacer mejor la app que no sea tan pesada porque usar mucho findviewbyID
        // hace re pesada la app por lo que este metodo si la busco una vez no lo vuelva a hacer de nuevo.
        if(convertView == null){
            //Inflamos la vista que nos ha llegado con nuestro layout personalizado //Creo un layoutInflater y le doy este contexto, inflamos a la vista
            LayoutInflater layoutInflater= LayoutInflater.from(this.context);
            // luego lo inflo y le paso la layout creada anteriormente y lo personalizo y lo devuelvo
            convertView = layoutInflater.inflate(this.layout,null);
            // El holder viene de la funcion ViewHolder creada mas abajo
            holder = new ViewHolder();
            //Referenciamos el elemento a modificar  y lo rellenamos
            holder.ConfigTextView=(TextView)convertView.findViewById(R.id.textViewListViewConfig);
            //al convertview le añadimos un obajeto o una clave-valor pero en esta vez no lo necesitamos
            convertView.setTag(holder);
        }else {
            holder =(ViewHolder)convertView.getTag();
        }



        //Para personalizarlo obtengo los elementos y los guardo en un string en este caso, Nos traemos el valor actual dependiente  de la posicion
        String currentName = lista.get(position);


        //Para rellenar el TextView con los nombres y lo referenciamos
        // lo deberia obtener con el findViewid pero como no esta en esta clase lo llamo con el inflador que esta en el list_item
        //cambia la referencia del holder
        holder.ConfigTextView.setText(currentName);

        //Devolvemos la vista inflada y modificada con nuestros datos
        return convertView;
    }
    static class ViewHolder{
        private TextView ConfigTextView;
    }
}
