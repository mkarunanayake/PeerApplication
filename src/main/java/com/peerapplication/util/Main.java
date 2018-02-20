package com.peerapplication.util;

import com.peerapplication.repository.TableRepository;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;
import com.peerapplication.messenger.PeerHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application{

    private static int systemUserID;
    private static int accountType;
    private static UIUpdater registerUpdater;
    private static UIUpdater loginUpdater;
    private static UIUpdater threadUpdater;
    private static UIUpdater answerUpdater;
    private static PasswordEncrypter pwEncrypter;

    public static int getSystemUserID() {
        return systemUserID;
    }

    public static void setSystemUserID(int systemUserID) {
        Main.systemUserID = systemUserID;
    }

    public static int getAccountType() {
        return accountType;
    }

    public static void setAccountType(int accountType) {
        Main.accountType = accountType;
    }

    public static UIUpdater getRegisterUpdater() {
        return registerUpdater;
    }

    public static void setRegisterUpdater(UIUpdater registerUpdater) {
        Main.registerUpdater = registerUpdater;
    }

    public static UIUpdater getLoginUpdater() {
        return loginUpdater;
    }

    public static void setLoginUpdater(UIUpdater loginUpdater) {
        Main.loginUpdater = loginUpdater;
    }

    public static UIUpdater getThreadUpdater() {
        return threadUpdater;
    }

    public static void setThreadUpdater(UIUpdater threadUpdater) {
        Main.threadUpdater = threadUpdater;
    }

    public static UIUpdater getAnswerUpdater() {
        return answerUpdater;
    }

    public static void setAnswerUpdater(UIUpdater answerUpdater) {
        Main.answerUpdater = answerUpdater;
    }

    public static PasswordEncrypter getPwEncrypter() {
        return pwEncrypter;
    }

    public static void setPwEncrypter(PasswordEncrypter pwEncrypter) {
        Main.pwEncrypter = pwEncrypter;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource("/views/login.fxml"));
        Scene scene = new Scene(parent, 1035, 859);
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });
        primaryStage.setScene(scene);
        primaryStage.setTitle("Login");
        primaryStage.show();
    }

    public static void main(String[] args){

        int port = 25030;
        if (args.length>1){
            port = Integer.parseInt(args[1]);
        }
        PeerHandler.setup(port);

        pwEncrypter = new PasswordEncrypter();

        TableRepository tableRepo = new TableRepository();
        tableRepo.createTables();

        TableRepository tableRepository = new TableRepository();
        tableRepository.createTables();

        launch(args);

    }
}
