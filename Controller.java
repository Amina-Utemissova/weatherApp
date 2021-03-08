package sample; //window style


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.sql.PreparedStatement;


public class Controller {
    //initialization of items for choice box
    ObservableList<String> SeasonList = FXCollections.observableArrayList("Winter", "Spring", "Summer", "Fall");

    //declaration of all elements in the application window
    @FXML
    private TextField city;

    @FXML
    private Button getTemp;

    @FXML
    private ChoiceBox<String> season;

    @FXML
    private Text temp1;

    @FXML
    private Text temp2;

    @FXML
    private Text temp3;

    @FXML
    private Text avgTemp;


    @FXML
    void initialize(){
        Database db = new Database();
        db.getConnection();
        if (db.connection != null) {

            //setting items for choice box
            season.setValue("Winter"); // will be displayed by default
            season.setItems(SeasonList);

            //when the button is clicked
            getTemp.setOnAction(event->{
                if (city != null) {

                    //getting values from the textarea and choice box
                    String ct = city.getText();
                    String ssn = season.getValue();

                    // table alternation: adding new column average that displays 3 digits with 1 digit precision
                    try {
                        PreparedStatement ps = db.connection.prepareStatement("ALTER TABLE "+ssn+ " ADD average decimal(3,1);");
                        ps.executeUpdate();
                        ps.close();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }

                    //setting the average to calculate the average temperature for each table
                    try {
                        if(ssn.equalsIgnoreCase("winter")){
                       PreparedStatement ps2 = db.connection.prepareStatement("update "+ssn+" set average=(temp_dec+temp_jan+temp_feb)/3;");
                        ps2.executeUpdate();
                        ps2.close();
                        }
                        else if(ssn.equalsIgnoreCase("spring")){
                            PreparedStatement ps2 = db.connection.prepareStatement("update "+ssn+" set average=(temp_march+temp_apr+temp_may)/3;");
                            ps2.executeUpdate();
                            ps2.close();
                        }
                        else if(ssn.equalsIgnoreCase("summer")){
                            PreparedStatement ps2 = db.connection.prepareStatement("update "+ssn+" set average=(temp_june+temp_july+temp_aug)/3;");
                            ps2.executeUpdate();
                            ps2.close();
                        }
                        else{
                            PreparedStatement ps2 = db.connection.prepareStatement("update "+ssn+" set average=(temp_sep+temp_oct+temp_nov)/3;");
                            ps2.executeUpdate();
                            ps2.close();
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }

                    // selection and display of all columns by city
                    try {
                        PreparedStatement ps3 = db.connection.prepareStatement("SELECT * FROM "+ssn+" where city='"+ct+"';");
                        db.rs = ps3.executeQuery();
                        while (db.rs.next()) {
                            temp1.setText("Temperature 1: "+db.rs.getInt(2));
                            temp2.setText("Temperature 2: "+ db.rs.getInt(3));
                            temp3.setText("Temperature 3: " + db.rs.getInt(4));
                            avgTemp.setText("Average Temperature: "  + db.rs.getDouble(5));
                        }
                        db.rs.close();
                        ps3.close();

                        //deleting the column average so there will be no errors when selecting the same season more than once
                        db.stmt.executeUpdate("alter table "+ssn+" drop column average;");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
});
            }

        }
    }
