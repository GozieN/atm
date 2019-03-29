package phase2.UserInterface;

import javafx.fxml.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.*;
import javafx.scene.*;
import javafx.event.*;
import phase2.Operators.BankAccountUser.User;

public class ViewCapabilitiesMenuController extends Menu implements java.io.Serializable {
	private String operatorType;

	@FXML
	private Label capabilities;

	public void initialize(String operatorType) {
		this.operatorType = operatorType;
		if (this.operatorType.equals("Bank Manager")) {
			this.capabilities.setText(GUI.getBM().viewCapabilities());
		} else if (this.operatorType.equals("Consultant")) {
			this.capabilities.setText(GUI.getUC().viewCapabilities());
		} else if (this.operatorType.equals("User)")) {
			this.capabilities.setText(GUI.getU().viewCapabilities());
		}
	}

	public void back(ActionEvent event) throws Exception {
		if (this.operatorType.equals("Bank Manager")) {
			Stage mainStage = (Stage)((Node)event.getSource()).getScene().getWindow();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("BankManagerInteractionsMenuScene.fxml"));
			Parent parent = loader.load();
			Scene bankManagerInteractionsMenuScene = new Scene(parent);
			mainStage.setScene(bankManagerInteractionsMenuScene);
			mainStage.show();
		} else if (this.operatorType.equals("Consultant")) {
			Stage mainStage = (Stage)((Node)event.getSource()).getScene().getWindow();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("ConsultantWorkInteractionsMenuScene.fxml"));
			Parent parent = loader.load();
			Scene consultantWorkInteractionsMenuScene = new Scene(parent);
			mainStage.setScene(consultantWorkInteractionsMenuScene);
			mainStage.show();
		} else if (this.operatorType.equals("User")) {
			// TODO: add this part - what is the prev menu for user?
		}
	}

	public void exit(ActionEvent event) throws Exception {
		super.exit(event);
	}
}