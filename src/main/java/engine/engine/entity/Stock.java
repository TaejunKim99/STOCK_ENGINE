package engine.engine.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Entity
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stockId;
    @Column
    private String stockName;
    @Column
    private Integer stockPrice;
    /**
     *  현재 날짜
     */
    @Column
    private String basDt;

    /**
     *  일주일 전 가격
     */
    private Integer priceLastWeek;
    /**
     *  vs 등락
     */
    @Column
    public String vs;
    /**
     *  fltRt 등락 비율
     */
    @Column
    public String fltRt;
    /**
     *  mkp 시가
     */
    @Column
    public String mkp;
    /**
     *  hipr 초고가
     */
    @Column
    public String hipr;
    /**
     *  lopr 저가
     */
    @Column
    public String lopr;
    /**
     *  trqu 거래량
     */
    @Column
    public String trqu;
    @Column
    public String trPrc;
    @Column
    public String lstgStCnt;
    @Column
    public String mrktTotAmt;
    @Column
    public String mrktCtg;
    @Column
    public String srtnCd;
    @Column
    public String isinCd;

}
