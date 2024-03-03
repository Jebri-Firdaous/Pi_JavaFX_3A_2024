package tn.pidev.test;

import com.esri.arcgisruntime.mapping.view.MapView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainFX extends Application {
    private MapView mapView;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherParkingsss.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setTitle("Gérer parkings");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    /*ListView<Label> list = new ListView<Label>();
    ObservableList<Label> data = FXCollections.observableArrayList(
            new Label("123"), new Label("45678999"));

    @Override
    public void start(Stage stage) {
        VBox box = new VBox();
        Scene scene = new Scene(box, 200, 200);
        stage.setScene(scene);
        stage.setTitle("ListViewExample");
        box.getChildren().addAll(list);
        VBox.setVgrow(list, Priority.ALWAYS);

        list.setItems(data);

        list.setCellFactory(new Callback<ListView<Label>, ListCell<Label>>() {


                                @Override
                                public ListCell<Label> call(ListView<Label> list) {
                                    ListCell<Label> cell = new ListCell<Label>() {
                                        @Override
                                        public void updateItem(Label item, boolean empty) {
                                            super.updateItem(item, empty);
                                            //if (item != null) {
                                            //   setItem(item);
                                            //}
                                            setGraphic(item);
                                        }
                                    };

                                    return cell;
                                }
                            }
        );

        stage.show();
    }*/
}
/*
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainFX extends Application {
    static class XCell extends ListCell<String> {
        HBox hbox = new HBox();
        Label label = new Label("");
        Pane pane = new Pane();
        Button button = new Button("Del");
        Button button2 = new Button("Mod");

        public XCell() {
            super();

            hbox.getChildren().addAll(label, pane, button, button2);
            HBox.setHgrow(pane, Priority.ALWAYS);
            button.setOnAction(event -> getListView().getItems().remove(getItem()));
            button2.setOnAction(event -> getListView().getItems().remove(getItem()));
        }

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            setText(null);
            setGraphic(null);

            if (item != null && !empty) {
                label.setText(item);
                setGraphic(hbox);
            }
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        StackPane pane = new StackPane();
        Scene scene = new Scene(pane, 300, 150);
        primaryStage.setScene(scene);
        ObservableList<String> list = FXCollections.observableArrayList(
                "Item 1", "Item 2", "Item 3", "Item 4");
        ListView<String> lv = new ListView<>(list);
        lv.setCellFactory(param -> new XCell());
        pane.getChildren().add(lv);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}*/
//        package tn.pidev.test;
//
//import com.esri.arcgisruntime.ArcGISRuntimeEnvironment;
//import com.esri.arcgisruntime.mapping.ArcGISMap;
//import com.esri.arcgisruntime.mapping.BasemapStyle;
//import com.esri.arcgisruntime.mapping.Viewpoint;
//import com.esri.arcgisruntime.mapping.view.MapView;
//import javafx.application.Application;
//import javafx.scene.Scene;
//import javafx.stage.Stage;
//
//public class MainFX extends Application {
//    private MapView mapView;
//
//    @Override
//    public void start(Stage primaryStage) {
//        ArcGISRuntimeEnvironment.setApiKey("AAPK35cf4b021e7a4c019b63243490179ac2jBL4IBgUxMIrLCpGR5HmtaB-FMYQ85qBAkLF6T5MDlW9AIDXn06SI6mVpyKvGmuy");
//
//        mapView = new MapView();
//
//        ArcGISMap map = new ArcGISMap(BasemapStyle.ARCGIS_NAVIGATION);
//        mapView.setMap(map);
//
//        mapView.setViewpointAsync(new Viewpoint(34.027, -118.805, 72223.819286));
//
//        Scene scene = new Scene(mapView);
//        primaryStage.setScene(scene);
//        primaryStage.setTitle("Map View");
//        primaryStage.setWidth(800);
//        primaryStage.setHeight(700);
//        primaryStage.show();
//    }
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//}