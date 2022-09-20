package sample.LoginViewController;

import javafx.animation.Animation; //Graphical User Interface import needed for the animation of clock
import javafx.animation.KeyFrame; //Graphical User Interface import needed for the animation of clock
import javafx.animation.Timeline; //Graphical User Interface import needed for the animation of clock
import javafx.event.EventHandler; //Graphical User Interface import needed to change colors of buttons when entered
import javafx.fxml.FXML; //Graphical User Interface import needed to create connection to FXML files
import javafx.fxml.Initializable; //Graphical User Interface import needed to create initializable method
import javafx.scene.control.Button; //Graphical User Interface import needed to operate with buttons
import javafx.scene.control.Label; //Graphical User Interface import needed to operate with labels
import javafx.scene.control.PasswordField; //Graphical User Interface import needed to operate with passwordFields
import javafx.scene.control.TextField; //Graphical User Interface import needed to operate with textFields
import javafx.scene.input.MouseEvent; //Graphical User Interface import needed to detect mouse's events
import javafx.util.Duration; //Graphical User Interface import needed for the animation of clock
import sample.Firebase_Controller;
import sample.Main;

import java.io.IOException; //needed for exception when button clicked
import java.net.URL; //needed for the initializable method
import java.time.LocalDateTime; //needed for using current data about date and hour
import java.util.ResourceBundle; //needed for the initializable method

public class LoginViewController implements Initializable {

    //declaring variables
    private Main main;

    //declaring FXML variables
    @FXML
    private Label HourLabel;

    @FXML
    private Label MinuteLabel;

    @FXML
    private Label SecondLabel;

    @FXML
    private Button OKButton;

    @FXML
    public TextField LoginTextField;

    @FXML
    private PasswordField PasswordTextField;

    //initialize method starts when this window is opened
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //creating a clock on the top-right side
        //setting refreshing every 0.05 seconds
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.05), event -> {
            //algorithm for setting hour number
            if (LocalDateTime.now().getHour() < 10) {
                HourLabel.setText("0" + LocalDateTime.now().getHour());
            } else {
                HourLabel.setText(String.valueOf(LocalDateTime.now().getHour()));
            }

            //algorithm for setting minute number
            if (LocalDateTime.now().getMinute() < 10) {
                MinuteLabel.setText("0" + LocalDateTime.now().getMinute());
            } else {
                MinuteLabel.setText(String.valueOf(LocalDateTime.now().getMinute()));
            }

            //algorithm for setting second number
            if (LocalDateTime.now().getSecond() < 10) {
                SecondLabel.setText("0" + LocalDateTime.now().getSecond());
            } else {
                SecondLabel.setText(String.valueOf(LocalDateTime.now().getSecond()));
            }
        }));
        //setting timeline to be played indefinitely
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        //set style when mouse is on the area of the ok button
        OKButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                OKButton.setStyle("-fx-background-color: #bfa005; -fx-border-color: #000000; -fx-border-radius: 8; -fx-background-radius: 8;");
            }
        });

        //set style when mouse is out of the area of the ok button
        OKButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                OKButton.setStyle("-fx-background-color: #ffdc00; -fx-border-color: #000000; -fx-border-radius: 8; -fx-background-radius: 8;");
            }
        });
    }

    //method of going to the main window
    @FXML
    private void goHome() throws IOException {
        //check if login and password is correct as in the Firebase
        if (LoginTextField.getText().equals(Firebase_Controller.globalAdmin.getLogin()) && PasswordTextField.getText().equals(Firebase_Controller.globalAdmin.getPassword())) {
            //login and password are the same as in the Firebase
            Main.showMainView();
            Firebase_Controller.changeStatusOfAdminToTrue();
        } else if (LoginTextField.getText().equals(null) || LoginTextField.getText().equals("") || PasswordTextField.getText().equals(null) || PasswordTextField.getText().equals("")) {
            //some error exists
            Main.showLoginError();
        } else {
            //some error exists
            Main.showLoginError();
        }
    }
}
