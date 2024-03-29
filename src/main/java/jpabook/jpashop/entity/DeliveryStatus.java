package jpabook.jpashop.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public enum DeliveryStatus {
    READY,COMP
}
