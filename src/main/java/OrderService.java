import java.time.LocalDate;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class OrderService {

    private final CustomerRepository customerRepo;
    private final ProductRepository  productRepo;
    private final AtomicInteger      correlativo = new AtomicInteger(1);

    public OrderService(CustomerRepository customerRepo, ProductRepository productRepo) {
        this.customerRepo = customerRepo;
        this.productRepo  = productRepo;
    }

    public Order registerOrder(String customerId,
                               Map<String, Double> products,
                               Map<String, Integer> quantities) {

        if (!customerRepo.existsById(customerId))
            throw new IllegalArgumentException("El cliente no existe");

        if (!customerRepo.isActive(customerId))
            throw new IllegalArgumentException("El cliente no está activo");

        for (Map.Entry<String, Integer> entry : quantities.entrySet()) {
            String  pid = entry.getKey();
            Integer qty = entry.getValue();

            if (qty == null || qty <= 0)
                throw new IllegalArgumentException("Cantidad inválida para producto " + pid);

            if (productRepo.getStock(pid) < qty)
                throw new IllegalArgumentException("Sin stock para producto " + pid);
        }

        double total = 0;
        for (Map.Entry<String, Double> p : products.entrySet())
            total += p.getValue() * quantities.getOrDefault(p.getKey(), 0);

        if (total > 500) total *= 0.90;

        Order order = new Order();
        order.setOrderCode(String.format("OR-%04d", correlativo.getAndIncrement()));
        order.setTotal(total);
        order.setOrderDate(LocalDate.now());
        return order;
    }
}