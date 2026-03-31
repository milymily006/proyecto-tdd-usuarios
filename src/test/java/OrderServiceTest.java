import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private CustomerRepository customerRepo;

    @Mock
    private ProductRepository productRepo;

    private OrderService orderService;

    @BeforeEach
    void setUp() {
        orderService = new OrderService(customerRepo, productRepo);
    }

    @Test
    void testOrdenConDescuentoCuandoTotalSuperaQuinientos() {
        when(customerRepo.existsById("C001")).thenReturn(true);
        when(customerRepo.isActive("C001")).thenReturn(true);
        when(productRepo.getStock("P001")).thenReturn(10);

        Map<String, Double>  products   = Map.of("P001", 300.0);
        Map<String, Integer> quantities = Map.of("P001", 2);

        Order order = orderService.registerOrder("C001", products, quantities);

        assertNotNull(order);
        assertEquals("OR-0001", order.getOrderCode());
        assertEquals(540.0, order.getTotal(), 0.01);
        assertNotNull(order.getOrderDate());

        verify(customerRepo).existsById("C001");
        verify(customerRepo).isActive("C001");
        verify(productRepo).getStock("P001");
    }
}