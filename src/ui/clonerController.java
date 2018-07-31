package ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.net.MalformedURLException;
import java.net.URL;

public class clonerController {

    @FXML TextField urlTextField;
    @FXML Label urlErrorLabel;
    @FXML Spinner<Integer> depthSpinner;
    @FXML CheckBox cloneOutsideDomainCheckBox;
    @FXML CheckBox downloadExternalResourcesCheckBox;
    @FXML Button startCloningButton;
    @FXML ProgressIndicator progressIndicator;

    // the initialize method is automatically invoked by the FXMLLoader - it's magic
    public void initialize() {

        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);

        depthSpinner.setValueFactory(valueFactory);

        progressIndicator.setVisible(false);
    }

    @FXML
    private void startCloningButtonHandler(ActionEvent event) {
        if (startCloningButton.getText().matches("Start Cloning")) {
            try {
                //ToDo add protocol checer and corrector
                URL cloneURL = new URL(urlTextField.getText());
                startCloningButton.setText("Cancel Cloning");
                urlErrorLabel.setVisible(false);
                progressIndicator.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
                progressIndicator.setVisible(true);
            } catch (MalformedURLException e) {
                urlErrorLabel.setText("Invalid url");
                urlErrorLabel.setVisible(true);
            }
        } else {
            startCloningButton.setText("Start Cloning");
            progressIndicator.setProgress(0);
            progressIndicator.setVisible(false);
        }
    }
}
