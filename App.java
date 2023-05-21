import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {
  private Button borrowComicButton;
  private Button historyButton;
  private Button exitButton;

  public void start(Stage stage) {
    stage.setTitle("Sewa Komik");

    Label lblTitle = new Label("Selamat Datang!");
    lblTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

    borrowComicButton = new Button("Sewa Komik");
    borrowComicButton.setOnAction(e -> {
      ComicTable comicTable = new ComicTable();
      comicTable.start(new Stage());
    });

    historyButton = new Button("Riwayat Sewa");
    historyButton.setOnAction(e -> {
      BorrowingTable borrowingTable = new BorrowingTable();
      borrowingTable.start(new Stage());
    });
    exitButton = new Button("Keluar");
    exitButton.setOnAction(e -> Platform.exit());

    VBox dashboardLayout = new VBox(10);
    dashboardLayout.setAlignment(Pos.TOP_CENTER);
    dashboardLayout.getChildren().addAll(lblTitle, borrowComicButton, historyButton, exitButton);

    Scene scene = new Scene(dashboardLayout, 400, 300);
    stage.setScene(scene);
    stage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}