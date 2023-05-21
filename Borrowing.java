import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Borrowing {
  private int idBorrowing;
  private String name;
  private String title;
  private int duration;
  private double price;
  private String createdAt;
  private int qty;
  private int comic_idcomic;

  public Borrowing() {
    // Empty construtor
  }

  public Borrowing(int idBorrowing, String name, String title, int duration, double price, String createdAt, int qty,
      int comic_idcomic) {
    this.idBorrowing = idBorrowing;
    this.name = name;
    this.title = title;
    this.duration = duration;
    this.price = price;
    this.createdAt = createdAt;
    this.qty = qty;
    this.comic_idcomic = comic_idcomic;
  }

  public int getIdBorrowing() {
    return idBorrowing;
  }

  public void setIdBorrowing(int idBorrowing) {
    this.idBorrowing = idBorrowing;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public int getDuration() {
    return duration;
  }

  public void setDuration(int duration) {
    this.duration = duration;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public String getPriceText() {
    if (price == 0) {
      return "Rp0";
    } else {
      NumberFormat formatter = new DecimalFormat("#,###");
      return ("Rp" + formatter.format(price));
    }
  }

  public String getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }

  public int getQty() {
    return qty;
  }

  public void setQty(int qty) {
    this.qty = qty;
  }

  public int getComic_idcomic() {
    return comic_idcomic;
  }

  public void setComic_idcomic(int comic_idcomic) {
    this.comic_idcomic = comic_idcomic;
  }
}
