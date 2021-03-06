package com.peerapplication.controller;

import com.peerapplication.handler.UserHandler;
import com.peerapplication.model.User;
import com.peerapplication.util.ControllerUtility;
import com.peerapplication.util.SystemUser;
import com.peerapplication.validator.UserValidator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RegisterController implements Initializable {

    @FXML
    private ImageView userImage;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtEmail;

    @FXML
    private Button btnRegister;

    @FXML
    private Label statusLabel;

    private FileChooser fileChooser;

    private BufferedImage bufferedImage;

    private UserValidator userValidator;

    private File file;

    @FXML
    void register(MouseEvent event) throws IOException {                                                                // user registration.
        String name = txtName.getText().trim();
        String email = txtEmail.getText().trim();
        User user = new User(SystemUser.getSystemUserID(), name, email);
        String validity = userValidator.validate(user);                                                                 // validate user
        if (validity.equals("Success")) {                                                                               // check user validity
            if (file != null) {                                                                                         // get image file
                user.setUserImage(ImageIO.read(file));
                user.setImageURL(String.valueOf(user.getUserID()));
            }
            user.setRegisterTime(new Date(System.currentTimeMillis()).getTime());
            user.setLastProfileUpdate(user.getRegisterTime());
            user.saveUser();                                                                                            // save user
            ExecutorService userService = Executors.newSingleThreadExecutor();
            userService.execute(new Runnable() {                                                                        // send user information to others.
                @Override
                public void run() {
                    UserHandler.postUser(user);
                }
            });
            Stage stage = (Stage) btnRegister.getScene().getWindow();
            ControllerUtility.openHome();
        } else {
            statusLabel.setText(validity);
        }
    }

    @FXML
    void selectImage(MouseEvent event) throws IOException {                                                             // image selection
        Stage stage = (Stage) userImage.getScene().getWindow();
        file = fileChooser.showOpenDialog(stage);                                                                       // file chooser to select image
        if (file != null) {
            Image image = new Image(file.toURI().toString());
            userImage.setImage(image);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {                                                    // initialize register controller.
        userValidator = UserValidator.getUserValidator();
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPEG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
        fileChooser.setTitle("Select Profile Picture");
    }
}
