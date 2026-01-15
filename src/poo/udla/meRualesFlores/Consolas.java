package poo.udla.meRualesFlores;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Consolas extends Producto implements Inter{
    private String edicion;
    private String fechaLanzamiento;
    Scanner sc = new Scanner(System.in);

    public Consolas(String nombre, double precio, int id, int stock, String edicion, String fechaLanzamiento) {
        super(nombre, precio, id, stock);
        this.edicion = edicion;
        this.fechaLanzamiento = fechaLanzamiento;
    }


    public String getEdicion() {
        return edicion;
    }

    public void setEdicion(String edicion) {
        this.edicion = edicion;
    }

    public String getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    public void setFechaLanzamiento(String fechaLanzamiento) {
        this.fechaLanzamiento = fechaLanzamiento;
    }


    /**Metodos de la interface**/

    @Override
    public void ingresoDatos(Connection conn) {
        System.out.println("Ingresar los datos de la Consola");
        System.out.println("Stock: ");
        setStock(sc.nextInt());
        sc.nextLine();
        System.out.println("Nombre de la consola: ");
        setNombre(sc.nextLine());
        System.out.println("Precio de la consola: ");
        setPrecio(sc.nextDouble());
        sc.nextLine();
        System.out.println("Fecha de lanzamiento la consola: ");
        setFechaLanzamiento(ingresoFecha());
        System.out.println("Edicion: ");
        setEdicion(sc.nextLine());
        ingresar(conn);
        System.out.println(toString());
    }


    @Override
    public void ingresar(Connection conn) {
        String sql = "INSERT INTO consolas(nombre, precio, stock, fechaLanzamiento, edicion) VALUES (?,?,?,?,?)";
        try{
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, getNombre());
            ps.setDouble(2, getPrecio());
            ps.setInt(3, getStock());
            ps.setString(4, fechaLanzamiento);
            ps.setString(5, edicion);

            int resultado = ps.executeUpdate();

            if(resultado > 0 ){
                System.out.println("La consola se ha insertado correctamente..");
            }else {
                System.out.println("La consola Juego no se inserto..");
            }

        } catch(Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void eliminar(Connection conn) {
        System.out.println("Eliminar");
        int id = obtener(conn,1);
        String sql = "DELETE  FROM consolas WHERE idConsolas = ?" ;
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
        System.out.print("Nuevo nombre: ");
        String nombre = sc.nextLine();

        System.out.print("Nuevo precio: ");
        double precio = sc.nextDouble();
        sc.nextLine();

        System.out.print("Nueva edicion: ");
        String tipo = sc.nextLine();

        System.out.println("Nuevo stock: ");
        int newStock = sc.nextInt();

        String sql = "UPDATE consolas  SET nombre = ?, precio = ?, edicion = ?, stock = ? WHERE idConsolas = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setDouble(2, precio);
            ps.setString(3, tipo);
            ps.setInt(4, getStock());
            ps.setInt(5, id);

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
            System.out.println("Buscar por id o mostrar todos los datos de los Consolas");
            System.out.println("1. Id   2. Todo");
            opcion = sc.nextInt();
            sc.nextLine();
        }

        if (opcion == 1) {
            System.out.print("Ingrese el id del item que desea buscar: ");
            int id = sc.nextInt();
            sc.nextLine();

            String sql = "SELECT * FROM sistema_ventas.consolas WHERE idConsolas = ?";

            try {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    Consolas vid = new Consolas(
                            rs.getString(2),
                            rs.getDouble(3),
                            rs.getInt(1),
                            rs.getInt(6),
                            rs.getString(5),
                            rs.getString(4)
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
            String sql = "SELECT * FROM sistema_ventas.consolas";
            try {
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    Consolas vid = new Consolas(
                            rs.getString(2),
                            rs.getDouble(3),
                            rs.getInt(1),
                            rs.getInt(6),
                            rs.getString(4),
                            rs.getString(5)
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
        return "Consolas{" +
                "ID = " + getId() + '\'' +
                "Stock = " + getStock() + '\'' +
                ", nombre = " + getNombre() + '\'' +
                ", precio = " + getPrecio() + '\'' +
                ", edicion = " + getEdicion() + '\'' +
                ", fechaLanzamiento='" + fechaLanzamiento + '\'' +
                '}';
    }

    public int obtenerMaxIdConsolas(Connection conn) {

        int maxId = 0;
        String sql = "SELECT MAX(idConsolas) AS maxId FROM consolas";

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
