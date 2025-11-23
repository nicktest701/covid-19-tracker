package onboarding;

import animatefx.animation.FadeIn;
import animatefx.animation.SlideInLeft;
import animatefx.animation.SlideInRight;
import com.jfoenix.controls.JFXButton;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.kordamp.ikonli.javafx.FontIcon;

public class OnboardingController implements Initializable {

    @FXML
    private Pane paneOnboard1;
    @FXML
    private Pane paneOnboard2;
    @FXML
    private Pane paneOnboard3;
    @FXML
    private JFXButton btnNext;
    @FXML
    private JFXButton btnPrevious;
    @FXML
    private StackPane mainOnboarding;

    Stage window;
    Double xcoordinate, ycoordinate;

    private FontIcon iconClose;
    @FXML
    private Pane paneOnboardGetStarted;
    @FXML
    private JFXButton btnGetStarted;
    @FXML
    private JFXButton btnClose;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        btnPrevious.setVisible(false);

    }

    @FXML
    private void Move_To_Next_Board(MouseEvent event) {

        if (paneOnboard1.isVisible()) {
            paneOnboard2.setVisible(true);
            new SlideInRight(paneOnboard2).setSpeed(2.5).play();
            paneOnboard1.setVisible(false);
            paneOnboard3.setVisible(false);
            paneOnboardGetStarted.setVisible(false);
            btnPrevious.setVisible(true);

        } else if (paneOnboard2.isVisible()) {

            paneOnboard3.setVisible(true);
            new SlideInRight(paneOnboard3).setSpeed(2.5).play();
            paneOnboard2.setVisible(false);
            paneOnboard1.setVisible(false);
            paneOnboardGetStarted.setVisible(false);
            btnPrevious.setVisible(true);

        } else {
            paneOnboardGetStarted.setVisible(true);
            new FadeIn(paneOnboardGetStarted).play();
            paneOnboard3.setVisible(false);
            paneOnboard2.setVisible(false);
            paneOnboard1.setVisible(false);
            btnNext.setVisible(false);
            btnPrevious.setVisible(false);

        }
    }

    @FXML
    private void Move_To_Previous_Board(MouseEvent event) {

        if (paneOnboard1.isVisible()) {

            paneOnboard3.setVisible(true);
            new SlideInLeft(paneOnboard3).setSpeed(2.5).play();
            paneOnboard1.setVisible(false);
            paneOnboard2.setVisible(false);
            paneOnboardGetStarted.setVisible(false);

        } else if (paneOnboard2.isVisible()) {

            paneOnboard1.setVisible(true);
            new SlideInLeft(paneOnboard1).setSpeed(2.5).play();
            paneOnboard2.setVisible(false);
            paneOnboard3.setVisible(false);
            paneOnboardGetStarted.setVisible(false);
            btnPrevious.setVisible(false);

        } else {

            paneOnboard2.setVisible(true);
            new SlideInLeft(paneOnboard2).setSpeed(2.5).play();
            paneOnboard3.setVisible(false);
            paneOnboard1.setVisible(false);
            paneOnboardGetStarted.setVisible(false);

        }

    }

    @FXML
    private void Open_Home_Page(MouseEvent event) throws IOException {
        flag_Onboarding();
        ((Stage) mainOnboarding.getScene().getWindow()).close();

        Parent root = FXMLLoader.load(getClass().getResource("/home/Home.fxml"));
        Scene scene = new Scene(root);
        window = new Stage(StageStyle.UNDECORATED);
        window.setTitle("Splash");
        window.setScene(scene);
        window.getIcons().add(new Image("/resources/images/logo2.png"));
        scene.setFill(Color.TRANSPARENT);
        scene.getStylesheets().add(getClass().getResource("/resources/css/main.css").toExternalForm());

        //get dimensions of the scene
        scene.setOnMousePressed(e -> {
            xcoordinate = e.getSceneX();
            ycoordinate = e.getSceneY();
        });

        //move screen on mouse move
        scene.setOnMouseDragged(e2 -> {
            window.setX(e2.getScreenX() - xcoordinate);
            window.setY(e2.getScreenY() - ycoordinate);
        });

        window.show();

    }

    @FXML
    private void Close_Onboarding(MouseEvent event) {

        ((Stage) mainOnboarding.getScene().getWindow()).close();

    }

    public void flag_Onboarding() { 
        FileWriter fileWriter = null;
        try {
            File p = new File("C:\\ProgramData\\CoronaTracker\\board.json");
            fileWriter = new FileWriter(p);
            if (Files.exists(p.toPath())) {

                fileWriter.write("{\"name\":\"tracker\"}");

            }
        } catch (IOException ex) {
            Logger.getLogger(OnboardingController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fileWriter.close();
            } catch (IOException ex) {
                Logger.getLogger(OnboardingController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}
