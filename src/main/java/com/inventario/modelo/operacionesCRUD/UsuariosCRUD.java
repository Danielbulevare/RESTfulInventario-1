package com.inventario.modelo.operacionesCRUD;

import com.inventario.modelo.ConexionBD;
import com.inventario.modelo.objetosBase.Usuario;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UsuariosCRUD {

    private Usuario usuario;

    public UsuariosCRUD() {
        this.usuario = new Usuario();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario buscarUsuario(int idUsuario, String contrasena) {
        String sql = "SELECT u.id_usuario, u.nombre, u.correo, u.contrasena, r.rol, e.estatus"
                + "	FROM usuarios u"
                + "    INNER JOIN roles r ON u.id_rol = r.id_rol"
                + "    INNER JOIN estatus e ON u.id_estatus = e.id_estatus WHERE u.id_usuario = " + idUsuario + " AND contrasena = " + contrasena + ";";

        try {
            Statement sentencia = ConexionBD.getConexion().createStatement();
            ResultSet consulta = sentencia.executeQuery(sql);

            if (consulta.next()) {
                this.usuario.setIdUsuario(consulta.getInt("id_usuario"));
                this.usuario.setNombre(consulta.getString("nombre"));
                this.usuario.setCorreo(consulta.getString("correo"));
                this.usuario.setContrasen(consulta.getString("contrasena"));
                this.usuario.setRol(consulta.getString("rol"));
                this.usuario.setEstatus(consulta.getString("estatus"));
            }

            ConexionBD.cerrarConexion();
            return this.getUsuario();
        } catch (Exception ex) {
            Logger.getLogger(UsuariosCRUD.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
