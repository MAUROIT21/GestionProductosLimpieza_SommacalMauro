package manager;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import models.ProductoLimpieza;
import models.ProductoQuimico;
import models.ProductoEcologico;
import excepciones.ProductoInvalidoException;

public class PrincipalViewController implements Initializable {

    @FXML private ListView<ProductoLimpieza> listView;
    @FXML private TextField txtNombre;
    @FXML private TextField txtConcentracion;
    @FXML private DatePicker dateVencimiento;
    @FXML private ComboBox<String> comboTipo;
    @FXML private TextField txtAdvertencia;
    @FXML private TextField txtEtiqueta;

    private ProductosManager manager;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        manager = new ProductosManager();
        comboTipo.getItems().addAll("Químico", "Ecológico");

        // Al inicio deshabilitamos ambos campos
        txtAdvertencia.setDisable(true);
        txtEtiqueta.setDisable(true);

        // Listener para habilitar el campo correcto según el tipo
        comboTipo.setOnAction(e -> {
            if ("Químico".equals(comboTipo.getValue())) {
                txtAdvertencia.setDisable(false);
                txtEtiqueta.setDisable(true);
            } else if ("Ecológico".equals(comboTipo.getValue())) {
                txtAdvertencia.setDisable(true);
                txtEtiqueta.setDisable(false);
            }
        });

        refrescarLista();
    }

    private void refrescarLista() {
        listView.getItems().setAll(manager.getProductos());
    }

    private ProductoLimpieza construirDesdeCampos() throws ProductoInvalidoException {
        String nombre = txtNombre.getText();
        String concentracion = txtConcentracion.getText();
        LocalDate fecha = dateVencimiento.getValue();
        String tipo = comboTipo.getValue();

        if (tipo == null) {
            throw new ProductoInvalidoException("Debes seleccionar un tipo de producto.");
        }

        if (tipo.equals("Químico")) {
            String advertencia = txtAdvertencia.getText();
            return new ProductoQuimico(nombre, concentracion, fecha, advertencia);
        } else {
            String etiqueta = txtEtiqueta.getText();
            return new ProductoEcologico(nombre, concentracion, fecha, etiqueta);
        }
    }

    @FXML
    private void onAgregar() {
        try {
            ProductoLimpieza p = construirDesdeCampos();
            manager.agregar(p);
            refrescarLista();
            mostrarInfo("Producto agregado correctamente.");
        } catch (ProductoInvalidoException e) {
            mostrarError(e.getMessage());
        }
    }

    @FXML
    private void onEliminar() {
        try {
            ProductoLimpieza sel = listView.getSelectionModel().getSelectedItem();
            if (sel == null) {
                mostrarError("Selecciona un producto para eliminar.");
                return;
            }
            manager.eliminar(sel);
            refrescarLista();
            mostrarInfo("Producto eliminado correctamente.");
        } catch (ProductoInvalidoException e) {
            mostrarError(e.getMessage());
        }
    }

    @FXML
    private void onModificar() {
        try {
            ProductoLimpieza original = listView.getSelectionModel().getSelectedItem();
            if (original == null) {
                mostrarError("Selecciona un producto para modificar.");
                return;
            }
            ProductoLimpieza actualizado = construirDesdeCampos();
            manager.modificar(original, actualizado);
            refrescarLista();
            mostrarInfo("Producto modificado correctamente.");
        } catch (ProductoInvalidoException e) {
            mostrarError(e.getMessage());
        }
    }

    private void mostrarInfo(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}