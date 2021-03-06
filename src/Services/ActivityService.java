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
import java.sql.ResultSet;
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
    public String getId(Activity act) {
        String id="";
        try {
            String query="SELECT * FROM `activity` WHERE name=? and id_Course=?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, act.getName());
            ps.setString(2, act.getId_Course());
            ResultSet rs;
            rs = ps.executeQuery();
            while(rs.next()) {
                id=rs.getString("id");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return id;
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
    public void delete(Activity act) {
        try {
            String req ="UPDATE `activity` SET `archived_by`=?,`archived_date`=?,`status`=? WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(req);
            ps.setString(1,"nour");
            ps.setDate(2, java.sql.Date.valueOf(java.time.LocalDate.now()));
            ps.setString(3, "archived");
            ps.setString(4, act.getId());
            ps.executeUpdate();
            System.out.println("removed succesfully");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void update(String id, Activity new_activity) {
//        try {
//            String query="UPDATE `activity` SET `name`=?,`owner_id`=?,`admin_number`=?,`last_updated_by`=?,`last_updated_date`=?, `specialties`=? WHERE id=?";
//            PreparedStatement pr = cnx.prepareStatement(query);
//            pr.setString(1, dep.getName());
//            pr.setInt(2, dep.getOwner_id());
//            pr.setInt(3, dep.getAdmin_number());
//            pr.setString(4, dep.getLast_updated_by());
//            pr.setDate(5, dep.getLast_update_date());
//            pr.setString(6, dep.getStringSpecialties());
//            pr.setInt(7, dep.getId());
//            pr.executeUpdate();
//            System.out.println("dep updated succesfully");
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }    
    }

    @Override
    public Activity details(String id) {
        Activity act = new Activity();
        boolean check = false;
        try {
            String query = "select * from activity where id= ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if(!"archived".equals(rs.getString("status"))) {
                    check = true;
                    act.setId(rs.getString("id"));
                    act.setName(rs.getString("name"));
                    act.setDeadline(rs.getDate("deadline"));
                    act.setStatus(rs.getString("status"));
                }
            }
        } catch (SQLException ex) { 
            ex.printStackTrace();
        }
        if (check == true) {
                return act;
            }
            else
                return null;
    }

    @Override
    public List<Activity> listAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
