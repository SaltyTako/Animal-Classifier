/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animalclassifier;

/**
 *
 * @author RAFAEL RAMIREZ
 */
public class Ganado {
    public String tatuaje;
    public String nombre;
    public int edad;
    public String expositor;
    public String estado;
    public int indice_raza;
    public String raza;
    public boolean checked;
    
    public Ganado()
    {
        //Constructor
        tatuaje = "";
        nombre = "";
        edad = 0;
        expositor = "";
        estado = "";
        indice_raza = 0;
        raza = "";
    }
    //Ahora, a construir una clase en la ventana, para poder usarla
    public Ganado(String a, String e, int i, String o, String u, int race, String raz)
    {
        tatuaje = a;
        nombre = e;
        edad = i;
        expositor = o;
        estado = u;
        indice_raza = race;
        raza = raz;
    }
    
    //Setters
    public void setTatto(String t)
    {
        tatuaje = t;
    }
    public void setnombre(String n)
    {
        nombre = n;
    }
    public void setedad(int e)
    {
        edad = e;
    }
    public void setexp(String ex)
    {
        expositor = ex;
    }
    public void setestado(String es)
    {
        estado = es;
    }
    
    //Getters
    public String getTatto()
    {
        return tatuaje;
    }
    public String getnombre()
    {
        return nombre;
    }
    public int getedad()
    {
        return edad;
    }
    public String getexp()
    {
        return expositor;
    }
    public String getestado()
    {
        return estado;
    }
    public int getindexraza()
    {
        return indice_raza;
    }
    public String getRaza()
    {
        return raza;
    }
    public void mostrar()
    {
        System.out.println("Tatuaje: "+tatuaje);
        System.out.println("Nombre: "+nombre);
        System.out.println("Edad: "+edad);
        System.out.println("Expositor: "+expositor);
        System.out.println("Estado: "+estado);
        System.out.println("Indice raza: "+indice_raza);
        System.out.println("Raza: "+raza);
    }
    //Veamos si esto no explota
    public void copiar(Ganado e)
    {
        tatuaje = e.getTatto();
        nombre = e.getnombre();
        edad = e.getedad();
        expositor = e.getexp();
        estado = e.getestado();
        indice_raza = e.getindexraza();
        raza = e.getRaza();
    }
    
    public String salida_a_archivo()
    {
        String sal=tatuaje+"-"+nombre+"-"+edad+"-"+expositor+"-"+estado+"-"+indice_raza+"-"+raza;
        return sal;
    }
}
