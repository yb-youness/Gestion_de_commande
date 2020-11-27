package sample.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.assests.helper.Dbhandeler;

import java.sql.*;

public class Utulisateur extends Dbhandeler {
    public int iduser;public  String username,Email,Pass;
    public   Utulisateur(){}
    public void insert(Utulisateur U){ this.exequery("INSERT INTO User(Username,email,pass)Values(?,?,?)"
            ,U.getUsername(),U.getEmail(),U.getPass()); }
    public void Delete(int id){ this.exequery("delete from User where id =?",id);}
    public ObservableList<Utulisateur> ShowAllUsers(){
        ObservableList<Utulisateur>Prod= FXCollections.observableArrayList();
        try{
            Connection con=this.Connect();
            Statement stm=con.createStatement();
            ResultSet rs=stm.executeQuery("SELECT id,Username,email from  User ");
            while(rs.next()){
                Utulisateur P =new Utulisateur();
                P.setIduser(rs.getInt("id"));
                P.setUsername(rs.getString("Username"));
                P.setEmail(rs.getString("email"));
                Prod.add(P);
            }
            stm.close();
            con.close();
        }catch (SQLException e){ System.out.println(e.getMessage()); }
        return Prod;
    }


    public ObservableList<Utulisateur> SearchMulti(String S){
        ObservableList<Utulisateur>user= FXCollections.observableArrayList();
        int id;
        try{
            Connection con=this.Connect();
            Statement stm=con.createStatement();
            PreparedStatement pstm=con.prepareStatement("SELECT id,Username,email from  User where id=? or  Username=? or email=?");
            try {
                id=Integer.parseInt(S);

            }catch (NumberFormatException e){
                id=0;
            }
           pstm.setInt(1,id);pstm.setString(2,S );pstm.setString(3,S );
            ResultSet rs=pstm.executeQuery();
            while(rs.next()){
                Utulisateur U =new Utulisateur();
                U.setIduser(rs.getInt("id"));  U.setUsername(rs.getString("Username"));  U.setEmail(rs.getString("email"));

                user.add(U);
            }
            stm.close();
            con.close();
        }catch (Exception e){ System.out.println(e.toString()); }

        return user;
    }




    public int verfierLogin(String name,String pass){
        int rep=0;
        try {
            Connection con=this.Connect();
            PreparedStatement pstm=con.prepareStatement("Select count(*) from User where (Username=? or email=?)");
            pstm.setString(1,name);
            pstm.setString(2,name);
           if(this.Count(pstm)==1){
               pstm=con.prepareStatement("Select count(*) from User where (Username=? or email=?) and pass=?");
               pstm.setString(1,name);
               pstm.setString(2,name);
               pstm.setString(3,pass);
              if(this.Count(pstm)==1){
                  rep=2;
              }else
                  rep=1;
           }else {
               rep=-1;
           }
            pstm.close();
            con.close();
        }catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
        return rep;
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPass() {
        return Pass;
    }

    public void setPass(String pass) {
        Pass = pass;
    }
}
