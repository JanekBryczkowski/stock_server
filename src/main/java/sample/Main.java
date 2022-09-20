package sample;

import javafx.application.Application; //import for the class to extend application class
import javafx.fxml.FXMLLoader; //import necessary to load FXML files
import javafx.scene.Scene; //import necessary for scene to be operated
import javafx.scene.layout.AnchorPane; //Graphical User Interface import to operate with AnchorPane
import javafx.stage.Stage; //import necessary to operate on stages

import java.io.IOException; //needed for exception when button clicked

public class Main extends Application {

    //declaring stages and other variables
    public static Stage primaryStage;
    public static Stage loginErrorStage = new Stage();
    public static AnchorPane mainLayout;

    //start method which opens when the program is opened (at start)
    @Override
    public void start(Stage primaryStage) throws Exception {
        //execute method from Firebase controller for initializing the database
        Firebase_Controller.initDatabase();

        //update the data about admin and change status of admin in Firebase
        Firebase_Controller.getAdmin();
        Firebase_Controller.changeStatusOfAdminToFalse();

        //set primary's stage purpose and title
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Stock Application");

        //open show login method
        showLogin();
    }

    //method which opens login window
    public static void showLogin() throws IOException {
        //creates loader to load FXML file
        FXMLLoader loader = new FXMLLoader();

        //shows the location of the FXML file
        loader.setLocation(Main.class.getResource("/FXML/LoginView.fxml"));

        //loads FXML file as a mainLayout
        mainLayout = loader.load();

        //set scene and stage
        Scene scene = new Scene(mainLayout, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    //method which opens main window
    public static void showMainView() throws IOException {
        //creates loader to load FXML file
        FXMLLoader loader = new FXMLLoader();

        //shows the location of the FXML file
        loader.setLocation(Main.class.getResource("/FXML/MainView.fxml"));

        //loads FXML file as a mainLayout
        mainLayout = loader.load();

        //set scene and stage
        Scene scene = new Scene(mainLayout, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

        //if primary stage is closed then change status of admin in Firebase to false
        primaryStage.setOnCloseRequest(event -> {
            Firebase_Controller.changeStatusOfAdminToFalse();
        });
    }

    //method which opens login error window
    public static void showLoginError() throws IOException {
        //creates loader to load FXML file
        FXMLLoader loader = new FXMLLoader();

        //shows the location of the FXML file
        loader.setLocation(Main.class.getResource("/FXML/LoginErrorView.fxml"));

        //loads FXML file as a LoginErrorAnchorPane
        AnchorPane LoginErrorAnchorPane = loader.load();

        loginErrorStage.setTitle("Error");
        loginErrorStage.setAlwaysOnTop(true);

        //set scene and stage
        Scene scene = new Scene(LoginErrorAnchorPane);
        loginErrorStage.setScene(scene);
        loginErrorStage.show();
    }

    //method which closes login error window
    public static void closeLoginError() throws IOException {
        //close
        loginErrorStage.close();
    }

    //main method
    public static void main(String[] args) {
        launch(args);
    }
}
