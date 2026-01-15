package poo.udla.meRualesFlores;

import poo.udla.meRualesFlores.Interfaces.Inter;
import poo.udla.meRualesFlores.baseDatos.Utilidades;
import poo.udla.meRualesFlores.modelos.Consolas;
import poo.udla.meRualesFlores.modelos.Decoracion;
import poo.udla.meRualesFlores.modelos.Videojuegos;

import java.sql.Connection;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Utilidades util = new Utilidades();
        Connection conn = util.getConnection();
        Scanner sc = new Scanner(System.in);

        // Objetos polimórficos
        Inter videojuegos = new Videojuegos("", 0.0, 0,0, "", "", 0.0);
        Inter consolas   = new Consolas("", 0.0, 0, 0,"", "");
        Inter decoracion = new Decoracion("", 0.0, 0,0, "",0.0);

        if (conn == null) {
            System.out.println("Error de conexión con la base de datos");
            return;
        }
        System.out.println("Conectado a la base de datos");

        int opcionPrincipal;
        do {
            opcionPrincipal = menuPrincipal(sc);
            switch (opcionPrincipal) {
                case 1:
                    gestionarProducto(videojuegos, conn, sc);
                    break;
                case 2:
                    gestionarProducto(consolas, conn, sc);
                    break;
                case 3:
                    gestionarProducto(decoracion, conn, sc);
                    break;
                case 4:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }

        } while (opcionPrincipal != 4);
        sc.close();
    }

    // ================= MENÚ PRINCIPAL =================
    public static int menuPrincipal(Scanner sc) {
        System.out.println("\n===== TIPO DE PRODUCTO =====");
        System.out.println("1. Videojuegos");
        System.out.println("2. Consolas");
        System.out.println("3. Decoracion");
        System.out.println("4. Salir");
        System.out.print("Seleccione una opción: ");
        int opcion = sc.nextInt();
        return opcion;
    }

    // ================= MENÚ DE OPCIONES =================
    public static int menuOpciones(Scanner sc) {
        System.out.println("\n===== MENÚ =====");
        System.out.println("1. Ingresar");
        System.out.println("2. Editar");
        System.out.println("3. Buscar");
        System.out.println("4. Vender");
        System.out.println("5. Regresar");
        System.out.print("Seleccione una opción: ");
        return sc.nextInt();
    }

    // ================= GESTIÓN DE PRODUCTOS =================
    public static void gestionarProducto(Inter producto, Connection conn, Scanner sc) {
        int opcion;
        do {
            opcion = menuOpciones(sc);
            sc.nextLine();
            switch (opcion) {
                case 1:
                    producto.ingresoDatos(conn);
                    break;
                case 2:
                    producto.editar(conn);
                    break;
                case 3:
                    producto.obtener(conn, 2);
                    break;
                case 4:
                    producto.vender(conn);
                    break;
                case 5:
                    System.out.println("Regresando al menú principal...");
                    try {
                        conn.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    System.out.println("Opción inválida");
            }

        } while (opcion != 5);
    }
}
