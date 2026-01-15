package poo.udla.meRualesFlores.modelos;

import poo.udla.meRualesFlores.Interfaces.Inter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Videojuegos extends Producto implements Inter {
    private String fechaPublicacion;
    private String tipo;
    private Double duracion;
    Scanner sc = new Scanner(System.in);

    /**Constructores**/
    public Videojuegos(String nombre, double precio, int id, int stock,
                       String fechaPublicacion, String tipo, double duracion) {
        super(nombre, precio, id, stock);
        this.fechaPublicacion = fechaPublicacion;
        this.tipo = tipo;
        this.duracion = duracion;
    }

    /**Metodos propios de java**/
    public String getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(String fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Double getDuracion() {
        return duracion;
    }

    public void setDuracion(Double duracion) {
        this.duracion = duracion;
    }

    /**Metodos de la interface**/
    @Override
    public void ingresoDatos(Connection conn) {
        System.out.println("Ingresar los datos del videojuego");
        System.out.println("Nombre del videojuego: ");
        setNombre(sc.nextLine());
        System.out.println("Stock: ");
        setStock(sc.nextInt());
        sc.nextLine();
        System.out.println("Precio del videojuego: ");
        setPrecio(sc.nextDouble());
        sc.nextLine();
        setFechaPublicacion(ingresoFecha());
        System.out.println("Tipo del videojuego: ");
        setTipo(sc.nextLine());
        System.out.println("Duracion del videojuego(Horas): ");
        setDuracion(sc.nextDouble());
        sc.nextLine();
        ingresar(conn);
        System.out.println(toString());
    }

    @Override
    public void vender(Connection conn) {

        System.out.println("=== VENTA DE VIDEOJUEGOS ===");

        // Mostrar productos y seleccionar ID
        int id = obtener(conn, 1);
        if (id == 0) {
            System.out.println("Producto no válido.");
            return;
        }

        System.out.print("Ingrese la cantidad a comprar: ");
        int cantidad = sc.nextInt();
        sc.nextLine();

        String sqlSelect = "SELECT stock, precio FROM videojuegos WHERE idVideojuegos = ?";
        String sqlUpdate = "UPDATE videojuegos SET stock = ? WHERE idVideojuegos = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sqlSelect);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int stockActual = rs.getInt("stock");
                double precio = rs.getDouble("precio");

                if (cantidad > stockActual) {
                    System.out.println("Stock insuficiente.");
                    return;
                }

                int nuevoStock = stockActual - cantidad;
                double total = cantidad * precio;

                PreparedStatement psUpdate = conn.prepareStatement(sqlUpdate);
                psUpdate.setInt(1, nuevoStock);
                psUpdate.setInt(2, id);
                psUpdate.executeUpdate();

                System.out.println("✅ Venta realizada con éxito");
                System.out.println("Cantidad vendida: " + cantidad);
                System.out.println("Total a pagar: $" + total);
                System.out.println("Stock restante: " + nuevoStock);

            } else {
                System.out.println("Producto no encontrado.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void ingresar(Connection conn) {
        String sql = "INSERT INTO videojuegos(nombre, precio, stock, fechaPublicacion, tipo, duracion) VALUES (?,?,?,?,?,?)";
        try{
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, getNombre());
            ps.setDouble(2, getPrecio());
            ps.setInt(3, getStock());
            ps.setString(4, fechaPublicacion);
            ps.setString(5, tipo);
            ps.setDouble(6, duracion);

            int resultado = ps.executeUpdate();

            if(resultado > 0 ){
                System.out.println("El Video Juego se ha insertado correctamente..");
            }else {
                System.out.println("El Video Juego no se inserto..");
            }

        } catch(Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void eliminar(Connection conn) {
        int id = obtener(conn,1);
        String sql = "DELETE  FROM videojuegos WHERE idVideojuegos = ?" ;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,id);
            int resultado = ps.executeUpdate();

            if(resultado > 0 ){
                System.out.println("El producto se ha eliminado correctamente..");
            }else {
                System.out.println("El producto no se ha eliminado ..");
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

        System.out.print("Nueva categoria: ");
        String tipo = sc.nextLine();

        System.out.print("Nueva duracion: ");
        Double duracion = sc.nextDouble();

        System.out.println("Nuevo stock: ");
        int newStock = sc.nextInt();


        String sql = "UPDATE videojuegos SET nombre = ?, precio = ?, tipo = ?, duracion = ?, stock = ? WHERE idVideojuegos = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setDouble(2, precio);
            ps.setString(3, tipo);
            ps.setDouble(4, duracion);
            ps.setInt(5, newStock);
            ps.setInt(6, id);
            int resultado = ps.executeUpdate();

            if (resultado > 0) {
                System.out.println("El Videojuego se ha actualizado correctamente.");
            } else {
                System.out.println("No se pudo actualizar el Videojuego.");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public int obtener(Connection conn, int num) {

        int opcion = 1;
        if (num == 2) {
            System.out.println("Buscar por id o mostrar todos los datos de los Videojuegos");
            System.out.println("1. Id   2. Todo");
            opcion = sc.nextInt();
            sc.nextLine();
        }

        if (opcion == 1) {
            System.out.print("Ingrese el id del item que desea buscar: ");
            int id = sc.nextInt();

            String sql = "SELECT * FROM sistema_ventas.videojuegos WHERE idVideojuegos = ?";

            try {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    Videojuegos vid = new Videojuegos(
                            rs.getString(2),
                            rs.getDouble(3),
                            rs.getInt(1),
                            rs.getInt(7),
                            rs.getString(4),
                            rs.getString(5),
                            rs.getDouble(6)
                    );
                    System.out.println(vid.toString());
                    return rs.getInt(1);
                } else {
                    System.out.println("El ID ingresado NO existe.");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        } else if (opcion == 2) {
            String sql = "SELECT * FROM sistema_ventas.videojuegos";
            try {
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    Videojuegos vid = new Videojuegos(
                            rs.getString(2),
                            rs.getDouble(3),
                            rs.getInt(1),
                            rs.getInt(7),
                            rs.getString(4),
                            rs.getString(5),
                            rs.getDouble(6)
                    );
                    System.out.println(vid.toString());
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return 0;
    }


    @Override
    public String toString() {
        return "Videojuegos{" +
                "ID = " + getId() +
                "Cantidad " + getStock() +
                ", nombre = " + getNombre() +
                ", precio = " + getPrecio() +
                ", fechaPublicacion = '" + fechaPublicacion + '\'' +
                ", tipo = '" + tipo + '\'' +
                ", duracion=" + duracion +
                '}';
    }

    /**Metodos propios**/

    public int obtenerMaxIdVideojuegos(Connection conn) {

        int maxId = 0;
        String sql = "SELECT MAX(idVideojuegos) AS maxId FROM videojuegos";

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
