package splash;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import onboarding.OnboardingController;

public class SplashController implements Initializable {

    Double xcoordinate, ycoordinate;
    @FXML
    private AnchorPane mainSplash;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Load_Onboarding();

    }



    public void Load_Onboarding() {

        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                Thread.sleep(5000);
                return null;
            }
        };
        task.setOnSucceeded(e -> {
            try {
//                Check whether to load onboarding or not
                String root = "/home/Home.fxml";

                try {
                    File p = new File("C:\\ProgramData\\CoronaTracker\\board.json");
                    if (Files.exists(p.toPath())) {
                        root = "/home/Home.fxml";
                    } else {
                        root = "/onboarding/Onboarding.fxml";

                        p.getParentFile().mkdirs();
                        p.createNewFile();
                    }
                } catch (IOException ex) {
                    Logger.getLogger(OnboardingController.class.getName()).log(Level.SEVERE, null, ex);
                }

                //close Splash UI
              ((Stage) mainSplash.getScene().getWindow()).close();
             

                //Open onboarding UI
                Parent onboarding = FXMLLoader.load(getClass().getResource(root));
                Stage stage = new Stage(StageStyle.UNDECORATED);
                Scene scene = new Scene(onboarding);
                stage.setScene(scene);
                stage.getIcons().add(new Image("/resources/images/logo2.png"));
                scene.getStylesheets().add(getClass().getResource("/resources/css/main.css").toExternalForm());

                //get dimensions of the scene
                scene.setOnMousePressed(e1 -> {
                    xcoordinate = e1.getSceneX();
                    ycoordinate = e1.getSceneY();
                });

                //move screen on mouse move
                scene.setOnMouseDragged(e2 -> {
                    stage.setX(e2.getScreenX() - xcoordinate);
                    stage.setY(e2.getScreenY() - ycoordinate);
                });

                stage.show();

            } catch (IOException ex) {
                Logger.getLogger(SplashController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
        new Thread(task).start();

    }

}
