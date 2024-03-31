package jpabook.jpashop.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded // 내장 타입을 포함 했다.
    private Address address;

    @OneToMany(mappedBy = "member") // order 테이블의 member 필드에 의해서 나는 매핑이 된 것 (읽기 전용) 값을 넣는다고 바뀌지 않음
    private List<Order> orders = new ArrayList<>(); // 바로 처리하는 것이 null문제에서 안전하다.


    public Member(String name) {
        this.name = name;
    }

    public Member(String name, Address address) {
        this.name = name;
        this.address = address;
    }
}
