package com.peerapplication.controller;

import com.peerapplication.messenger.PeerHandler;
import com.peerapplication.util.Main;
import com.peerapplication.util.UIUpdater;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Pagination;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import message.LogoutMessage;
import message.Message;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable, UIUpdater{

    @FXML
    private TabPane homeTabs;

    @FXML
    private Tab latestThreadTab;

    @FXML
    private Pagination paginationLtstThreads;

    @FXML
    private TableView<?> threadTable;

    @FXML
    private TableColumn<?, ?> colAnswersLatest;

    @FXML
    private TableColumn<?, ?> colTitleLatest;

    @FXML
    private Tab myThreadTab;

    @FXML
    private Pagination paginationMyThreads;

    @FXML
    private TableView<?> myThreadsTable;

    @FXML
    private TableColumn<?, ?> colAnswersMyTh;

    @FXML
    private TableColumn<?, ?> colTitleMyTh;

    @FXML
    private Tab notificationTab;

    @FXML
    private Pagination paginationNotifications;

    @FXML
    private TableView<?> notificationTable;

    @FXML
    private TableColumn<?, ?> colAnswersNotification;

    @FXML
    private TableColumn<?, ?> colTitleNotification;

    @FXML
    private ImageView userImage;

    @FXML
    private Label lblName;

    @FXML
    private Label lblPostedThreads;

    @FXML
    private Label lblAnsweredThreads;

    @FXML
    private Label lblRegDate;

    @FXML
    private Button btnPostThread;

    @FXML
    private Label lblHeading;

    @FXML
    private Button btnHome;

    @FXML
    private Button btnThreads;

    @FXML
    private MenuButton btnMenu;

    @FXML
    private MenuItem menuItemSettings;

    @FXML
    private MenuItem menuItemLogout;

    @FXML
    void btnHomeClicked(MouseEvent event) {

    }

    @FXML
    void btnTagsClicked(MouseEvent event) {

    }

    @FXML
    void btnThreadsClicked(MouseEvent event) {

    }

    @FXML
    void headingClicked(MouseEvent event) {

    }

    @FXML
    void logout(ActionEvent event) throws IOException {
        LogoutMessage logoutMessage = new LogoutMessage(Main.getSystemUserID());
        PeerHandler.getBsHandler().logout(logoutMessage);
        Stage stage = (Stage) btnPostThread.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/views/login.fxml"));
        stage.setScene( new Scene(root, 1035, 859));
        stage.show();
    }

    @FXML
    void openSettings(ActionEvent event) {

    }

    @FXML
    void postThread(MouseEvent event) {

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        notificationTab.setOnSelectionChanged(ActionEvent -> {
            if (notificationTab.isSelected()){
                System.out.println("Notification");
            }
        });

        myThreadTab.setOnSelectionChanged(ActionEvent -> {
            if (myThreadTab.isSelected()){
                System.out.println("MyThread");
            }
        });

        latestThreadTab.setOnSelectionChanged(ActionEvent -> {
            if (latestThreadTab.isSelected()){
                System.out.println("LatestThread");
            }
        });
    }

    @Override
    public void updateUI(Message message) {

    }
}
