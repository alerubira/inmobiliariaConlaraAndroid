package com.principal.inmobiliariaconlaraandroid.ui.contratos.pagos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.principal.inmobiliariaconlaraandroid.R;
import com.principal.inmobiliariaconlaraandroid.clases.Inmueble;
import com.principal.inmobiliariaconlaraandroid.clases.Pago;
import com.principal.inmobiliariaconlaraandroid.ui.inmuebles.InmuebleAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public  class PagoAdapter extends RecyclerView.Adapter<PagoAdapter.PagoViewHolder>{
    private List<Pago> lista;
    private Context context;

    public PagoAdapter(List<Pago> lista, Context context) {
        this.lista = lista;
        this.context = context;
    }

    @NonNull
    @Override
    public PagoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pago, parent, false);
        return new PagoAdapter.PagoViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull PagoViewHolder holder, int position) {
             Pago p=lista.get(position);
             holder.txvId.setText("Codigo Interno: "+ p.getIdPago());
                Date fecha = p.getFechaPago(); // Supongamos que devuelve un java.util.Date
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                holder.txvFecha.setText("Fecha del Pago: "+ sdf.format(fecha));
             holder.txvMonto.setText("Monto: "+ p.getMonto());
             holder.txvdetalle.setText("Detalle: "+ p.getDetalle());

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class PagoViewHolder extends RecyclerView.ViewHolder {
        private TextView txvId, txvFecha, txvMonto,txvdetalle;
        private CardView cardView;
        public PagoViewHolder(@NonNull View itemView) {
            super(itemView);
            txvId=itemView.findViewById(R.id.txvIdPago);
            txvFecha=itemView.findViewById(R.id.txvFechaPago);
            txvdetalle=itemView.findViewById(R.id.txvDetallePago);
            txvMonto=itemView.findViewById(R.id.txvMontoPago);
            cardView=itemView.findViewById(R.id.idCardPago);
        }
    }

    }

