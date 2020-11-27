package sample.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.assests.helper.Dbhandeler;

import java.sql.*;

public class Client extends Dbhandeler {
    public int id_client;
    public String Num_tel;
    public String nom;
    public String prenom;
    public String sexe;
    public String email;

    public Client() {
    }


    public int getId_client() {
        return id_client;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }

    public String getNum_tel() {
        return Num_tel;
    }

    public void setNum_tel(String num_tel) {
        Num_tel = num_tel;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getSexe() {
        return sexe;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }


   public String toString2() {
        return "Client{" +
                "id_client=" + id_client +
                ", Num_tel='" + Num_tel + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", sexe='" + sexe + '\'' +
                "email=" + email +
        '}';
    }



    @Override
    public String toString() {
        return nom +" " + prenom;

    }
    public void Delete(int id){ this.exequery("delete from client where id_client =?",id);}



    public void insert(Client c) { this.exequery("INSERT INTO client(Num_tel,nom,prenom,sexe,email)Values(?,?,?,?,?)",c.getNum_tel(),c.getNom()
            ,c.getPrenom(),c.getSexe(),c.getEmail());}



    public ObservableList<Client> ShowAllClient(){
        ObservableList<Client>client= FXCollections.observableArrayList();
        try{
            Connection con=this.Connect();
            Statement stm=con.createStatement();
            ResultSet rs=stm.executeQuery("SELECT * from client ");
            while(rs.next()){
                Client c =new Client();
                c.setId_client(rs.getInt("id_client"));
                c.setNum_tel(rs.getString("num_tel"));
                c.setNom(rs.getString("nom"));
                c.setPrenom(rs.getString("prenom"));
                c.setSexe(rs.getString("sexe"));
                c.setEmail(rs.getString("email"));
                client.add(c);
            }
            stm.close();
            con.close();
        }catch (SQLException e){ System.out.println(e.getMessage()); }
        return client;
    }

    public ObservableList<Client> rechercherClient(String nom){
        ObservableList<Client>client= FXCollections.observableArrayList();
        try{
            Connection con=this.Connect();
            PreparedStatement stm=con.prepareStatement("SELECT * from client where nom = ?");
            stm.setString(1,nom);
            ResultSet rs=stm.executeQuery();
            while(rs.next()){
                Client c =new Client();
                c.setId_client(rs.getInt("id_client"));
                c.setNum_tel(rs.getString("num_tel"));
                c.setNom(rs.getString("nom"));
                c.setPrenom(rs.getString("prenom"));
                c.setSexe(rs.getString("sexe"));
                c.setEmail(rs.getString("email"));
                client.add(c);
            }
            stm.close();
            con.close();
        }catch (SQLException e){ System.out.println(e.getMessage()); }
        return client;
    }


    public ObservableList<Client> SearchMultiClient(String S){
        ObservableList<Client>client= FXCollections.observableArrayList();
        try{
            Connection con=this.Connect();
            Statement stm=con.createStatement();
            PreparedStatement pstm=con.prepareStatement("SELECT * from client where id_client=? or  Num_tel=? or  nom=? or prenom=? or  sexe=? or  email=?");
            int id;
            try {
                id=Integer.parseInt(S);

            }catch (NumberFormatException e){
                id=0;
            }
            pstm.setInt(1, id);
            pstm.setString(2, S);
            pstm.setString(3, S);
            pstm.setString(4, S);
            pstm.setString(5, S);
            pstm.setString(6, S);
            ResultSet rs=pstm.executeQuery();
            while(rs.next()){
                Client c =new Client();
                c.setId_client(rs.getInt("id_client"));
                c.setNum_tel(rs.getString("num_tel"));
                c.setNom(rs.getString("nom"));
                c.setPrenom(rs.getString("prenom"));
                c.setSexe(rs.getString("sexe"));
                c.setEmail(rs.getString("email"));
                client.add(c);
            }
            stm.close();
            con.close();
        }catch (Exception e){ System.out.println(e.toString()); }

        return client;
    }




public Client SelectClient(int id ){
    Client C =new Client();
    try{
        Connection con=this.Connect();
        Statement stm=con.createStatement();
        PreparedStatement pstm=con.prepareStatement("SELECT * from client where  id_client= ?   ");
        pstm.setInt(1, id);
        ResultSet rs=pstm.executeQuery();
        while(rs.next()){
            C.setId_client(rs.getInt("id_client"));
            C.setNum_tel(rs.getString("num_tel"));
            C.setNom(rs.getString("nom"));
            C.setPrenom(rs.getString("prenom"));
            C.setSexe(rs.getString("sexe"));
            C.setEmail(rs.getString("email"));
        }
        stm.close();
        con.close();
    }catch (Exception e){ System.out.println(e.toString()); }

    return C;
}
public ObservableList<Client> rechercheroneClient(int id){
    Client c =new Client();
    ObservableList<Client>client= FXCollections.observableArrayList();
    try{
        Connection con=this.Connect();
        PreparedStatement stm=con.prepareStatement("SELECT * from client where id_client= ?");
        stm.setInt(1,id);
        ResultSet rs=stm.executeQuery();
        while(rs.next()){
            c.setId_client(rs.getInt("id_client"));
            c.setNum_tel(rs.getString("num_tel"));
            c.setNom(rs.getString("nom"));
            c.setPrenom(rs.getString("prenom"));
            c.setSexe(rs.getString("sexe"));
            c.setEmail(rs.getString("email"));
            client.add(c);
        }

        stm.close();
        con.close();
    }catch (SQLException e){ System.out.println(e.getMessage()); }
    return client;
}
    public void Upadate(int idd, String num_tel, String nom, String prenom, String sexe, String email){ this.exequery("update client  set num_tel= ?,nom=?,prenom=?,sexe=?,email=? where id_client=?", num_tel,nom,prenom,sexe,email,idd); }

}
