package engine.engine.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sequenceId;
    @Column(unique=true)
    private String userId;
    @Column
    private String userPassword;
    @Column
    private Integer userMoney;
    @Column
    private String userNickName;
    @Column
    private String role;

    @OneToMany(mappedBy = "member")
    private List<StockOrder> stockOrderList = new ArrayList<>();

    public Member(String userId, String userPassword, Integer userMoney, String userNickName) {
        this.userId = userId;
        this.userPassword = userPassword;
        this.userMoney = userMoney;
        this.userNickName = userNickName;
    }
}
