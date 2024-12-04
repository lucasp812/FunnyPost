package application;

import entite.Post;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import service.Modele;
import java.util.List;

public class VueController {

  @FXML
  private TableView<Post> tableView;

  @FXML
  private TableColumn<Post, Integer> userIdCol;

  @FXML
  private TableColumn<Post, Integer> idCol;

  @FXML
  private TableColumn<Post, String> titleCol;

  @FXML
  private TableColumn<Post, String> bodyCol;

  @FXML
  private Button init; 

  @FXML
  private TextField idInput; 

  @FXML
  private Button deleteButton; 

  private Modele modele;

  @FXML
  public void initialize() {
    userIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
    idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
    titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
    bodyCol.setCellValueFactory(new PropertyValueFactory<>("body"));

    modele = new Modele(); 

    refreshTable();

  }
  
  
  @FXML
  public void createPost() {
    
    
  }

  public void refreshTable() {
    List<Post> posts = modele.getAllPosts();
    ObservableList<Post> observablePosts = FXCollections.observableArrayList(posts);
    tableView.setItems(observablePosts);
  }

  @FXML
  public void onInitButtonClicked() {
    modele.fetchAndSavePosts();  
    refreshTable();
    System.out.println("Posts récupérés et ajoutés avec succès !");
  }

  @FXML
  public void onDeleteButtonClicked() {
    try {

      int postIdToDelete = Integer.parseInt(idInput.getText());
      boolean isDeleted = modele.deletePost(postIdToDelete);
      if (isDeleted) {
        
        refreshTable();
        System.out.println("Post supprimé avec succès !");
      } else {
        System.out.println("Erreur de suppression du post !");
      }
    } catch (NumberFormatException e) {
      System.out.println("Veuillez entrer un ID valide.");
    }
  }
}
