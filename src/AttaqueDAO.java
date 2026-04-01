import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AttaqueDAO {
    public final static int MAX_ATTAQUES = 200;
    private DatabaseManager dbm;

    public AttaqueDAO() {
        dbm = new DatabaseManager();
        try {
            dbm.connect();
        } catch (SQLException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }

    public Attaque[] chargeAttaque(DatabaseManager dbm) {
        String sql = "SELECT * FROM attaques";
        Attaque[] tabAttaque = new Attaque[MAX_ATTAQUES];
        try {
            PreparedStatement requete = dbm.getConnection().prepareStatement(sql);
            ResultSet donnee = requete.executeQuery();
            int i = 0;
            while (donnee.next() && i < MAX_ATTAQUES) {
                String libelle  = donnee.getString("libelle");
                int puissance   = donnee.getInt("puissance");
                int typeId      = donnee.getInt("type_id");
                tabAttaque[i]   = new Attaque(libelle, puissance, typeId);
                i++;
            }
        } catch (SQLException e) {
            System.out.println("ERREUR DU CHARGEMENT DES ATTAQUES : " + e.getErrorCode());
        }
        return tabAttaque;
    }

    public Attaque[] recupAttaquesAleatoires(int typeId, DatabaseManager dbm) {
        String sql = "SELECT libelle, puissance, type_id FROM attaques WHERE type_id = ? ORDER BY RAND() LIMIT 4";
        Attaque[] tabAttaque = new Attaque[4];
        try {
            PreparedStatement pstmt = dbm.getConnection().prepareStatement(sql);
            pstmt.setInt(1, typeId);
            ResultSet donnee = pstmt.executeQuery();
            int i = 0;
            while (donnee.next() && i < 4) {
                String libelle = donnee.getString("libelle");
                int puissance  = donnee.getInt("puissance");
                int type       = donnee.getInt("type_id");
                tabAttaque[i]  = new Attaque(libelle, puissance, type);
                i++;
            }
        } catch (SQLException e) {
            System.out.println("ERREUR ATTAQUES ALEATOIRES : " + e.getErrorCode());
        }
        return tabAttaque;
    }
}