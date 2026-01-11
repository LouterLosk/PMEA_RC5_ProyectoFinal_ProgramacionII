package poo.udla.meRualesFlores;

import java.io.Serializable;

public class Decoracion extends Producto {
private String  tipoDecorativo;
private double tamanio;

    public Decoracion(String nombre, double precio, int id, String tipoDecorativo, double tamanio) {
        super(nombre, precio, id);
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

    public void ingresarDecorativo(){
        System.out.println("Ingresar el tipo del decorativo: ");
        setTipoDecorativo(sc.nextLine());
        System.out.println("Ingrese la fecha de lanzamaiento del videojuego: ");
        setTamanio(Double.parseDouble(sc.nextLine()));
    }


}
