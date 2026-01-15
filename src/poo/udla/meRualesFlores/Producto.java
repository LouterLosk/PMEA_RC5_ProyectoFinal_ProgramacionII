package poo.udla.meRualesFlores;

import java.sql.Connection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Producto {
    private String nombre;
    private double precio;
    private int id;
    private int stock;
    Scanner sc = new Scanner(System.in);

    public Producto(String nombre, double precio, int id, int stock) {
        this.nombre = nombre;
        this.precio = precio;
        this.id = id;
        this.stock = stock;
    }

    // GETTERS
    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    /**Metodos propios**/

    public String ingresoFecha() {
        String fecha = "";
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        boolean esCorrecta = false;
        LocalDate fechaHoy = LocalDate.now();
        LocalDate fecha1;
        while (!esCorrecta) {
            System.out.println("Ingresa la fecha (dd/MM/yyyy) o 0 si no aplica: ");
            fecha = sc.nextLine().trim();

            // Si el usuario no quiere ingresar fecha
            if (fecha.equals("0")) {
                return fecha;
            }
            try {
                fecha1 = LocalDate.parse(fecha, formato);
                if (!fechaHoy.isAfter(fecha1)) {
                    System.out.println("Fecha incorrecta");
                    System.out.println("No pude ingresar un fecha anterior a " + fechaHoy);
                } else {
                    esCorrecta = true;
                }
            } catch (DateTimeParseException e) {
                System.out.println("Formato inválido. Ejemplo correcto: 23/02/2025");
            }
        }
        return fecha;
    }
}

