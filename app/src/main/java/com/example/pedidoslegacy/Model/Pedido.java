package com.example.pedidoslegacy.Model;

//Clase para poder crear los objetos llamados pedidos, estos objetos seran
//los que vamos a mandar a llamar en recyclerView para poder mostrar los pedidos
public class Pedido {
    // Atributos de la clase Pedido privadas por seguridad
    private String nombreCliente;
    private String estatus;
    private int cantidadLitros;

    //Constructor de la clase para instanciar cada objeto
    public Pedido() {}

    //Constructor para el POST
    public Pedido(String nombreCliente, String estatus, int cantidadLitros) {
        this.nombreCliente = nombreCliente;
        this.estatus = estatus;
        this.cantidadLitros = cantidadLitros;
    }

    //Metodos para poder acceder a las variables, utilizando lowerCamelCase
    public String getNombreCliente() {
        return nombreCliente;
    }

    public String getEstatus() {
        return estatus;
    }

    public int getCantidadLitros() {
        return cantidadLitros;
    }
}
