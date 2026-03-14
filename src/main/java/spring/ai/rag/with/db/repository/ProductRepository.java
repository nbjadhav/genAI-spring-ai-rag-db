package spring.ai.rag.with.db.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import spring.ai.rag.with.db.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
