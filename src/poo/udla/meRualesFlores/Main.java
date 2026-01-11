package poo.udla.meRualesFlores;

import javax.swing.*;
import java.sql.Connection;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Utilidades util = new Utilidades();
        Connection conn = null;
        conn = util.getConnection();
        Scanner sc = new Scanner(System.in);
        int seguir;
        int seguir2 = 0;
        String tipo;

        Inter vid = new Videojuegos("", 0.0, 0, "", "", 0.0);
        Inter cos = new Consolas("", 0.0, 0, "", "");

        if (conn != null) {
            System.out.println("Conectados ..!!");
        } else {
            System.out.println("NO Conectado ...!!");
        }

        do {
            System.out.println("Ingrese el tipo de producto?");
            System.out.println("Consolas, Video Juegos, Decoracion, Salir");
            tipo = sc.nextLine();
            if (tipo.equalsIgnoreCase("Video Juegos")) {
                seguir = 0;
                do {
                    System.out.println("Video Juegos");
                    menu();
                    int opcion = sc.nextInt();
                    sc.nextLine();
                    switch (opcion) {
                        case 1:
                            System.out.println("Agregar un producto");
                            vid.ingresoDatos(conn);
                            break;
                        case 2:
                            System.out.println("Editar un producto");
                            vid.editar(conn);
                            break;
                        case 3:
                            System.out.println("Buscar un producto");
                            vid.obtener(conn,2);
                            break;
                        case 4:
                            System.out.println("Eliminar un producto");
                            vid.eliminar(conn);
                            break;
                        case 5:
                            /**Salir**/
                            seguir = 1;
                            break;
                    }
                } while (seguir != 1);
            } else if (tipo.equalsIgnoreCase("Consolas")) {
                seguir = 0;
                do {
                    System.out.println("Consolas");
                    menu();
                    int opcion = sc.nextInt();
                    sc.nextLine();
                    switch (opcion) {
                        case 1:
                            System.out.println("Agregar un producto");
                            cos.ingresoDatos(conn);
                            break;
                        case 2:
                            System.out.println("Editar un producto");
                            cos.editar(conn);
                            break;
                        case 3:
                            System.out.println("Buscar un producto");
                            cos.obtener(conn,2);
                            break;
                        case 4:
                            System.out.println("Eliminar un producto");
                            cos.eliminar(conn);
                            break;
                        case 5:
                            seguir = 1;
                            break;
                    }
                } while (seguir != 1);

            } else if (tipo.equalsIgnoreCase("Decoracion")) {
                System.out.println("Decoracion");


            } else if (tipo.equalsIgnoreCase("Salir")) {
                seguir2 = 1;
                System.out.println("Saliendo....");
            }
            else {
                System.out.println("Opcion incorrecta");
            }
        }while (seguir2 != 1);

    }
    public static void menu() {
        System.out.println("Menu");
        System.out.println("1. Ingresar");
        System.out.println("2. Editar");
        System.out.println("3. Buscar");
        System.out.println("4. Eliminar");
        System.out.println("5. Regresar");
    }


}