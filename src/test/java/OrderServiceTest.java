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

    // Test 1 — Orden registrada con descuento cuando total > 500
    @Test
    void testOrdenConDescuentoCuandoTotalSuperaQuinientos() {
        // Arrange
        when(customerRepo.existsById("C001")).thenReturn(true);
        when(customerRepo.isActive("C001")).thenReturn(true);
        when(productRepo.getStock("P001")).thenReturn(10);

        Map<String, Double>  products  = Map.of("P001", 300.0); // precio unitario
        Map<String, Integer> quantities = Map.of("P001", 2);    // 2 unidades → total 600

        // Act
        Order order = orderService.registerOrder("C001", products, quantities);

        // Assert
        assertNotNull(order);
        assertEquals("OR-0001", order.getOrderCode());
        assertEquals(540.0, order.getTotal(), 0.01); // 600 * 0.90
        assertNotNull(order.getOrderDate());

        verify(customerRepo).existsById("C001");
        verify(customerRepo).isActive("C001");
        verify(productRepo).getStock("P001");
    }

    // Test 2 — Orden cancelada cuando producto no tiene stock
    @Test
    void testOrdenCanceladaCuandoSinStock() {
        // Arrange
        when(customerRepo.existsById("C002")).thenReturn(true);
        when(customerRepo.isActive("C002")).thenReturn(true);
        when(productRepo.getStock("P002")).thenReturn(0); // sin stock

        Map<String, Double>  products   = Map.of("P002", 100.0);
        Map<String, Integer> quantities = Map.of("P002", 1);

        // Act & Assert
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> orderService.registerOrder("C002", products, quantities)
        );
        assertEquals("Sin stock para producto P002", ex.getMessage());

        verify(productRepo).getStock("P002");
    }
}