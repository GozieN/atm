package phase2.UserInterface;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javafx.application.Application;
import javafx.fxml.*;
import javafx.stage.*;
import javafx.scene.*;
import phase2.Operators.BankWorker.BankManager;
import java.io.Serializable;

public class GUI extends Application implements Serializable {
    private static BankManager BM = new BankManager("BMuser", "BMpass");

    @Override
    public void start(Stage mainStage) throws Exception {
        mainStage.setTitle("ATM");
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("MainMenuScene.fxml"));
		Parent parent = loader.load();
		Scene mainMenuScene = new Scene(parent);
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