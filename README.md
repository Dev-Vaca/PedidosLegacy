# 🛒 PedidosLegacy

Una aplicación Android que combina código clásico en **Java** con las nuevas tecnologías de **Kotlin**. Permite ver una lista de pedidos y agregar nuevos registros conectándose a una API real simulada.

## 📱 Funcionalidades
* **Lista de Pedidos:** Muestra los pedidos existentes trayendo los datos desde internet (GET).
* **Crear Pedido:** Un formulario moderno para agregar nuevos clientes y litros (POST).
* **Actualización Automática:** La lista se refresca sola al agregar un pedido nuevo.

## 🛠️ ¿Con qué se hizo?
Este proyecto demuestra cómo pueden convivir dos mundos en una misma app:

* **Lenguajes:** Java (Pantalla principal) y Kotlin (Pantalla de formulario).
* **Diseño (UI):** XML clásico para la lista y **Jetpack Compose** para la pantalla nueva.
* **Conexión:** Librería **Retrofit** para consumir la API.
* **Arquitectura:** Organización limpia por carpetas (Model, Views, ViewModel).
