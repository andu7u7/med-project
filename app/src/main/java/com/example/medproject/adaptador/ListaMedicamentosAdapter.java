package com.example.medproject.adaptador;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.medproject.ListaMedicamento;
import com.example.medproject.R;
import com.example.medproject.entidad.Medicamento;

import java.util.List;

public class ListaMedicamentosAdapter extends RecyclerView.Adapter<ListaMedicamentosAdapter.ViewHolder> {
    private List<Medicamento> mData;
    private LayoutInflater mInflater;
    private Context context;

    public ListaMedicamentosAdapter(List<Medicamento> itemsList, Context context){
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
                Medicamento medicamento = mData.get(position);
                Intent intent = new Intent(holder.itemView.getContext(), ListaMedicamento.class);
                intent.putExtra("medicamentoSeleccionado", medicamento);
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    public void setItems(List<Medicamento> items) {mData = items; }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nombre, dosis;

        ViewHolder(View itemView){
            super(itemView);
            nombre = itemView.findViewById(R.id.txtItemNombreMedicamento);
            dosis = itemView.findViewById(R.id.txtItemDosis);
        }

        void bindData(final Medicamento item) {
            nombre.setText(item.getNombre());
            dosis.setText(item.getDosis()+"");
        }
    }

}
