package com.geekbrains.repeatapp.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "orders")
@NamedEntityGraph( // говорим, что нам хотелось бы доставать целый граф объектов. Не только Order, но и что-то с ним связанное.
        name = "orders.for-front", // вот так мы назовем этот граф
        attributeNodes = { // указываем те поля, которые хотим догрузить
                @NamedAttributeNode(value = "items", subgraph = "items-products")  // хотим "дотянуть" поле с именем "items" (аналог join fetch).
                // при этом если хотим получить не стандартную загрузку, а донастроить ее, чтобы у items тоже какие-то объекты "дотянулись"
                // указываем, что будем догружать это поле вместе с подграфом subgraph, которому даем произвольное имя,
                // здесь "items-products"
        },
        subgraphs = {  // описываем подграф
                @NamedSubgraph(
                        name = "items-products", // с присвоенным ранее именем
                        attributeNodes = { // говорим говорим какие поля хотим догрузить
                                @NamedAttributeNode("product") // в данном случае это поле "product"
                        }
                )
        }
        // для запросов, где нужно вытаскивать такой граф, после @Query указываем @EntityGraph(value = "orders.for-front")
)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    // All - потому что у нас не могут оставаться OrderItem, привязанные к несуществующему заказу
    private List<OrderItem> items;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    @Column(name = "price")
    private int price;
}
