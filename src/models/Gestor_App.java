
package models;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author mauro
 */

public class Gestor_App extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load((getClass().getResource("/views/PrincipalView.fxml")));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Inventario de Productos");
        stage.show();
    }

    public static void main(String[] args) {
        //launch(args);
        Application.launch();

    }
}

