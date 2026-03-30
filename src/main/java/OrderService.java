import java.time.LocalDate;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class OrderService {

    private final CustomerRepository customerRepo;
    private final ProductRepository  productRepo;
    private final AtomicInteger      correlativo = new AtomicInteger(1);

    public OrderService(CustomerRepository customerRepo, ProductRepository productRepo) {
        this.customerRepo = customerRepo;
        this.productRepo  = productRepo;
    }

    /**
     * @param customerId  ID del cliente
     * @param products    mapa productId -> precio unitario
     * @param quantities  mapa productId -> cantidad pedida
     * @return Order registrada o lanza IllegalArgumentException
     */
    public Order registerOrder(String customerId,
                               Map<String, Double> products,
                               Map<String, Integer> quantities) {

        // Cliente debe existir y estar activo
        if (!customerRepo.existsById(customerId)) {
            throw new IllegalArgumentException("El cliente no existe");
        }
        if (!customerRepo.isActive(customerId)) {
            throw new IllegalArgumentException("El cliente no está activo");
        }

        // No se permiten productos duplicados (keys del mapa ya son únicos)
        // Cantidades > 0 y stock disponible
        for (Map.Entry<String, Integer> entry : quantities.entrySet()) {
            String  pid = entry.getKey();
            Integer qty = entry.getValue();

            if (qty == null || qty <= 0) {
                throw new IllegalArgumentException("Cantidad inválida para producto " + pid);
            }

            int stock = productRepo.getStock(pid);
            if (stock < qty) {
                throw new IllegalArgumentException("Sin stock para producto " + pid);
            }
        }

        // Calcular total
        double total = 0;
        for (Map.Entry<String, Double> p : products.entrySet()) {
            total += p.getValue() * quantities.getOrDefault(p.getKey(), 0);
        }

        // Descuento 10% si total > 500
        if (total > 500) {
            total *= 0.90;
        }

        // Construir orden
        Order order = new Order();
        order.setOrderCode(String.format("OR-%04d", correlativo.getAndIncrement()));
        order.setTotal(total);
        order.setOrderDate(LocalDate.now());
        return order;
    }
}