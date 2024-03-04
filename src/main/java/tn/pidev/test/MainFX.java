
//package tn.pidev.test;
//import javafx.application.Application;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.scene.Node;
//import javafx.scene.Scene;
//import javafx.scene.control.Label;
//import javafx.scene.control.ListCell;
//import javafx.scene.control.ListView;
//import javafx.scene.control.Pagination;
//import javafx.scene.layout.HBox;
//import javafx.scene.layout.VBox;
//import javafx.stage.Stage;
//import javafx.util.Callback;
//import tn.pidev.entities.ParkingEntities.Parking;
//import tn.pidev.services.ParkingServices.ParkingService;
//
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Arrays;
//
//public class MainFX extends Application {
//
//    private static final int ITEMS_PER_PAGE = 5;
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//
//    @Override
//    public void start(Stage primaryStage) {
//        ParkingService ps = new ParkingService();
//        List<Parking> parkings = new ArrayList<>();
//        try {
//            parkings= ps.recuperer();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//        ObservableList<Parking> data1 = FXCollections.observableArrayList(parkings);
//
//        ListView<Parking> listid = new ListView<>(data1);
//
//        Pagination pagination = new Pagination((int) Math.ceil((double) data1.size() / ITEMS_PER_PAGE), 0);
//        pagination.setPageFactory(new Callback<Integer, Node>() {
//            @Override
//            public Node call(Integer pageIndex) {
//                int fromIndex = pageIndex * ITEMS_PER_PAGE;
//                int toIndex = Math.min(fromIndex + ITEMS_PER_PAGE, data1.size());
//                listid.setItems(FXCollections.observableArrayList(data1.subList(fromIndex, toIndex)));
//                listid.setCellFactory(new Callback<ListView<Parking>, ListCell<Parking>>() {
//                    @Override
//                    public ListCell<Parking> call(ListView<Parking> param) {
//                        return new ListCell<Parking>() {
//                            @Override
//                            protected void updateItem(Parking parking, boolean empty) {
//                                super.updateItem(parking, empty);
//                                if (empty || parking == null) {
//                                    setText(null);
//                                    setGraphic(null);
//                                } else {
//                                    // Assuming this code is inside a method where you have access to the rendezVous object
//                                    String parkingName = parking.getNom();
//                                    String addresse = parking.getAddresse();
//                                    int nbPlacesLibres = parking.getNbPlaceMax() - parking.getNbPlaceOcc();
//                                    String etat = parking.getEtat();
//                                    // Create an HBox to hold the details
//                                    HBox hbox = new HBox();
//                                    hbox.setSpacing(20); // Adjust spacing as needed
//
//                                    // Add details to the HBox with styled Labels
//                                    Label nomPrenomDoctorLabel = new Label("Parking  " + parkingName);
//                                    nomPrenomDoctorLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: black; -fx-pref-width:   250; -fx-pref-height:   21");
//
//                                    Label dateLabel = new Label(addresse);
//                                    dateLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: black; -fx-pref-width:   200; -fx-pref-height:   21");
//
//                                    Label specialiteLabel = new Label(Integer.toString(nbPlacesLibres));
//                                    specialiteLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: black; -fx-pref-width:   150; -fx-pref-height:   21");
//
//                                    Label adresseLabel = new Label(etat);
//                                    adresseLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: black; -fx-pref-width:   118; -fx-pref-height:   21");
//
//                                    // Add the Labels to the HBox
//                                    hbox.getChildren().addAll(nomPrenomDoctorLabel);
//
//                                    // Assuming this is inside a ListCell or similar where you can set the graphic
//                                    setGraphic(hbox);
//                                }
//                            }
//                        };
//                    }
//                });
//                return new VBox(listid);
//            }
//        });
//
//        VBox root = new VBox(pagination);
//        Scene scene = new Scene(root, 300, 200);
//        primaryStage.setScene(scene);
//        primaryStage.setTitle("String Pagination Example");
//        primaryStage.show();
//    }
//}
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ParkingResources/AfficherParkingg.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setTitle("Gérer parkings");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
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
//}
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