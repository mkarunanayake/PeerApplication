package com.peerapplication.controller;

import com.peerapplication.handler.BSHandler;
import com.peerapplication.handler.ForumUpdateHandler;
import com.peerapplication.util.ControllerUtility;
import com.peerapplication.util.PasswordEncrypter;
import com.peerapplication.util.UIUpdateHandler;
import com.peerapplication.util.UIUpdater;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import message.Message;
import message.RegisterMessage;
import message.RequestStatusMessage;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

public class SignupController implements Initializable, UIUpdater {

    @FXML
    private Label statusLabel;

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Button btnSignup;

    @FXML
    private Button btnLogin;

    @FXML
    private PasswordField txtCnfmPassword;


    @FXML
    void confirmSignup(MouseEvent event) throws NoSuchAlgorithmException, UnsupportedEncodingException {                // signup confirmation
        String username = txtUsername.getText().trim();
        String password = txtPassword.getText().trim();
        String cnfmPassword = txtCnfmPassword.getText().trim();
        if (!(username.isEmpty()) && !(password.isEmpty()) && !(cnfmPassword.isEmpty())) {                              // validate password
            if (username.length() < 8 || username.length() > 20) {
                statusLabel.setText("Username should have 8-20 characters!");
                txtUsername.clear();
            } else if (!username.matches("[a-zA-Z0-9]+")) {                                                       // validate username
                statusLabel.setText("Username should contain only alpha-numeric characters!");
                txtUsername.clear();
            } else if (password.length() < 8 || password.length() > 20) {                                               // validate password length
                statusLabel.setText("Password should have 8-20 characters!");
                txtCnfmPassword.clear();
                txtPassword.clear();
            } else if (password.contains("[-*/|&^+ ]+")) {
                statusLabel.setText("Password shouldn't contain empty spaces or -*/|&^+ characters!");
            } else if (!password.equals(cnfmPassword)) {
                statusLabel.setText("Password mismatch!");
                txtPassword.clear();
                txtCnfmPassword.clear();
            } else {                                                                                                    // signup user
                RegisterMessage regMsg = new RegisterMessage();
                String pw = PasswordEncrypter.SHA1(password);                                                           // encrypt password
                regMsg.setUsername(username);
                regMsg.setPassword(pw);
                BSHandler.signup(regMsg);
                System.out.println("Reg");
            }
        } else {
            statusLabel.setText("All fields are required!");
        }


    }

    @FXML
    void openLogin(MouseEvent event) throws IOException {
        Stage stage = (Stage) btnLogin.getScene().getWindow();
        ControllerUtility.login();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        UIUpdateHandler.refreshUpdater();
        UIUpdateHandler.setRegisterUpdater(this);
    }

    @Override
    public void updateUI(Message message) {                                                                             // update UI from UIUpdateHandler
        if (message instanceof RequestStatusMessage) {
            RequestStatusMessage reqMessage = (RequestStatusMessage) message;
            Platform.runLater(new Runnable() {                                                                          // send register message to BS
                @Override
                public void run() {
                    if (!(reqMessage.getStatus().equals("Success"))) {
                        statusLabel.setText(reqMessage.getStatus());
                    } else {
                        Stage stage = (Stage) btnLogin.getScene().getWindow();
                        ForumUpdateHandler.requestUpdate();
                        try {
                            ControllerUtility.openRegister();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
    }
}

