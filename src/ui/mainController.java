package ui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class mainController {

    ArrayList<Clone> listOfClones;

    @FXML TextField cloneDateTextField;
    @FXML TextField cloneDepthTextField;
    @FXML TextField numberOfClonedHTMLTextField;
    @FXML TextField numberOfClonedCSSTextField;
    @FXML TextField numberOfClonedJSTextField;
    @FXML TextField numberOfClonedImagesTextField;
    @FXML TextField totalClonedFilesTextField;


    // the FXML annotation tells the loader to inject this variable before invoking initialize.
    @FXML
    private TreeView<String> cloneTreeView;

    // the initialize method is automatically invoked by the FXMLLoader - it's magic
    public void initialize() {
        listOfClones = new ArrayList<>();
        updateCloneList();

        cloneTreeView.getSelectionModel().selectedItemProperty().addListener( new ChangeListener() {

            @Override
            public void changed(ObservableValue observable, Object oldValue,
                                Object newValue) {

                TreeItem<String> selectedItem = (TreeItem<String>) newValue;
                Clone selectedClone = null;
                for(Clone clone : listOfClones) {
                    if (clone.url.matches(selectedItem.getValue())) {
                        selectedClone = clone;

                        System.out.print(selectedClone.url);
                        cloneDateTextField.setText(selectedClone.cloneDate);
                        cloneDepthTextField.setText(selectedClone.cloneDepth);
                        numberOfClonedHTMLTextField.setText(selectedClone.numberOfClonedHTML);
                        numberOfClonedCSSTextField.setText(selectedClone.numberOfClonedCSS);
                        numberOfClonedJSTextField.setText(selectedClone.numberOfClonedJS);
                        numberOfClonedImagesTextField.setText(selectedClone.numberOfClonedImages);
                        totalClonedFilesTextField.setText(Integer.toString(Integer.parseInt(selectedClone.numberOfClonedHTML)+Integer.parseInt(selectedClone.numberOfClonedCSS)+Integer.parseInt(selectedClone.numberOfClonedJS)+Integer.parseInt(selectedClone.numberOfClonedImages)));
                    }
                }

            }

        });
    }

    // loads some strings into the tree in the application UI.
    public void updateCloneList() {

        File choice = new File("./Clones/");
        cloneTreeView.setRoot(getNodesForDirectory(choice));

    }

    public Clone getNodesForDirectory(File directory) { //Returns a TreeItem representation of the specified directory
        Clone root = new Clone(directory.getName());
        listOfClones.add(root);
        for (File f : directory.listFiles()) {
            System.out.println("Loading " + f.getName());
            if (f.isDirectory()) { //Then we call the function recursively
                root.getChildren().add(getNodesForDirectory(f));
            } else {
                root.getChildren().add(new TreeItem<String>(f.getName()));
            }
        }
        return root;
    }

    @FXML
    private void cloneNewSiteButtonHandler(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("cloner.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Clone New Site");
            stage.setResizable(false);
            stage.setHeight(270);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
