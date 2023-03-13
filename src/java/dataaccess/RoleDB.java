package dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import models.Role;
import models.User;


/**
 *
 * @author Sparsh
 */


public class RoleDB {
    
    public Role getRole(int id) throws Exception{
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        
        ResultSet rset = null;
        Role role = new Role();
       
        String sql = "SELECT * FROM role where role_id = ?";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rset = ps.executeQuery();
            while (rset.next()) {
                String name = rset.getString(2);
                
                role = new Role(id, name);
            }
        } 
        
        finally {
            
            DBUtil.closeResultSet(rset);
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
            
        }
       return role;
       
    }  
    
}
