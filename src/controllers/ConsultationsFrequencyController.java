package controllers;

import additionals.DataCount;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class ConsultationsFrequencyController {

	@FXML
	private BarChart<String, Integer> barChart;

	@FXML
	private CategoryAxis x;

	@FXML
	private NumberAxis y;

	/** 
	 * Fills the bar chart with information in consulationsHistoryAl
	 * @param event
	 */
	public void initialize() {

		for(int i = 1; i < 13; i++) {
			int count = 0;		
			for (int j = 0; j < application.Main.consultationsHistoryAl.getConsultationsAl().size(); j++) {	
				String dateString = application.Main.consultationsHistoryAl.getConsultation(j).getConsultationDate();
				String [] date = dateString.split("-");		int monthnum = Integer.parseInt(date[1]);
				if(application.Main.monthsOfTheYear[monthnum].equals(application.Main.monthsOfTheYear[i])) {
					count++;
				}
			}
			DataCount temp = new DataCount(application.Main.monthsOfTheYear[i], count);
			application.Main.consultationsCountAl.getRdCount().add(temp);
		}
		XYChart.Series<String , Integer> series1 = new XYChart.Series<String, Integer>();

		series1.setName("Total Consultations");

		for (int i = 0; i < application.Main.consultationsCountAl.getRdCount().size(); i++) {
			series1.getData().add(new XYChart.Data<>(application.Main.consultationsCountAl.getRdCount().get(i).getDocname(),application.Main.consultationsCountAl.getRdCount().get(i).getCount()));
		}

		barChart.getData().addAll(series1);
	}
	/** 
	 * Returns to the Home Page scene.
	 */
	@FXML
	void mItemReturnToHomePage(ActionEvent event) {
		application.Main.mainstage.setScene(application.Main.homePage);

	}

}
