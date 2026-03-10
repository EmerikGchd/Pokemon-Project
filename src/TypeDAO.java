import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TypeDAO {

    public final static int GEN1 = 15 ;
    public Type[] chargeType(DatabaseManager dbm){
        String sql = "SELECT * from type" ;
        Type[] tabType = new Type[GEN1] ;
        try {
            PreparedStatement requete = dbm.getConnection().prepareStatement(sql);
            ResultSet donnee = requete.executeQuery() ;
            int i = 0 ;
            while (donnee.next()) {
                String libelle = donnee.getString("libelle");
                tabType[i] = new Type(libelle);
                i++;
            }
        }
        catch(SQLException e)  {
            System.out.println("ERREUR DU CHARGEMENT DES TYPES : " + e.getErrorCode());
            

        }
        return tabType;
    }
}