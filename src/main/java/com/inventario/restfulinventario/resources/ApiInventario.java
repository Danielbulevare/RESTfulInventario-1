package com.inventario.restfulinventario.resources;

import com.google.gson.Gson;
import com.inventario.modelo.ConexionBD;
import com.inventario.modelo.operacionesCRUD.ProductosCRUD;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("inventario")
public class ApiInventario {
    @GET
    public Response ping() {
        return Response
                .ok("ping Jakarta EE")
                .build();
    }
    
    @GET
    @Path("/{estatus}")
    @Produces(MediaType.APPLICATION_JSON)
    public String obtenerInventario(@PathParam("estatus") String estatus){
        if (ConexionBD.establecerConexion()) {
            ProductosCRUD productosCRUD = new ProductosCRUD();
            
            Gson json = new Gson();
            
            //Convierta el ArrayList de Productos a un json
            String jsonListaProductos = json.toJson(productosCRUD.obtenerInventario(estatus));
            
            return jsonListaProductos;
        } else {
            return "No se pudo establecer conexión con la BD.";
        }
    }
}
