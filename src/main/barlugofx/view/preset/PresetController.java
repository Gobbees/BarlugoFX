package barlugofx.view.preset;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;

import barlugofx.view.View;
import barlugofx.view.AbstractViewControllerWithManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Separator;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import javafx.scene.control.SpinnerValueFactory.DoubleSpinnerValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * This class manages the view events. IMPORTANT: set the app manager with
 * setManager() function. Creating a PresetController object is useless and it
 * probably will cause some sort of exception.
 */
public final class PresetController extends AbstractViewControllerWithManager implements EventHandler<ActionEvent> {
    private static final double BTN_WIDTH_MULTIPLIER = 0.5;
    private static final double BTN_HEIGHT_MULTIPLIER = 0.1;
    private static final double SPN_WIDTH_MULTIPLIER = 0.22;
    private static final double SPN_HEIGHT_MULTIPLIER = 0.01;
    private static final double SEP_HEIGHT_MULTIPLIER = 0.02;
    private static final double SEP_WIDTH_MULTIPLIER = 0.01;
    private static final int MIN_ZERO = 0;
    private static final int MIN_RGB = -255;
    private static final int MIN_HUNDRED = -100;
    private static final int MAX_HUNDRED = 100;
    private static final int MAX_RGB = 255;
    private static final int STEP = 1;
    private static final int SUBSTRING_INDEX_NAME = 3;
    private static final int SUBSTRING_INDEX_EXTENSION = 4;
    @FXML
    private JFXButton btnSave;
    @FXML
    private JFXButton btnCancel;
    @FXML
    private Separator horizSep;
    @FXML
    private Separator verticSep;
    @FXML
    private Separator leftSep;
    @FXML
    private Spinner<Integer> spnExposure;
    @FXML
    private Spinner<Integer> spnContrast;
    @FXML
    private Spinner<Integer> spnBrightness;
    @FXML
    private Spinner<Integer> spnWhiteBalance;
    @FXML
    private Spinner<Integer> spnSaturation;
    @FXML
    private Spinner<Integer> spnHue;
    @FXML
    private Spinner<Integer> spnVibrance;
    @FXML
    private Spinner<Integer> spnColR;
    @FXML
    private Spinner<Integer> spnColG;
    @FXML
    private Spinner<Integer> spnColB;
    @FXML
    private Spinner<Double> spndBlkR;
    @FXML
    private Spinner<Double> spndBlkG;
    @FXML
    private Spinner<Double> spndBlkB;
    @FXML
    private JFXCheckBox chkExposure;
    @FXML
    private JFXCheckBox chkContrast;
    @FXML
    private JFXCheckBox chkBrightness;
    @FXML
    private JFXCheckBox chkWhiteBalance;
    @FXML
    private JFXCheckBox chkSaturation;
    @FXML
    private JFXCheckBox chkHue;
    @FXML
    private JFXCheckBox chkVibrance;
    @FXML
    private JFXCheckBox chkColors;
    @FXML
    private JFXCheckBox chkBlkWht;
    private Map<JFXCheckBox, List<Spinner<Integer>>> components;
    private Spinner<Integer> spnBlkR;
    private Spinner<Integer> spnBlkG;
    private Spinner<Integer> spnBlkB;

    @Override
    public void setStage(final Stage stage) {
        super.setStage(stage);
        initComponents();
    }

    private void initComponents() {

        resizeComponents();

/*        Spinner<Integer> spne = new Spinner<>();
        Spinner<Double> spinner = new Spinner<>();
        spne.setValueFactory(new IntegerSpinnerValueFactory(MIN_RGB, 1000, MIN_ZERO, STEP));
        spne.getValueFactory().setValue((int) (spinner.getValueFactory().getValue() * 100));
*/
        spnBlkR = new Spinner<>();
        spnBlkG = new Spinner<>();
        spnBlkB = new Spinner<>();

        spnExposure.setValueFactory(new IntegerSpinnerValueFactory(MIN_HUNDRED, MAX_HUNDRED, MIN_ZERO, STEP));
        spnContrast.setValueFactory(new IntegerSpinnerValueFactory(MIN_HUNDRED, MAX_HUNDRED, MIN_ZERO, STEP));
        spnBrightness.setValueFactory(new IntegerSpinnerValueFactory(MIN_HUNDRED, MAX_HUNDRED, MIN_ZERO, STEP));
        spnWhiteBalance.setValueFactory(new IntegerSpinnerValueFactory(MIN_ZERO, MAX_HUNDRED, MIN_ZERO, STEP));
        spnSaturation.setValueFactory(new IntegerSpinnerValueFactory(MIN_HUNDRED, MAX_HUNDRED, MIN_ZERO, STEP));
        spnHue.setValueFactory(new IntegerSpinnerValueFactory(MIN_HUNDRED, MAX_HUNDRED, MIN_ZERO, STEP));
        spnVibrance.setValueFactory(new IntegerSpinnerValueFactory(MIN_HUNDRED, MAX_HUNDRED, MIN_ZERO, STEP));
        spnColR.setValueFactory(new IntegerSpinnerValueFactory(MIN_RGB, MAX_RGB, MIN_ZERO, STEP));
        spnColG.setValueFactory(new IntegerSpinnerValueFactory(MIN_RGB, MAX_RGB, MIN_ZERO, STEP));
        spnColB.setValueFactory(new IntegerSpinnerValueFactory(MIN_RGB, MAX_RGB, MIN_ZERO, STEP));
        spndBlkR.setValueFactory(new DoubleSpinnerValueFactory(MIN_ZERO, MAX_HUNDRED, MIN_ZERO, STEP));
        spndBlkG.setValueFactory(new DoubleSpinnerValueFactory(MIN_ZERO, MAX_HUNDRED, MIN_ZERO, STEP));
        spndBlkB.setValueFactory(new DoubleSpinnerValueFactory(MIN_ZERO, MAX_HUNDRED, MIN_ZERO, 0.5));


        spnBlkR.setValueFactory(new IntegerSpinnerValueFactory(MIN_ZERO, 10000, MIN_ZERO, STEP));
        spnBlkG.setValueFactory(new IntegerSpinnerValueFactory(MIN_ZERO, 10000, MIN_ZERO, STEP));
        spnBlkB.setValueFactory(new IntegerSpinnerValueFactory(MIN_ZERO, 10000, MIN_ZERO, STEP));
        spnBlkR.setId("spnBlkR");
        spnBlkG.setId("spnBlkG");
        spnBlkB.setId("spnBlkB");

        components = new LinkedHashMap<>();
        components.put(chkExposure, Arrays.asList(spnExposure));
        components.put(chkContrast, Arrays.asList(spnContrast));
        components.put(chkBrightness, Arrays.asList(spnBrightness));
        components.put(chkWhiteBalance, Arrays.asList(spnWhiteBalance));
        components.put(chkSaturation, Arrays.asList(spnSaturation));
        components.put(chkHue, Arrays.asList(spnHue));
        components.put(chkVibrance, Arrays.asList(spnVibrance));
        components.put(chkColors, Arrays.asList(spnColR, spnColG, spnColB));
        components.put(chkBlkWht, Arrays.asList(spnBlkR, spnBlkG, spnBlkB));

        addListeners();
    }

    @Override
    public void handle(final ActionEvent event) {
        final JFXCheckBox checkBox = (JFXCheckBox) event.getSource();
        final List<Spinner<Integer>> list = spinnersCheck(checkBox);
        if (checkBox.isSelected()) {
            for (final Spinner<Integer> spinner : list) {
                /// TODO cancellare println
                System.out.println(spinner.getValue());
                spinner.setDisable(true);
            }
        } else {
            for (final Spinner<Integer> spinner : list) {
                spinner.setDisable(false);
            }
        }
    }

    /**
     * Iterate over the entire components map to find the spinners associated with
     * the selected checkbox.
     * 
     * @param checkbox the checkbox that fired the event
     * @return a list of all spinners that correspond to the selected checkbox
     */
    private List<Spinner<Integer>> spinnersCheck(final JFXCheckBox checkBox) {

        final List<List<Spinner<Integer>>> spinners = new ArrayList<>();
        for (final Map.Entry<JFXCheckBox, List<Spinner<Integer>>> entry : components.entrySet()) {
            if (entry.getKey().equals(checkBox)) {
                spinners.add(entry.getValue());
            }
        }
        return spinners.stream().flatMap(x -> x.stream()).collect(Collectors.toList());
    }

    /**
     * 
     * * Save selected filters and values on bps file.
     *
     */
    @FXML
    public void save() {
        checkManager();
        final List<List<Spinner<Integer>>> valuesToSave = new ArrayList<>();
        for (final Map.Entry<JFXCheckBox, List<Spinner<Integer>>> entry : components.entrySet()) {
            if (entry.getKey().isSelected()) {
                valuesToSave.add(entry.getValue());
            }
        }
        if (valuesToSave.isEmpty()) {
            View.showErrorAlert("Select at least one filter to save!");
            return;
        }

        final Properties filters = new Properties();
        String filterName;
        final String colBal = "ColR";
        final String blkWht = "BlkR";
        String value;
        final List<Spinner<Integer>> savingList = valuesToSave.stream().flatMap(x -> x.stream())
                .collect(Collectors.toList());

        ///TODO Remove print
        savingList.forEach(x -> System.out.println(x));

        for (int i = 0; i < savingList.size(); i++) {
            filterName = savingList.get(i).getId().substring(SUBSTRING_INDEX_NAME);
            value = savingList.get(i).getValue().toString();
            if (filterName.equals(colBal)) {
                filterName = "SelectiveColors";
                value = value + "," + savingList.get(++i).getValue().toString() + ","
                        + savingList.get(++i).getValue().toString();
            } else if (filterName.equals(blkWht)) {
                filterName = "BlackAndWhite";
                value = value + "," + savingList.get(++i).getValue().toString() + ","
                        + savingList.get(++i).getValue().toString();
            }
            filters.setProperty(filterName, value);
        }
        final File file = getFileFromDialog();
        if (file != null) {
            checkManager();
            checkExtension(file);
            new Thread(() -> {
                try {
                    this.getManager().savePreset(filters, file);
                } catch (IOException | InterruptedException | ExecutionException e) {
                    View.showErrorAlert(e.getMessage());
                    e.printStackTrace();
                }
            }, "Save preset").start();
            final Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Saved");
            alert.setHeaderText(null);
            alert.setContentText("The preset was successfully saved!");
            alert.showAndWait();
            this.getStage().close();
        }
    }

    /**
     * Close the preset gui.
     */
    @FXML
    public void cancel() {
        checkStage();
        this.getStage().close();
    }

    private File getFileFromDialog() {
        checkStage();
        final FileChooser choose = new FileChooser();
        choose.getExtensionFilters().add(new FileChooser.ExtensionFilter("BarlugoFX preset(.bps)", "*.bps"));
        choose.setInitialFileName("New Preset" + ".bps");
        return choose.showSaveDialog(this.getStage());
    }

    /**
     * Checks if the name file has the correct extension.
     * 
     * @param f the file to check the extension
     * @return a file with the correct extension
     */
    private File checkExtension(final File f) {
        File file = f;
        if (!f.getName().substring(f.getName().length() - SUBSTRING_INDEX_EXTENSION).equals(".bps")) {
            file = new File(f.getAbsolutePath() + ".bps");
        }
        return file;
    }

    private void resizeComponents() {
        checkStage();
        final double width = this.getStage().getScene().getWidth();
        final double height = this.getStage().getScene().getHeight();

        btnSave.setMinWidth(width * BTN_WIDTH_MULTIPLIER);
        btnSave.setPrefHeight(height * BTN_HEIGHT_MULTIPLIER);
        btnCancel.setMinWidth(width * BTN_WIDTH_MULTIPLIER);
        btnCancel.setPrefHeight(height * BTN_HEIGHT_MULTIPLIER);
        spnExposure.setPrefWidth(width * SPN_WIDTH_MULTIPLIER);
        spnExposure.setPrefHeight(height * SPN_HEIGHT_MULTIPLIER);
        spnContrast.setPrefWidth(width * SPN_WIDTH_MULTIPLIER);
        spnContrast.setPrefHeight(height * SPN_HEIGHT_MULTIPLIER);
        spnBrightness.setPrefWidth(width * SPN_WIDTH_MULTIPLIER);
        spnBrightness.setPrefHeight(height * SPN_HEIGHT_MULTIPLIER);
        spnWhiteBalance.setPrefWidth(width * SPN_WIDTH_MULTIPLIER);
        spnWhiteBalance.setPrefHeight(height * SPN_HEIGHT_MULTIPLIER);
        spnSaturation.setPrefWidth(width * SPN_WIDTH_MULTIPLIER);
        spnSaturation.setPrefHeight(height * SPN_HEIGHT_MULTIPLIER);
        spnHue.setPrefWidth(width * SPN_WIDTH_MULTIPLIER);
        spnHue.setPrefHeight(height * SPN_HEIGHT_MULTIPLIER);
        spnVibrance.setPrefWidth(width * SPN_WIDTH_MULTIPLIER);
        spnVibrance.setPrefHeight(height * SPN_HEIGHT_MULTIPLIER);
        spnColR.setPrefWidth(width * SPN_WIDTH_MULTIPLIER);
        spnColR.setPrefHeight(height * SPN_HEIGHT_MULTIPLIER);
        spnColG.setPrefWidth(width * SPN_WIDTH_MULTIPLIER);
        spnColG.setPrefHeight(height * SPN_HEIGHT_MULTIPLIER);
        spnColB.setPrefWidth(width * SPN_WIDTH_MULTIPLIER);
        spnColB.setPrefHeight(height * SPN_HEIGHT_MULTIPLIER);
        spndBlkR.setPrefWidth(width * SPN_WIDTH_MULTIPLIER);
        spndBlkR.setPrefHeight(height * SPN_HEIGHT_MULTIPLIER);
        spndBlkG.setPrefWidth(width * SPN_WIDTH_MULTIPLIER);
        spndBlkG.setPrefHeight(height * SPN_HEIGHT_MULTIPLIER);
        spndBlkB.setPrefWidth(width * SPN_WIDTH_MULTIPLIER);
        spndBlkB.setPrefHeight(height * SPN_HEIGHT_MULTIPLIER);
        horizSep.setMinWidth(width);
        verticSep.setMinHeight(height * SEP_HEIGHT_MULTIPLIER);
        leftSep.setMinWidth(width * SEP_WIDTH_MULTIPLIER);
    }

    private void addListeners() {
        /*
         * Adds a listener to every checkbox.
         */
        for (final JFXCheckBox entry : components.keySet()) {
            entry.setOnAction(this);
        }
        /*
         * This adds a listener to every spinner.focusedProperty() that will update the
         * value when the focus is lost we have to do this because in JavaFX8 spinners
         * won't update value automatically
         */
        for (final List<Spinner<Integer>> entry : components.values()) {
            for (final Spinner<Integer> e : entry) {
                IntegerStringConverter.createFor(e);
                e.focusedProperty().addListener((observable, oldValue, newValue) -> {
                    if (!newValue) {
                        e.increment(0); // won't change value, but will commit editor
                    }
                });
            }
        }
        spndBlkR.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                spndBlkR.increment(0); // won't change value, but will commit editor
                spnBlkR.getValueFactory().setValue((int) (spndBlkR.getValueFactory().getValue() * MAX_HUNDRED));
                System.out.println(spnBlkR.getValue());
            }
        });
        DoubleStringConverter.createFor(spndBlkR);
        spndBlkG.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                spndBlkG.increment(0); // won't change value, but will commit editor
                spnBlkG.getValueFactory().setValue((int) (spndBlkG.getValueFactory().getValue() * MAX_HUNDRED));
                System.out.println(spnBlkG.getValue());
            }
        });
        DoubleStringConverter.createFor(spndBlkG);
        spndBlkB.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                spndBlkB.increment(0); // won't change value, but will commit editor
                spnBlkB.getValueFactory().setValue((int) (spndBlkB.getValueFactory().getValue() * MAX_HUNDRED));
                System.out.println(spnBlkB.getValue());
            }
        });
        DoubleStringConverter.createFor(spndBlkB);
    }
}
