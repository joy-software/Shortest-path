/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlleur;

import Modele.Point;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;



/**
 *
 * @author NDJAMA
 */
public class AlgoCoutUniforme {
    
    /**
     * Cet attribut contiendra notre liste Open
     */
    private ArrayList<Point> open = new ArrayList<>();
    /**
     * cet attribut contiendra notre liste CLose
     */
    private ArrayList<Point> close = new ArrayList<>();
    /**
     * le point ayant la plus faible valeur pour la fonction 
     * d'evaluation
     */
    private static Point faible = null;
    /**
     * Conserve le chainage de nos différentes étapes
     */
    private ArrayList<HashMap<String,String>> chainage = new ArrayList<>();
    /**
     * le string contenant le chainage final de notre parcours
     */
    public String result = "";
    /**
     * l'index de notre chaine
     */
    private int indexChaine;
    /**
     * Notre fichier de sortie
     */
    private static PrintWriter fich;
    /**
     * permet de compter les etapes de l'exécution de l'algorithme
     */
    private static int iter = 1;
    
    
    public void callmeilleurDabord(ArrayList<Point> ensemble)
    {
    
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy  HH:mm:ss");
        String dateStr = sdf.format(new Date());
        String nom = "outputs/resultat Algo Cout Uniforme.joar";

        try {
                fich = new PrintWriter(new BufferedWriter(new FileWriter(nom)));
                fich.println("\t\t\t\t *****************NOUVELLE EXECUTION (ALGORITHME COUT UNIFORME) DU "+dateStr+"*****************");			//true c'est elle qui permet d'écrire à la suite des donnée enregistrer et non de les remplacé 
            } catch (IOException e1) {
            } 
        fich.println();
        fich.println();
        fich.println("\tNotons ici qu'on exécute l'algorithme meilleur d'abord\n"
                + "\tPour l'algorithme Cout uniforme la fonction d'evaluation f est égale au coût (f(n) = g(n))");
        fich.println();
        fich.println();
        
        /**
         * Etape 1:
         * on insère le point initial dans la liste OPEN
         */
        initalisation(ensemble);
        meilleurDabord(ensemble);
    }
    
    /**
     * permet de mettre en oeuvre l'algorithme meilleur d'abord
     * @param ensemble l'ensemble de nos points
     * pour cet algorithme la fonction d'évaluation est égale 
     * à l'heuristique
     * @return z
     */
    private boolean meilleurDabord(ArrayList<Point> ensemble)
    {
        fich.println("\t\t\t********Etape n°:"+iter+"********");
        iter++;
        /**
         * etape 2 on vérifie que OPen n'est pas vide
         */
        if(!possede(open))
        {
            fich.println("\t\t\t******FIN******");
            fich.println("Fin de l'execution de l'algorithme Open est vide");
            fich.close();
            return false;
        }
        else
        {
            /**
             * etape 3 
             * ON retire de OPEN pour CLOSE
             */
            plusfaiblepoint();
            
            /**
             * etape 4 on vérifie si faible est final
             */
            if(faible.isFina())
            {
                fich.println("Open: "+open);
                fich.println("Close: "+close);
                fich.println("Chainage: ");
                fich.println();
                ReconstituerChainage();
                return true;
            }
            /**
             * etape 5 on vérifie que ce point possède des succeseurs
             * on passe à cette étape si le point faible n'est pas final
             */
            if(faible.getSuccesseur().isEmpty())
            {
                fich.println("Open: "+open);
                fich.println("Close: "+close);
                fich.println("Chainage: ");
                fich.println();
                meilleurDabord(ensemble);
            }
            else
            {
                String tempfich = "";
                /**
                 * on engendre les successeurs
                 */
                HashMap<String,String> chaine = new HashMap<>();
                for(String auto : faible.getSuccesseur().keySet())
                {
                    Point temp = searchEnsemble(ensemble,auto);
                    /**
                     * on calcule la valeur de la fonction d'evaluation
                     * pour le successeur trouvé
                     */
                    temp.setCout(faible.getCout() + faible.getSuccesseur().get(auto));
                    //System.out.println("pere: "+faible.getNom()+" cout: "+faible.getCout()+" **** fils:"+ auto+ " cout: " + temp.getCout());
                    temp.setEvaluation(temp.getCout());
                    //System.out.println("Evaluation de "+auto+ " "+ temp.getEvaluation());
                    /**
                     * on insère dans open
                     */
                    if( !contains(open,temp) && !contains(close,temp))
                    {
                        open.add(temp);
                    }
                    else
                    {
                        //System.out.println("Update value ***");
                        /**
                         * on update si la valeur contenue dans open est plus
                         * grande que la nouvelle valeur d'evaluation
                         */
                        if(contains(open,temp))
                        {
                            double test = open.get(indexOf(open,temp)).getEvaluation();
                            if(test > temp.getEvaluation())
                            {
                                open.get(indexOf(open,temp)).setEvaluation(temp.getEvaluation());
                            }
                        }
                        if(contains(close,temp))
                        {
                            double test = close.get(indexOf(close,temp)).getEvaluation();
                            if(test > temp.getEvaluation())
                            {
                                close.remove(indexOf(close,temp));
                                open.add(temp);
                            }   
                        }
                    }
                    /**
                     * on cree notre chaine
                     */
                    tempfich += temp.getNom() + "->" + faible.getNom() + "; ";
                    chaine.put(temp.getNom(),faible.getNom());
                    
                }
                fich.println("Open: "+open);
                fich.println("Close: "+close);
                fich.println("Chainage: "+tempfich);
                fich.println();
                //System.out.println("chaine "+chaine);
                chainage.add(chaine);
                meilleurDabord(ensemble);
                //ajouter l'appel à la nouvelle méthode
            }
        }   
        
        
        /**
         * attention!!!!!!1
         */
        return false;
    }

    
    /**
     * cette méthode permet de reconstituer le chainage du graphe*
     * a partir du point initial vers le point final
     */
    private void ReconstituerChainage() {
    
        indexChaine = chainage.size() -1 ;
        String tempnom = faible.getNom();
        //System.out.println("faible "+faible.getNom());
        result = tempnom;
        //System.out.println("********reconstitution du chainage********");
        fich.println("\t\t\t********Reconstitution du chainage********");
        do
        {
            HashMap<String, String> temp = recherche(chainage,tempnom);
            //System.out.println("chainage "+ (indexChaine + 1) + temp);
            String ptiteChaine = temp.get(tempnom)+"->"; 
            result = ptiteChaine + result;
            fich.println(ptiteChaine+tempnom);
            tempnom = temp.get(tempnom);
        }
        while(indexChaine >= 0);
        
        //System.out.println("le resultat final "+result);
        fich.println("le resultat final "+result);
        fich.close();
    }


    /**
     * Etape 1:
     * on insère le point initial dans la liste OPEN
     */
    private void initalisation(ArrayList<Point> ensemble) {
        //System.out.println("Debut initialisation");
        fich.println("\t\t\t********Initialisation********");
        for(Point auto : ensemble)
        {
            if(auto.isSommet())
            {
                auto.setEvaluation(auto.getHeuristique());
                //System.out.println("Initialisation terminée");
                open.add(auto);  
            }
        }
        fich.println("Choix: ");
        fich.println("Open: "+open);
        fich.println("Close: "+close);
        fich.println("Chainage: ");
        fich.println();
    }

    /**
     * etape 2 on vérifie que OPen n'est pas vide
     */
    private boolean possede(ArrayList<Point> open) {
        //System.out.println("Open vide?");
        return !open.isEmpty();   
    }


    /**
     * etape 3 
     * ON retire de OPEN pour CLOSE le point ayant la plus
     * faible valeur d'evaluation
     */
    private void plusfaiblepoint() {
        //System.out.println("get point faible");
        double temp = open.get(0).getEvaluation();
        Boolean trouve = false;  
        int index = 0;
        int indexfaible = 0;
        
            for(Point auto : open)
            {
                if(auto.getEvaluation() < temp)
                {
                    trouve = true;
                    temp = auto.getEvaluation();
                    indexfaible = index;
                }
                index++;
            }
        
            if(!trouve && !open.isEmpty())
            {
                faible = open.get(0);
            }
            else
            {
                faible = open.get(indexfaible);
            }
            
        //System.out.println("faible : "+faible);
        open.remove(indexOf(open,faible));
        close.add(faible);
        fich.println("Choix : "+faible);
        //System.out.println("*********************************Open************************");
        //System.out.println(open);
        //System.out.println("*********************************Close************************");
        //System.out.println(close);
   
    }

    
    /**
     * cette méthode permet de parcourir notre arraylist de point
     * pour en faire ressortir celui dont le nom correspond avec le 
     * nom passé en paramètres
     * @param ensemble
     * @return 
     */
    private Point searchEnsemble(ArrayList<Point> ensemble,String nom) {
        
        for(Point auto : ensemble)
        {
            if(auto.getNom().equals(nom))
            {
                return auto;
            }
        }
        return null;
    }

    
    /**
     * vérifie que le point passé en paramètre est contenu dans l'arraylist de points
     * @param open
     * @param temp
     * @return boolean
     */
    private boolean contains(ArrayList<Point> open, Point temp) {
        for(Point auto : open)
        {
            if(auto.getNom().equals(temp.getNom()))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * permet de renvoyer l'indice d'un point dans un ArrayList de point
     * @param open
     * @param temp
     * @return int
     */
    private int indexOf(ArrayList<Point> open, Point temp) {
    
        for(int i = 0; i < open.size(); i++)
        {
            if(open.get(i).getNom().equals(temp.getNom()))
            {
                return i;
            }
        }
        return -1;
    }

    
    /**
     * permet de rechercher et retrouver le chainage de l'element courant
     * @param chainage
     * @param tempnom
     * @return 
     */
    private HashMap<String, String> recherche(ArrayList<HashMap<String, String>> chainage, String tempnom) {
        HashMap<String, String> resultChaine = null;
        boolean trouve = false;
        
        do
        {
            if(chainage.get(indexChaine).containsKey(tempnom))
            {
                trouve = true;
                resultChaine = chainage.get(indexChaine);
            }
            indexChaine--;
        }
        while(!trouve);
        return resultChaine;
    }
    
    

}