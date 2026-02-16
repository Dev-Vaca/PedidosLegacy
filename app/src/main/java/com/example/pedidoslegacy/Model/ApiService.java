package com.example.pedidoslegacy.Model;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    @GET("pedidos")
    Call<List<Pedido>> obtenerPedidos();

    @POST("pedidos")
    Call<Pedido> crearPedido(@Body Pedido pedido);
}
