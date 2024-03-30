package jpabook.jpashop.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "delivery")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Delivery {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY) // 주문 조회용 거울
    private Order order;

    private Address address;

    @Enumerated(EnumType.STRING) // ORDINAL 금지 (순서 대로)
    private DeliveryStatus status;


    //== 주문 생성==//
    public Delivery(Address address) {
        this.address = address;
    }

    // 연관 관계 메서드에 사용
    protected void setOrder(Order order) {
        this.order = order;
    }
}
