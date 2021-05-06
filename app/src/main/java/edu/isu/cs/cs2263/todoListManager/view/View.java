/**
 * @author Brandon Watkins
 * 3/30/2021
 */
package edu.isu.cs.cs2263.todoListManager.view;

import edu.isu.cs.cs2263.todoListManager.controller.Controller;
import edu.isu.cs.cs2263.todoListManager.model.state.SystemState;
import edu.isu.cs.cs2263.todoListManager.model.state.account.AccountListState;
import edu.isu.cs.cs2263.todoListManager.model.state.account.AccountLoginState;
import edu.isu.cs.cs2263.todoListManager.observer.Observable;
import edu.isu.cs.cs2263.todoListManager.observer.Observer;
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

public class View extends Application implements Observer {

    private static final int splashDelay = 5;

    @FXML
    public static Stage primaryStage;

    @FXML
    public static Stage secondaryStage;

    @FXML
    public static Stage errorStage;

    @FXML
    TextField loginEmailTxt;
    Button loginRegisterBtn;

    @FXML
    public static void positionSecondaryStage() {
        double xCurrentSize = primaryStage.getWidth();
        double yCurrentSize = primaryStage.getHeight();
        double xCurrentPos = primaryStage.getX();
        double yCurrentPos = primaryStage.getY();

        double xNewSize = secondaryStage.getWidth();
        double yNewSize = secondaryStage.getHeight();

        double x = xCurrentPos + (xCurrentSize / 2) - (xNewSize / 2);
        double y = yCurrentPos + (yCurrentSize / 2) - (yNewSize / 2);

        secondaryStage.setX(x);
        secondaryStage.setY(y);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        getSplash();
        ((AccountListState) AccountListState.instance()).setAccounts(Read.readAllUserData());
        ((SystemState) SystemState.instance()).addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        // For each state, do whatever it needs to do to update the view accordingly.
        switch (arg.getClass().getSimpleName()) {
            case "AccountCreateState":
                register();
                break;
            case "AccountInfoState":
                //profile();
                break;
            case "AccountListState":
                //accountList();
                break;
            case "AccountLoginState":
                login();
                break;
            case "AccountUpdateState":
                //updateAccount();
                break;
            case "SectionCreateState":
                //createSection();
                break;
            case "SectionInfoState":
                //section();
                break;
            case "SectionUpdateState":
                //updateSection();
                break;
            case "TaskCreateState":
                //createTask();
                break;
            case "TaskInfoState":
                //task();
                break;
            case "TaskUpdateState":
                //updateTask();
                break;
            case "TaskListCreateState":
                //createTaskList();
                break;
            case "TaskListInfoState":
                //taskList();
                homeScreen();
                break;
            case "TaskListUpdateState":
                //updateTaskList();
                break;
            default:

                break;
        }
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
                    ((SystemState) SystemState.instance()).setState(AccountLoginState.instance());
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

            primaryStage = stage;
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

            //Stage stage = new Stage();
            //stage.setTitle("Register");
            //stage.setScene(scene);

            //stage.show();

            secondaryStage = new Stage();
            secondaryStage.setTitle("Register");
            secondaryStage.setScene(scene);

            secondaryStage.show();
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
            Boolean showingSecondary = false;
            if (secondaryStage != null && secondaryStage.isShowing()) showingSecondary = true;

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

            if (errorStage != null) {
                errorStage.close();
            }

            errorStage = new Stage();
            errorStage.setTitle("Error");
            errorStage.setScene(scene);

            Boolean finalShowingSecondary = showingSecondary; // needed to be right before the lambda for it to work.
            errorStage.setOnCloseRequest(e -> {
                if (secondaryStage != null && finalShowingSecondary) secondaryStage.show();
                else if (primaryStage != null) primaryStage.show();
                if (errorStage != null) errorStage.close();
            });

            errorStage.show();
            if (primaryStage != null) primaryStage.hide();
            if (secondaryStage != null) secondaryStage.hide();
        } catch(IOException e) {
            System.out.println(e);
        }
    }

    public void homeScreen() {
        FXMLLoader loader = new FXMLLoader();
        try {
            loader.setLocation(new File("app/src/main/resources/fxml/homeScreen.fxml").toURI().toURL());
            Parent root = loader.load();
            Scene scene = new Scene(root);

            //Stage stage = new Stage();
            //stage.setTitle("Home Screen");
            //stage.setScene(scene);

            //stage.show();

            primaryStage.setTitle("Home");
            Controller controllerRef = loader.getController();
            controllerRef.populateHomeScrollPane();
            primaryStage.setScene(scene);

            //Controller.instance().populateTaskListAccordion();


            primaryStage.show();

        } catch(IOException e) {
            System.out.println(e);
        }






    }
    /**
     * Shows list creation screen
     *
     * @author Liam Andrus
     */
    public void createList() {
        FXMLLoader loader = new FXMLLoader();
        try {
            loader.setLocation(new File("app/src/main/resources/fxml/createList.fxml").toURI().toURL());
            Parent root = loader.load();
            Scene scene = new Scene(root);

            //Stage stage = new Stage();
            //stage.setTitle("Register");
            //stage.setScene(scene);

            //stage.show();

            secondaryStage = new Stage();
            secondaryStage.setTitle("Create List");
            secondaryStage.setScene(scene);

            secondaryStage.show();
        } catch(IOException e) {
            System.out.println(e);
        }
    }
    /**
     * Shows section creation screen
     *
     * @author Liam Andrus
     */
    public void createSection() {
        FXMLLoader loader = new FXMLLoader();
        try {
            loader.setLocation(new File("app/src/main/resources/fxml/createSection.fxml").toURI().toURL());
            Parent root = loader.load();
            Scene scene = new Scene(root);

            //Stage stage = new Stage();
            //stage.setTitle("Register");
            //stage.setScene(scene);

            //stage.show();

            secondaryStage = new Stage();
            secondaryStage.setTitle("Create Section");
            secondaryStage.setScene(scene);

            secondaryStage.show();
        } catch(IOException e) {
            System.out.println(e);
        }
    }
    /**
     * Shows task creation screen
     *
     * @author Liam Andrus
     */
    public void createTask() {
        FXMLLoader loader = new FXMLLoader();
        try {
            loader.setLocation(new File("app/src/main/resources/fxml/createTask.fxml").toURI().toURL());
            Parent root = loader.load();
            Scene scene = new Scene(root);

            secondaryStage = new Stage();
            secondaryStage.setTitle("Create Task");

            //Get controller to populate choice box
            Controller controllerRef = loader.getController();
            controllerRef.populatePrioCb();

            secondaryStage.setScene(scene);
            secondaryStage.show();
        } catch(IOException e) {
            System.out.println(e);
        }
    }
}
