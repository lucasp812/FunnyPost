package service;

import entite.Post;
import java.util.List;
import java.util.ArrayList;

public class Modele {

  public void fetchAndSavePosts() {

    List<Post> posts = WebService.fetchPosts();  

    for (Post post : posts) {
      if (!BDD.postExists(post.getId())) {  
        BDD.savePost(post);  
      }
    }
  }
  
  
  
  
 

  public List<Post> getAllPosts() {
    List<Post> posts = new ArrayList<>();
    try (java.sql.Connection conn = BDD.getConnection()) {
      String query = "SELECT * FROM post";
      java.sql.Statement stmt = conn.createStatement();
      java.sql.ResultSet rs = stmt.executeQuery(query);

      while (rs.next()) {
        Post post = new Post(
            rs.getInt("userId"),
            rs.getInt("id"),
            rs.getString("title"),
            rs.getString("body")
            );
        posts.add(post);
      }
    } catch (Exception e) {
      System.err.println("Error retrieving posts: " + e.getMessage());
    }
    return posts;
  }

  public boolean deletePost(int postId) {
    return BDD.deletePost(postId);

  }




}
