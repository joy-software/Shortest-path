/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vue;

import Controlleur.AlgoA;
import Controlleur.AlgoCoutUniforme;
import Controlleur.AlgoEtoile;
import Controlleur.LoadApp;
import Modele.Point;
import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.effect.ImageInput;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


/**
 * FXML Controller class
 *
 * @author NDJAMA
 */
public class AlgoRechercheController implements Initializable {

    
    
    /**
     * le toolbar qui gère la page de bienvenue
     */
    @FXML
    private ToolBar bienvenue;
    /**
     * le toolbar qui gère la page de collecte des données
     */
    @FXML
    private ToolBar donnees;
    /**
     * le toolbar qui gère la page du choix des algorithmes
     */
    @FXML
    private ToolBar algorithme;
    /**
     * le toolbar qui gère la page des resultats
     */
    @FXML
    private ToolBar resultat;
    /**
     * le button precédent
     */
    @FXML
    private Button precedent;   
    /**
     * le button suivant
     */
    @FXML
    private Button suivant;  
    /**
     * l'entier qui nous permettra de savoir à quelle page nous nous trouvons
     */
    private static int page = 1;
    /**
     * le label qui permet de nous donner des informations sur la page en cours 
     */
    @FXML
    private Label pagelab;
    /**
     * L'Anchorpane de la page Bienvenue
     */
    @FXML
    public static AnchorPane anchorBienvenue;
    /**
     * L'Anchorpane de la page Donnees
     */
    @FXML
    public static AnchorPane anchorDonne;
    /**
     * L'Anchorpane de la page algorithme
     */
    @FXML
    public static AnchorPane anchorAlgorithme;
    /**
     * L'Anchorpane de la page resultat
     */
    @FXML
    public static AnchorPane anchorResultat;
    /**
     * Le Splitpane qui contiendra les anchorpanes ci-dessus
     */
    @FXML
    private SplitPane split;
    /**
     * le file qui contiendra le fichier contenant notre graphe 
     */
    private static File filePoint;
    /**
     * une ligne de notre fichier excel
     */
    private XSSFRow row;
    /**
     * l'ensemble de nos points 
     */
    public static ArrayList<Point> ensemble = new ArrayList<>();
    /**
     * le label pour le chargement de notre fichier
     */
    @FXML
    private Label bienvenue_chargement;
    /**
     * le progress indicator de notre process de chargement du fichier
     */
    @FXML
    private ProgressIndicator bienvenue_progress;
    /**
     * le boutton d'upload du fichier
     */
    @FXML
    private Button bienvenue_button;
    /**
     * le button de selection du point initial
     */
    @FXML
    private Button donnees_validation;
    /**
     * le button d'ajout de l'heuristique
     */
    @FXML
    private Button donnees_button;
    /**
     * l'anchorPane secondaire de la page Données
     */
    @FXML
    private AnchorPane donnees_anchor;
    /**
     * le combo contenant nos différents points
     */
    @FXML
    private ComboBox donnees_combo;
    /**
     * le combo contenant nos différents points
     */
    @FXML
    private ChoiceBox donnees_choiceBox;
    /**
     * notre tableau de la page donnee
     */
    @FXML
    private TableView<PointFx> donnees_table;
    /**
     * la colonne point du tableau des données
     */
    @FXML
    private TableColumn<PointFx,String> donnees_colpoint;
    /**
     * la colonne heurisitique du tableau des données
     */
    @FXML
    private TableColumn<PointFx,String> donnees_colheuristique;
    /**
     * la colonne finale du tableau des données
     */
    @FXML
    private TableColumn<PointFx,Boolean> donnees_colfinale;
    /**
     * le textfield d'ajout d'heuristique de la page donnée
     */
    @FXML
    private TextField donnees_text;
    /**
     * le label affichant l'heuristique choisi
     */
    @FXML
    private Label donnees_label;
    /**
     * les données de notre tableau de points
     */
    private static  ObservableList<PointFx> donnees_data = FXCollections.observableArrayList();
    /**
     * Pour le page a la page suivant  Bienvenue
     */
    private static boolean bienvenue_buttonBool = false;
    /**
     * ce boolean sert a savoir si nous avons choisi un point comme point final
     */
    private static boolean donnees_changeOccur = false;
    /**
     * le checkBox de l'algorithme A
     */
    @FXML
    private CheckBox Algorithme_algoA;
    /**
     * le checkBox de l'algorithme Aprime
     */
    @FXML
    private CheckBox Algorithme_Aprime;
    /**
     * le checkBox de l'algorithme cout uniforme 
     */
    @FXML
    private CheckBox Algorithme_pluscourt;
    /**
     * aide au choix de l'algorithme
     */
    private static int algorithme_algo;
    /***
     * pour la photo de la page algorithme
     */
    @FXML
    private Label algorithme_photo;
    /**
     * le progressBar indiquant que l'algorithme est en cours d'éxécution
     */
    @FXML
    private ProgressIndicator resultat_progress;
    private boolean reset;
    /**
     * L'arrayList qui contiendra nos différentes solutions
     */
    public static ArrayList<String> resultat_resultat = new ArrayList();
    /**
     * Le tableau qui contiendra les noms des destinations finales
     */
    private String[] resultat_resultats;
    /**
     * contiendra le nom de la ville de destiantion
     */
    private String finas;
    /**
     * Le checkBox servant à afficher ou non le graphe de résolution
     */
    @FXML
    private CheckBox resultat_graphe;
    
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    
    /**
     * la fonction qui gère l'affichage des pages 
     */
    @FXML
    private void shownextPages()
    {
        page++;
        handlePage();
    }
    
    /**
     * la fonction qui gère l'affichage des pages 
     */
    @FXML
    private void showPreviousPages()
    {
        page--;
        handlePage();
    }
    
    /**
     * la fonction qui gère l'affichage de la page Bienvenue
     */
    @FXML
    private void showBienvenue()
    {
        page = 1;
        handlePage();
    }
    
    /**
     * la fonction qui gère l'affichage de la page donnees
     */
    @FXML
    private void showDonnees()
    {
        page = 2;
        handlePage();
        
    }
    
    /**
     * cette méthode permet de selectionner le type d'algorithme à exécuter
     */
    @FXML
    private void algorithme_A()
    {
        algorithme_algo = 1;
        ////System.out.println(" algo "+algorithme_algo);
        Algorithme_Aprime.setSelected(false);
        Algorithme_pluscourt.setSelected(false);
    }
    
    
    /**
     * cette méthode permet de selectionner le type d'algorithme à exécuter
     */
    @FXML
    private void algorithme_APrime()
    {
        algorithme_algo = 2;
        //System.out.println(" algo "+algorithme_algo);
        Algorithme_algoA.setSelected(false);
        Algorithme_pluscourt.setSelected(false);
    }
    
    
    /**
     * cette méthode permet de selectionner le type d'algorithme à exécuter
     */
    @FXML
    private void algorithme_pluscourt()
    {
        algorithme_algo = 3;
        ////System.out.println(" algo "+algorithme_algo);
        Algorithme_Aprime.setSelected(false);
        Algorithme_algoA.setSelected(false);
    }
    
    /**
     * la fonction qui gère l'affichage de la page choix des algorithmes
     */
    @FXML
    private void showAlgorithme()
    {
        page = 3;
        handlePage();
    }
    
   /**
     * la fonction qui gère l'affichage de la page des resultats
     */
    @FXML
    private void showResultat()
    {
        page = 4;
        handlePage();
    }
    
    
    /**
     * cette méthode permet de lancer la bonne page dans notre fenêtre fonction
     * de l'attribut page passé en paramètres au test de contrôle switch
     */
    private void handlePage()
    {
        switch(page)
        {
            case 1:
                precedent.setDisable(true);
                suivant.setDisable(false);
                bienvenue.setVisible(true);
                donnees.setVisible(false);
                algorithme.setVisible(false);
                resultat.setVisible(false);
                pagelab.setText("Bienvenue dans notre CSP Resolver");
                setSecondaire(AlgoRechercheController.anchorBienvenue);
                File f = new File("./photo/Bienvenue.png");
                AlgoRechercheController.anchorBienvenue.getChildren().get(0).setEffect(new ImageInput(new Image("file:/"+f.getAbsolutePath())));
                break;
            case 2:
                if(bienvenue_buttonBool)
                {
                    precedent.setDisable(false);
                    suivant.setDisable(false);
                    bienvenue.setVisible(true);
                    donnees.setVisible(true);
                    algorithme.setVisible(false);
                    resultat.setVisible(false);
                    pagelab.setText("Cliquer sur suivant pour choisir l'algorithme");
                    setSecondaire(AlgoRechercheController.anchorDonne);
                }
                else
                {
                    messageErreur("du fichier excel", "Veuiller cliquer sur le bouton 'Charger le fichier' et \n "
                            + "selectionner le fichier excel convenable ");
                    page--;
                }
                //mnemonic(donnees_button);
                break;
            case 3:
                if(checkHeuristique())
                {
                    precedent.setDisable(false);
                    suivant.setDisable(false);
                    bienvenue.setVisible(true);
                    donnees.setVisible(true);
                    algorithme.setVisible(true);
                    resultat.setVisible(false);
                    pagelab.setText("Choississez un algorithme");
                    setSecondaire(AlgoRechercheController.anchorAlgorithme);
                    f = new File("./photo/algo.jpg");
                    AlgoRechercheController.anchorAlgorithme.getChildren().get(2).setEffect(new ImageInput(new Image("file:/"+f.getAbsolutePath())));
                }
                else
                {
                    page--;
                }
                break;
            case 4:
                ////System.out.println(" algo "+algorithme_algo+ " choix "+algorithme_choixSolution);
                if(algorithme_algo != 0)
                {
                    precedent.setDisable(false);
                    suivant.setDisable(true);
                    bienvenue.setVisible(true);
                    donnees.setVisible(true);
                    algorithme.setVisible(true);
                    pagelab.setText("Voici les resultats ");
                    resultat.setVisible(true);
                    setSecondaire(AlgoRechercheController.anchorResultat);
                    f = new File("./photo/resultat.jpg");
                    AlgoRechercheController.anchorResultat.getChildren().get(2).setEffect(new ImageInput(new Image("file:/"+f.getAbsolutePath())));
                    response();
                    loadAlgo.start();
                }
                else
                {
                    messageErreur("du choix des algorithmes", "Veillez selectionner l'algorithme à executer avant de continuer");
                    page--;
                }
                break;
        }
        
    }
    /**
     * cette méthode permet de montrer à l'utilisateur le graphe obtenu lors de l'exécution 
     */
    @FXML
    private void showGraphe()
    {
            showGraphe1();
    
    }
    
    /**
     * cette méthode permet de montrer à l'utilisateur le graphe obtenu lors de l'exécution 
     */
    private void showGraphe1()
    {
        File f;
        switch(algorithme_algo)
                {
                    case 1:
                        f = new File("outputs/resultat Algo A.joar");
                        {
                            try {
                                messageInformation("Le graphe","Patienter quelques instants votre ordinateur affiche le fichier");
                                 Desktop.getDesktop().open(f);
                                 upload(f);
                                } catch (IOException ex) {
                                     LoadApp.printStrace(ex);
                                }
                        }
                        break;
                    case 2:
                        f = new File("outputs/resultat Algo AEtoile.joar");
                        {
                            try {
                                 messageInformation("Le graphe","Patienter quelques instants votre ordinateur affiche le fichier");
                                 Desktop.getDesktop().open(f);
                                 upload(f);
                                } catch (IOException ex) {
                                     LoadApp.printStrace(ex);
                                }
                        }
                        break;
                    case 3:
                        f = new File("outputs/resultat Algo Cout Uniforme.joar");
                        {
                            try {
                                 messageInformation("Le graphe","Patienter quelques instants votre ordinateur affiche le fichier");
                                 Desktop.getDesktop().open(f);
                                 upload(f);
                                } catch (IOException ex) {
                                     LoadApp.printStrace(ex);
                                }
                        }
                        break;
                }
    }

    /**
     * Demande a l'utilisateur s'il voudrait uploader un fichier
     */
    private void upload(File f)
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Dialogue de Confirmation");
        alert.setHeaderText("Nous voulons nous rassurer:");
        alert.setContentText("Souhaitez-vous telecharger le fichier contenant les détails?");
        alert.initOwner(LoadApp.getPrimaryStage()); 
            // Get the Stage.
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            
            File f1 = new File("./photo/iconCSP.png");
            
            // Add a custom icon.
            stage.getIcons().add(new Image("file:/"+f1.getAbsolutePath()));
            
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            FileChooser fileChooser = new FileChooser();

        // Set extension filter
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "Joy Algo Research (*.joar)","*.joar");
            fileChooser.getExtensionFilters().add(extFilter);
            fileChooser.setInitialFileName(f.getName());

            // Show save file dialog
            File file = fileChooser.showSaveDialog(LoadApp.getPrimaryStage());
            Path monFichier = Paths.get(f.getAbsolutePath());
            if(file != null)
            {
                Path monFichierCopie = Paths.get(file.getAbsolutePath());
                try {
                         Files.copy(monFichier, monFichierCopie);
                } catch (IOException ex) {
                    LoadApp.printStrace(ex);
                }
            }
        } else {
                // ... user chose CANCEL or closed the dialog
        }
    }
    

    
    /**
     * cette méthode permet d'arreter le service 
     */
    private void response()
    {
        AnchorPane temp = (AnchorPane)anchorResultat.getChildren().get(3);
        HBox tempbar = (HBox)temp.getChildren().get(0);
        if(reset)
        {
            CheckBox tempcheck = (CheckBox)anchorResultat.getChildren().get(4);
            tempcheck.setSelected(false);
            tempbar.getChildren().remove(1);
        }
        Label label = (Label)tempbar.getChildren().get(0);
        label.setText("En cours d'execution ...");
        resultat_progress = new ProgressIndicator();
        resultat_progress.progressProperty().unbind();
        resultat_progress.progressProperty().bind(loadAlgo.progressProperty());
        tempbar.getChildren().add(resultat_progress);
         loadAlgo.stateProperty().addListener((ObservableValue<? extends Worker.State> observableValue, 
                 Worker.State oldState, Worker.State newState) -> {
             switch (newState) {
                 case SCHEDULED:
                     break;
                 case READY:
                 case RUNNING:
                     break;
                 case SUCCEEDED:
                     //AnchorPane temp = (AnchorPane)anchorResultat.getChildren().get(3);
                     label.setText("Execution terminée.");
                     anchorResultat.getChildren().get(1).setDisable(false);
                     putResult();
                     //resultat_progress.setProgress(1);
                     loadAlgo.reset();
                     tempbar.getChildren().remove(1);
                     resultat_progress = new ProgressIndicator();
                     resultat_progress.setProgress(1);
                     tempbar.getChildren().add(resultat_progress);
                     reset = true;
                     //tempbar.progressProperty().unbind();
                     break;
                 case CANCELLED:
                     
                     break;
                 case FAILED:
                     loadAlgo.reset();
                     break;
                     
             }
        });
    
    }
    
    /**
     * cette méthode permet d'enregistrer nos resultats dans notre gridPane pour affichage
     */
    private void putResult()
    {
        AnchorPane temp = (AnchorPane)anchorResultat.getChildren().get(1);
        
        ListView<String> templist = (ListView<String>)temp.getChildren().get(0);
        ObservableList<String> resultat_resulta = FXCollections.observableArrayList();
      
        Label templabel = (Label)temp.getChildren().get(3);
        templabel.setVisible(true);
        templabel.setText("-> Vous etes à " + resultat_resultats[0]);
        
        Label templabel1 = (Label)temp.getChildren().get(4);
        templabel1.setVisible(true);
        templabel1.setText( "-> Vous desirez arriver à " + finas);
                        
        resultat_resulta.addAll(resultat_resultat);
        templist.setItems(resultat_resulta);
      
    }
    
    /**
     * Le service qui démarre l'éxécution des algorithmes
     */
    private final Service<Void> loadAlgo = new Service<Void>(){

     @Override
    protected Task<Void> createTask() {
        return new Task<Void>(){

        @Override
        protected Void call() 
            {
                
                switch(algorithme_algo)
                {
                    case 1:
                        AlgoA test = new AlgoA();
                        //resultat_resultat.clear();
                        test.callmeilleurDabord(ensemble);
                        resultat_resultats = test.result.split("->");
                        ranger(resultat_resultats);
                        updateProgress(1000, 1000);
                        break;
                    case 2:
                        AlgoEtoile test1 = new AlgoEtoile();
                        //resultat_resultat.clear();
                        //System.out.println("case 2");
                        test1.callmeilleurDabord(ensemble);
                        resultat_resultats = test1.result.split("->");
                        ranger(resultat_resultats);
                        updateProgress(1000, 1000);
                        break;
                    case 3:
                        AlgoCoutUniforme test2 = new AlgoCoutUniforme();
                        //resultat_resultat.clear();
                        test2.callmeilleurDabord(ensemble);
                        resultat_resultats = test2.result.split("->");
                        ranger(resultat_resultats);
                        updateProgress(1000, 1000);
                        break;
                }
                return null;
            };

            /**
             * permet de ranger nos resultats pour un bon affichage
             * @param resultat_resultats 
             */
            private void ranger(String[] resultat_resultats) {
                        int rang = 0;
                        finas = "";
                        resultat_resultat.clear();
                        for (String auto : resultat_resultats)
                        {
                            rang++;
                            //System.out.println("**********"+auto+"**********");
                            if(rang != resultat_resultats.length)
                            {
                                switch(rang)
                                {
                                    case 1:
                                        resultat_resultat.add(rang+"- Quitter "+auto);
                                        break;

                                    case 2:
                                        resultat_resultat.add(rang+"- Aller à "+auto);
                                        break;
                                    default:
                                        resultat_resultat.add(rang+"- Puis "+auto);
                                        break;
                                }
                            }
                            else
                            {
                                finas = auto;
                                resultat_resultat.add(rang+"- Enfin, aller à "+auto);
                            }
                        }
                    }
        };
                }
    };
    
    
    /**
     * Cette fonction permet de recuperer le nombre de ensembles de notre CSP tout en 
     * activant l'anchorpane servant à entrer les autres données 
     */
    @FXML
    private void donnees_activeDonnees()
    {
        Boolean ok = false;
       
        
        for(Point auto : ensemble)
        {
            if(auto.getNom().equals(""+donnees_combo.getValue()))
            {
                ok = true;
                auto.setSommet(true);
                break;
            }
        }
        if(ok)
        {
            donnees_combo.setDisable(true);
            donnees_anchor.setDisable(false);
            donnees_choiceBox.setItems(fillList1());
            donnees_label.setText( "Le point initial choisi est : "+donnees_combo.getValue());
            if(!donnees_choiceBox.getItems().isEmpty())
            {
                donnees_choiceBox.setValue(fillList1().get(0));//on met un élément par défaut.
            }
            donnees_table.setItems(donnees_data);
            donnees_colpoint.setCellValueFactory(cellData -> cellData.getValue().getPointProp());
            donnees_colheuristique.setCellValueFactory(cellData -> cellData.getValue().getHeuristiqueProp());
            donnees_colfinale.setCellValueFactory(( TableColumn.CellDataFeatures<PointFx, Boolean> f) -> {
                donnees_commitCheck(f.getValue().getPoint(),f.getValue().getfinale());
                return f.getValue().getFinaleProp();
            });
            donnees_colfinale.setCellFactory( tc -> new CheckBoxTableCell<>());
            mnemonic(donnees_button);
            if(donnees_data.size() == ensemble.size())
            {
                donnees_choiceBox.setDisable(true);
                donnees_text.setDisable(true);
                donnees_button.setDisable(true);
            }
            
        }
        else
        {
            messageErreur("du point initial","Choississez une valeur parmi la liste proposée");
        }
    }
    
    
    /**
     * permet d'enregistrer un point comme étant un point final
     */
    @FXML
    private void donnees_commitCheck(String test,Boolean bool)
    {
        
            for(Point auto : ensemble)
            {
                if(auto.getNom().equals(test))
                {
                    //System.out.println("La nouvelle valeur de finale " +bool+"****"+auto);
                    auto.setFina(bool);
                }
            }
      
    }
    
    /**
     * permet de verifier qu'on a ajouter des heuristiques
     */
    @FXML
    private Boolean checkHeuristique()
    {
        //System.out.println("data "+donnees_data.size()+" ensemble "+ensemble.size());
        if(!donnees_changeOccur || donnees_data.size() != ensemble.size())
        {
            messageErreur("des heuristiques", "Veuillez entrer les heuristiques de tous vos différents points");
            return false;
        }
        else
        {
            Boolean result = false;
            for(Point auto : ensemble)
            {
                if(auto.isFina())
                {
                    result = true;
                }
            }
            if(!result)
            {
                messageErreur("du point final", "Veuillez entrer les heuristiques de vos differents points\n"
                        + "Indiquer un point comme final.");
            }
            return result;
        }
    }
    
    
    /**
     * le remplissage du comboBox
     */
    @FXML
    private void fillCombo()
    {
        donnees_combo.setItems(fillList());
    }
    
    /**
     * cette méthode permet de remplir notre ChoiceBox ou notre comboBox
     */
    private ObservableList<String> fillList()
    {
        ObservableList<String> list = FXCollections.observableArrayList();
        
        for(Point auto : ensemble)
        {

            list.add(auto.getNom());
            
        }
        
        return list;
    }
    
    /**
     * cette méthode permet de remplir notre ChoiceBox ou notre comboBox
     */
    private ObservableList<String> fillList1()
    {
        ObservableList<String> list = FXCollections.observableArrayList();
        
        for(Point auto : ensemble)
        {
            if(auto.getHeuristique() != -1)
            {
                if(auto.getHeuristique() == 0)
                {
                    auto.setFina(true);
                }
                donnees_changeOccur = true;
                donnees_data.add(new PointFx(auto.getNom(), ""+auto.getHeuristique(), auto.isFina()));
            }
            else
            {
                list.add(auto.getNom());
            }
        }
        
        return list;
    }
    
    
    /**
     * cette méthode permet de modifier l'anchorpane secondaire de 
     * notre Slitpane permettant ainsi de pouvoir modifier dynamiquement
     * le contenu de notre page
     * @param pane 
     */
    public void setSecondaire(AnchorPane pane) {
         if(pane != null)
         {
             split.getItems().remove(1);
             split.getItems().add(pane);
         }
    }
    
    /**
     * close the app
     */
    @FXML
    private void closeApp()
    {
        LoadApp.getPrimaryStage().close();
    }
    
    
    /**
     * affiche l'apropos
     */
    @FXML
    private void aboutUs()
    {
        messageInformation("des Auteurs"," \t\t\t\tNDJAMA JOY JEDIDJA \n"
        +"\t\t\tDJOUBISSIE OLAMA MARCELIN \n"
        +"\t\t\t Elèves Ingénieurs à l'Ecole Nationale\n"+
        " \t\t\t\tSupérieure Polytechnique\n"+
        " \t\t\tCopyright (C) Novembre 2015\n"+
        " \t\t\t\t\t GENIE INFOS \n");
    }
    
    /**
     * cette méthode gère les messages d'erreur de notre application
     */
    private void messageErreur(String titre,String sms)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(LoadApp.getPrimaryStage()); 
            message(alert,titre,sms);
    }
    
    
    
    /**
     * cette méthode gère les messages d'information de notre application
     */
    private void messageInformation(String titre,String sms)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
            message(alert,titre,sms);
    }
    
    /**
     * Donner le mnemonic au bouton OK de la page donnnées
     */
    @FXML
    private void donnees_getMnemonic()
    {
        mnemonic(donnees_button);
    }
    
    /**
     * cette méthode permet de génerer la boite de dialogue d'erreur proprement dite
     */
    private void message(Alert alert,String titre,String messag)
    {
        alert.initOwner(LoadApp.getPrimaryStage()); 
            // Get the Stage.
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            
            File f = new File("./photo/iconCSP.png");
            
            // Add a custom icon.
            stage.getIcons().add(new Image("file:/"+f.getAbsolutePath()));
           
            alert.setTitle("Message d'erreur");
            alert.setHeaderText("\t\t\tA propos "+titre);
            alert.setContentText(messag);
            alert.showAndWait();
    }
    
    
    /**
     * pour charger notre fichier
     */
    @FXML
    private void loadFile()
    {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XLSX, XLS files (*.xlsx,*.xls)", "*.xlsx","*.xls");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        filePoint = fileChooser.showOpenDialog(LoadApp.getPrimaryStage());
        
        bienvenue_chargement.setText("Récupération des données en cours ...");
        bienvenue_progress.progressProperty().unbind();
        bienvenue_progress.progressProperty().bind(LoadPointFromFile.progressProperty());
        LoadPointFromFile.stateProperty().addListener((ObservableValue<? extends Worker.State> observableValue, 
                 Worker.State oldState, Worker.State newState) -> {
             switch (newState) {
                 case SCHEDULED:
                 case READY:
                 case RUNNING:
                     break;
                 case SUCCEEDED:
                     //AnchorPane temp = (AnchorPane)anchorResultat.getChildren().get(3);
                     bienvenue_chargement.setText("Récupération terminée.");
                     bienvenue_button.setDisable(true);
                     bienvenue_buttonBool = true;
                     //LoadPointFromFile.cancel();
                     break;
                 case CANCELLED:
                      break;
                 case FAILED:
                     //System.out.println("************** failed ************");
                     messageErreur("du fichier chargé", "Mauvais format de données\n"
                             + "Verifier que chaque ville est representée sur la première ligne\n"
                             + "comme sur la première colonne et dans le même ordre.");
                    restart();
                     break;
                     
             }
        });
        LoadPointFromFile.start();
    }
    
    
    /**
     * to load a licence data file in the background
     */
    Service<Void> LoadPointFromFile = new Service<Void>(){

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>(){
            private String[] donnee;
            private String[] numVille;
            private int heuris = -1;
            private boolean heur = false;
            private String[] don;
            private HSSFRow rows;
        @Override
        protected Void call() throws FileNotFoundException{
            // Sauvegarder le fichier ici.
     
            String filename = filePoint.getName();
            
           // //System.out.println("We are handling the document");
           
         try  {
            FileInputStream fis = new FileInputStream(filePoint); 
            //System.out.println("before entering");
            if(lookforextension(filename) == 1)
            {
                XSSFWorkbook workbook; 
                workbook = new XSSFWorkbook(fis);
                //System.out.println("what is happening?");
                int k = 0;
                int j;
                XSSFSheet spreadsheet = workbook.getSheetAt(0);
                int nbr = spreadsheet.getLastRowNum();
                //System.out.println("last one "+ nbr);
                Iterator < Row > rowIterator = spreadsheet.iterator();
                donnee = new String[nbr+2];
                don = new String[nbr+2];
                numVille = new String[nbr+1];
                
                for (int i = 0; i < donnee.length; i++)
                    {
                        donnee[i] = "";
                    }
                for (int i = 0; i < numVille.length; i++)
                {
                    numVille[i] = "";
                }
                for(int i = 0; i < don.length; i++)
                {
                    don[i] = "";
                }
                while (rowIterator.hasNext())
                {
                    row = (XSSFRow) rowIterator.next();
                    Iterator < Cell > cellIterator = row.cellIterator();
                    j = 1;
                    //System.out.println("k  value" + k);
                    while ( cellIterator.hasNext())
                    {
                        Cell cell = cellIterator.next();
                        String cells = "";
                      
                        switch (cell.getCellType())
                        {
                            
                            case Cell.CELL_TYPE_NUMERIC:
                                    cells = ""+cell.getNumericCellValue();
                                    break;
                            
                            
                            case Cell.CELL_TYPE_STRING:
                                cells = cell.getStringCellValue();
                                break;
                            
                        }//*/
                        //System.out.println("j value"+ j);
                        don[j - 1] = cells;
                         if(!cells.isEmpty())
                         {
                                donnee[j-1] = cells;
                                //System.out.println("inside cells "+donnee[j-1]);
                         }
                         //System.out.println("before modifying " + cells);
                         if (cells.isEmpty() || cells == null)
                        {
                            cells = "---------";
                        }
                      
                        //System.out.println("cellls " + cells);
                        j++;
                    }
                    //System.out.println("k  " + k);
                        if(k == 0)
                        {  
                            //System.out.println("inside the if");
                            for(int w = 1; w < donnee.length; w++)
                            {
                                if(donnee[w].equalsIgnoreCase("Heuristique"))
                                {
                                    heur = true;
                                    heuris = w;
                                }
                                else
                                {
                                    if(donnee[w] != null && !donnee[w].isEmpty() )
                                    {
                                        //System.out.println("donnee "+donnee[w]+" ** "+w);
                                        Point point = new Point();
                                        point.setNom(donnee[w]);
                                        ensemble.add(point);
                                        numVille[w] = donnee[w];   
                                    }
                                }
                            }
                        }
                        else
                        {
                          fill(don,donnee,k);    
                        }
                        //System.out.println("we are fighting");
                      k++;  
                    }
                    //System.out.println("ensemble " + ensemble);
                        
            }
            else
            {
        
                //System.out.println("else");
                HSSFWorkbook workbook; 
                workbook = new HSSFWorkbook(fis);
                int k = 0;
                int j;
                HSSFSheet spreadsheet = workbook.getSheetAt(0);
                int nbr =  spreadsheet.getLastRowNum();
                //System.out.println("last one "+nbr);
                Iterator < Row > rowIterator = spreadsheet.iterator();
                donnee = new String[nbr+2];
                don = new String[nbr+2];
                numVille = new String[nbr+1];
                
                for (int i = 0; i < donnee.length; i++)
                    {
                        donnee[i] = "";
                    }
                    for (int i = 0; i < numVille.length; i++)
                    {
                        numVille[i] = "";
                    }
                    for(int i = 0; i < don.length; i++)
                    {
                        don[i] = "";
                    }
                while (rowIterator.hasNext())
                 {
                    rows = (HSSFRow) rowIterator.next();
                    Iterator < Cell > cellIterator = rows.cellIterator();
                    j = 1;
                    //System.out.println("k  value" + k);
                    while ( cellIterator.hasNext())
                    {
                        Cell cell = cellIterator.next();
                        String cells = "";
                      
                        switch (cell.getCellType())
                        {
                            
                            case Cell.CELL_TYPE_NUMERIC:
                                    cells = ""+cell.getNumericCellValue();
                                    break;
                            
                            
                            case Cell.CELL_TYPE_STRING:
                                cells = cell.getStringCellValue();
                                break;
                            
                        }//*/
                        //System.out.println("j value"+ j);
                        don[j - 1] = cells;
                         if(!cells.isEmpty())
                         {
                                donnee[j-1] = cells;
                                //System.out.println("inside cells "+donnee[j-1]);
                         }
                         //System.out.println("before modifying " + cells);
                         if (cells.isEmpty() || cells == null)
                        {
                            cells = "---------";
                        }
                      
                        //System.out.println("cellls " + cells);
                        j++;
                    }
                    //System.out.println("k  " + k);
                        if(k == 0)
                        {  
                            //System.out.println("inside the if");
                            for(int w = 1; w < donnee.length; w++)
                            {
                                if(donnee[w].equalsIgnoreCase("Heuristique"))
                                {
                                    heur = true;
                                    heuris = w;
                                }
                                else
                                {
                                    //System.out.println("donnee "+donnee[w]);
                                    Point point = new Point();
                                    point.setNom(donnee[w]);
                                    ensemble.add(point);
                                    numVille[w] = donnee[w];
                                }
                            }
                        }
                        else
                        {
                          fill(don,donnee,k);    
                        }
                        //System.out.println("we are fighting");
                      k++;  
                    }
                    //System.out.println("ensemble " + ensemble);
                }
        }
        catch(IOException | NumberFormatException  e)
        {
            LoadApp.printStrace(e);
        }
            updateProgress(10000, 10000);
            return null;
        } 

            private void fill(String[] don, String[] donnee,int k) {
                //System.out.println("ensemble "+ensemble+" taille "+ensemble.size());
            for(int y = 0; y < donnee.length ; y++)
            {
                ////System.out.println("taille donnee"+donnee.length);
                //System.out.println("k ****"+k+"****y "+y); 
                //System.out.println("donnee "+donnee[y]+" don  "+don[y]);
                if(!donnee[0].equalsIgnoreCase(numVille[k]) )
                {
                    LoadPointFromFile.cancel();
                }
                if(heur)
                {
                    ensemble.get(k-1).setHeuristique(Double.parseDouble(donnee[heuris]));   
                }
                else
                {
                    ensemble.get(k-1).setHeuristique(-1);  
                }
                if(don[y].equals(donnee[y]) && y > 0 && y < (donnee.length - 1)  && !don[y].equals("---------") )
                    {
                        Double doub = null;
                        try {
                            //System.out.println("****"+ensemble.get(y - 1).getNom());
                            doub = Double.parseDouble(donnee[y]);
                            //System.out.println("doub "+doub);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        ////System.out.println("nom "+ensemble.get(y).getNom());
                        ensemble.get(k-1).getSuccesseur().put(ensemble.get(y - 1).getNom(),
                                doub);
                          //System.out.println("save k ****"+k+"****y "+y);                            
                    }
                }

                                //*/
            }
        };
        };
            
        };
    
    /**
     * permet de redemarrer l'application
     */
    @FXML 
    void restart()
    {
        closeApp();
        LoadApp app = new LoadApp();
        donnees_data.clear();
        ensemble.clear();
        app.start(LoadApp.getPrimaryStage());
    
    }
    
    
    /**
     * permet de determiner l'extension d'un fichier
     * @param filename
     * @return 
     */
    private static int lookforextension(String filename) {
        
        String result;
        
        int pos = filename.lastIndexOf('.');
        result = filename.substring(pos + 1, filename.length());
      
        //System.out.println("file "+filename);
        if(result.equals("xls"))
        {
            return 0;
        }
        else
        {
            return 1;
        }
    }
    
    
    /**
     * cette méthode permet de une nouvelle valeur et à l'ajouter au domaine de la ensemble selectionnée
     */
    @FXML
    private void donnee_addvalue()
    {
        Boolean ok = true;
         Double val = 0.0;
        try {
           val = Double.parseDouble(donnees_text.getText());
        } catch (Exception e) {
            ok = false;
            messageErreur("de l'heuristique a ajoutée",
                    "Entrer des nombres réels uniquement");
        }
        //si c'est bien un nombre qu'on a entré
        if(ok)
        {
                donnees_text.setText(null);
                int temp = 0;
                int i = 0;
                for(Point auto : ensemble)
                {
                    i++;
                    if(auto.getNom().equals(""+donnees_choiceBox.getValue()))
                    {
                        temp = i - 1;
                    }
                }
                    
                ensemble.get(temp).setHeuristique(val);
                donnees_changeOccur = true;
                PointFx pointfx = new PointFx(""+donnees_choiceBox.getValue(),""+val,false);
                donnees_data.add(pointfx);
                if(val == 0)
                {
                    //System.out.println("Point final ");
                    ensemble.get(temp).setFina(true);
                    pointfx.setFinale(true);
                }
                donnees_choiceBox.getItems().remove(donnees_choiceBox.getValue());
                if(donnees_choiceBox.getItems().size() > 0)
                {
                    donnees_choiceBox.setValue(donnees_choiceBox.getItems().get(0));
                }
                else
                {
                    donnees_choiceBox.setDisable(true);
                    donnees_text.setDisable(true);
                    donnees_button.setDisable(true);
                }
        }
    }
    
    
    /**
     *Cette méthode permet d'ajouter un mnemonic à un bouton 
     */
    private void mnemonic(Button button)
    {
       // button.getScene().getAccelerators().clear();
        button.getScene().getAccelerators().
           put(new KeyCodeCombination(KeyCode.ENTER), (Runnable) () -> {
            button.fire();
        });
    }
}


/**
 * cette classe nous permettra de remplir notre tableau de points pour la collecte des données
 */
class PointFx
{
        private  final SimpleStringProperty point;
        private final SimpleStringProperty heuristique;
        private  SimpleBooleanProperty finale;

    public SimpleStringProperty getPointProp() {
        return point;
    }

    public void setFinale(Boolean finale) {
        this.finale = new SimpleBooleanProperty(finale);
    }

    public String getPoint() {
        return point.getValue();
    }
    
    public SimpleStringProperty getHeuristiqueProp() {
        return heuristique;
    }

    public String getHeuristique() {
        return heuristique.getValue();
    }
    
    public SimpleBooleanProperty getFinaleProp() {
        return finale;
    }

    public Boolean getfinale() {
        return finale.getValue();
    }
    
    public PointFx(String point, String heuristique, Boolean finale) {
        this.point = new SimpleStringProperty(point);
        this.heuristique = new SimpleStringProperty(heuristique);
        this.finale = new SimpleBooleanProperty(finale);
    }

    
}


