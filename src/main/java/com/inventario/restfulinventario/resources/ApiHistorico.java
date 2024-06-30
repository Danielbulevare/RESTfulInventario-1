package com.inventario.restfulinventario.resources;

import com.google.gson.Gson;
import com.inventario.modelo.ConexionBD;
import com.inventario.modelo.operacionesCRUD.HistoricoCRUD;
import com.inventario.modelo.operacionesCRUD.ProductosCRUD;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("historico")
public class ApiHistorico {
    @GET
    public Response ping() {
        return Response
                .ok("ping Jakarta EE")
                .build();
    }
    
    @GET
    @Path("/{movimiento}")
    @Produces(MediaType.APPLICATION_JSON)
    public String obtenerInventario(@PathParam("movimiento") String movimiento){
        if (ConexionBD.establecerConexion()) {
            HistoricoCRUD historicoCRUD = new HistoricoCRUD();
            
            Gson json = new Gson();
            
            //Convierta el ArrayList de Transacciones a un json
            String jsonListaProductos = json.toJson(historicoCRUD.obtenerHistorico(movimiento));
            
            return jsonListaProductos;
        } else {
            return "No se pudo establecer conexión con la BD.";
        }
    }
}
