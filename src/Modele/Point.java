/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import java.util.HashMap;

/**
 *
 * @author NDJAMA
 */
public class Point {
    
    /**
     * l'heuristique de notre point
     */
    private double heuristique;
    /**
     * un boolean qui permet qui permet de dire si notre point un sommet
     */
    private boolean sommet;
    /**
     * un boolean qui permet qui permet de dire si notre point final
     */
    private boolean fina;
    /**
     * la fonction d'evaluation
     */
    private double evaluation;
    /**
     * le cout pour passer de l'état initial au noeud courant
     */
    private double cout;

    /**
     * le nom de notre point
     */
    private String nom;
    /**
     * Les successeurs de notre point 
     */
    private final HashMap<String,Double> successeur = new HashMap<>();

    public HashMap<String, Double> getSuccesseur() {
        return successeur;
    }

    public double getHeuristique() {
        return heuristique;
    }

    public void setHeuristique(double heuristique) {
        this.heuristique = heuristique;
    }

    public boolean isSommet() {
        return sommet;
    }

    public void setSommet(boolean sommet) {
        this.sommet = sommet;
    }

    public boolean isFina() {
        return fina;
    }
    
    public double getCout() {
        return cout;
    }

    public void setCout(double cout) {
        this.cout = cout;
    }
    
    public void setFina(boolean fina) {
        this.fina = fina;
    }

    public double getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(double evaluation) {
        this.evaluation = evaluation;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return  nom +"("+ evaluation +") " ;
    }
    
    
    
}
