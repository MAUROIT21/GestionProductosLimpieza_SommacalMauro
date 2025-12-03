
package models;

/**
 *
 * @author mauro
 */

import java.time.LocalDate;
import java.util.Objects;

public abstract class ProductoLimpieza {
    private String nombreComercial;
    private String concentracion;
    private LocalDate fechaVencimiento;

    public ProductoLimpieza(String nombreComercial, String concentracion, LocalDate fechaVencimiento) {
        this.nombreComercial = nombreComercial;
        this.concentracion = concentracion;
        this.fechaVencimiento = fechaVencimiento;
    }

    public boolean isVencido() {
        return fechaVencimiento != null && fechaVencimiento.isBefore(LocalDate.now());
    }

    public boolean isProximoAVencer(long dias) {
        if (fechaVencimiento == null) return false;
        LocalDate hoy = LocalDate.now();
        LocalDate limite = hoy.plusDays(dias);
        return !isVencido() && !fechaVencimiento.isAfter(limite);
    }

    public String getNombreComercial() { return nombreComercial; }
    public void setNombreComercial(String nombreComercial) { this.nombreComercial = nombreComercial; }

    public String getConcentracion() { return concentracion; }
    public void setConcentracion(String concentracion) { this.concentracion = concentracion; }

    public LocalDate getFechaVencimiento() { return fechaVencimiento; }
    public void setFechaVencimiento(LocalDate fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductoLimpieza p = (ProductoLimpieza) o;

        return nombreComercial.equalsIgnoreCase(p.nombreComercial)
                && concentracion.equalsIgnoreCase(p.concentracion)
                && fechaVencimiento.equals(p.fechaVencimiento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                nombreComercial.toLowerCase(),
                concentracion.toLowerCase(),
                fechaVencimiento
            );
    }

    public abstract String getTipo();

    @Override
    public String toString() {
        return nombreComercial + " | " + concentracion + " | vence: " + fechaVencimiento;
    }
}
