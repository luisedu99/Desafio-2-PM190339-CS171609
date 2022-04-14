package sv.edu.udb.desafio2pm190339cs171609;

public class Details {
    private int id_detail;
    private int id_billfk;
    private int id_medicinefk;
    private int quantity;
    private double precio;
    private double total;

    public int getId_detail() {
        return id_detail;
    }

    public void setId_detail(int id_detail) {
        this.id_detail = id_detail;
    }

    public int getId_billfk() {
        return id_billfk;
    }

    public void setId_billfk(int id_billfk) {
        this.id_billfk = id_billfk;
    }

    public int getId_medicinefk() {
        return id_medicinefk;
    }

    public void setId_medicinefk(int id_medicinefk) {
        this.id_medicinefk = id_medicinefk;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
