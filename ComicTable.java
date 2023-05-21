import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ComicTable extends Application {
  private TableView<Comic> table;
  private ObservableList<Comic> data;

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage stage) {
    stage.setTitle("Sewa Komik");

    table = new TableView<>();
    data = FXCollections.observableArrayList(getComicData());

    TableColumn<Comic, Integer> colNo = new TableColumn<>("ID");
    colNo.setCellFactory(column -> new TableCell<Comic, Integer>() {
      @Override
      protected void updateItem(Integer item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
          setText(null);
        } else {
          setText(Integer.toString(getIndex() + 1) + ".");
        }
      }
    });

    TableColumn<Comic, String> colName = new TableColumn<>("Judul");
    colName.setCellValueFactory(new PropertyValueFactory<>("name"));

    TableColumn<Comic, String> colWriter = new TableColumn<>("Penulis");
    colWriter.setCellValueFactory(new PropertyValueFactory<>("writer"));

    TableColumn<Comic, String> colQty = new TableColumn<>("Qty");
    colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));

    TableColumn<Comic, Void> colBorrow = new TableColumn<>("");
    colBorrow.setCellFactory(column -> new TableCell<Comic, Void>() {
      private final Button borrowButton = new Button("Sewa");

      @Override
      protected void updateItem(Void item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
          setGraphic(null);
        } else {
          Comic comic = getTableView().getItems().get(getIndex());

          borrowButton.setOnAction(event -> {
            BorrowingForm borrowingForm = new BorrowingForm(comic);
            borrowingForm.start(stage);
          });

          setGraphic(borrowButton);
        }
      }
    });

    table.getColumns().addAll(colNo, colName, colWriter, colQty, colBorrow);
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

    Scene scene = new Scene(gridPane, 400, 350);
    stage.setScene(scene);
    stage.show();
  }

  private ObservableList<Comic> getComicData() {
    ObservableList<Comic> data = FXCollections.observableArrayList();

    String tableName = "comic";
    String[] columns = { "*" };
    ResultSet rs = Database.select(tableName, columns, null);

    try {
      while (rs.next()) {
        int id = rs.getInt("idcomic");
        String name = rs.getString("name");
        String writer = rs.getString("writer");
        int qty = rs.getInt("qty");
        Comic comic = new Comic(id, name, writer, qty);
        data.add(comic);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return data;
  }
}