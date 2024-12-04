package service;

import java.sql.Connection;
import java.sql.DriverManager;
import entite.Post;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class BDD {

  public static Connection getConnection() {
    Connection conn = null;
    try {
      String driver = "com.mysql.jdbc.Driver";
      String url = "jdbc:mysql://127.0.0.1:3306/funnypost";
      String username = "root";
      String password = "";
      Class.forName(driver);
      conn = DriverManager.getConnection(url, username, password);
      System.out.println("Connected");
    } catch (Exception e) {
      System.err.println("Connection error: " + e.getMessage());
    }
    return conn;
  }

  public static boolean deletePost(int postId) {
    boolean isDeleted = false;

    try (Connection conn = getConnection()) {
      String query = "DELETE FROM post WHERE id = ?";
      PreparedStatement stmt = conn.prepareStatement(query);
      stmt.setInt(1, postId);

      int rowsAffected = stmt.executeUpdate();
      if (rowsAffected > 0) {
        isDeleted = true;  
      }
    } catch (SQLException e) {
      System.err.println("Erreur lors de la suppression du post : " + e.getMessage());
    }

    return isDeleted;
  }

  public static void savePostsToDatabase(List<Post> posts) {
    try (Connection conn = getConnection()) {
      String query = "INSERT INTO post (id, userId, title, body) VALUES (?, ?, ?, ?)";
      PreparedStatement stmt = conn.prepareStatement(query);

      for (Post post : posts) {
        stmt.setInt(1, post.getId());
        stmt.setInt(2, post.getUserId());
        stmt.setString(3, post.getTitle());
        stmt.setString(4, post.getBody());
        stmt.executeUpdate();
      }
    } catch (Exception e) {
      System.err.println("Error saving posts: " + e.getMessage());
    }
  }
  
  public static boolean postExists(int postId) {
    try (Connection conn = BDD.getConnection()) {
      String query = "SELECT COUNT(*) FROM post WHERE id = ?";
      PreparedStatement stmt = conn.prepareStatement(query);
      stmt.setInt(1, postId);

      ResultSet rs = stmt.executeQuery();
      if (rs.next()) {
        return rs.getInt(1) > 0;  
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    return false;
  }

  
 

  public static void savePost(Post post) {
    try (Connection conn = BDD.getConnection()) {
      String query = "INSERT INTO post (id, userId, title, body) VALUES (?, ?, ?, ?)";
      PreparedStatement stmt = conn.prepareStatement(query);
      stmt.setInt(1, post.getId());
      stmt.setInt(2, post.getUserId());
      stmt.setString(3, post.getTitle());
      stmt.setString(4, post.getBody());

      stmt.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
}
