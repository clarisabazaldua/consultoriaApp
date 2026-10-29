package controllers;

import additionals.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;

public class ReferringDoctorsController {
	/** 
	 * Fills up the pie chart with
	 * information in the doctors ArrayList
	 * @param event
	 */
    @FXML
    private PieChart referringDoctorsChart;

    ObservableList<Data> data;
    public void initialize () {
    	for(int i = 0; i < application.Main.doctorsAl.getDocsAl().size(); i++ ) {
    		int count = 0;
    		for ( int j = 0; j < application.Main.patientsAl.getPatientAl().size(); j++) {
    			if (application.Main.doctorsAl.getDoctor(i).getName().equalsIgnoreCase(application.Main.patientsAl.getPatient(j).getDocReferred())) {
    				count++;
    			}
    		}
    		DataCount rdCount = new DataCount(application.Main.doctorsAl.getDoctor(i).getName(), count);
    		application.Main.doccountAl.getRdCount().add(rdCount);
    	}
    	
    	System.out.println("Referring doc");
    	for (int i =0; i < application.Main.doccountAl.getRdCount().size(); i++) {
    		System.out.println(application.Main.doccountAl.getRdCount().get(i));
    	}
    	
    	data = FXCollections.observableArrayList();
    	
    	for (int i = 0; i < application.Main.doccountAl.getRdCount().size(); i++) {
    		data.add(new Data(application.Main.doccountAl.getRdCount().get(i).getDocname(),application.Main.doccountAl.getRdCount().get(i).getCount()));
    	}
    	referringDoctorsChart.setData(data);
    	
    	
    	
    }
    /** 
	 * Changes Scene to the home Pages Scene.
	 * @param event
	 */
    @FXML
    void mItemReturnToHomePage(ActionEvent event) {
    	application.Main.mainstage.setScene(application.Main.homePage);
    }
   
}
