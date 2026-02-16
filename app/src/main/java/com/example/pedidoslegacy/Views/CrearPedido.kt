package com.example.pedidoslegacy.Views

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.pedidoslegacy.Model.ApiService
import com.example.pedidoslegacy.Model.Pedido
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CrearPedido : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PantallaFormulario(onFinish = {
                finish()
            })
        }
    }
}

@Composable
fun PantallaFormulario(onFinish: () -> Unit) {
    var nombre by remember { mutableStateOf("") }
    var litros by remember { mutableStateOf("") }
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Nuevo Pedido", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de Texto: Nombre
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it }, // Actualizamos el estado al escribir
            label = { Text("Nombre del Cliente") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Campo de Texto: Litros (Solo números)
        OutlinedTextField(
            value = litros,
            onValueChange = { litros = it },
            label = { Text("Cantidad de Litros") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Botón de Enviar
        Button(
            onClick = {
                // Acción: Enviar a la API
                enviarPedido(nombre, litros, context) { exito ->
                    if (exito) {
                        // Si funcionó, cerramos la pantalla y volvemos a la lista
                        onFinish()
                    }
                }
            },
            enabled = nombre.isNotEmpty() && litros.isNotEmpty(), // Se desactiva si está vacío
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Crear Pedido")
        }
    }
}

// LÓGICA DE NEGOCIO
private fun enviarPedido(nombre: String, litrosTexto: String, context: Context, alTerminar: (Boolean) -> Unit) {
    val litros = litrosTexto.toIntOrNull() ?: 0

    // Creamos el objeto
    val nuevoPedido = Pedido()
    // Agregar los datos del objeto
    val pedidoParaEnviar = Pedido(nombre, "Pendiente", litros)

    // Configurar Retrofit
    val retrofit = Retrofit.Builder()
        .baseUrl("https://699220ea8f29113acd3d3a1f.mockapi.io/gas/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // Crear servicio
    val servicio = retrofit.create(ApiService::class.java)

    // Llamada POST
    servicio.crearPedido(pedidoParaEnviar).enqueue(object : Callback<Pedido> {
        override fun onResponse(call: Call<Pedido>, response: Response<Pedido>) {
            if (response.isSuccessful) {
                Toast.makeText(context, "¡Pedido Creado!", Toast.LENGTH_SHORT).show()
                alTerminar(true)
            } else {
                Toast.makeText(context, "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                alTerminar(false)
            }
        }

        override fun onFailure(call: Call<Pedido>, t: Throwable) {
           Toast.makeText(context, "Fallo conexión", Toast.LENGTH_SHORT).show()
            Log.e("API_ERROR", t.message.toString())
            alTerminar(false)
        }

    })
}
