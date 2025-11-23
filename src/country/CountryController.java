package country;

import com.jfoenix.controls.JFXButton;
import countryInfo.CountryInfoController;
import data.CovidService;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import org.kordamp.ikonli.javafx.FontIcon;

public class CountryController implements Initializable {

    @FXML
    private TableView<Country> tableCountry;
    @FXML
    private Label lblDate;
    @FXML
    private TableColumn<Country, String> colCountry;
    @FXML
    private TableColumn<Country, String> colCases;
    @FXML
    private TableColumn<Country, String> colDeaths;
    @FXML
    private TableColumn<Country, String> colRecovered;
    @FXML
    private TableColumn<Country, String> colActive;
    @FXML
    private TableColumn colViewMore;
    @FXML
    private ImageView imgTableBack;

    ObservableList<Country> list;
    @FXML
    private JFXButton btnRefresh;
    @FXML
    private FontIcon iconRefresh;
    @FXML
    private TextField txtSearchForCountry;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Reload_Every_Five_Minute();

        Label label = new Label("No Country Available");
        label.setStyle("-fx-text-fill:#fff;");
        tableCountry.setPlaceholder(label);

        Load_Table();

    }

    public void getTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd , hh:mm:ss a");
        LocalDateTime dateTime = LocalDateTime.now();
        lblDate.setText(dateTime.format(formatter));

    }

    public void Load_Table() {
        colCountry.setCellValueFactory(new PropertyValueFactory<>("country"));
        colCases.setCellValueFactory(new PropertyValueFactory<>("cases"));
        colRecovered.setCellValueFactory(new PropertyValueFactory<>("recovered"));
        colDeaths.setCellValueFactory(new PropertyValueFactory<>("deaths"));
        colActive.setCellValueFactory(new PropertyValueFactory<>("active"));

        load_View_More_Button();
    }

    public void load_View_More_Button() {

        Callback<TableColumn<Country, String>, TableCell<Country, String>> viewMoreCallback = (param) -> {

            TableCell<Country, String> cell = new TableCell<Country, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {

                        JFXButton viewButton = new JFXButton("Details");
                        viewButton.setMinSize(60, 28);
                        viewButton.getStyleClass().add("view-more-btn");
                        viewButton.setOnAction(event -> {
                            Country countryRow = getTableView().getItems().get(getIndex());
                            tableCountry.getSelectionModel().clearAndSelect(getIndex());

                            try {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/countryInfo/CountryInfo.fxml"));
                                StackPane stackPane = loader.load();
                                Stage stage = new Stage(StageStyle.UTILITY);
                                stage.initModality(Modality.APPLICATION_MODAL);
                                CountryInfoController controller = loader.getController();
                                controller.getCountryInfo(countryRow);
                                stage.setScene(new Scene(stackPane));
                                stage.setTitle(countryRow.getCountry());
                                stage.setResizable(false);
                                stage.show();
                            } catch (IOException ex) {
                                Logger.getLogger(CountryController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        });

                        setGraphic(viewButton);
                        setText(null);

                    }
                }
            };
            return cell;
        };

        colViewMore.setCellFactory(viewMoreCallback);

    }

    public void Load_Table_Data() {

        LoadTask loadTask = new LoadTask();
        loadTask.restart();

    }

    public void Reload_Every_Five_Minute() {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(this::Load_Table_Data, 0,300 , TimeUnit.SECONDS);
    }

    @FXML
    private void Refresh_Data(MouseEvent event) {
        Load_Table_Data();
    }

    @FXML
    private void Search_For_Country(KeyEvent event) {
        String search_key = txtSearchForCountry.getText();

        if (search_key.isEmpty() || search_key.length() == 0) {
//            tableCountry.setItems(null);
            Load_Table_Data();

        } else {

            list.forEach((countrydata) -> {
                String country = countrydata.getCountry();

                if (country.equalsIgnoreCase(search_key)) {
                    list = FXCollections.observableArrayList();
                    list.add(countrydata);
                }
                tableCountry.setItems(list);
            });

        }

    }

    private class LoadTask extends Service {

        @Override
        protected Task createTask() {
            return new Task() {
                @Override
                protected Object call() throws Exception {
                    CovidService service = new CovidService();
                    list = FXCollections.observableList(service.getAllCountry());

                    return null;
                }

            };

        }

        @Override
        protected void running() {
            super.running();

            ImageView imageView = new ImageView("/resources/images/loading.GIF");
            imageView.setFitWidth(40);
            imageView.setPreserveRatio(true);

            Label labela = new Label("Please wait....");
            labela.setStyle("-fx-text-fill:#fff;");
            labela.setGraphic(imageView);
            labela.setContentDisplay(ContentDisplay.TOP);

            tableCountry.setPlaceholder(labela);
//            rotateTransition.play();
        }

        @Override
        protected void succeeded() {
            super.succeeded();
            tableCountry.setItems(list);
            getTime();
        }

        @Override
        protected void failed() {
            super.failed();

            JFXButton button = new JFXButton("Try Again");
            button.setStyle("-fx-background-color:#2aa5d1; -fx-text-fill:#fff;");
            button.setMinSize(100, 35);
            button.setOnAction(e -> {

                Load_Table_Data();
            });

            Label labela = new Label("Error Fetching Countries.");
            labela.setStyle("-fx-text-fill:#fff;");

            VBox pane = new VBox(labela, button);
            pane.setStyle("-fx-background-color:transparent;");
            pane.setMinSize(200, 200);
            pane.setAlignment(Pos.CENTER);
            pane.setSpacing(15);

            tableCountry.setPlaceholder(pane);

        }

    }

}
