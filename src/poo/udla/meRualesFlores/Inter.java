package poo.udla.meRualesFlores;

import java.sql.Connection;

public interface Inter {
    public void ingresar(Connection conn);
    public void eliminar(Connection conn);
    public void editar(Connection conn);
    public int obtener(Connection conn,int num);
    public void ingresoDatos(Connection conn);

}
