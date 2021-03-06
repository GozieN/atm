package phase2.UserInterface;

import javafx.fxml.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.event.*;
import phase2.Operators.BankAccountUser.User;

public class ConsultantUseOptionsMenuController extends Menu implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private User user;

	public void initialize(User user) {
		this.user = user;
	}

	public void personal(ActionEvent event) throws Exception {
		Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("UserInteractionsMenuScene.fxml"));
		Parent parent = loader.load();
		Scene userInteractionsMenuScene = new Scene(parent);
		UserInteractionsMenuController controller = loader.getController();
		controller.initialize(this.user);
		mainStage.setScene(userInteractionsMenuScene);
		mainStage.show();
	}

	public void work(ActionEvent event) throws Exception {
		Stage mainStage = (Stage)((Node)event.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("ConsultantWorkInteractionsMenuScene.fxml"));
		Parent parent = loader.load();
		Scene consultantWorkInteractionsMenuScene = new Scene(parent);
		mainStage.setScene(consultantWorkInteractionsMenuScene);
		mainStage.show();
	}

	public void back(ActionEvent event) throws Exception {
		Stage mainStage = (Stage)((Node)event.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("ConsultantLoginMenuScene.fxml"));
		Parent parent = loader.load();
		Scene consultantLoginMenuScene = new Scene(parent);
		mainStage.setScene(consultantLoginMenuScene);
		mainStage.show();
	}

	public void exit(ActionEvent event) throws Exception {
		super.exit(event);
	}
}