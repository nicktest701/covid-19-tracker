package covid.tracker;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class CovidTracker extends Application {

    Double xcoordinate, ycoordinate;

    @Override
    public void start(Stage primaryStage) throws IOException {

//        Parent root = FXMLLoader.load(getClass().getResource("/home/Home.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("/splash/Splash.fxml"));
        Scene scene = new Scene(root);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setTitle("Splash");
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image("/resources/images/logo2.png"));
        scene.setFill(Color.TRANSPARENT);
        scene.getStylesheets().add(getClass().getResource("/resources/css/main.css").toExternalForm());

        //get dimensions of the scene
        scene.setOnMousePressed(e -> {
            xcoordinate = e.getSceneX();
            ycoordinate = e.getSceneY();
        });

        //move screen on mouse move
        scene.setOnMouseDragged(e2 -> {
            primaryStage.setX(e2.getScreenX() - xcoordinate);
            primaryStage.setY(e2.getScreenY() - ycoordinate);
        });

        primaryStage.show();
        
        

    }

    public static void main(String[] args) {
        launch(args);
    }

}
