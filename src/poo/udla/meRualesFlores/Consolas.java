package poo.udla.meRualesFlores;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Consolas extends Producto implements Inter{
    private String edicion;
    private String fechaLanzamiento;

    public Consolas(String nombre, double precio, int id, String edicion, String fechaLanzamiento) {
        super(nombre, precio, id);
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
    public void ingresar(Connection conn) {
        String sql = "INSERT INTO consolas(nombre, precio, fechaLanzamiento,edicion) VALUES (?,?,?,?)";
        try{
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,getNombre());
            ps.setDouble(2,getPrecio());
            ps.setString(3,getFechaLanzamiento());
            ps.setString(4,getEdicion());

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
        String sql = "DELETE  FROM consolas WHERE idConsolas= " + id;
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

        System.out.print("Nuevo nombre: ");
        String nombre = sc.nextLine();

        System.out.print("Nuevo precio: ");
        double precio = sc.nextDouble();
        sc.nextLine();

        System.out.print("Nueva categoria: ");
        String tipo = sc.nextLine();

        String sql = "UPDATE consolas  SET nombre = ?, precio = ?, tipo = ? WHERE idConsolas = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setDouble(2, precio);
            ps.setString(3, tipo);
            ps.setInt(4, id);

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
    public int obtener(Connection conn,int num) {
        System.out.println("Ingrese el id del item que desea buscar: ");
        int id = sc.nextInt();
        String sql = "SELECT * FROM sistema_ventas.Consolas WHERE idConsolas = " + id;
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
        return 0;
    }

    @Override
    public void ingresoDatos(Connection conn,String Tipo) {
        System.out.println("Ingresar los datos de la Consola");
        System.out.println("Nombre de la consola: ");
        setNombre(sc.nextLine());
        System.out.println("Precio de la consola: ");
        setPrecio(sc.nextDouble());
        sc.nextLine();
        setFechaLanzamiento(ingresoFecha());
        setEdicion(Tipo);
        //ingresar(conn);
        System.out.println(toString());
    }
}
