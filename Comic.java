public class Comic {
  private int idcomic = 0;
  private String name;
  private String writer;
  private int qty;

  public Comic() {
    // Empty construtor
  }

  public Comic(int idcomic, String name, String writer, int qty) {
    this.idcomic = idcomic;
    this.name = name;
    this.writer = writer;
    this.qty = qty;
  }

  public int getIdcomic() {
    return idcomic;
  }

  public void setIdcomic(int idcomic) {
    this.idcomic = idcomic;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getWriter() {
    return writer;
  }

  public void setWriter(String writer) {
    this.writer = writer;
  }

  public int getQty() {
    return qty;
  }

  public void setQty(int qty) {
    this.qty = qty;
  }
}
