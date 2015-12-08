//StudentDAO.java
//  Adapted from the Module 4 Example
/**
 *
 * @author Alan Johnson
 */
package domain;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import util.DBConnectionPool;

public class FriendDAO {

    private DBConnectionPool connPool;

    public int countFriends() {
        return getFriends().size();
    }

    public void delete(Friend friend) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = connPool.getPoolConnection();
            stmt = conn.prepareStatement(DELETE_STMT);
            
            stmt.setInt(1, friend.getObjectID());
            stmt.executeUpdate();

        } catch (SQLException se) {
            throw new RuntimeException(
                    "A database error occurred. " + se.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Exception: " + e.getMessage());
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException se) {
                    se.printStackTrace(System.err);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace(System.err);
                }
            }
        }
    }
    private static final String DELETE_STMT = "DELETE FROM app.friends "
            + "WHERE id = ?";
    
    public Friend getFriend(int friendID) {
        Friend friend = null;
        PreparedStatement request = null;
        Connection conn = null;

        try {
            conn = DBConnectionPool.getPoolConnection();
            request = conn.prepareStatement(GET_STMT);
            request.setInt(1, friendID);

            ResultSet rset = request.executeQuery();

            if (rset.next()) {
                int id = rset.getInt("id");
                String firstName = rset.getString("firstName");
                String lastName = rset.getString("lastName");
                String phone = rset.getString("phone");

                friend = new Friend(id, firstName, lastName, phone);
            }
        } catch (SQLException se) {
            throw new RuntimeException(
                    "A database error occurred. " + se.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Exception: " + e.getMessage());
        } finally {
            if (request != null) {
                try {
                    request.close();
                } catch (SQLException se) {
                    se.printStackTrace(System.err);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace(System.err);
                }
            }
        }

        return friend;
    }
    private static final String GET_STMT = "SELECT * FROM app.friends "
            + "WHERE id = ?";

    public ArrayList<Friend> findFriends(String search) {

        PreparedStatement request = null;
        Connection conn = null;

        ArrayList<Friend> friendList = new ArrayList<>();
        try {
            conn = DBConnectionPool.getPoolConnection();
            request = conn.prepareStatement(SEARCH_STMT);
            search = search.toUpperCase();
            request.setString(1, "%" + search + "%");
            request.setString(2, "%" + search + "%");

            ResultSet rset = request.executeQuery();

            while (rset.next()) {
                int id = rset.getInt("id");
                String firstName = rset.getString("firstName");
                String lastName = rset.getString("lastName");
                String phone = rset.getString("phone");

                Friend friend = new Friend(id, firstName, lastName, phone);

                friendList.add(friend);
            }

        } catch (SQLException se) {
            throw new RuntimeException(
                    "A database error occurred. " + se.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Exception: " + e.getMessage());
        } finally {
            if (request != null) {
                try {
                    request.close();
                } catch (SQLException se) {
                    se.printStackTrace(System.err);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace(System.err);
                }
            }
        }

        return friendList;
    }
    private static final String SEARCH_STMT = "SELECT * FROM app.friends "
            + "WHERE (UPPER(firstname) LIKE ?) "
            + "OR    (UPPER(lastname)  LIKE ?)";

    public ArrayList<Friend> getFriends() {

        ArrayList<Friend> friendList = new ArrayList<>();
        Statement request = null;
        Connection conn = null;

        try {
            conn = DBConnectionPool.getPoolConnection();
            String requestString = "SELECT * FROM app.friends";
            request = conn.createStatement();
            ResultSet rset = null;

            rset = request.executeQuery(requestString);

            while (rset.next()) {
                int id = rset.getInt("id");
                String firstName = rset.getString("firstName");
                String lastName = rset.getString("lastName");
                String phone = rset.getString("phone");

                Friend friend = new Friend(id, firstName, lastName, phone);

                friendList.add(friend);
            }

        } catch (SQLException se) {
            throw new RuntimeException(
                    "A database error occurred. " + se.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Exception: " + e.getMessage());
        } finally {
            if (request != null) {
                try {
                    request.close();
                } catch (SQLException se) {
                    se.printStackTrace(System.err);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace(System.err);
                }
            }
        }

        return friendList;
    }

    public void insert(Friend friend) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;

        try {
            conn = connPool.getPoolConnection();
            stmt = conn.prepareStatement(INSERT_STMT);
            ObjectIdDAO objIdDAO = new ObjectIdDAO();
            int friendID = objIdDAO.getNextObjectID(ObjectIdDAO.FRIEND);
            stmt.setInt(1, friendID);
            stmt.setString(2, friend.getFirstName());
            stmt.setString(3, friend.getLastName());
            stmt.setString(4, friend.getPhone());
            stmt.executeUpdate();
            friend.setObjectID(friendID);
        } catch (SQLException se) {
            throw new RuntimeException(
                    "A database error occurred. " + se.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Exception: " + e.getMessage());
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException se) {
                    se.printStackTrace(System.err);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace(System.err);
                }
            }
        }
    }
    private static final String INSERT_STMT = "INSERT INTO app.friends ("
            + "ID,firstName,lastName,phone) VALUES (?,?,?,?)";

    public boolean newFriend(String fn, String ln, String ph) {
        Friend friend = new Friend(-1, fn, ln, ph);

        if (friend == null) {
            return false;
        }

        insert(friend);

        return true;
    }

    public void update(Friend friend) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = connPool.getPoolConnection();
            stmt = conn.prepareStatement(UPDATE_STMT);
            stmt.setString(1, friend.getFirstName());
            stmt.setString(2, friend.getLastName());
            stmt.setString(3, friend.getPhone());
            stmt.setInt(4, friend.getObjectID());
            stmt.executeUpdate();

        } catch (SQLException se) {
            throw new RuntimeException(
                    "A database error occurred. " + se.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Exception: " + e.getMessage());
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException se) {
                    se.printStackTrace(System.err);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace(System.err);
                }
            }
        }
    }
    private static final String UPDATE_STMT = "UPDATE app.friends "
            + "SET firstname = ?, lastname = ?, phone = ?"
            + "WHERE id = ?";

}
