/**
 * @author Brandon Watkins
 * 3/30/2021
 */
package edu.isu.cs.cs2263.todoListManager.view;

import edu.isu.cs.cs2263.todoListManager.model.state.account.AccountListState;
import edu.isu.cs.cs2263.todoListManager.storage.Read;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;

public class View extends Application {

    private static final int splashDelay = 5;

    @FXML
    TextField loginEmailTxt;
    Button loginRegisterBtn;

    @Override
    public void start(Stage primaryStage) throws Exception {
        getSplash();
        ((AccountListState) AccountListState.instance()).setAccounts(Read.readAllUserData());
    }

    /**
     * Helper class for singleton implementation
     *
     * @author Brandon Watkins
     */
    private static final class Helper {
        private static final View INSTANCE = new View();
    }

    /**
     * Gets this singleton's instance.
     *
     * @return This singleton's instance (concrete Context).
     *
     * @author Brandon Watkins
     */
    public static View instance() {
        return Helper.INSTANCE;
    }

    /**
     * Shows splash screen intro
     *
     * @return This class's splash Scene
     *
     * @author Alex Losser
     */
    public void getSplash() {
        FXMLLoader loader = new FXMLLoader();
        try {
            loader.setLocation(new File("app/src/main/resources/fxml/splash.fxml").toURI().toURL());
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);

            stage.show();
            PauseTransition delay = new PauseTransition(Duration.seconds(splashDelay));
            delay.setOnFinished(value -> {
                stage.close();
                try {
                    System.out.println("Test to see if this works");
                    login();
                } catch (Exception e) {
                    System.out.println(e);
                }
            });
            delay.play();

        } catch(IOException e) {
            System.out.println(e);
        }
    }

    /**
     * Shows login screen
     *
     * @author Alex Losser
     */
    public void login() {
        FXMLLoader loader = new FXMLLoader();
        try {
            loader.setLocation(new File("app/src/main/resources/fxml/login.fxml").toURI().toURL());
            Parent root = loader.load();
            Scene scene = new Scene(root);

            Stage stage = new Stage();
            stage.setTitle("Login");
            stage.setScene(scene);

            stage.show();
        } catch(IOException e) {
            System.out.println(e);
        }
    }

    /**
     * Shows register screen
     *
     * @author Alex Losser
     */
    public void register() {
        FXMLLoader loader = new FXMLLoader();
        try {
            loader.setLocation(new File("app/src/main/resources/fxml/register.fxml").toURI().toURL());
            Parent root = loader.load();
            Scene scene = new Scene(root);

            Stage stage = new Stage();
            stage.setTitle("Register");
            stage.setScene(scene);

            stage.show();
        } catch(IOException e) {
            System.out.println(e);
        }
    }
    /**
     * Shows an error popup with custom message
     *
     * @param msg Message to be displayed on the popup
     *
     * @author Alex Losser
     */
    public static void errorMsg(String msg) {
        FXMLLoader loader = new FXMLLoader();
        try {
            loader.setLocation(new File("app/src/main/resources/fxml/error.fxml").toURI().toURL());
            Parent root = loader.load();
            Scene scene = new Scene(root);

            //Set error message
            try {
                Text errorLabel = (Text) scene.lookup("#errorMsgLoad");
                errorLabel.setText(msg);
            } catch(Exception e) {
                System.out.println(e);
            }

            Stage stage = new Stage();
            stage.setTitle("Error!");
            stage.setScene(scene);

            stage.show();
        } catch(IOException e) {
            System.out.println(e);
        }
    }

    public void homeScreen() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(new File("app/src/main/resources/fxml/homeScreen.fxml").toURI().toURL());
        Parent root = loader.load();
        Scene scene = new Scene(root);

        Stage stage = new Stage();
        stage.setTitle("Home Screen");
        stage.setScene(scene);

        stage.show();


    }

    public void createList() {
        throw new RuntimeException("not implemented yet.");
    }



}
