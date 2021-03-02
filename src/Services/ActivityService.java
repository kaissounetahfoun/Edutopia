/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.Activity;
import IServices.IActivity;
import Utils.DataBaseConnection;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ADMIN
 */
public class ActivityService implements IActivity{
    
    Connection conn;

    public ActivityService() {
        conn = DataBaseConnection.getInstance().getConnection();
    }
    

    @Override
    public void add(Activity activity) {
         try {
            String requete = "INSERT INTO activity (name,deadline,work_todo,id_course,created_by,ceated_date) VALUES(?,?,?,?,?,?)";
            FileReader reader = new FileReader(activity.getWork_todo());
 
            PreparedStatement pst = conn.prepareStatement(requete);
            pst.setString(1, activity.getName());
            pst.setDate(2, (Date) activity.getDeadline());
            pst.setCharacterStream(3, reader);
            pst.setString(4, activity.getId_Course());
            pst.setString(5, activity.getCreated_by());
            pst.setDate(6, (Date) activity.getCreated_date());
            pst.executeUpdate();
            
        } catch (SQLException excep) {
            System.err.println(excep.getMessage());
        } catch (FileNotFoundException excep) {
            Logger.getLogger(ActivityService.class.getName()).log(Level.SEVERE, null, excep);
        }

    }

    @Override
    public void delete(Activity activity) {
//       try {
//            String requete = "DELETE FROM user WHERE id=? and role='Student'";
//
//            PreparedStatement pst = cnx.prepareStatement(requete);
//            pst.setInt(1, p.getId());
//            pst.executeUpdate();
//            System.out.println("Etudiant supprimé avec succées");
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//        }
    }

    @Override
    public void edit(int id, Activity new_activity) {
//         try {
//            String requete = "UPDATE user SET ? = ? WHERE id = ? and role = 'Student'";
//            PreparedStatement pst = cnx.prepareStatement(requete);
//            pst.setString(1, object);
//            pst.setObject(2, obj);
//            pst.setInt(3, id);
//            String ch = pst.toString().replaceFirst("\'", "");
//            String ch2 = ch.replaceFirst("\'", "");
//            int pos = ch2.indexOf("UPDATE");
//            String ch3 = ch2.substring(pos, ch2.length());
//            pst = cnx.prepareStatement(ch3);
//            pst.executeUpdate();
//            System.out.println("Etudiant modifié avec succées");
//
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//        }
    }

    @Override
    public void details(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Activity> listAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
