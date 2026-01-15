package poo.udla.meRualesFlores.modelos;


import poo.udla.meRualesFlores.Interfaces.Inter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Decoracion extends Producto implements Inter {
    private String  tipoDecorativo;
    private double tamanio;

    public Decoracion(String nombre, double precio, int id, int stock,
                      String tipoDecorativo, double tamanio) {
        super(nombre, precio, id, stock);
        this.tipoDecorativo = tipoDecorativo;
        this.tamanio = tamanio;
    }


    public String getTipoDecorativo() {
        return tipoDecorativo;
    }

    public void setTipoDecorativo(String tipoDecorativo) {
        this.tipoDecorativo = tipoDecorativo;
    }

    public double getTamanio() {
        return tamanio;
    }

    public void setTamanio(double tamanio) {
        this.tamanio = tamanio;
    }

    public void ingresoDatos(Connection conn) {
        System.out.println("Ingresar los datos del decorativo");
        System.out.println("Nombre del decorativo: ");
        setNombre(sc.nextLine());
        System.out.println("Stock: ");
        setStock(sc.nextInt());
        sc.nextLine();
        System.out.println("Precio del decorativo: ");
        setPrecio(sc.nextDouble());
        sc.nextLine();
        System.out.println("Tipo del decorativo: ");
        setTipoDecorativo(sc.nextLine());
        System.out.println("Tamanio del decorativo(centimetros): ");
        setTamanio(sc.nextDouble());
        sc.nextLine();
        ingresar(conn);
        System.out.println(toString());
    }

    @Override
    public void vender(Connection conn) {

        System.out.println("=== VENTA DE DECORACIÓN ===");

        int id = obtener(conn, 1);
        if (id == 0) return;

        System.out.print("Cantidad a comprar: ");
        int cantidad = sc.nextInt();
        sc.nextLine();

        String sqlSelect = "SELECT stock, precio FROM decoracion WHERE idDecoracion = ?";
        String sqlUpdate = "UPDATE decoracion SET stock = ? WHERE idDecoracion = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sqlSelect);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int stock = rs.getInt("stock");
                double precio = rs.getDouble("precio");

                if (cantidad > stock) {
                    System.out.println("Stock insuficiente");
                    return;
                }

                int nuevoStock = stock - cantidad;
                double total = cantidad * precio;

                PreparedStatement ps2 = conn.prepareStatement(sqlUpdate);
                ps2.setInt(1, nuevoStock);
                ps2.setInt(2, id);
                ps2.executeUpdate();

                System.out.println("Venta realizada");
                System.out.println("Total: $" + total);
                System.out.println("Stock restante: " + nuevoStock);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void ingresar(Connection conn) {
        String sql = "INSERT INTO decoracion(nombre, precio, stock, tipoDecorativo, tamanio) VALUES (?,?,?,?,?)";

        try{
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, getNombre());
            ps.setDouble(2, getPrecio());
            ps.setInt(3, getStock());
            ps.setString(4, tipoDecorativo);
            ps.setDouble(5, tamanio);
            int resultado = ps.executeUpdate();

            if(resultado > 0 ){
                System.out.println("El Decorativo se ha insertado correctamente..");
            }else {
                System.out.println("El Decorativo no se inserto..");
            }

        } catch(Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void eliminar(Connection conn) {
        int id = obtener(conn,1);
        String sql = "DELETE  FROM Decoracion WHERE idDecoracion = ?" ;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,id);
            int resultado = ps.executeUpdate();

            if(resultado > 0 ){
                System.out.println("El Decorativo se ha eliminado correctamente..");
            }else {
                System.out.println("El Decorativo no se ha eliminado ..");
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void editar(Connection conn) {
        System.out.println("Editar");
        int id = obtener(conn,1);
        if (id == 0){
            return;
        }
        sc.nextLine();
        System.out.print("Nuevo nombre: ");
        String nombre = sc.nextLine();

        System.out.print("Nuevo precio: ");
        double precio = sc.nextDouble();
        sc.nextLine();

        System.out.print("Nuevo tipo: ");
        String tipo = sc.nextLine();

        System.out.print("Nuevo tamanio: ");
        Double tamanio = sc.nextDouble();


        System.out.println("Nuevo stock: ");
        int newStock = sc.nextInt();


        String sql = "UPDATE Decoracion SET nombre = ?, precio = ?, tipo = ?, tamanio = ?, stock = ? WHERE idDecoracion = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setDouble(2, precio);
            ps.setString(3, tipo);
            ps.setDouble(4, tamanio);
            ps.setInt(5, newStock);
            ps.setInt(6, id);
            int resultado = ps.executeUpdate();

            if (resultado > 0) {
                System.out.println("El Decorativo se ha actualizado correctamente.");
            } else {
                System.out.println("No se pudo actualizar el Decorativo.");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    @Override
    public int obtener(Connection conn, int num) {

        int opcion = 1;
        if (num == 2) {
            System.out.println("Buscar por id o mostrar todos los datos de los Decorativos");
            System.out.println("1. Id   2. Todo");
            opcion = sc.nextInt();
            sc.nextLine();
        }

        if (opcion == 1) {
            System.out.print("Ingrese el id del item que desea buscar: ");
            int id = sc.nextInt();

            String sql = "SELECT * FROM sistema_ventas.Decoracion WHERE idDecoracion = ?";

            try {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    Decoracion dec = new Decoracion(
                            rs.getString(2),
                            rs.getDouble(3),
                            rs.getInt(1),
                            rs.getInt(4),
                            rs.getString(5),
                            rs.getDouble(6)
                    );
                    System.out.println(dec.toString());
                    return rs.getInt(1);
                } else {
                    System.out.println("El ID ingresado NO existe.");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        } else if (opcion == 2) {
            String sql = "SELECT * FROM sistema_ventas.decoracion";
            try {
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    Decoracion dec = new Decoracion(
                            rs.getString(2),
                            rs.getDouble(3),
                            rs.getInt(1),
                            rs.getInt(4),
                            rs.getString(5),
                            rs.getDouble(6)
                    );
                    System.out.println(dec.toString());
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return 0;

    }

    @Override
    public String toString() {
        return "Decoracion{" +
                "ID = " + getId() +
                "Cantidad " + getStock() +
                ", nombre = " + getNombre() +
                ", precio = " + getPrecio() +
                "tipoDecorativo='" + tipoDecorativo + '\'' +
                ", tamanio=" + tamanio +
                '}';
    }

    public int obtenerMaxIddecoracion(Connection conn) {

        int maxId = 0;
        String sql = "SELECT MAX(idDecoracion) AS maxId FROM Decoracion";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                maxId = rs.getInt("maxId");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return maxId;
    }

}