package com.example.pedidoslegacy.Views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pedidoslegacy.Model.ApiService;
import com.example.pedidoslegacy.Model.Pedido;
import com.example.pedidoslegacy.R;
import com.example.pedidoslegacy.ViewModel.PedidosAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerPedidos;
    private PedidosAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_main);
        recyclerPedidos = findViewById(R.id.recyclerPedidos);
        recyclerPedidos.setLayoutManager(new LinearLayoutManager(this));

        //Buscamos el boton nuevo
        FloatingActionButton fab = findViewById(R.id.fabNuevo);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Creamos un Intent para ir de "this" (Java) a "CrearPedido.class" (Kotlin)
                Intent intent = new Intent(MainActivity.this, CrearPedido.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarDatosDeApi();
    }

    private void cargarDatosDeApi() {
        //Configurar Retrofit con la URL
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://699220ea8f29113acd3d3a1f.mockapi.io/gas/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Crear el servicio
        ApiService servicio = retrofit.create(ApiService.class);

        //LLamar a la API
        Call<List<Pedido>> llamada = servicio.obtenerPedidos();

        llamada.enqueue(new Callback<List<Pedido>>() {
            @Override
            public void onResponse(Call<List<Pedido>> call, Response<List<Pedido>> response) {
                // Si el servidor responde (Código 200 OK)
                if (response.isSuccessful()) {
                    List<Pedido> listaDeInternet = response.body();

                    // Conectamos los datos reales al Adapter
                    adapter = new PedidosAdapter(listaDeInternet);
                    recyclerPedidos.setAdapter(adapter);

                    Log.d("API_EXITO", "Llegaron " + listaDeInternet.size() + " pedidos.");
                } else {
                    Log.e("API_ERROR", "Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Pedido>> call, Throwable t) {
                // Si no hay internet o la URL está mal
                Log.e("API_FALLO", "Error: " + t.getMessage());
                Toast.makeText(MainActivity.this, "Fallo conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }
}