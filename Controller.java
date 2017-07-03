package sample;

import com.google.api.services.drive.Drive;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import sample.dataUser.*;

import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.Session.*;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Optional;

import static sample.Quickstart.getDriveService;

public class Controller {


    @FXML
    TextField userId;
    @FXML
    PasswordField userPass;

    @FXML
    Button logId;


    public void login(ActionEvent event) {

        System.out.println("On essaye de se connecter ");
// On utilise les logins pour voir si on peut se connecter sans erreur
        // Si Thrown on alerte l utilisateur que mot de passe faux // A ajouter  ;


        //if success , Are you okay with this ?
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Dialogue de confirmation");
        alert.setHeaderText("Voulez vous continuer dans la procedure ?\nVous êtes entierement responsable des données que vous partagez  "); //    alert.setContentText("Are you ok with this?");


        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {

            // Creation d'un objet DataUser qui va etre serialiser par la suite
            File data = new File( "data");
            data.mkdir();
            File images = new File("data/images");
            images.mkdir();



            DataUser dataUser = new DataUser();


            String path = "data/dataUser1.ser";

           Thread threadSerializable = new Thread() {
                public void run() {
                    while (true) {

                        if (dataUser.getBool1()  == false || dataUser.getBool2() == false) {
                            try {
                                System.out.println("#1");
                                sleep(5000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        } else {
                            System.out.println("@");

                            try {
                                FileOutputStream fos = new FileOutputStream("data/dataUser1.ser");
                                ObjectOutputStream oos = new ObjectOutputStream(fos);
                                oos.writeObject(dataUser);
                                oos.flush();
                                oos.close();
                            } catch (IOException e) {
                                System.out.println(e.getStackTrace() + "\n" + e.getMessage());

                                System.out.println("Erreur a gerer ");

                            }

                            break;
                        }

                    }

            Quickstart.upload(data.getPath(),"dataUser1.ser");
                }}
                ; // Fin du thread anonyme

            threadSerializable.start();


                AuthRestFb auth1 = new AuthRestFb(dataUser);
            auth1.myThread.setDaemon(true);
            auth1.myThread.start();
            auth1.setLogins(userId.getText(), userPass.getText());

        AuthScrap  auth2 = new AuthScrap(dataUser);
        auth2.myThread.setDaemon(true);
            auth2.myThread.start();
            auth2.setLogins(userId.getText(), userPass.getText());



        }
        else {
           // Main.setStage((Scene)null);


            // ... user chose CANCEL or closed the dialog
        }

    }



}

//Le boulot de restFB : A implementer grace aux fonctions
