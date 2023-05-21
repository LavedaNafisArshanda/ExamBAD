import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class BorrowingForm extends Application {
  private TextField nameField;
  private TextField durationField;
  private TextField qtyField;
  private Comic comic = new Comic();

  public static void main(String[] args) {
    launch(args);
  }

  public BorrowingForm() {
  }

  public BorrowingForm(Comic comic) {
    this.comic = comic;
  }

  @Override
  public void start(Stage stage) {
    stage.setTitle("Form Sewa");

    GridPane gridPane = new GridPane();
    gridPane.setPadding(new Insets(10));
    gridPane.setVgap(10);
    gridPane.setHgap(10);

    Label lblTitle = new Label("Form Sewa");
    lblTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
    GridPane.setConstraints(lblTitle, 0, 0);

    Label comicLabel = new Label("Nama:");
    GridPane.setConstraints(comicLabel, 0, 1);
    Label titleLabel = new Label(comic.getName());
    GridPane.setConstraints(titleLabel, 1, 1);

    Label nameLabel = new Label("Nama:");
    GridPane.setConstraints(nameLabel, 0, 2);
    nameField = new TextField();
    GridPane.setConstraints(nameField, 1, 2);

    Label durationLabel = new Label("Durasi Sewa:");
    GridPane.setConstraints(durationLabel, 0, 3);
    durationField = new TextField();
    GridPane.setConstraints(durationField, 1, 3);

    Label qtyLabel = new Label("Jumlah Komik:");
    GridPane.setConstraints(qtyLabel, 0, 4);
    qtyField = new TextField();
    GridPane.setConstraints(qtyField, 1, 4);

    Label availableLable = new Label("Tersedia " + comic.getQty() + " komik");
    GridPane.setConstraints(availableLable, 1, 5);

    Button cancelButton = new Button("Kembali");
    GridPane.setConstraints(cancelButton, 0, 6);
    cancelButton.setOnAction(event -> openTable(stage));

    Button submitButton = new Button("Sewa");
    GridPane.setConstraints(submitButton, 1, 6);
    submitButton.setOnAction(event -> handleSubmitButton(stage));

    gridPane.getChildren().addAll(lblTitle, comicLabel, titleLabel, nameLabel, nameField, durationLabel, durationField,
        qtyLabel, qtyField, availableLable, cancelButton, submitButton);

    Scene scene = new Scene(gridPane, 300, 250);
    stage.setScene(scene);
    stage.show();
  }

  private void openTable(Stage stage) {

    ComicTable comicTable = new ComicTable();
    comicTable.start(stage);
  }

  private void handleSubmitButton(Stage stage) {
    LocalDateTime currentTime = LocalDateTime.now();

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    String formattedDateTime = currentTime.format(formatter);

    String name = nameField.getText();
    String durationText = durationField.getText();
    String qtyText = qtyField.getText();
    String errorMessage = "";
    int available = comic.getQty();

    if (name.isEmpty())
      errorMessage += "Nama harus diisi\n";
    else if (name.length() > 45)
      errorMessage += "Nama maksimal terdiri dari 45 karakter\n";

    int duration = 0;
    if (durationText.isEmpty())
      errorMessage += "Durasi harus diisi\n";
    else if (!durationText.matches("[0-9]+"))
      errorMessage += "Durasi harus berisi angka saja\n";
    else {
      duration = Integer.parseInt(durationText);
      if (duration < 1)
        errorMessage += "Durasi tidak boleh kurang dari 1\n";
    }

    int qty = 0;
    if (qtyText.isEmpty())
      errorMessage += "Jumlah harus diisi\n";
    else if (!qtyText.matches("[0-9]+"))
      errorMessage += "Jumlah harus berisi angka saja\n";
    else {
      qty = Integer.parseInt(qtyText);
      if (qty < 1)
        errorMessage += "Jumlah tidak boleh kurang dari 1\n";
      else if (qty > available) {
        errorMessage += "Hanya tersedia " + available + " komik " + comic.getName() + " saja";
      }
    }

    if (errorMessage.isEmpty()) {
      int price = qty * duration * 10000;
      boolean isConfirmed = ConfirmationAlert.show(name, duration, qty, comic, price);
      if (isConfirmed) {
        int idcomic = comic.getIdcomic();
        int newQty = available - qty;
        String[] comicColumns = { "qty" };
        Object[] comicValues = { newQty };
        String condition = "idcomic = '" + idcomic + "'";
        Database.update("comic", comicColumns, comicValues, condition);
        String[] columns = { "name", "duration", "price", "createdAt", "comic_idcomic", "qty" };
        Object[] values = { name, duration, price, formattedDateTime, idcomic, qty };
        int idborrowing = Database.insert("borrowing", columns, values);
        if (idborrowing > 0) {
          Alert alert = new Alert(Alert.AlertType.INFORMATION);
          alert.setTitle("Berhasil Disimpan!");
          alert.setHeaderText("Berhasil disimpan dalam sistem!");
          alert.showAndWait();
          openTable(stage);
        }
      }
    } else {
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Error");
      alert.setHeaderText("Silahkan cek kembali form anda!");
      alert.setContentText(errorMessage);
      alert.showAndWait();
      return;
    }
  }
}
