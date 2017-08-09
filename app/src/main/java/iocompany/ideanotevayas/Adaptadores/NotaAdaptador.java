package iocompany.ideanotevayas.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import iocompany.ideanotevayas.Modelos.Nota;
import iocompany.ideanotevayas.R;

/**
 * Created by PC on 05/07/2017.
 */

public class NotaAdaptador extends BaseAdapter {

    private Context context;
    private List<Nota> lista;
    private int layout;

    public NotaAdaptador (Context context,List<Nota> notas,int layout){
        this.context = context;
        this.lista = notas;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int position) {
        return lista.get(position);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(layout,null);
            vh= new ViewHolder();
            vh.contenido=(TextView)convertView.findViewById(R.id.descripcion_listView);
            vh.fecha=(TextView)convertView.findViewById(R.id.fecha_listView);
            vh.favorito =(ImageView)convertView.findViewById(R.id.favoritoIMG);

            convertView.setTag(vh);
        }else{
            vh=(ViewHolder)convertView.getTag();
        }
        Nota nota =lista.get(position);
        vh.contenido.setText(nota.getDescripcion());
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String date = df.format(nota.getCreadaElFecha());
        vh.fecha.setText(date);
        if(nota.isFavorito()==false){
            vh.favorito.setImageResource(R.drawable.ic_favorito);
        }else{
            vh.favorito.setImageResource(R.drawable.ic_favorito_full);
        }

        return convertView;
    }

    public class ViewHolder{
        TextView contenido;
        TextView fecha;
        ImageView favorito;
    }
}
