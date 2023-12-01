package com.example.medproject.adaptador;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.medproject.DetalleMedicamento;
import com.example.medproject.ListaRecordatorio;
import com.example.medproject.R;
import com.example.medproject.entidad.Recordatorio;

import java.util.List;

public class ListaMedicamentosAdapter extends RecyclerView.Adapter<ListaMedicamentosAdapter.ViewHolder> {
    private List<Recordatorio> mData;
    private LayoutInflater mInflater;
    private Context context;

    public ListaMedicamentosAdapter(List<Recordatorio> itemsList, Context context){
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = itemsList;
    }

    @Override
    public int getItemCount() {return mData.size(); }

    @Override
    public ListaMedicamentosAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.lista_item_medicamento,null);
        return new ListaMedicamentosAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListaMedicamentosAdapter.ViewHolder holder, final int position){
        holder.bindData(mData.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Recordatorio medicamento = mData.get(position);
                Intent intent = new Intent(holder.itemView.getContext(), DetalleMedicamento.class);
                intent.putExtra("medicamentoSeleccionado", medicamento);
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    public void setItems(List<Recordatorio> items) {mData = items; }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nombre, dosis;
        ImageView imagenMedicamento;

        ViewHolder(View itemView){
            super(itemView);
            nombre = itemView.findViewById(R.id.txtItemNombreMedicamento);
            dosis = itemView.findViewById(R.id.txtItemDosis);
            imagenMedicamento = itemView.findViewById(R.id.imagenMedicamento);
        }

        void bindData(final Recordatorio item) {
            nombre.setText(item.getNombre());
            dosis.setText("Dosis: "+ item.getDosis()+"");
            //Verificar que la imagen no este vacia
            if(item.getImagen() != null && !item.getImagen().isEmpty()){
                Glide.with(itemView.getContext()).load(item.getImagen()).into(imagenMedicamento);
            }
        }
    }

}
