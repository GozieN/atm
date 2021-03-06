package phase2.UserInterface.AccountMenus.SummarizeAccountsMenus;

import phase2.FundStores.*;

import javafx.fxml.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.TextArea;
import javafx.event.*;
import phase2.Operators.BankAccountUser.User;
import phase2.UserInterface.MainMenu.Menu;

public class SingleAccountSummaryMenuController extends Menu implements java.io.Serializable {
	private User user;
	private String operatorType;

	@FXML
	private TextArea singleAccountSummary;

	public void initialize(User user, Account account, String operatorType) {
		this.user = user;
		this.operatorType = operatorType;
		this.singleAccountSummary.setText(this.user.singleAccountSummary(account));
	}

	public void back(ActionEvent event) throws Exception {
		Stage mainStage = (Stage)((Node)event.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("AccountsSummaryOptionsMenuScene.fxml"));
		Parent parent = loader.load();
		Scene accountsSummaryOptionsMenuScene = new Scene(parent);
		AccountsSummaryOptionsMenuController controller = loader.getController();
		controller.initialize(this.user, this.operatorType);
		mainStage.setScene(accountsSummaryOptionsMenuScene);
		mainStage.show();
	}

	public void exit(ActionEvent event) throws Exception {
		super.exit(event);
	}
}