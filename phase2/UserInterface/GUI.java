package phase2.UserInterface;

import phase2.FundHolders.*;
import phase2.Operators.*;

import java.io.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javafx.application.Application;
import javafx.fxml.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.event.*;

public class GUI extends Application implements java.io.Serializable {
    private static BankManager BM = new BankManager("BMuser", "BMpass");

    @Override
    public void start(Stage mainStage) throws Exception {
        mainStage.setTitle("ATM");
        Parent parent = FXMLLoader.load(getClass().getResource("MainMenuScene.fxml"));
        Scene mainMenuScene = new Scene(parent, 800, 600);
        mainStage.setScene(mainMenuScene);
        mainStage.show();
    }

    public static BankManager getBM() {
        return BM;
    }

    public static void updateDate(String date, File f) throws IOException {
        FileWriter fw = new FileWriter(f);
        fw.write(date);
        fw.close();
    }

    public static void main(String[] args) {
        System.out.println("current directory: " + System.getProperty("user.dir"));
        try {
            File f = new File("phase2/txtfiles/date.txt");
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("ddmmyyyy HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            updateDate(dtf.format(now), f);
        } catch (Exception e) {
            e.printStackTrace();
        }
        launch(args);
    }
}