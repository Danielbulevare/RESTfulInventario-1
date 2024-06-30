package com.inventario.modelo.operacionesCRUD;

import com.inventario.modelo.ConexionBD;
import com.inventario.modelo.objetosBase.Producto;
import com.inventario.modelo.objetosBase.TransaccionInventario;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HistoricoCRUD {

    private ArrayList<TransaccionInventario> listaTransacciones;

    public HistoricoCRUD() {
        this.listaTransacciones = new ArrayList<TransaccionInventario>();
    }

    public ArrayList<TransaccionInventario> obtenerHistorico(String movimiento) {
        String sql = "SELECT p.id_producto, p.producto, t.movimiento, SUM(t.cant) AS cantidad, t.fecha_hora_transaccion, u.nombre FROM productos p"
                + "	INNER JOIN transacciones_inventario t ON t.id_producto = p.id_producto"
                + "    INNER JOIN estatus e ON e.id_estatus = p.id_estatus"
                + "    INNER JOIN usuarios u ON u.id_usuario = t.realizado_por_usuario WHERE t.movimiento = '" + movimiento + "' GROUP BY t.id_transaccion;";

        try {
            Statement sentencia = ConexionBD.getConexion().createStatement();
            ResultSet consulta = sentencia.executeQuery(sql);

            while (consulta.next()) {
                TransaccionInventario transaccion = new TransaccionInventario();

                transaccion.setIdProducto(consulta.getInt("id_producto"));
                transaccion.setProducto(consulta.getString("producto"));
                transaccion.setMovimiento(consulta.getString("movimiento"));
                transaccion.setCantidad(consulta.getInt("cantidad"));
                transaccion.setFechaHoraTransaccion(consulta.getTimestamp("fecha_hora_transaccion"));
                transaccion.setRealizadoPorUsuario(consulta.getString("nombre"));

                listaTransacciones.add(transaccion);
            }

            ConexionBD.cerrarConexion();

            return this.getListaTransacciones();
        } catch (SQLException ex) {
            Logger.getLogger(HistoricoCRUD.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public ArrayList<TransaccionInventario> getListaTransacciones() {
        return listaTransacciones;
    }

    public void setListaTransacciones(ArrayList<TransaccionInventario> listaTransacciones) {
        this.listaTransacciones = listaTransacciones;
    }
}
