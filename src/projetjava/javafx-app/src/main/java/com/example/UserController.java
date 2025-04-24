package com.monprojet;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserController {

  
    @FXML private TableView<User> tableView;
    @FXML private TableColumn<User, Integer> colId;
    @FXML private TableColumn<User, String> colName;
    @FXML private TableColumn<User, String> colPrenom;
    @FXML private TableColumn<User, String> colEmail;
    @FXML private TextField searchField; // 💡 Ajout de cette ligne pour éviter l'erreur
    @FXML private TextField deleteIdField;
    @FXML private Button deleteButton;
    @FXML private TextField nameField;
    @FXML private TextField prenomField;
    @FXML private TextField emailField;
    

    private final ObservableList<User> users = FXCollections.observableArrayList();
    private final Connexion connexion = new Connexion();

    @FXML
    private void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        loadUsers();

        // Permettre l'édition des colonnes
        tableView.setEditable(true);
        colName.setCellFactory(TextFieldTableCell.forTableColumn());
        colPrenom.setCellFactory(TextFieldTableCell.forTableColumn());
        colEmail.setCellFactory(TextFieldTableCell.forTableColumn());

        colName.setOnEditCommit(event -> updateUser(event.getRowValue().getId(), "nom", event.getNewValue()));
        colPrenom.setOnEditCommit(event -> updateUser(event.getRowValue().getId(), "prenom", event.getNewValue()));
        colEmail.setOnEditCommit(event -> updateUser(event.getRowValue().getId(), "email", event.getNewValue()));
    }

    @FXML
    private void searchUser() {
        String searchText = searchField.getText().trim();

        if (searchText.isEmpty()) {
            loadUsers();
            return;
        }

        users.clear();

        try {
            String sql = "SELECT * FROM utilisateurs WHERE nom LIKE ? OR email LIKE ?";
            PreparedStatement pstmt = connexion.connexion.prepareStatement(sql);
            pstmt.setString(1, "%" + searchText + "%");
            pstmt.setString(2, "%" + searchText + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                users.add(new User(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email")));
            }

            tableView.setItems(users);
        } catch (SQLException e) {
            System.err.println("Erreur SQL : " + e.getMessage());
        }
    }

    private void loadUsers() {
        users.clear();
        try {
            String sql = "SELECT * FROM utilisateurs";
            PreparedStatement pstmt = connexion.connexion.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                users.add(new User(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email")));
            }
            tableView.setItems(users);
        } catch (SQLException e) {
            System.err.println("Erreur SQL : " + e.getMessage());
        }
    }


    @FXML
    private void addUser() {
        String nom = nameField.getText();
        String prenom = prenomField.getText();
        String email = emailField.getText();

        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty()) {
            System.out.println("Veuillez remplir tous les champs.");
            return;
        }

        try {
            String sql = "INSERT INTO utilisateurs (nom, prenom, email) VALUES (?, ?, ?)";
            PreparedStatement pstmt = connexion.connexion.prepareStatement(sql);
            pstmt.setString(1, nom);
            pstmt.setString(2, prenom);
            pstmt.setString(3, email);
            pstmt.executeUpdate();
            System.out.println("Utilisateur ajouté !");
            loadUsers();
        } catch (SQLException e) {
            System.err.println("Erreur SQL : " + e.getMessage());
        }
    }

    private void updateUser(int id, String column, String newValue) {
        try {
            String sql = "UPDATE utilisateurs SET " + column + " = ? WHERE id = ?";
            PreparedStatement pstmt = connexion.connexion.prepareStatement(sql);
            pstmt.setString(1, newValue);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
            System.out.println("Utilisateur mis à jour !");
        } catch (SQLException e) {
            System.err.println("Erreur SQL : " + e.getMessage());
        }
    }

    @FXML
    private void deleteUser() {
        int id;
        try {
            id = Integer.parseInt(deleteIdField.getText());
        } catch (NumberFormatException e) {
            System.out.println("ID invalide");
            return;
        }

        try {
            String sql = "DELETE FROM utilisateurs WHERE id = ?";
            PreparedStatement pstmt = connexion.connexion.prepareStatement(sql);
            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Utilisateur supprimé");
                loadUsers();
            } else {
                System.out.println("Aucun utilisateur trouvé avec cet ID");
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL : " + e.getMessage());
        }
    }
}
