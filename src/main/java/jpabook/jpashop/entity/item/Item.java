package jpabook.jpashop.entity.item;


import jakarta.persistence.*;
import jpabook.jpashop.entity.Category;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
// SINGLE_TABLE : 한 테이블에 다 넣는것
// TABLE_PER_CLASS : 북 앨범 무비 3개만 나오는 걱
// JOINED : 가장 정규화 된 스타일
@DiscriminatorColumn(name = "dtype") // @DiscriminatorValue("B") 벨류를 각 상속받은 클래스에 지정
@Getter
public abstract class Item { // 추상 클래스로 진행 (구현체를 이용하여 만들 계획)

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    // 공통 속성
    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();


}