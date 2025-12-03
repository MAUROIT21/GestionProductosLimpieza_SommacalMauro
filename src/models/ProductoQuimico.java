
package models;

/**
 *
 * @author mauro
 */

import java.time.LocalDate;
import java.util.Objects;

public class ProductoQuimico extends ProductoLimpieza {
    private String advertencia;

    public ProductoQuimico(String nombreComercial, String concentracion, LocalDate fechaVencimiento, String advertencia) {
        super(nombreComercial, concentracion, fechaVencimiento);
        this.advertencia = advertencia;
    }

    public String getAdvertencia() { return advertencia; }
    public void setAdvertencia(String advertencia) { this.advertencia = advertencia; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true; 
        if (o == null || getClass() != o.getClass()) return false;
        ProductoQuimico q = (ProductoQuimico) o;
        return super.equals(q) && advertencia.equalsIgnoreCase(q.advertencia);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), advertencia.toLowerCase());
    }

    @Override
    public String toString() {
        return super.toString() + " [" + advertencia + "]";
    }

    @Override
    public String getTipo() {
        return "Quimico";
    }
}