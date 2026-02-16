package com.example.pedidoslegacy.ViewModel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pedidoslegacy.Model.Pedido;
import com.example.pedidoslegacy.R;

import java.util.List;

public class PedidosAdapter extends RecyclerView.Adapter<PedidosAdapter.ViewHolder> {

    //Lista local para guardar los objetos que vayan llegando.
    private List<Pedido> listaPedidos;

    //Metodo Constructor
    public PedidosAdapter(List<Pedido> listaPedidos) {
        this.listaPedidos = listaPedidos;
    }

    // Crear el plato vacío
    // Este metodo se ejecuta cuando la lista necesita dibujar una fila NUEVA.
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // "Inflar" significa convertir el archivo XML (texto) en un objeto View (memoria) real.
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pedido, parent, false);
        return new ViewHolder(view);
    }

    // Servir la comida
    // Aquí es donde juntamos los datos con el diseño.
    // position = ¿Qué número de fila es esta? (0, 1, 2...)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // A. Sacamos el pedido de la lista según la posición
        Pedido pedidoActual = listaPedidos.get(position);

        // B. Escribimos los datos en los textos del ViewHolder
        holder.tvCliente.setText(pedidoActual.getNombreCliente());

        // Concatenamos texto con variables
        String detalle = pedidoActual.getEstatus() + " - " + pedidoActual.getCantidadLitros() + " Lts";
        holder.tvDetalle.setText(detalle);
    }

    // GET ITEM COUNT ¿Cuántos platos sirvo?
    // El RecyclerView nos pregunta: "¿Cuántos elementos tengo que dibujar?"
    @Override
    public int getItemCount() {
        return listaPedidos.size();
    }

    // CLASE VIEWHOLDER La cajita de referencias
    // Sirve para no tener que hacer findViewById cada vez que se mueve la lista (ahorra batería y memoria).
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Declaramos los componentes visuales de NUESTRO ITEM
        TextView tvCliente;
        TextView tvDetalle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Buscamos los IDs que pusimos en 'item_pedido.xml'
            tvCliente = itemView.findViewById(R.id.tvCliente);
            tvDetalle = itemView.findViewById(R.id.tvDetalle);
        }
    }
}
