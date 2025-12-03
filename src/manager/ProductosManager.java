
package manager;

/**
 *
 * @author mauro
 */

import excepciones.ProductoInvalidoException;
import models.ProductoEcologico;
import models.ProductoLimpieza;
import models.ProductoQuimico;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class ProductosManager {
    private final List<ProductoLimpieza> productos = new ArrayList<>();
    private final String archivoInventario = "productos.json";

    public List<ProductoLimpieza> getProductos() {
        return productos;
    }

    // CRUD
    
    public void agregar(ProductoLimpieza p) throws ProductoInvalidoException {
        validar(p);
        if (productos.contains(p)) {
            throw new ProductoInvalidoException("Producto repetido.");
        }
        productos.add(p);
        guardarInventarioJSON();
    }

    public void eliminar(ProductoLimpieza p) throws ProductoInvalidoException {
        if (!productos.remove(p)) {
            throw new ProductoInvalidoException("No se encontró el producto a eliminar.");
        }
        guardarInventarioJSON();
    }

    public void modificar(ProductoLimpieza original, ProductoLimpieza actualizado) throws ProductoInvalidoException {
        validar(actualizado);
        int idOriginal = productos.indexOf(original);
        if (idOriginal < 0) throw new ProductoInvalidoException("No se encontró el producto a modificar.");
        if (!original.equals(actualizado) && productos.contains(actualizado)) {
            throw new ProductoInvalidoException("No se puede modidicar: producto ya existente");
        }
        productos.set(idOriginal, actualizado);
        guardarInventarioJSON();
    }

    public List<ProductoLimpieza> proximosAVencer(int dias) {
        List<ProductoLimpieza> lista = new ArrayList<>();
        for (ProductoLimpieza p : productos) {
            if (p.isProximoAVencer(dias)) {lista.add(p);}
        }
        return lista;
    }

    // VALIDACIONES
    
    private void validar(ProductoLimpieza p) throws ProductoInvalidoException {
    if (p == null) throw new ProductoInvalidoException("Producto nulo.");

    if (p.getNombreComercial() == null || p.getNombreComercial().trim().equals("")) {
        throw new ProductoInvalidoException("Nombre comercial requerido.");
    }

    if (p.getConcentracion() == null || p.getConcentracion().trim().equals("")) {
        throw new ProductoInvalidoException("Concentración requerida.");
    }

    if (p.getFechaVencimiento() == null) {
        throw new ProductoInvalidoException("Fecha de vencimiento no ingresada");
    }

    if (p.isVencido()) {
        throw new ProductoInvalidoException("El producto ya tiene fecha vencida");
    }

    if (p instanceof ProductoQuimico q) {
        if (q.getAdvertencia() == null || q.getAdvertencia().trim().equals("")) {
            throw new ProductoInvalidoException("Advertencia necesaria para producto químico.");
        }
    }

    if (p instanceof ProductoEcologico e) {
        if (e.getEtiquetaEcologica() == null || e.getEtiquetaEcologica().trim().equals("")) {
            throw new ProductoInvalidoException("Etiqueta ecológica necesaria para producto ecológico.");
        }
    }
}

    // Persistencia JSON
    private void guardarInventarioJSON() {
    try (Writer writer = new FileWriter("productos.json")) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        gson.toJson(productos, writer);
        System.out.println(" Inventario guardado en JSON");
    } catch (IOException e) {
        System.out.println("Error al guardar: " + e.getMessage());
    }
}

private void cargarInventarioJSON() {
    try (Reader reader = new FileReader("productos.json")) {
        Gson gson = new Gson();
        ProductoLimpieza[] array = gson.fromJson(reader, ProductoLimpieza[].class);
        productos.clear();
        if (array != null) {
            productos.addAll(Arrays.asList(array));
        }
        System.out.println(" Inventario cargado desde JSON");
    } catch (IOException e) {
        System.out.println("Error al cargar el archivo JSON: " + e.getMessage());
    }
}


}