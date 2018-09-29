package models;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
/**
 *
 * @author moicsmarkez
 */
@XmlRootElement
public class ResultadoEntiti implements Serializable, Comparable{

    private String titulo;
    private String imagen_link;
    private String Precio_txt;
    private String nomTienda;
    private double precio;
    private byte[] ilogo;
     
    public ResultadoEntiti() {
    }

    public ResultadoEntiti(String titulo, String imagen_link, String Precio_txt, String nomTienda, double precio, byte[] ilogo) {
        this.titulo = titulo;
        this.imagen_link = imagen_link;
        this.Precio_txt = Precio_txt;
        this.nomTienda = nomTienda;
        this.precio = precio;
        this.ilogo = ilogo;
    }

    public byte[] getIlogo() {
        return ilogo;
    }

    public void setIlogo(byte[] ilogo) {
        this.ilogo = ilogo;
    }

    
 
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getImagen_link() {
        return imagen_link;
    }

    public void setImagen_link(String imagen_link) {
        this.imagen_link = imagen_link;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getPrecio_txt() {
        return Precio_txt;
    }

    public void setPrecio_txt(String Precio_txt) {
        this.Precio_txt = Precio_txt;
    }    

    public String getNomTienda() {
        return nomTienda;
    }

    public void setNomTienda(String nomTienda) {
        this.nomTienda = nomTienda;
    }

    @Override
    public int compareTo(Object rst) {
        int comparaPrecio =(int) ((ResultadoEntiti)rst).getPrecio();
        return ((int)this.precio)-comparaPrecio; 
    }
    
}
