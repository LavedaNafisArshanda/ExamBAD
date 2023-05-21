import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BorrowingTable extends Application {
  private TableView<Borrowing> table;
  private ObservableList<Borrowing> data;

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage stage) {
    stage.setTitle("Sewa Komik");

    table = new TableView<>();
    data = FXCollections.observableArrayList(getBorrowingData());

    TableColumn<Borrowing, String> colTime = new TableColumn<>("time");
    colTime.setCellValueFactory(new PropertyValueFactory<>("createdAt"));

    TableColumn<Borrowing, String> colName = new TableColumn<>("Nama");
    colName.setCellValueFactory(new PropertyValueFactory<>("name"));

    TableColumn<Borrowing, String> colTitle = new TableColumn<>("Judul");
    colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));

    TableColumn<Borrowing, String> colDuration = new TableColumn<>("Durasi");
    colDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));

    TableColumn<Borrowing, String> colQty = new TableColumn<>("Qty");
    colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));

    TableColumn<Borrowing, String> colPrice = new TableColumn<>("Harga");
    colPrice.setCellValueFactory(new PropertyValueFactory<>("priceText"));

    table.getColumns().addAll(colTime, colName, colTitle, colDuration, colQty, colPrice);
    table.setItems(data);

    GridPane gridPane = new GridPane();
    gridPane.setAlignment(Pos.TOP_CENTER);
    gridPane.setHgap(10);
    gridPane.setVgap(10);

    Label lblTitle = new Label("Daftar Komik");
    lblTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
    GridPane.setHalignment(lblTitle, HPos.LEFT);

    gridPane.add(lblTitle, 0, 0);
    gridPane.add(table, 0, 1, 2, 1);
    gridPane.setPadding(new Insets(10));

    ColumnConstraints columnConstraints = new ColumnConstraints();
    columnConstraints.setPercentWidth(100);
    gridPane.getColumnConstraints().add(columnConstraints);

    Scene scene = new Scene(gridPane, 470, 350);
    stage.setScene(scene);
    stage.show();
  }

  private ObservableList<Borrowing> getBorrowingData() {
    ObservableList<Borrowing> data = FXCollections.observableArrayList();

    String tableName = "borrowing b JOIN comic c ON b.comic_idcomic = c.idcomic";
    String[] columns = {
        "b.idborrowing, b.name as title, b.createdAt, c.name, b.qty, b.duration, b.price" };
    ResultSet rs = Database.select(tableName, columns, null);

    try {
      while (rs.next()) {
        int id = rs.getInt("idborrowing");
        String name = rs.getString("name");
        String title = rs.getString("title");
        int qty = rs.getInt("qty");
        int duration = rs.getInt("duration");
        int price = rs.getInt("price");
        String createdAt = rs.getString("createdAt");
        Borrowing borrowing = new Borrowing(id, name, title, duration, price, createdAt, qty, 0);
        data.add(borrowing);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return data;
  }
}