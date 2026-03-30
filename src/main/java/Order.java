import java.time.LocalDate;
import java.util.Map;

public class Order {
    private String orderCode;
    private String customerId;
    private Map<String, Integer> products; // productId -> cantidad
    private double total;
    private LocalDate orderDate;

    // Getters y setters
    public String getOrderCode()           { return orderCode; }
    public void   setOrderCode(String c)   { this.orderCode = c; }
    public double getTotal()               { return total; }
    public void   setTotal(double t)       { this.total = t; }
    public LocalDate getOrderDate()        { return orderDate; }
    public void   setOrderDate(LocalDate d){ this.orderDate = d; }
}