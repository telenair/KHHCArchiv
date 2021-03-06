package application.controller;

import application.Main;
import helper.PaneHelper;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Controller für das Hauptfenster.
 * 
 * @author kerstin, helge, chris, holger
 *
 */
public final class MainWindowController {

    public Main main;

    @FXML
    private AnchorPane anchorMainMain;

    @FXML
    private Button manualStore;

    @FXML
    private Button autoStore;

    @FXML
    private Button search;

    @FXML
    private Button config;

    @FXML
    private Button stop;

    @FXML
    private AnchorPane anchorDetails;

    @FXML
    private Label labelHeadline;

    @FXML
    private Label labelSite;

    @FXML
    private MenuBar menuBar;

    @FXML
    private MenuItem menuBarFileCloseAplication;

    @FXML
    private MenuItem menuBarHelp;

    @FXML
    private MenuItem menuBarHelpFaq;

    @FXML
    private MenuItem menuBarHelpAbaout;

    @FXML
    private Button mainFooterLogo;

    @FXML
    private Slider manualSliderZoom;

    /**
     * SetMain.
     * 
     * @author kerstin
     * @param main
     *            main
     */
    public void setMain(Main main) {
        this.main = main;

        // Ueberschrift wird auf den Wert Archivierung (Version 1.0) gesetzt
        // labelHeadline.setText("Archivierung (Version 1.0)");
    }

    /**
     * Mauszeiger geändert.
     * 
     * @author kerstin, helge
     */

    @FXML
    void setOnMouseEntered(MouseEvent event) {
        main.getPrimarayStage().getScene().setCursor(Cursor.HAND);
    }

    @FXML
    void setOnMouseExited(MouseEvent event) {
        main.getPrimarayStage().getScene().setCursor(Cursor.DEFAULT);
    }

    /**
     * Überprüfung ob die Einstellungen schon vorgenommen wurden und Überprüfung ob das gewählte
     * Zielverzeichnis erreichbar ist wenn beides ok - Aufruf der manuellen Ablage sonst der
     * Configurationsseite.
     * 
     * @author holger, helge, kerstin
     * 
     * @param event
     *            event
     */

    @FXML
    private void handleManualButtonAction(ActionEvent event) {
        /*
         * Auswählbare Buttons werden enabled, nicht auswählbare Buttons werden disabled. Aufruf der
         * Folgeseite über die Zuweisung an pane. Ueberschrift wird auf den Wert "Manuelle Ablage"
         * gesetzt
         */
        main.setLastScene("manual");
        try {
            if (main.getMyConfig().getDestinationDir().equals("bitte Ziel-Verzeichnis auswählen")) {
                // noch keine Einstellungen vorgenommen
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Konfigurationsfehler");
                alert.setHeaderText("Quell- und/oder Zielverzeichnis nicht festgelegt");
                alert.setContentText("Bitte legen Sie das Quell- und Zielverzeichnis fest");
                alert.showAndWait();
                handleConfigButtonAction(event);
            } else {
                File myFile = new File(main.getMyConfig().getDestinationDir());
                if (!myFile.exists()) {
                    // Zielverzeichnis ist nicht erreichbar
                    Alert dialog = new Alert(AlertType.ERROR);
                    dialog.setTitle("Ziellaufwerk nicht erreichbar");
                    dialog.setHeaderText(
                            main.getMyConfig().getDestinationDir() + " kann nicht erreicht werden");
                    dialog.setContentText("Bitte wählen Sie ein neues Zielverzeichnis"
                            + " oder stellen Sie die Verbindung wieder her");
                    dialog.showAndWait();
                    handleConfigButtonAction(event);
                } else {
                    manualStore.setDisable(true);
                    search.setDisable(false);
                    config.setDisable(false);
                    AnchorPane pane = FXMLLoader
                            .load(getClass().getResource("../fxml/ManualWindow.fxml"));
                    ManualWindowController.main = this.main;
                    main.getPrimarayStage().setTitle(main.getTitle() + " - " + "Manuelle Ablage");
                    anchorDetails.getChildren().setAll(pane);
                }

            }
        } catch (Exception e) {
            System.out.println("Fehler in MainWindowController - handleManualButtonAction");
            e.printStackTrace();
        }

    }

    /**
     * Aufruf der Dokumentensuche.
     * 
     * @author holger, helge
     * 
     * @param event
     *            event
     */
    @FXML
    private void handleSearchButton(ActionEvent event) {
        /*
         * Auswählbare Buttons werden enabled, nicht auswählbare Buttons werden disabled. Aufruf der
         * Folgeseite über die Zuweisung an pane. Ueberschrift wird auf den Wert "Dokument suchen"
         * gesetzt
         */
        try {
            manualStore.setDisable(false);
            search.setDisable(true);
            config.setDisable(false);
            AnchorPane pane = FXMLLoader.load(getClass().getResource("../fxml/SearchWindow.fxml"));
            SearchWindowController.main = this.main;
            main.getPrimarayStage().setTitle(main.getTitle() + " - " + "Dokument suchen...");
            anchorDetails.getChildren().setAll(pane);
        } catch (Exception e) {
            System.out.println("Fehler in MainWindowController - handleSearchButtonAction");
            e.printStackTrace();
        }

    }

    /**
     * Aufruf der Einstellungen.
     * 
     * @author holger, helge
     * 
     * @param event
     *            event
     */
    @FXML
    private void handleConfigButtonAction(ActionEvent event) {
        /*
         * Auswählbare Buttons werden enabled, nicht auswählbare Buttons werden disabled. Aufruf der
         * Folgeseite über die Zuweisung an pane. Ueberschrift wird auf den Wert "Einstellungen"
         * gesetzt
         */
        // ConfigWindowController myController=new ConfigWindowController();
        try {
            manualStore.setDisable(false);
            search.setDisable(false);
            config.setDisable(true);
            AnchorPane configPane = FXMLLoader
                    .load(getClass().getResource("../fxml/ConfigWindow.fxml"));
            ConfigWindowController.main = this.main;
            main.getPrimarayStage().setTitle(main.getTitle() + " - " + "Einstellungen");
            List<Node> activeNodes = PaneHelper.activeNodes(configPane);
            int count = 0;
            for (Node n : activeNodes) {
                if (PaneHelper.initializeLabel(n, "labelPathSourceLocation",
                        main.getMyConfig().getSourceDir())) {
                    count++;
                }
                if (PaneHelper.initializeLabel(n, "labelPathDestinationLocation",
                        main.getMyConfig().getDestinationDir())) {
                    count++;

                }
                if (count == 2) {
                    break;
                }
                anchorDetails.getChildren().setAll(configPane);
            }
        } catch (Exception e) {
            System.out.println("Fehler in MainWindowController - handleConfigButtonAction");
            e.printStackTrace();
        }
    }

    // Beispielbaustelle für Helpfunktion.....
    @FXML
    void menuBarHelpOpen(ActionEvent event) {
        AnchorPane secondaryLayout = new AnchorPane();
        try {
            secondaryLayout = FXMLLoader.load(getClass().getResource("../fxml/HelpWindow.fxml"));
        } catch (IOException e) {
            System.out.println("kann HelpWindow.fxml nicht öffnen...");
            e.printStackTrace();
        }
        Scene secondScene = new Scene(secondaryLayout);
        Stage secondStage = new Stage();
        secondStage.setTitle("Hilfe zur Archivanwendung");
        secondStage.getIcons().add(new Image("file:resources/images/help_icon.png"));
        secondStage.setScene(secondScene);
        secondStage.show();
    }

    /**
     * Beenden der Anwendung.
     * 
     * @author kerstin
     */
    @FXML
    private void stopProgram() {
        /*
         * KK 170222 Programm wird beendet Lifecycle einer fx-Anwendung ist: init - start - (warten
         * auf Platform.exit) - stop init und stop sind in Application bereits codiert (leer) und
         * müssen nicht überschrieben werden start muss auscodiert werden Platform.exit() erlaubt
         * noch das Aufrufen von stop System.exit() wäre eine Alternative !!es gibt dann aber keine
         * Möglichkeit auf das Aufrufen von stop()!!
         */
        Platform.exit();
    }

}
