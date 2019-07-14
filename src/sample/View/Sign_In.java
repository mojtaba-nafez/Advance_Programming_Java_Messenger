package sample.View;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.stage.Window;
import sample.LisstenToMassage;
import sample.Main;

import java.util.concurrent.TimeUnit;

public class Sign_In extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Registration Form JavaFX Application");
        GridPane gridPane = createRegistrationFormPane();
        addUIControls(gridPane, primaryStage);
        Scene scene = new Scene(gridPane, 800, 900);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void addUIControls(GridPane gridPane, Stage primaryStage) {
        Label headerLabel = new Label("Sign In");
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        gridPane.add(headerLabel, 0, 0, 2, 1);
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(20, 0, 20, 0));
        // Add Name Label

        Label userNameLable = new Label("UserName: ");
        gridPane.add(userNameLable, 0, 0);
        // Add Email Text Field
        TextField userNameField = new TextField();
        userNameField.setPrefHeight(40);
        gridPane.add(userNameField, 1, 0);


        // Add Password Label
        Label passwordLabel = new Label("Password : ");
        gridPane.add(passwordLabel, 0, 1);

        // Add Password Field
        PasswordField passwordField = new PasswordField();
        passwordField.setPrefHeight(40);
        gridPane.add(passwordField, 1, 1);

        Hyperlink changePage = new Hyperlink("Click here");
        changePage.setOnAction(event -> {
            Main.number = 0;
            primaryStage.close();
            try {
                Main main = new Main();
                main.start(primaryStage);
            } catch (Exception e) {
                System.out.println("Mojjtaba");
                e.printStackTrace();
            }
            return;
        });

        TextFlow flow = new TextFlow(new Text("Don't have an account? "), changePage);
        flow.setPadding(new Insets(120));
        gridPane.add(flow, 1, 7);

        // Add Submit Button
        Button submitButton = new Button("Submit");
        submitButton.setPrefHeight(40);
        submitButton.setDefaultButton(true);
        submitButton.setPrefWidth(100);
        gridPane.add(submitButton, 0, 6, 2, 1);
        GridPane.setHalignment(submitButton, HPos.CENTER);
        GridPane.setMargin(submitButton, new Insets(20, 0, 20, 0));

        submitButton.setOnAction(event -> {

            if (userNameField.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Please enter your username");
                return;
            }
            if (passwordField.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Please enter pauseForCorrectAnswerInSignIn password");
                return;
            }

            String Massage = "SignIn" + "^" + passwordField.getText() + "^" + userNameField.getText();
            Main main = new Main();
            main.SendMassage(Massage);

            while (LisstenToMassage.pauseForCorrectAnswerInSignIn ==0){
            //    System.out.println(45);
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            LisstenToMassage.pauseForCorrectAnswerInSignIn =0;

            if(LisstenToMassage.SignInOk==0){
                showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "This Account dose not exist!!!!");
                LisstenToMassage.SignInOk=1;
                return;
            }
            //showAlert(Alert.AlertType.CONFIRMATION, gridPane.getScene().getWindow(), "Registration Successful!", "Welcome ");
            primaryStage.close();
            Main.number = 2;
            try {
                main.start(primaryStage);
            } catch (Exception e) {
                System.out.println("Mojjtaba");
                e.printStackTrace();
            }
            return;
        });
    }

    public GridPane createRegistrationFormPane() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(40, 40, 40, 40));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        ColumnConstraints columnOneConstraints = new ColumnConstraints(100, 100, Double.MAX_VALUE);
        columnOneConstraints.setHalignment(HPos.RIGHT);
        ColumnConstraints columnTwoConstrains = new ColumnConstraints(200, 200, Double.MAX_VALUE);
        columnTwoConstrains.setHgrow(Priority.ALWAYS);
        gridPane.getColumnConstraints().addAll(columnOneConstraints, columnTwoConstrains);
        return gridPane;
    }

    private void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }
}