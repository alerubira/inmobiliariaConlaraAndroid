package com.principal.inmobiliariaconlaraandroid.ui.contratos;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.principal.inmobiliariaconlaraandroid.R;
import com.principal.inmobiliariaconlaraandroid.clases.Inmueble;
import com.principal.inmobiliariaconlaraandroid.request.ApiClient;
import com.principal.inmobiliariaconlaraandroid.ui.Inqilinos.InquilinosAdapter;

import java.util.List;

public class ContratosAdapter extends RecyclerView.Adapter<ContratosAdapter.ContratosViewHolder>{
    private List<Inmueble> lista;
    private Context context;

    public ContratosAdapter(List<Inmueble> lista, Context context) {
        this.lista = lista;
        this.context = context;
    }

    @NonNull
    @Override
    public ContratosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_inmueble_contratos, parent, false);
        return new ContratosAdapter.ContratosViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ContratosViewHolder holder, int position) {
        Inmueble i = lista.get(position);

        holder.tvDireccion.setText(i.getDireccion());
        holder.tvTipo.setText(i.getTipo());
        holder.tvPrecio.setText("$"+String.valueOf(i.getValor()));
        Glide.with(context)
                .load(ApiClient.URLBASE + i.getImagen())
                .placeholder(R.drawable.casa)
                .error("null")
                .into(holder.imgInmueble);
        holder.cardView.setOnClickListener(v ->{
            Bundle bundle = new Bundle();
            bundle.putSerializable("inmueble", i);
            Navigation.findNavController((Activity)v.getContext(), R.id.nav_host_fragment_content_main).navigate(R.id.contratoFragment, bundle);
           // Navigation.findNavController(v).navigate(R.id.contratoFragment, bundle);
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class ContratosViewHolder extends RecyclerView.ViewHolder{
        private TextView tvDireccion, tvTipo, tvPrecio;
        private ImageView imgInmueble;
        private CardView cardView;
        public ContratosViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDireccion = itemView.findViewById(R.id.tvDireccionIC);
            tvTipo = itemView.findViewById(R.id.tvTipoIC);
            tvPrecio = itemView.findViewById(R.id.tvPrecioIC);
            imgInmueble = itemView.findViewById(R.id.imgInmuebleC);
            cardView=itemView.findViewById(R.id.idCardIC);
        }
    }
}
