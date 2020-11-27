package sample.model;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;
import sample.assests.helper.Dbhandeler;

import java.sql.*;
import java.text.NumberFormat;

public class Command extends Dbhandeler {
         public int id;
         public String client;
         public String produit;
         public String adresse;
         public String status;
         public double prix;
         public int quantite;
         public double total;
         public  int id_prod;
         public int id_client;
         public String date;


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Command() {
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getProduit() {
        return produit;
    }

    public void setProduit(String produit) {
        this.produit = produit;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getId_prod() {
        return id_prod;
    }

    public void setId_prod(int id_prod) {
        this.id_prod = id_prod;
    }

    public int getId_client() {
        return id_client;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }

    @Override
    public String toString() {
        return "Command{" +
                "id=" + id +
                ", client=" + client +
                ", produit='" + produit + '\'' +
                ", adresse='" + adresse + '\'' +
                ", prix=" + prix +
                ", quantite=" + quantite +
                ", total=" + total +
                ", id_prod=" + id_prod +
                ", id_client=" + id_client +
                '}';
    }
    public void insert(Command C){this.exequery("INSERT INTO commande(id_produit,idclient,adresse,quantite,statut,date_commande)Values(?,?,?,?,?,?)",C.getId_prod(),
            C.getId_client(),C.getAdresse(),C.getQuantite(),C.getStatus(),C.getDate());

    }

    public ObservableList<Command> ShowAllcommand(){
        ObservableList<Command>comm= FXCollections.observableArrayList();
        try{
            Connection con=this.Connect();
            Statement stm=con.createStatement();
            ResultSet rs=stm.executeQuery("SELECT id_commande,prenom,nom ,ProduitId,Libele,adresse,quantite,statut,date_commande,Prix from commande inner join produit on ProduitId=id_produit INNER join client on idclient=id_client;");
            while(rs.next()){
                Command C =new Command();
                C.setId(rs.getInt("id_commande"));
                C.setProduit(rs.getString("Libele"));
                C.setAdresse(rs.getString("adresse"));
                C.setQuantite(rs.getInt("quantite"));
                C.setStatus(rs.getString("statut"));
                C.setDate(rs.getString("date_commande"));
                C.setPrix(rs.getDouble("Prix"));
                C.setClient(rs.getString("nom")+" "+rs.getString("prenom"));
                C.setId_prod(rs.getInt("ProduitId"));
                C.setTotal(rs.getDouble("Prix")*rs.getDouble("quantite"));
                comm.add(C);
            }
            stm.close();
            con.close();
        }catch (SQLException e){ System.out.println(e.getMessage()); }

        return comm;
    }
    public ObservableList<Command> search(String S){
        ObservableList<Command>comm= FXCollections.observableArrayList();
        try{
            Connection con=this.Connect();
            Statement stm=con.createStatement();
            PreparedStatement pstm=con.prepareStatement("SELECT id_commande,c.prenom,c.nom ,ProduitId,Libele,adresse,quantite,statut,date_commande,Prix from commande inner join produit on produit.ProduitId=commande.id_produit INNER join client as c on idclient=c.id_client where id_commande = ? or c.prenom=? or c.nom=? or date_commande=? or  Libele=? or statut=? or Prix=? or quantite=? ;");
        int id,qnt;double Prix;
         try {
             id=Integer.parseInt(S);
             qnt=Integer.parseInt(S);
             Prix=Double.parseDouble(S);
         }catch (NumberFormatException e){
             id=qnt=0;Prix=0;
         }
            pstm.setInt(1, id);
            pstm.setString(2, S);
            pstm.setString(3, S);
            pstm.setString(4, S);
            pstm.setString(5, S);
            pstm.setString(6, S);
            pstm.setDouble(7, Prix);
            pstm.setInt(8,qnt );
            ResultSet rs=pstm.executeQuery();
            while(rs.next()){
                Command C =new Command();
                C.setId(rs.getInt("id_commande"));
                C.setProduit(rs.getString("Libele"));
                // C.setId_client(rs.getInt("id_client"));
                C.setAdresse(rs.getString("adresse"));
                C.setQuantite(rs.getInt("quantite"));
                C.setStatus(rs.getString("statut"));
                C.setDate(rs.getString("date_commande"));
                C.setPrix(rs.getDouble("Prix"));
                C.setClient(rs.getString("nom")+" "+rs.getString("prenom"));
                C.setId_prod(rs.getInt("ProduitId"));
                C.setTotal(rs.getDouble("Prix")*rs.getDouble("quantite"));
                comm.add(C);
            }
            stm.close();
            con.close();
        }catch (Exception e){ System.out.println(e.toString()); }

        return comm;
    }

    public void SupprimerComm(int id){
        try{
            Connection con=this.Connect();
            PreparedStatement pstm=con.prepareStatement("delete from commande where id_commande=?");
            pstm.setInt(1,id);
            pstm.executeUpdate();
            pstm.close();
            con.close();
        }catch (SQLException e){
            System.out.println(e.getMessage()); }
    }


    public Command searchob(String S){
        Command C =new Command();
        try{
            Connection con=this.Connect();
            Statement stm=con.createStatement();
            PreparedStatement pstm=con.prepareStatement("SELECT id_commande,c.prenom,c.nom,idclient ,ProduitId,Libele,adresse,quantite,statut,date_commande,Prix from commande inner join produit on produit.ProduitId=commande.id_produit INNER join client as c on idclient=c.id_client where id_commande = ?;");
            int id;
            try {
                id=Integer.parseInt(S);
            }catch (NumberFormatException e){
                id=0;
            }
            pstm.setInt(1, id);


            ResultSet rs=pstm.executeQuery();
            while(rs.next()){

                C.setId(rs.getInt("id_commande"));
                C.setProduit(rs.getString("Libele"));
                // C.setId_client(rs.getInt("id_client"));
                C.setAdresse(rs.getString("adresse"));
                C.setQuantite(rs.getInt("quantite"));
                C.setStatus(rs.getString("statut"));
                C.setDate(rs.getString("date_commande"));
                C.setPrix(rs.getDouble("Prix"));
                C.setClient(rs.getString("nom")+" "+rs.getString("prenom"));
                C.setId_prod(rs.getInt("ProduitId"));
                C.setTotal(rs.getDouble("Prix")*rs.getDouble("quantite"));
                C.setId_client(rs.getInt("idclient"));
            }
            stm.close();
            con.close();
        }catch (Exception e){ System.out.println(e.toString()); }

        return C;
    }
    //! Upadate
    public void Upadate(int id, int q, String Adr, String status, String date){ this.exequery("update commande set quantite= ?,adresse=?,statut=?,date_commande=? where id_commande=?", q,Adr,status,date,id); }
}
