/**
 * @author Brandon Watkins
 * 3/30/2021
 */
package edu.isu.cs.cs2263.todoListManager.view;

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
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;

public class View extends Application {

    @FXML
    TextField loginEmailTxt;
    Button loginRegisterBtn;

    @Override
    public void start(Stage primaryStage) throws Exception {
        getSplash();
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
    public void getSplash() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(new File("app/src/main/resources/fxml/splash.fxml").toURI().toURL());
        Parent root = loader.load();
        Scene scene = new Scene(root);

        Stage stage = new Stage();
        stage.setScene(scene);

        stage.show();

        PauseTransition delay = new PauseTransition(Duration.seconds(5));
        delay.setOnFinished(value -> {
            stage.close();
            try {
                login();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        delay.play();


    }

    /**
     * Shows login screen
     *
     * @return This class's login Scene
     *
     * @author Alex Losser
     */
    public void login() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(new File("app/src/main/resources/fxml/login.fxml").toURI().toURL());
        Parent root = loader.load();
        Scene scene = new Scene(root);

        Stage stage = new Stage();
        stage.setTitle("Login");
        stage.setScene(scene);

        stage.show();
    }

    /**
     * Shows register screen
     *
     * @return This class's register Scene
     *
     * @author Alex Losser
     */
    public void register() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(new File("app/src/main/resources/fxml/register.fxml").toURI().toURL());
        Parent root = loader.load();
        Scene scene = new Scene(root);

        Stage stage = new Stage();
        stage.setTitle("Register");
        stage.setScene(scene);

        stage.show();
    }

    public void notImplemented() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(new File("app/src/main/resources/fxml/register.fxml").toURI().toURL());
        Parent root = loader.load();
        Scene scene = new Scene(root);

        Stage stage = new Stage();
        stage.setTitle("Register");
        stage.setScene(scene);

        stage.show();
    }

}
