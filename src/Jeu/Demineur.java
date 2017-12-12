package Jeu;

import java.util.Random;
import java.util.Scanner;

public class Demineur {
	
	public static void main(String[] args){
        
        int x,y;
        Scanner sc=new Scanner(System.in);
        
        System.out.println("Taille du tableau (entre 4 et 10):");
        int taille=sc.nextInt();
        while (taille<4 || taille>10){
            System.out.println("Taille du tableau (entre 4 et 10):");
            taille=sc.nextInt();
        }
        
        System.out.println("Probabilité de présence d'une bombe (entre 0 et 1):");
        float prob=sc.nextFloat();
        while (prob<0 || prob>1){
            System.out.println("Probabilité de présence d'une bombe (entre 0 et 1):");
            prob=sc.nextFloat();
        }
        
        //création du tableau des bombes et de la grille de jeu
        int[][] tB = new int[taille][taille];
        tB = creationTabBombe(prob, taille);
        String[][] tG = new String[taille][taille];
        tG = creationGrille(taille);
        
        //tableau pour l'affichage en cas de défaite
        String[][] tDef = new String[taille][taille];
        for (int i=0;i<taille;i++){
            for (int j=0;j<taille;j++){
                tDef[i][j]=Integer.toString(tB[i][j]);
            }
        }
        
        int nbBombe = compte(tB, 1, taille);
        System.out.println("Il y a "+nbBombe+" bombe(s)");
        
        //Début de la partie
        boolean defaite=false;
        affichageTab(taille, tG);
        while (reste(taille, tB, tG)==false && defaite==false){
            
            System.out.println("coordonnée en x");
            x=sc.nextInt();
            while(x<0 || x>taille){
                System.out.println("coordonnée en x");
                x=sc.nextInt();
            } 
            
            System.out.println("coordonnée en y");
            y=sc.nextInt();
            while(y<0 || y>taille){
                System.out.println("coordonnée en y");
                y=sc.nextInt();
            } 

            if(tour(x,y,tB,tG,taille)){
                System.out.println("");
                affichageTab(taille,tG);
            }    
            else {
                System.out.println("Vous perdez une jambe");
                System.out.println("Fin de partie");
                System.out.println("");
                affichageTab(taille,tDef);
                defaite=true;
            }
        }
        if (defaite==false)
            System.out.println("Partie gagnée");
    }

    //Création du tableau contenant les bombes
    public static int[][] creationTabBombe(float proba, int tailleTab){

        int[][] tab = new int[tailleTab][tailleTab];
        Random rand = new Random();
        while(compte(tab,1,tailleTab)==0){
            for (int i=0;i<tailleTab;i++){
                for (int j=0;j<tailleTab;j++){
                    float t=rand.nextFloat();
                    if (t <= proba)
                        tab[i][j] = 1;
                }
            }
        }
        return tab;
    }

    public static String[][] creationGrille(int tailleTab){
        String[][] tab = new String[tailleTab][tailleTab];
        for (int i=0;i<tailleTab;i++)
            for (int j=0;j<tailleTab;j++)
                tab[i][j]="*";
        return tab;
    }

    // Affichage du tableau 
    public static void affichageTab(int tailleTab,String tab[][]){
        for (int i=0;i<tailleTab;i++){
            for (int j=0;j<tailleTab;j++)
                System.out.print(tab[i][j]+" ");
            System.out.println("");
        }
    }

    // Tour du joueur, si il y a une bombe, renvoie False, sinon, renvoie True, et remplace la case jouée par un espace
    public static boolean tour(int x, int y, int[][] tabB, String[][] tabJ, int tailleTab){
        if (tabB[x][y] == 1)
            return false;
        else {
            int t= autour(x, y, tailleTab, tabB);
            tabJ[x][y]=Integer.toString(t);
            return true;
        }
    }

    // Compte le nombre de bombes dans le tableau de bombes
    public static int compte(int[][] tab,int val, int tailleTab){
        int c = 0;
        for (int i=0;i<tailleTab;i++){
            for (int j=0;j<tailleTab;j++){
                if (tab[i][j] == val)
                    c += 1;
            }
        }  
         return c;
    }
    
    //Compte le nombre de cases non découvertes dans le tableau de jeu
    public static int compteCasesRestantes(String[][] tab,String val, int tailleTab){
        int c = 0;
        for (int i=0;i<tailleTab;i++){
            for (int j=0;j<tailleTab;j++){
                if (tab[i][j].equals(val))
                    c += 1;
            }
        }
        return c;
    }

    // Compte le nombre de bombes autour de la case
    public static int autour(int x, int y, int tailleTab, int[][] tabB){
        int c = 0;
        for (int i=x-1;i<x+2;i++){
            if (i < 0 || i >= tailleTab )
                continue;
            for (int j=y-1;j<y+2;j++){
                if ( j < 0 || j >= tailleTab)
                    continue;
                else if (tabB[i][j]==1)
                    c++;
            }
        }               
        return c;
    }

    public static boolean reste(int tailleTab, int [][]tabB, String[][]tabJ){
        int nbB = compte(tabB, 1, tailleTab);
        int nbV = compteCasesRestantes(tabJ, "*", tailleTab);
        if (nbB == nbV)
            return true;
        else
            return false;
    }
}
