package com.inventario.restfulinventario.resources;

import com.google.gson.Gson;
import com.inventario.modelo.ConexionBD;
import com.inventario.modelo.objetosBase.Producto;
import com.inventario.modelo.operacionesCRUD.ProductosCRUD;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("productos")
public class ApiProductos {

    @GET
    public Response ping() {
        return Response
                .ok("ping Jakarta EE")
                .build();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String nuevoProducto(String producto) {
        if (ConexionBD.establecerConexion()) {
            Gson json = new Gson();

            //Convierte el string Json a un objeto de tipo producto
            Producto produ = json.fromJson(producto, Producto.class);

            ProductosCRUD productosCRUD = new ProductosCRUD();
            productosCRUD.setProducto(produ);

            if (productosCRUD.nuevoProducto()) {
                return "Creado";
            } else {
                return "No creado";
            }
        } else {
            return "No se pudo establecer conexión con la BD.";
        }
    }

    @GET
    @Path("/{idProducto}")
    @Produces(MediaType.APPLICATION_JSON)
    public String buscarProducto(@PathParam("idProducto") int idProducto) {
        if (ConexionBD.establecerConexion()) {
            ProductosCRUD productosCRUD = new ProductosCRUD();
            Producto producto = productosCRUD.buscarProducto(idProducto);

            Gson json = new Gson();
            String jsonProducto = json.toJson(producto); //Convierte el objeto producto a un String de tipo Json

            return jsonProducto;
        } else {
            return "No se pudo establecer conexión con la BD.";
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String editarProducto(String producto) {
        if (ConexionBD.establecerConexion()) {
            Gson json = new Gson();

            //Convierte el string Json a un objeto de tipo producto
            Producto produ = json.fromJson(producto, Producto.class);

            ProductosCRUD productosCRUD = new ProductosCRUD();
            productosCRUD.setProducto(produ);

            if (productosCRUD.editarProducto()) {
                return "Modificado";
            } else {
                return "No modificado";
            }
        } else {
            return "No se pudo establecer conexión con la BD.";
        }
    }
}
