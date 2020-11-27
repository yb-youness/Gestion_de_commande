package sample.controller.StatistiqueController;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import sample.model.Produit;
import java.net.URL;
import java.util.ResourceBundle;


public class StatistiqueController  implements Initializable {
    @FXML
    private BorderPane c;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        PieChart pieChart = new PieChart();  Produit p =new Produit();
        pieChart.setData(p.chrats());
        pieChart.setLegendSide(Side.RIGHT);
        pieChart.setStartAngle(30);
        FadeTransition f = new FadeTransition(Duration.seconds(4), pieChart);
        f.setFromValue(0);
        f.setToValue(1);
        f.play();
        for (PieChart.Data data :  pieChart.getData()){
            data.nameProperty().set(data.getName()+ " = "+(int)data.getPieValue()); }
        pieChart.setPrefHeight(120);
        pieChart.setMinHeight(320);
        pieChart.setMaxHeight(500);
        pieChart.setPrefSize(616,320 );
        pieChart.setMinSize(400, 400);
        pieChart.setMaxSize(700, 680);
         c.setCenter(pieChart);

    }

}
