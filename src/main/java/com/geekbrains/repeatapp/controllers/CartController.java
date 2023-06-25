package com.geekbrains.repeatapp.controllers;

import com.geekbrains.repeatapp.dtos.StringResponse;
import com.geekbrains.repeatapp.servises.CartService;
import com.geekbrains.repeatapp.utils.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * Когда к нам кто-то стучится, он обязан запросить id корзины, если у него его нет. Если есть - не обязан.
 * по "/generate" мы сгенерим какую-то последовательность символов, и отдадим этот id (uuid) клиенту.
 * Далее пользователь может запросить свою текущую корзину (гостевую). Чтобы получить гостевую корзину, (пользватель не
 * авторизован) пользователь присылает uuid ("/uuid"), который мы ему только что выдали. И мы ему благополучно эту
 * корзину возвращаем.
 * Если пользователь хочет что-то добавить в корзину, что-то уменьшить или удалить из корзины - он обязан идентифицировать
 * корзину этим uuid (напр. "/{uuid}/add/{productId}")
 * cartService по uuid найдет имя корзины, и достанет ее из Redis
 * Когда мы только выдали клиенту uuid, разумеется корзины для этого uuid в Redis'e нет. Но Exception мы не словим, т.к.
 * в методе getCartForCurrentUser, а также в getCartByKey, мы проверяем наличие корзины по ключу, и если корзины нет -
 * создаем новую.
 * В любых действиях по удалению, добавлению товаров из корзины и т.п. прокидывается uuid. Uuid будет храниться на
 * фронте в localStorage.
 * <p>
 * Если пользователь заходит под своей учеткой, то значит Principle principle не null. И корзина будет точно называться
 * по имени пользователя.
 * Все методы мы написали так, что principle "перебивает" uuid.
 */

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping("/{uuid}")
    public Cart getCart(Principal principal, @PathVariable String uuid) {
        return cartService.getCurrentCart(getCurrentCartUuid(principal, uuid));
    }

    @GetMapping("/generate")
    public StringResponse generateCartUuid() {
        return new StringResponse(cartService.generateCartUuid());
    }

    @GetMapping("/{uuid}/add/{productId}")
    public void add(Principal principal, @PathVariable String uuid, @PathVariable Long productId) {
        cartService.addToCart(getCurrentCartUuid(principal, uuid), productId);
    }

    @GetMapping("/{uuid}/remove/{productId}")
    public void remove(Principal principal, @PathVariable String uuid, @PathVariable Long productId) {
        cartService.removeItemFromCart(getCurrentCartUuid(principal, uuid), productId);
    }

    @GetMapping("/{uuid}/decrement/{productId}")
    public void decrement(Principal principal, @PathVariable String uuid, @PathVariable Long productId) {
        cartService.decrementItem(getCurrentCartUuid(principal, uuid), productId);
    }

    @GetMapping("/{uuid}/clear")
    public void clear(Principal principal, @PathVariable String uuid) {
        cartService.clearCart(getCurrentCartUuid(principal, uuid));
    }

    @GetMapping("/{uuid}/merge")
    public void mergeCarts(Principal principal, @PathVariable String uuid) {
        //todo кто-нибудь может додуматься это вызвать без токена
        cartService.merge(
                getCurrentCartUuid(principal, null),
                getCurrentCartUuid(null, uuid));
    }

    private String getCurrentCartUuid(Principal principal, String uuid) {
        if (principal != null) {
            return cartService.getCartUuidFromSuffix(principal.getName());
        }
        return cartService.getCartUuidFromSuffix(uuid);
    }
}
