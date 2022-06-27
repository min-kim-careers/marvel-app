package major_project.view;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextInputDialog;

public class PopularityChecker {
    private final int minThreshold = 3;
    private final int maxThreshold = 50;

    private int comicCountThreshold;

    public PopularityChecker() {
        while (true) {
            TextInputDialog textInput = new TextInputDialog();
            textInput.setHeaderText("Please enter a number from 3 to 50 for popularity threshold");

            Optional<String> input = textInput.showAndWait();

            if (input.isEmpty()) {
                Alert dialogClosedError = new Alert(AlertType.ERROR);
                dialogClosedError.setHeaderText("Operation aborted. Goodbye.");
                dialogClosedError.showAndWait();
                System.exit(0);
            }

            if (input.get().trim().isEmpty()) {
                Alert emptyInputError = new Alert(AlertType.ERROR);
                emptyInputError.setHeaderText("Please enter some value to proceed");
                emptyInputError.showAndWait();
                continue;
            }
            try {
                int num = Integer.parseInt(input.get());
                if (num >= minThreshold && num <= maxThreshold) {
                    comicCountThreshold = num;
                    break;
                } else {
                    Alert outOfBoundsError = new Alert(AlertType.ERROR);
                    outOfBoundsError.setHeaderText("Number must be from 3 to 50");
                    outOfBoundsError.showAndWait();
                    continue;
                }
            } catch (NumberFormatException e) {
                Alert incorrectInputFormatError = new Alert(AlertType.ERROR);
                incorrectInputFormatError.setHeaderText("Input must be a number/integer");
                incorrectInputFormatError.showAndWait();
                continue;
            }
        }
    }

    public int getComicCountThreshold() {
        return comicCountThreshold;
    }
}
