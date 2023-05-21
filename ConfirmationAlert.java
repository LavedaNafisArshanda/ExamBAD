import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Optional;

public class ConfirmationAlert {

  public static boolean show(String name, int duration, int qty, Comic comic, int price) {
    NumberFormat formatter = new DecimalFormat("#,###");
    String formattedPrice = "Rp" + formatter.format(price);

    Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
    confirmationAlert.setTitle("Konfirmasi Sewa");
    confirmationAlert.setHeaderText("Silahkan cek kembali detail sewa anda!");
    confirmationAlert.setContentText(
        "Judul \t\t: " + comic.getName() +
            "\nnama \t\t: " + name +
            "\nDurasi Sewa \t: " + duration +
            "\nJumlah Buku \t: " + qty +
            "\nHarga \t\t: " + formattedPrice);

    ButtonType buttonTypeSubmit = new ButtonType("Konfirmasi");
    ButtonType buttonTypeCancel = new ButtonType("Batal", ButtonBar.ButtonData.CANCEL_CLOSE);
    confirmationAlert.getButtonTypes().setAll(buttonTypeCancel, buttonTypeSubmit);

    Optional<ButtonType> result = confirmationAlert.showAndWait();
    return result.isPresent() && result.get() == buttonTypeSubmit;
  }
}
