import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TypeDAO {

    public final static int GEN1 = 15 ;
    private DatabaseManager dbm;
    public TypeDAO(){
        
        dbm = new DatabaseManager();

        try{
            dbm.connect();
        }catch(SQLException e){
            System.out.println("Erreur : " + e.getMessage());
        }
    }

    public Type[] chargeType(DatabaseManager dbm){
        String sql = "SELECT * from type" ;
        Type[] tabType = new Type[GEN1] ;// definir la taille du tab //
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


    public double recupMulti(int fkAtt,int fkDef ,DatabaseManager dbm){
        String sql = "SELECT multi from efficacite WHERE fkAtt = ? and fkDef= ? LIMIT 1;";
        try{


        PreparedStatement pstmt = dbm.getConnection().prepareStatement(sql);
        pstmt.setInt(1,fkAtt);
        pstmt.setInt(2,fkDef);
        ResultSet multi = pstmt.executeQuery(sql);
        
        System.out.println(multi.getDouble("multi"));

        }catch(SQLException e)  {
            System.out.println("ERREUR DU CHARGEMENT DES TYPES : " + e.getErrorCode());
        }
        return 1 ;
    }
}