package major_project.view;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import major_project.controller.AppController;

public class ReportForm extends GridPane {
    private final AppController controller;

    private TextField toEmailField;
    private TextField toNameField;
    private TextField fromEmailField;
    private TextField fromNameField;
    private TextField subjectField;

    private Button submitButton;

    private Stage newWindow;
    
    public ReportForm(AppController controller) {
        this.controller = controller;

        this.setAlignment(Pos.CENTER);
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(25, 25, 25, 25));

        initFields();
        initNewWindow();

        submitButton.setOnAction((e) -> submitButtonAction(e));
    }

    private void initNewWindow() {
        Scene scene = new Scene(this, AppWindow.WIDTH * .50, AppWindow.HEIGHT * .50);
        newWindow = new Stage();
        newWindow.setTitle("Send Report");
        newWindow.setScene(scene);
        newWindow.initModality(Modality.WINDOW_MODAL);
        newWindow.initOwner(controller.view().scene().getWindow());
        newWindow.show();
    }

    private void initFields() {
        Label headerLabel = new Label("Report form");
        headerLabel.setPadding(new Insets(10));
        headerLabel.setFont(new Font(30));
        this.add(headerLabel, 0, 0, 2, 1);

        Label toLabel = new Label("To:");
        this.add(toLabel, 0, 1);

        toEmailField = new TextField("Email");
        this.add(toEmailField, 1, 1);

        toNameField = new TextField("Name");
        this.add(toNameField, 2, 1);

        Label fromLabel = new Label("From:");
        this.add(fromLabel, 0, 2);

        fromEmailField = new TextField("Email");
        this.add(fromEmailField, 1, 2);

        fromNameField = new TextField("Name");
        this.add(fromNameField, 2, 2);

        Label subjectLabel = new Label("Subject:");
        this.add(subjectLabel, 0, 3);

        subjectField = new TextField();
        this.add(subjectField, 1, 3);

        submitButton = new Button("Submit");
        this.add(submitButton, 3, 4);
    }

    private void submitButtonAction(ActionEvent event) {
        for (Node n : this.getChildren()) {
            if (n.getClass() == TextField.class) {
                if (((TextField) n).getText().isEmpty() || ((TextField) n).getText().isBlank()) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setHeaderText("At least one field is empty. Please fill in all fields");
                    alert.showAndWait();
                    return;
                }
            }
        }

        String toEmail = toEmailField.getText();
        String toName = toNameField.getText();
        String fromEmail = fromEmailField.getText();
        String fromName = fromNameField.getText();
        String subject = subjectField.getText();
        String content = controller.view().viewer().getCurrentView().container().getEmailOutput();

        if (controller.model().sendReport(toEmail, toName, fromEmail, fromName, subject, content)) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setHeaderText("Reporting successful");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText("Reporting unsuccessful");
            alert.showAndWait();
        }
        newWindow.close();
    }
}
