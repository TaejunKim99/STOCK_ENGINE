package engine.engine.repository;

import engine.engine.entity.StockOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockOrderRepository extends JpaRepository<StockOrder, Long> {
    StockOrder findByStockName(String stockName);
}
