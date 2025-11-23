package home;

import animatefx.animation.FadeIn;
import com.google.gson.JsonObject;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXToggleButton;
import data.CovidService;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;

public class HomeController implements Initializable {

    @FXML
    private JFXButton btnClose;
    @FXML
    private JFXButton btnClose1;
    ObservableList<XYChart.Series<String, Number>> list;

    ObservableList<PieChart.Data> piedata;
    @FXML
    private StackPane mainHome;
    @FXML
    private StackPane barchartStack;
    @FXML
    private Label lblDate;
    @FXML
    private JFXButton btnHome;
    @FXML
    private JFXButton btnCountry;
    @FXML
    private JFXButton btnInfo;
    @FXML
    private JFXButton btnSettings;
    @FXML
    private JFXButton btnExit;
    Parent root;
    @FXML
    private BorderPane mainContainer;
    @FXML
    private AnchorPane mainDashboard;
    @FXML
    ScheduledExecutorService executorService;
    @FXML
    LoadWorld task;

    JsonObject chartd;
    Preferences pref = Preferences.userNodeForPackage(HomeController.class);

    Font font = new Font("/resources/fonts/Poppins-Regular.ttf", 12);

    DecimalFormat decimalFormat = new DecimalFormat("#,###");
    DecimalFormat percent = new DecimalFormat("###.0");
    @FXML
    private Label lblCases;
    @FXML
    private Label lblRecovered;
    @FXML
    private Label lblDeaths;
    @FXML
    private Label lblErr;
    @FXML
    private ImageView barLoader;
    @FXML
    private JFXToggleButton switchLightDarkMode;
    @FXML
    private StackPane piechartStack;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Reload_Every_Five_Minutes();

    }

    public void Load_Data() {
        task = new LoadWorld();
        task.restart();
    }

    public void LoadChart(Integer cases, Integer recovered, Integer deaths) {

        CategoryAxis xaxis = new CategoryAxis();
        xaxis.setLabel("Total Cases");

        NumberAxis yaxis = new NumberAxis();
        yaxis.setLabel("Population (in millions)");

        Double total = Double.sum(cases, deaths);
        String c = percent.format((cases / total) * 100) + "%";
        String d = percent.format((deaths / total) * 100) + "%";

        XYChart.Data<String, Number> data1 = new XYChart.Data<>("Cases", cases);
        XYChart.Data<String, Number> data2 = new XYChart.Data<>("Recovered", recovered);
        XYChart.Data<String, Number> data3 = new XYChart.Data<>("Deaths", deaths);

        PieChart.Data pie1 = new PieChart.Data("Recovered (" + c + ")", recovered);
        PieChart.Data pie2 = new PieChart.Data("Deaths (" + d + ")", deaths);

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.getData().addAll(data1, data2, data3);

        list = FXCollections.observableArrayList();
        list.addAll(series);
        piedata = FXCollections.observableArrayList();
        piedata.addAll(pie1, pie2);

        BarChart<String, Number> barChart = new BarChart<>(xaxis, yaxis);
        barChart.setTitle("Coronavirus Pandemic Statistics Around the World");
        barChart.setTitleSide(Side.TOP);
        barChart.setLegendVisible(false);
        barChart.setBarGap(4);
        barChart.setData(list);
        barchartStack.getChildren().add(barChart);

        PieChart pieChart = new PieChart(piedata);
        pieChart.setLabelsVisible(true);
        pieChart.setTitle("Settled Cases");
        pieChart.setTitleSide(Side.TOP);
        pieChart.setAnimated(true);
        pieChart.setLabelLineLength(10);
        piechartStack.getChildren().add(pieChart);

    }

    @FXML
    private void Close_Home(MouseEvent event) {
        load_Close_Dialog();
    }

    @FXML
    private void Minimize_Home(MouseEvent event) {
        Stage stage = (Stage) mainHome.getScene().getWindow();
        if (stage.isIconified()) {
            stage.setIconified(false);
        } else {
            stage.setIconified(true);
        }

    }

    public void LoadPane(String path) {
        try {
            root = FXMLLoader.load(getClass().getResource(path));
            new FadeIn(root).setSpeed(4.0).play();
            mainContainer.setCenter(root);
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void Open_Home(MouseEvent event) throws IOException {
        new FadeIn(mainDashboard).setSpeed(4.0).play();
        mainContainer.setCenter(mainDashboard);
    }

    @FXML
    private void Open_Country(MouseEvent event) {
        LoadPane("/country/Country.fxml");
    }

    @FXML
    private void Open_Info(MouseEvent event) {
        LoadPane("/info/Info.fxml");
    }

    @FXML
    private void Open_Settings(MouseEvent event) {
        LoadPane("/about/About.fxml");
    }

    @FXML
    private void Exit_App(MouseEvent event) {
        load_Close_Dialog();
    }

    public void load_Close_Dialog() {

        JFXDialogLayout jFXDialogLayout = new JFXDialogLayout();
        JFXDialog fXDialog = new JFXDialog(mainHome, jFXDialogLayout, JFXDialog.DialogTransition.TOP);

        String s = "-fx-text-fill:#fff;";
        Label body = new Label("Do you want to exit?");
        body.setStyle(s);
        Label heading = new Label("Exiting");
        heading.setStyle(s);

        JFXButton ok = new JFXButton("    ok    ");
        ok.getStyleClass().add("dialog-ok-btn");
        ok.setOnMouseClicked(e -> Platform.exit());

        JFXButton cancel = new JFXButton(" cancel ");
        cancel.getStyleClass().add("dialog-cancel-btn");
        cancel.setOnMouseClicked(e -> fXDialog.close());

        jFXDialogLayout.setHeading(heading);
        jFXDialogLayout.setBody(body);
        jFXDialogLayout.setActions(ok, cancel);
        jFXDialogLayout.setStyle("-fx-background-color:#202940;");

        fXDialog.setOverlayClose(false);
        fXDialog.show();

    }

    public void Reload_Every_Five_Minutes() {
        executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(this::Load_Data, 0, 120, TimeUnit.SECONDS);
    }

    @FXML
    private void Refresh_Data(MouseEvent event) {
        Load_Data();
    }

    public class LoadWorld extends Service {

        @Override
        protected Task createTask() {
            return new Task() {
                @Override
                protected Object call() throws Exception {

                    CovidService service = new CovidService();
                    chartd = service.getAll();

                    return null;
                }
            };
        }

        @Override
        protected void running() {
            super.running();
            barLoader.setVisible(true);

            ImageView img = new ImageView("/resources/images/loading.GIF");
            img.setFitWidth(30);
            img.setPreserveRatio(true);
            lblErr.setGraphic(img);
            lblErr.setStyle("-fx-text-fill:#fff;");

            lblErr.setText("Please Wait...");
        }

        @Override
        protected void succeeded() {
            super.succeeded();
            barLoader.setVisible(false);
            barchartStack.getChildren().clear();
            piechartStack.getChildren().clear();

            Integer cases = Integer.valueOf(chartd.get("cases").toString());
            Integer deaths = Integer.valueOf(chartd.get("deaths").toString());
            Integer recovered = Integer.valueOf(chartd.get("recovered").toString());

            LoadChart(cases, recovered, deaths);

            pref.putInt("cases", cases);
            pref.putInt("recovered", recovered);
            pref.putInt("deaths", deaths);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd , hh:mm:ss a");
            LocalDateTime dateTime = LocalDateTime.now();
            lblDate.setText(dateTime.format(formatter));
            pref.put("lastdate", dateTime.format(formatter));

            lblCases.setText(String.valueOf(decimalFormat.format(cases)));
            lblRecovered.setText(String.valueOf(decimalFormat.format(recovered)));
            lblDeaths.setText(String.valueOf(decimalFormat.format(deaths)));

            lblErr.setText("Done");
            lblErr.setTextFill(Color.GREEN);
            lblErr.setGraphic(null);

        }

        @Override
        protected void failed() {
            super.failed();
            barLoader.setVisible(false);
            barchartStack.getChildren().clear();
            piechartStack.getChildren().clear();

            Integer cases = pref.getInt("cases", 0);
            Integer recovered = pref.getInt("recovered", 0);
            Integer deaths = pref.getInt("deaths", 0);

            lblDate.setText(pref.get("lastdate", "0"));

            LoadChart(cases, recovered, deaths);

            lblCases.setText(String.valueOf(decimalFormat.format(cases)));
            lblRecovered.setText(String.valueOf(decimalFormat.format(recovered)));
            lblDeaths.setText(String.valueOf(decimalFormat.format(deaths)));

            FontIcon icon = new FontIcon("gmi-wifi");
            icon.setIconSize(20);
            icon.setFill(Color.RED);

            lblErr.setText("Error fetching data. Check your Internet Connection.");
            lblErr.setTextFill(Color.WHITE);
            lblErr.setFont(font);
            lblErr.setGraphic(icon);

        }

    }

}
