package tn.esprit.entities.gestionMedecin;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

public class StethoscopeModel extends Group {
    public StethoscopeModel() {
        // Placeholder for the stethoscope model
        Box box = new Box(100, 100, 100);
        box.setMaterial(new PhongMaterial(Color.BLUE));
        getChildren().add(box);
    }
}
