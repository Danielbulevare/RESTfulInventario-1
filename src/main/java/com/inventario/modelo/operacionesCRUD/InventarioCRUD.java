package com.inventario.modelo.operacionesCRUD;

import com.inventario.modelo.ConexionBD;
import com.inventario.modelo.objetosBase.Producto;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InventarioCRUD {

    private ArrayList<Producto> listaProductos;

    public InventarioCRUD() {

        this.listaProductos = new ArrayList<Producto>();
    }

    public ArrayList<Producto> obtenerInventario(String estatus) {
        int idEstatus = 1;

        if (estatus.equals("INACTIVO")) {
            idEstatus = 2;
        }

        String sql = "SELECT p.id_producto, p.producto, SUM(t.cant) AS cantidad, e.estatus FROM productos p"
                + "	INNER JOIN transacciones_inventario t ON t.id_producto = p.id_producto"
                + "    INNER JOIN estatus e ON e.id_estatus = p.id_estatus WHERE p.id_estatus = " + idEstatus + " GROUP BY p.producto;";

        try {
            Statement sentencia = ConexionBD.getConexion().createStatement();
            ResultSet consulta = sentencia.executeQuery(sql);

            while (consulta.next()) {
                Producto produc = new Producto();

                produc.setId_producto(consulta.getInt("id_producto"));
                produc.setProducto(consulta.getString("producto"));
                produc.setCantidad(consulta.getInt("cantidad"));
                produc.setEstatus(consulta.getString("estatus"));

                listaProductos.add(produc);
            }

            ConexionBD.cerrarConexion();

            return this.getListaProductos();
        } catch (SQLException ex) {
            Logger.getLogger(ProductosCRUD.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public ArrayList<Producto> getListaProductos() {
        return listaProductos;
    }

    public void setListaProductos(ArrayList<Producto> listaProductos) {
        this.listaProductos = listaProductos;
    }
}
