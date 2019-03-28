package phase2.UserInterface;

import javafx.fxml.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.*;
import javafx.scene.*;
import javafx.event.*;
import phase2.Operators.BankAccountUser.User;

public class UserContractMenuController extends Menu implements java.io.Serializable {
	private String username;
	private String password;

	@FXML
	private Label contract;
	@FXML
	private Label endStatus;

	public void initialize(String username, String password) {
		this.username = username;
		this.password = password;
		this.contract.setText(GUI.getU().viewContract());
	}

	public void agree(ActionEvent event) throws Exception {
		if (!(this.username == null) && !(this.password == null)) {
			GUI.getBM().createUser(this.username, this.password);
			this.endStatus.setText("your user account creation request is being processed");
			this.username = null;
			this.password = null;
		} else {
			this.endStatus.setText("your user account creation request is already being processed");
		}
	}

	public void back(ActionEvent event) throws Exception {
		Stage mainStage = (Stage)((Node)event.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("RequestNewUserAccountCreationMenuScene.fxml"));
		Parent parent = loader.load();
		Scene requestNewUserAccountCreationMenuScene = new Scene(parent);
		mainStage.setScene(requestNewUserAccountCreationMenuScene);
		mainStage.show();
	}

	public void exit(ActionEvent event) throws Exception {
		super.exit(event);
	}
}