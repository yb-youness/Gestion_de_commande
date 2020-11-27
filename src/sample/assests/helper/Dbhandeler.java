package sample.assests.helper;

import sample.model.Command;

import java.sql.*;

public class Dbhandeler {

    public Connection Connect(){
        Connection con=null;
        try{
           // String urlcon = "jdbc:sqlite::resource:sample/assests/database/DataBasee.db";
            String urlcon = "jdbc:sqlite:DataBasee.db";
            con= DriverManager.getConnection(urlcon);
        }catch (SQLException e){
            Helper.Exseptiongmsg(e.toString());
        }
        return con;
    }

    public ResultSet ExsecuteQ(PreparedStatement pstm){
        ResultSet res= null;
        int count=0;
        try {
            res = pstm.executeQuery();
            res.next();
            count=res.getInt("count(*)");
        } catch (SQLException e) {
            Helper.Exseptiongmsg(e.toString());
        }
        return  res;
    }
//!la methode pour reduire le code Ajouter ,Modiifier,Supprimer (Reutilisable)
    public boolean exequery(String query,Object ... values)  {
        int re=0;
        try {
            PreparedStatement pr =Connect().prepareStatement(query);
            for(int i =0;i<values.length;i++){
                pr.setObject((i+1),values[i]);//System.out.println("Value "+values[i]+" Type "+values.getClass());
            }
            re=pr.executeUpdate();
            pr.close();
            Connect().close();
        }catch (Exception ex){
            System.out.println(ex.toString());
        }
        if(re==1){
            return true;
        }
        return false;
    }



    public int Count(PreparedStatement pstm){
        ResultSet res= null;
        int count=0;
        try {
            res = pstm.executeQuery();
            res.next();
            count=res.getInt("count(*)");
            pstm.close();
        } catch (SQLException e) {
            Helper.Exseptiongmsg(e.toString());
        }
        return count;
    }

}
