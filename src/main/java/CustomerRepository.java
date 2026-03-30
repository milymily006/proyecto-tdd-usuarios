public interface CustomerRepository {
    boolean existsById(String customerId);
    boolean isActive(String customerId);
}