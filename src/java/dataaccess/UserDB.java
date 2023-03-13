package dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import models.Role;
import models.User;
import services.RoleService;

/**
 *
 * @author Sparsh
 */

public class UserDB {

    public List<User> getAll() throws Exception {
        List<User> users = new ArrayList<>();
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        ResultSet rset = null;
        RoleService roleS = new RoleService();
        String sql = "SELECT * FROM USER";

        try {
            ps = con.prepareStatement(sql);
            rset = ps.executeQuery();
            while (rset.next()) {
                String email = rset.getString(1);
                String firstName = rset.getString(2);
                String lastName = rset.getString(3);
                String password = rset.getString(4);
                Role role = roleS.getRole(rset.getInt(5));

                User user = new User(email, firstName, lastName, password, role);
                users.add(user);
            }
        } 
        
        finally {
            DBUtil.closeResultSet(rset);
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }

        return users;
    }
    

    public User get(String email) throws Exception {
        User user = null;
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        ResultSet rset = null;
        RoleService roleS = new RoleService();
         String sql = "SELECT * FROM USER where email = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, email);
            rset = ps.executeQuery();
            while (rset.next()) {
                String firstName = rset.getString(2);
                String lastName = rset.getString(3);
                String password = rset.getString(4);
                Role role = roleS.getRole(rset.getInt(5));
                user = new User(email, firstName, lastName, password, role);
            }
        } 
        
        finally {
            DBUtil.closeResultSet(rset);
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }
        
        return user;
    }

    public void insert(User users) throws Exception {
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        String preparedQuery
                = "INSERT INTO USER(email, first_name, last_name, password, role) VALUES (?, ?, ?, ?, ?)";
        ps = con.prepareStatement(preparedQuery);
        ps.setString(1, users.getEmail());
        ps.setString(2, users.getFirstName());
        ps.setString(3, users.getLastName());
        ps.setString(4, users.getPassword());
        ps.setInt(5, users.getRole().getRoleID());
        ps.executeUpdate();
        DBUtil.closePreparedStatement(ps);
        cp.freeConnection(con);
    }
    

    public void update(User users) throws Exception {
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;

        String preparedSQL =  "UPDATE user SET first_name=?, last_name=?, password=?, role=? WHERE email=?";
        ps = con.prepareStatement(preparedSQL);
         ps.setString(1, users.getFirstName());
            ps.setString(2, users.getLastName());
            ps.setString(3, users.getPassword());
            ps.setInt(4,  users.getRole().getRoleID());
            ps.setString(5, users.getEmail());
        ps.executeUpdate();
        DBUtil.closePreparedStatement(ps);
        cp.freeConnection(con);
    }
    

    public void delete(User users) throws Exception {
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;

        String sql = "DELETE FROM USER WHERE email = ?";
        ps = con.prepareStatement(sql);
        ps.setString(1, users.getEmail());
        ps.executeUpdate();
        DBUtil.closePreparedStatement(ps);
        cp.freeConnection(con);
    }
    
}

