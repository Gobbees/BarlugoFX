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

import barlugofx.app.AppManager;
import barlugofx.view.ViewController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Separator;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * This class manages the view events. IMPORTANT: set the app manager with
 * setManager() function. Creating a PresetController object is useless and it
 * probably will cause some sort of exception.
 */
public final class PresetController implements ViewController {
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
    private Spinner<Integer> spnWbalance;
    @FXML
    private Spinner<Integer> spnSaturation;
    @FXML
    private Spinner<Integer> spnHue;
    @FXML
    private Spinner<Integer> spnVibrance;
    @FXML
    private JFXCheckBox chkExposure;
    @FXML
    private JFXCheckBox chkContrast;
    @FXML
    private JFXCheckBox chkBrightness;
    @FXML
    private JFXCheckBox chkWbalance;
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
    @FXML
    private Spinner<Integer> spnColR;
    @FXML
    private Spinner<Integer> spnColG;
    @FXML
    private Spinner<Integer> spnColB;
    @FXML
    private Spinner<Integer> spnBlkR;
    @FXML
    private Spinner<Integer> spnBlkG;
    @FXML
    private Spinner<Integer> spnBlkB;
    @FXML
    private HBox hbxColors;
    @FXML
    private HBox hbxBlkWht;
    private Stage stage;
    private AppManager manager;
    private Map<JFXCheckBox, List<Spinner<Integer>>> components;

    @Override
    public void setStage(final Stage stage) {
        this.stage = stage;
        initComponents();
    }

    /**
     * This function sets the app manager (controller). It must be called in order
     * to avoid future errors.
     * 
     * @param manager the input manager
     */
    public void setManager(final AppManager manager) {
        this.manager = manager;
    }

    private void initComponents() {
        final double width = stage.getScene().getWidth();
        final double height = stage.getScene().getHeight();

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
        spnWbalance.setPrefWidth(width * SPN_WIDTH_MULTIPLIER);
        spnWbalance.setPrefHeight(height * SPN_HEIGHT_MULTIPLIER);
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
        spnBlkR.setPrefWidth(width * SPN_WIDTH_MULTIPLIER);
        spnBlkR.setPrefHeight(height * SPN_HEIGHT_MULTIPLIER);
        spnBlkG.setPrefWidth(width * SPN_WIDTH_MULTIPLIER);
        spnBlkG.setPrefHeight(height * SPN_HEIGHT_MULTIPLIER);
        spnBlkB.setPrefWidth(width * SPN_WIDTH_MULTIPLIER);
        spnBlkB.setPrefHeight(height * SPN_HEIGHT_MULTIPLIER);
        horizSep.setMinWidth(width);
        verticSep.setMinHeight(height * SEP_HEIGHT_MULTIPLIER);
        leftSep.setMinWidth(width * SEP_WIDTH_MULTIPLIER);
        //
        spnExposure.disableProperty().bind(chkExposure.selectedProperty());
        spnContrast.disableProperty().bind(chkContrast.selectedProperty());
        spnBrightness.disableProperty().bind(chkBrightness.selectedProperty());
        spnWbalance.disableProperty().bind(chkWbalance.selectedProperty());
        spnSaturation.disableProperty().bind(chkSaturation.selectedProperty());
        spnHue.disableProperty().bind(chkHue.selectedProperty());
        spnVibrance.disableProperty().bind(chkVibrance.selectedProperty());
        hbxColors.disableProperty().bind(chkColors.selectedProperty());
        hbxBlkWht.disableProperty().bind(chkBlkWht.selectedProperty());
        //
        spnExposure.setValueFactory(new IntegerSpinnerValueFactory(MIN_HUNDRED, MAX_HUNDRED, MIN_ZERO, STEP));
        spnContrast.setValueFactory(new IntegerSpinnerValueFactory(MIN_HUNDRED, MAX_HUNDRED, MIN_ZERO, STEP));
        spnBrightness.setValueFactory(new IntegerSpinnerValueFactory(MIN_HUNDRED, MAX_HUNDRED, MIN_ZERO, STEP));
        spnWbalance.setValueFactory(new IntegerSpinnerValueFactory(MIN_ZERO, MAX_HUNDRED, MIN_ZERO, STEP));
        spnSaturation.setValueFactory(new IntegerSpinnerValueFactory(MIN_HUNDRED, MAX_HUNDRED, MIN_ZERO, STEP));
        spnHue.setValueFactory(new IntegerSpinnerValueFactory(MIN_HUNDRED, MAX_HUNDRED, MIN_ZERO, STEP));
        spnVibrance.setValueFactory(new IntegerSpinnerValueFactory(MIN_HUNDRED, MAX_HUNDRED, MIN_ZERO, STEP));
        spnColR.setValueFactory(new IntegerSpinnerValueFactory(MIN_RGB, MAX_RGB, MIN_ZERO, STEP));
        spnColG.setValueFactory(new IntegerSpinnerValueFactory(MIN_RGB, MAX_RGB, MIN_ZERO, STEP));
        spnColB.setValueFactory(new IntegerSpinnerValueFactory(MIN_RGB, MAX_RGB, MIN_ZERO, STEP));
        spnBlkR.setValueFactory(new IntegerSpinnerValueFactory(MIN_RGB, MAX_RGB, MIN_ZERO, STEP));
        spnBlkG.setValueFactory(new IntegerSpinnerValueFactory(MIN_RGB, MAX_RGB, MIN_ZERO, STEP));
        spnBlkB.setValueFactory(new IntegerSpinnerValueFactory(MIN_RGB, MAX_RGB, MIN_ZERO, STEP));
        IntegerStringConverter.createFor(spnExposure);
        IntegerStringConverter.createFor(spnContrast);
        IntegerStringConverter.createFor(spnBrightness);
        IntegerStringConverter.createFor(spnWbalance);
        IntegerStringConverter.createFor(spnSaturation);
        IntegerStringConverter.createFor(spnHue);
        IntegerStringConverter.createFor(spnVibrance);
        IntegerStringConverter.createFor(spnColR);
        IntegerStringConverter.createFor(spnColG);
        IntegerStringConverter.createFor(spnColB);
        IntegerStringConverter.createFor(spnBlkR);
        IntegerStringConverter.createFor(spnBlkG);
        IntegerStringConverter.createFor(spnBlkB);
        //
        components = new LinkedHashMap<>();
        components.put(chkExposure, Arrays.asList(spnExposure));
        components.put(chkContrast, Arrays.asList(spnContrast));
        components.put(chkBrightness, Arrays.asList(spnBrightness));
        components.put(chkWbalance, Arrays.asList(spnWbalance));
        components.put(chkSaturation, Arrays.asList(spnSaturation));
        components.put(chkHue, Arrays.asList(spnHue));
        components.put(chkVibrance, Arrays.asList(spnVibrance));
        components.put(chkColors, Arrays.asList(spnColR, spnColG, spnColB));
        components.put(chkBlkWht, Arrays.asList(spnBlkR, spnBlkG, spnBlkB));
    }

    /**
     * * Save selected filters and values on bps file.
     *
     */
    @FXML
    public void save() {
        final List<List<Spinner<Integer>>> valuesToSave = new ArrayList<>();
        for (final Map.Entry<JFXCheckBox, List<Spinner<Integer>>> entry : components.entrySet()) {
            if (entry.getKey().isSelected()) {
                valuesToSave.add(entry.getValue());
                // UPDATE VALORI!!!
            }
        }
        if (valuesToSave.isEmpty()) {
            //System.out.println("Select at least one filter to save!");
            final Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Select at least one filter to save!");
            alert.showAndWait();
            return;
        }
        final Properties filters = new Properties();
        String filterName;
        final String colBal = "ColR";
        final String blkWht = "BlkR";
        final List<Spinner<Integer>> savingList = valuesToSave.stream().flatMap(x -> x.stream())
                .collect(Collectors.toList());
        savingList.forEach(x -> System.out.println(x));
        for (int i = 0; i < savingList.size(); i++) {
            filterName = savingList.get(i).getId().substring(SUBSTRING_INDEX_NAME);
            filters.setProperty(filterName, savingList.get(i).getValue().toString());
            if (filterName.equals(colBal) || filterName.equals(blkWht)) {
                filterName = savingList.get(++i).getId().substring(SUBSTRING_INDEX_NAME);
                filters.setProperty(filterName, savingList.get(i).getValue().toString());
                filterName = savingList.get(++i).getId().substring(SUBSTRING_INDEX_NAME);
                filters.setProperty(filterName, savingList.get(i).getValue().toString());
            }
        }
        final File file = getFileFromDialog();
        if (file != null) {
            try {
                checkManager();
                manager.savePreset(filters, file);
            } catch (IOException | InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Close the preset gui.
     */
    @FXML
    public void cancel() {
        this.stage.close();
    }

    /*
     * private void setToNearestLimit(final Spinner<Integer> spn) { final
     * IntegerSpinnerValueFactory s = (IntegerSpinnerValueFactory)
     * spnBrightness.getValueFactory(); final int m = s.getMax(); // if
     * (spn.getValue() > m) { System.out.println("p"); } }
     */
    private File getFileFromDialog() {
        final FileChooser choose = new FileChooser();
        choose.getExtensionFilters().add(new FileChooser.ExtensionFilter("BarlugoFX preset(.bps)", "*.bps"));
        choose.setInitialFileName("New Preset" + ".bps");
        File f = choose.showSaveDialog(stage);
        try {
            checkManager();
            if (!f.getName().substring(f.getName().length() - SUBSTRING_INDEX_EXTENSION).equals(".bps")) {
                f = new File(f.getAbsolutePath() + ".bps");
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        return f;
    }

    private void checkManager() throws IllegalStateException {
        if (manager == null) {
            throw new IllegalStateException("The manager is null");
        }
    }
}
