package com.inventario.restfulinventario.resources;

import com.google.gson.Gson;
import com.inventario.modelo.ConexionBD;
import com.inventario.modelo.objetosBase.Producto;
import com.inventario.modelo.objetosBase.Usuario;
import com.inventario.modelo.operacionesCRUD.ProductosCRUD;
import com.inventario.modelo.operacionesCRUD.UsuariosCRUD;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("usuarios")
public class ApiUsuarios {
    
    @GET
    public Response ping(){
        return Response
                .ok("ping Jakarta EE")
                .build();
    }
    
    @GET
    @Path("/{idUsuario}/{contrasena}")
    @Produces(MediaType.APPLICATION_JSON)
    public String buscarUsuario(@PathParam("idUsuario") int idUsuario, @PathParam("contrasena") String contrasena){
        if(ConexionBD.establecerConexion()){
            UsuariosCRUD usuariosCRUD = new UsuariosCRUD();
            Usuario usuario= usuariosCRUD.buscarUsuario(idUsuario, contrasena);
            
            Gson json = new Gson();
            String jsonUsuario = json.toJson(usuario); //Convierte el objeto usuario a un String de tipo Json
            
            return jsonUsuario;
        } else {
            return "No se pudo establecer conexión con la BD.";
        }
    }
}
