package engine.engine.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class StockOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    @Column(nullable = false)
    private String stockName;
    /**
     *  구매한 주식의 평균 값
     */
    @Column(nullable = false)
    private Integer stockPrice;

    @Column
    private Integer currentStockPrice;

    @Column(nullable = false)
    private Integer stockCount;
    /**
     *  현재 주식의 총 가격
     */
    @Column
    private Integer currentTotalPrice;

    @Column(nullable = false)
    private Integer totalPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sequence_id")
    private Member member;

}
