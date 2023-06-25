package com.geekbrains.repeatapp.utils;

import com.geekbrains.repeatapp.dtos.OrderItemDto;
import com.geekbrains.repeatapp.entities.Product;
import lombok.Data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Data
public class Cart {
    private List<OrderItemDto> items;
    private int totalPrice;

    public Cart() {
        this.items = new ArrayList<>();
    }

//    public void add(Long productId){
//        items.stream()
//                .filter(i -> i.getProductId().equals(productId))
//                .findFirst()
//                .ifPresent(i -> i.changeQuantity(1));
//    }

    public boolean add(Long productId) {
        for (OrderItemDto item : items) {
            if (item.getProductId().equals(productId)) {
                item.changeQuantity(1);
                recalculate();
                return true;
            }
        }
        return false;
    }

    public void add(Product product) {
        items.add(new OrderItemDto(product));
        recalculate();
    }

    public void decrement(Long productId) {
        changeQuantity(productId, -1);
    }

    public void changeQuantity(Long productId, int i) {
        Iterator<OrderItemDto> iterator = items.iterator();
        while (iterator.hasNext()) {
            OrderItemDto item = iterator.next();
            if (item.getProductId().equals(productId)) {
                item.changeQuantity(i);
                if (item.getQuantity() <= 0) {
                    iterator.remove();
                }
                recalculate();
                return;
            }
        }
    }

    public void remove(Long productId) {
        items.removeIf(i -> i.getProductId().equals(productId));
        recalculate();
    }

    private void recalculate() {
        totalPrice = 0;
        for (OrderItemDto item : items) {
            totalPrice += item.getPrice();
        }
    }

    public void clear() {
        items.clear();
        totalPrice = 0;
    }

    /**
     * Основная корзина - та, у которой вызываем метод merge()
     *
     * @param another - корзина, из которой мержим содержимое в основную корзину
     */
    public void merge(Cart another) {
        for (OrderItemDto anotherItem : another.items) {
            boolean merged = false;
            for (OrderItemDto myItem : this.items) {
                if (myItem.getProductId().equals(anotherItem.getProductId())) {
                    myItem.changeQuantity(anotherItem.getQuantity());
                    merged = true;
                    break;
                }
            }
            if (!merged) {
                items.add(anotherItem);
            }
        }
        recalculate();
        another.clear();
    }
}
