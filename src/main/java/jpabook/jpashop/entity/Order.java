package jpabook.jpashop.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id") // 연관 관계 주인
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL) // persist 가 동시에 된다.
    @JoinColumn(name = "delivery_id")// 주문을 통해서 배송을 보는 경우가 많기 떄문에 FK는 주문에 지정
    private Delivery delivery;

    private LocalDateTime orderDate; //

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus; // 주문상태 [ORDER, CANCEL]

    //==연관 관계 메서드==//
    // 핵심적으로 컨트롤 하는 쪽에서 연관 관계 메서드를 가지고 있는 것이 좋다
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    //==연관 관계 메서드==//
    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    //==연관 관계 메서드==//
    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    // member1.getOrders().add(order); 를 연관관계 메서드에 하나로 묶어서 관리
//    public static void main(String[] args) {
//        Member member1 = new Member();
//        Order order = new Order();
//
//        member1.getOrders().add(order);
//        order.setMember(member1);
//    }

}
