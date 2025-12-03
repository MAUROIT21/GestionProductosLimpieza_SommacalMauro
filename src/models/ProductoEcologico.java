
package models;

/**
 *
 * @author mauro
 */

import java.time.LocalDate;
import java.util.Objects;

public class ProductoEcologico extends ProductoLimpieza {
    private String etiquetaEcologica;

    public ProductoEcologico(String nombreComercial, String concentracion, LocalDate fechaVencimiento, String etiquetaEcologica) {
        super(nombreComercial, concentracion, fechaVencimiento);
        this.etiquetaEcologica = etiquetaEcologica;
    }

    public String getEtiquetaEcologica() { return etiquetaEcologica; }
    public void setEtiquetaEcologica(String etiquetaEcologica) { this.etiquetaEcologica = etiquetaEcologica; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true; 
        if (o == null || getClass() != o.getClass()) return false;
        ProductoEcologico e = (ProductoEcologico) o;
        return super.equals(e) && etiquetaEcologica.equalsIgnoreCase(e.etiquetaEcologica);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), etiquetaEcologica.toLowerCase());
    }

    @Override
    public String toString() {
        return super.toString() + " [" + etiquetaEcologica + "]";
    }

    @Override
    public String getTipo() {
        return "Ecologico";
        }
    
    
}