package countryInfo;

import country.Country;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class CountryInfoController implements Initializable {

    ObservableList<Object> countryList;
    @FXML
    private Label lblCountry;
    @FXML
    private Label lblCases;
    @FXML
    private Label lblTodayCases;
    @FXML
    private Label lblDeaths;
    @FXML
    private Label lblTodayDeaths;
    @FXML
    private Label lblRecovered;
    @FXML
    private Label lblActive;
    @FXML
    private Label lblCritical;
    @FXML
    private Label lblTotalTests;

    ObservableList<PieChart.Data> piedata;
    DecimalFormat percent = new DecimalFormat("#,###.0");
    DecimalFormat format = new DecimalFormat("###.0");

    @FXML
    private StackPane chartStack;
    @FXML
    private ImageView imgFlag;
    @FXML
    private AnchorPane con;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }

    public void getCountryInfo(Country countryinfo) {

        if (countryinfo == null) {
            return;
        }

        lblCountry.setText(countryinfo.getCountry());
        lblCases.setText(countryinfo.getCases());
        lblTodayCases.setText(countryinfo.getTodayCases());
        lblDeaths.setText(countryinfo.getDeaths());
        lblTodayDeaths.setText(countryinfo.getTodayDeaths());
        lblRecovered.setText(countryinfo.getRecovered());
        lblActive.setText(countryinfo.getActive());
        lblTotalTests.setText(countryinfo.getTotalTests());
        lblCritical.setText(countryinfo.getCritical());

        Double recovered = Double.valueOf(countryinfo.getRecovered().replace(",", ""));
        Double deaths = Double.valueOf(countryinfo.getDeaths().replace(",", ""));
        Double active = Double.valueOf(countryinfo.getActive().replace(",", ""));

        Double total = recovered + deaths + active;
        String r = percent.format((recovered / total) * 100) + "%";
        String d = percent.format((deaths / total) * 100) + "%";
        String a = percent.format((active / total) * 100) + "%";

        PieChart.Data pie1 = new PieChart.Data("Recovered (" + r + ")", recovered);
        PieChart.Data pie2 = new PieChart.Data("Deaths (" + d + ")", deaths);
        PieChart.Data pie3 = new PieChart.Data("Active (" + a + ")", active);

        piedata = FXCollections.observableArrayList();
        piedata.addAll(pie1, pie2, pie3);

        PieChart pieChart = new PieChart(piedata);
        pieChart.setLabelsVisible(true);
        pieChart.setTitle("Cases");
        pieChart.setTitleSide(Side.TOP);
        pieChart.setAnimated(true);
        chartStack.getChildren().add(pieChart);

        String countryname = countryinfo.getCountry().toLowerCase();
        String imageurl = "https://assets.thebasetrip.com/api/v2/countries/flags/" + countryname + ".png";
        Image image = new Image(imageurl, true);
        imgFlag.setImage(image);

    }

}
