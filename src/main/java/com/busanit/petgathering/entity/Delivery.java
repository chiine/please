package com.busanit.petgathering.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.busanit.petgathering.dto.DeliveryDto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity //이 클래스를 엔티티로 선언
@Table(name="Delivery") //엔티티와 매핑할 테이블을 지정
@Getter
@Setter
@ToString
public class Delivery extends BaseEntity{

    @Id
    @Column(name="delivery_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY , cascade = CascadeType.REMOVE)
    @JoinColumn(name = "order_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Order order;

    private String deliveryName;

    private String deliveryZipcode;

    private String deliveryStreetAdr;

    private String deliveryDetailAdr;

    private String deliveryDetail;

    private String deliveryAccount;

    private String deliveryAccountName;

    private String deliveryPhone;

    public static Delivery createDelivery(DeliveryDto deliveryDto, Order order) {
        Delivery delivery = new Delivery();
        delivery.setOrder(order);
        delivery.setDeliveryName(deliveryDto.getDeliveryName());
        delivery.setDeliveryZipcode(deliveryDto.getDeliveryZipcode());
        delivery.setDeliveryStreetAdr(deliveryDto.getDeliveryStreetAdr());
        delivery.setDeliveryDetailAdr(deliveryDto.getDeliveryDetailAdr());
        delivery.setDeliveryDetail(deliveryDto.getDeliveryDetail());
        delivery.setDeliveryAccount(deliveryDto.getDeliveryAccount());
        delivery.setDeliveryAccountName(deliveryDto.getDeliveryAccountName());
        delivery.setDeliveryPhone(deliveryDto.getDeliveryPhone());

        return delivery;
    }
}
