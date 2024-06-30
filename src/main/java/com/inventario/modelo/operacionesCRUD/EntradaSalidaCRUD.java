package com.inventario.modelo.operacionesCRUD;

import com.inventario.modelo.ConexionBD;
import com.inventario.modelo.objetosBase.Producto;
import com.inventario.modelo.objetosBase.TransaccionInventario;
import com.inventario.modelo.objetosBase.Usuario;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EntradaSalidaCRUD {
    private Producto producto;
    private Usuario usuario;
    private TransaccionInventario transaccionInventario;

    public EntradaSalidaCRUD() {
        this.producto = new Producto();
        this.usuario = new Usuario();
        this.transaccionInventario = new TransaccionInventario();
    }

    public Producto obtenerDetalleProductoEntradaSalida(int idProducto) {
        String sql = "SELECT p.id_producto, p.producto, SUM(t.cant) AS cantidad, e.estatus FROM productos p"
                + "	LEFT JOIN transacciones_inventario t ON t.id_producto = p.id_producto"
                + "    INNER JOIN estatus e ON e.id_estatus = p.id_estatus WHERE p.id_estatus = 1 AND p.id_producto = " + idProducto + " GROUP BY p.producto;";

        try {
            Statement sentencia = ConexionBD.getConexion().createStatement();
            ResultSet consulta = sentencia.executeQuery(sql);

            if (consulta.next()) {
                producto.setId_producto(consulta.getInt("id_producto"));
                producto.setProducto(consulta.getString("producto"));
                producto.setCantidad(consulta.getInt("cantidad"));
                producto.setEstatus(consulta.getString("estatus"));
            }

            ConexionBD.cerrarConexion();

            return this.getProducto();
        } catch (SQLException ex) {
            Logger.getLogger(ProductosCRUD.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public boolean nuevoTransaccionInventario() {
        String sql = "INSERT INTO transacciones_inventario (id_producto, cant, movimiento, fecha_hora_transaccion, realizado_por_usuario) VALUES (?, ?, ?, ?, ?);";

        try {
            PreparedStatement ps = ConexionBD.getConexion().prepareStatement(sql);

            ps.setInt(1, this.producto.getId_producto());
            ps.setInt(2, this.transaccionInventario.getCantidad());
            ps.setString(3, this.transaccionInventario.getMovimiento());
            ps.setTimestamp(4, this.transaccionInventario.getFechaHoraTransaccion());
            ps.setInt(5, this.usuario.getIdUsuario());
            ps.execute();

            ConexionBD.cerrarConexion();

            return true;
        } catch (Exception ex) {
            Logger.getLogger(ProductosCRUD.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public TransaccionInventario getTransaccionInventario() {
        return transaccionInventario;
    }

    public void setTransaccionInventario(TransaccionInventario transaccionInventario) {
        this.transaccionInventario = transaccionInventario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
