import java.sql.SQLException;
import java.util.Random;
import java.util.Scanner;
@SuppressWarnings("ConvertToTryWithResources")

public class Partie{

    public static final int NB_POKEMON_PAR_EQUIPE = 6;
    private Joueur joueur1;
    private Joueur joueur2;
    private String nomJ;
    Pokemon[] equipeJ;
    Random rand = new Random();
    AttaqueDAO aDAO = new AttaqueDAO();
    Scanner scJ1 = new Scanner(System.in);
    Scanner scJ = new Scanner(System.in);

    //Méthode pour le début de la partie, elle gère la création des joueurs et de leurs équipes
    @SuppressWarnings("ConvertToTryWithResources")
    public Joueur[] debutPartie(DatabaseManager dbm){

        Joueur[] tabJoueur = new Joueur[2];
        System.out.println("Début de la partie");
        Scanner nbJoueur = new Scanner(System.in);
        int choix;
        do{
            System.out.println("Choisissez le nombre de joueurs : ");
            System.out.println("/1/  1 Joueur (contre l'IA)");
            System.out.println("/2/  2 Joueurs");
            choix = nbJoueur.nextInt();
            if (choix == 1){
                System.out.println("Vous avez choisi de jouer contre l'IA");
                pause(500);


                //creation de l'IA
                nomJ = "Professeur Axeman";
                equipeJ = new Pokemon[NB_POKEMON_PAR_EQUIPE];
                for (int i = 0; i < equipeJ.length; i++) {
                    int idPokeIA = rand.nextInt(151) + 1;
                    Attaque[] attaquesPokeIA = aDAO.recupAttaquesPokemon(idPokeIA);
                    PokemonDAO pokeDAO = new PokemonDAO();
                    equipeJ[i] = pokeDAO.chargerParId(idPokeIA, attaquesPokeIA);
                }
                JoueurIA joueurIA = new JoueurIA(nomJ, equipeJ);
                System.out.println("\n Voici votre adversaire : \n ");
                pause(500);
                joueurIA.afficherJoueur();
                

                //creation du joueur humain
                joueur1 = creationJH(dbm);
                System.out.println("Voici votre dresseur : ");
                pause(500);
                joueur1.afficherJoueur();

                tabJoueur[0] = joueurIA;
                tabJoueur[1] = joueur1; 

            } else if (choix == 2){
            System.out.println("Vous avez choisi de jouer contre un autre joueur");
                //creation du joueur 1
                System.out.println("Création du Joueur 1 : ");
                joueur1 = creationJH(dbm);

                //creation du joueur 2
                System.out.println("Création du Joueur 2 : ");
                joueur2 = creationJH(dbm);

                System.out.println("Ce combat opposera " + joueur1.getNom() + " à " + joueur2.getNom());
                System.out.println("Voici vos equipes : \n");
                pause(500);
                joueur1.afficherJoueur();
                pause(1000);
                System.out.println("\n VS \n");
                pause(500);
                joueur2.afficherJoueur();
                pause(1000);

                tabJoueur[0] = joueur1;
                tabJoueur[1] = joueur2;
        
            }

            if (choix != 1 && choix != 2){
                System.out.println("Choix invalide, veuillez réessayer, Choix Valide : 1 ou 2");        
            }
            
        } while (choix != 1 && choix != 2);
        nbJoueur.close(); 

        return tabJoueur;
    }


    //Methode du combat qui gère le déroulement du combat, les tours, les attaques, les changements de pokémon, etc.
    public void combat(Joueur[] tabJoueur){
        //TODO : implémenter la logique du combat
    }



    private JoueurHumain creationJH(DatabaseManager dbm){

        System.out.println("Entrez votre nom : ");
        nomJ = scJ1.nextLine();
        equipeJ = choisirPokemonJH(dbm);
        JoueurHumain joueur = new JoueurHumain(nomJ, equipeJ);
        try {
            dbm.disconnect();
        } catch (SQLException e) {
            System.out.println("Erreur lors de la déconnexion ! Code Erreur : " + e.getErrorCode());
        }
        return joueur;        
    
    }

    
    private Pokemon[] choisirPokemonJH(DatabaseManager dbm) {
        try {
            dbm.connect();
        } catch (SQLException e) {
            System.out.println("Erreur lors de la connexion : " + e.getMessage());
        }
        System.out.println("Voici la liste des Pokémon disponibles : ");
        pause(1000);
        PokemonDAO pokeDAO = new PokemonDAO();
        pokeDAO.chargerTous();

        int idPokeJ1;
        equipeJ = new Pokemon[NB_POKEMON_PAR_EQUIPE];
        for (int i = 0; i < equipeJ.length; i++) {
            do{
                System.out.println("Entrez le numéro de votre Pokémon n°" + (i+1) + " : ");
                idPokeJ1 = scJ.nextInt();
            }while(idPokeJ1 < 1 || idPokeJ1 > PokemonDAO.GEN1);
            Attaque[] attaquesPokeJ1 = new AttaqueDAO().recupAttaquesPokemon(idPokeJ1);
            equipeJ[i] = pokeDAO.chargerParId(idPokeJ1, attaquesPokeJ1);
            scJ.nextLine();
        }
        try {
            dbm.disconnect();
        } catch (SQLException e) {
            System.out.println("Erreur lors de la déconnexion ! Code Erreur : " + e.getErrorCode());
        }
        return equipeJ;
    }
    //Methode pour le tour a tour du combat, elle gère les actions possibles pour chaque joueur (attaquer, changer de pokémon, fuire)
    public void tourCombat(Joueur[] tabJoueur){
        //TODO : implémenter la logique du tour a tour du combat
    }
    
    public static void pause(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    

}
