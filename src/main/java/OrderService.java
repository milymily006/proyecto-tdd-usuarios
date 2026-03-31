public class OrderService {

    private final CustomerRepository customerRepo;
    private final ProductRepository productRepo;

    public OrderService(CustomerRepository customerRepo, ProductRepository productRepo) {
        this.customerRepo = customerRepo;
        this.productRepo = productRepo;
    }

    public Order registerOrder(String customerId,
                               java.util.Map<String, Double> products,
                               java.util.Map<String, Integer> quantities) {
        return null; // sin implementar
    }
}