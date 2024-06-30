package com.inventario.restfulinventario.resources;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.inventario.modelo.ConexionBD;
import com.inventario.modelo.objetosBase.Producto;
import com.inventario.modelo.objetosBase.TransaccionInventario;
import com.inventario.modelo.objetosBase.Usuario;
import com.inventario.modelo.operacionesCRUD.EntradaSalidaCRUD;
import com.inventario.modelo.operacionesCRUD.ProductosCRUD;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.sql.Timestamp;
import java.util.Calendar;

@Path("entrada-salida")
public class ApiEntradaSalida {
    @GET
    public Response ping() {
        return Response
                .ok("ping Jakarta EE")
                .build();
    }
    
    @GET
    @Path("/{idProducto}")
    @Produces(MediaType.APPLICATION_JSON)
    public String obtenerDetalleProductoEntradaSalida(@PathParam("idProducto") int idProducto) {
        if (ConexionBD.establecerConexion()) {
            EntradaSalidaCRUD entradaSalidaCRUD = new EntradaSalidaCRUD();
            Producto producto = entradaSalidaCRUD.obtenerDetalleProductoEntradaSalida(idProducto);

            Gson json = new Gson();
            String jsonProducto = json.toJson(producto); //Convierte el objeto producto a un String de tipo Json

            return jsonProducto;
        } else {
            return "No se pudo establecer conexión con la BD.";
        }
    }
    
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String nuevoTransaccionInventario(String json) {
        if (ConexionBD.establecerConexion()) {
            Gson objetosJson = new Gson();

            // Convertir el JSON a las clases correspondientes
            JsonObject jsonObject = objetosJson.fromJson(json, JsonObject.class);
            Producto producto = objetosJson.fromJson(jsonObject.get("producto"), Producto.class);
            Usuario usuario = objetosJson.fromJson(jsonObject.get("usuario"), Usuario.class);
            TransaccionInventario transaccionInventario = objetosJson.fromJson(jsonObject.get("transaccionInventario"), TransaccionInventario.class);

            EntradaSalidaCRUD entradaSalidaCRUD = new EntradaSalidaCRUD();
            entradaSalidaCRUD.setProducto(producto);
            entradaSalidaCRUD.setUsuario(usuario);
            entradaSalidaCRUD.setTransaccionInventario(transaccionInventario);
            
            Timestamp v = new Timestamp(Calendar.getInstance().getTimeInMillis()); //Fecha actual
            System.out.println(v);

            if (entradaSalidaCRUD.nuevoTransaccionInventario()) {
                return "Creado";
            } else {
                return "No creado";
            }
        } else {
            return "No se pudo establecer conexión con la BD.";
        }
    }
}
