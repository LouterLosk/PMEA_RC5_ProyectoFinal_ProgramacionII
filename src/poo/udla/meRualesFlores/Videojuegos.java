package poo.udla.meRualesFlores;

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
    public Videojuegos(String nombre, double precio, int id, String fechaPublicacion, String tipo, Double duracion) {
        super(nombre, precio, id);
        this.fechaPublicacion = fechaPublicacion;
        this.tipo = tipo;
        this.duracion = duracion;
    }

    public Videojuegos(String nombre, double precio, int id) {
        super(nombre, precio, id);
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
    public void ingresoDatos(Connection conn,String Tipo) {
        System.out.println("Ingresar los datos del videojuego");
        System.out.println("Nombre del videojuego: ");
        setNombre(sc.nextLine());
        System.out.println("Precio del videojuego: ");
        setPrecio(sc.nextDouble());
        sc.nextLine();
        setFechaPublicacion(ingresoFecha());
        setTipo(Tipo);
        System.out.println("Duracion del videojuego(Horas): ");
        setDuracion(sc.nextDouble());
        sc.nextLine();
        ingresar(conn);
        System.out.println(toString());
    }

    @Override
    public void ingresar(Connection conn) {
        String sql = "INSERT INTO videojuegos(nombre, precio, fechaPublicacion,tipo,duracion) VALUES (?,?,?,?,?)";
        try{
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,getNombre());
            ps.setDouble(2,getPrecio());
            ps.setString(3,getFechaPublicacion());
            ps.setString(4,getTipo());
            ps.setDouble(5,getDuracion());

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
        String sql = "DELETE  FROM videojuegos WHERE idVideojuegos= " + id;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
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


        String sql = "UPDATE videojuegos SET nombre = ?, precio = ?, tipo = ?, duracion = ? WHERE idVideojuegos = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setDouble(2, precio);
            ps.setString(3, tipo);
            ps.setDouble(4, duracion);
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
    public int obtener(Connection conn , int num) {
        int opcion = 1;
        if (num == 2) {
            opcion = 0;
            System.out.println("Buscar por id o mostar todos los datos de los video Juegos");
            System.out.println("1.Id   2.Todo");
            opcion = sc.nextInt();
            sc.nextLine();
        }
            if (opcion == 1) {
                System.out.println("Ingrese el id del item que desea buscar: ");
                int id = sc.nextInt();
                String sql = "SELECT * FROM sistema_ventas.videojuegos WHERE idVideojuegos = " + id;
                try{
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ResultSet rs = ps.executeQuery(sql);
                    while(rs.next()){
                        Videojuegos vid = new Videojuegos (
                                rs.getString(2),
                                rs.getDouble(3),
                                rs.getInt(1),
                                rs.getString(4),
                                rs.getString(5),
                                rs.getDouble(6)
                        );
                        System.out.println(vid.toString());
                        return rs.getInt(1);
                    }

                } catch(Exception ex) {
                    ex.printStackTrace();
                }
            }else if (opcion == 2) {
                int id;
                int max = obtenerMaxIdVideojuegos(conn);
                for (id = 0;id <= max; id++){
                    String sql = "SELECT * FROM sistema_ventas.videojuegos WHERE idVideojuegos = " + id;
                    try{
                        PreparedStatement ps = conn.prepareStatement(sql);
                        ResultSet rs = ps.executeQuery(sql);
                        while(rs.next()){
                            Videojuegos vid = new Videojuegos (
                                    rs.getString(2),
                                    rs.getDouble(3),
                                    rs.getInt(1),
                                    rs.getString(4),
                                    rs.getString(5),
                                    rs.getDouble(6)
                            );
                            System.out.println(vid.toString());
                            //return rs.getInt(1);
                        }
                    } catch(Exception ex) {
                        ex.printStackTrace();
                    }
            }
            System.out.println();
        }
        return 0;
    }

    @Override
    public String toString() {
        return "Videojuegos{" +
                "nombre=" + getNombre() +
                ", precio=" + getPrecio() +
                ", fechaPublicacion='" + fechaPublicacion + '\'' +
                ", tipo='" + tipo + '\'' +
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
