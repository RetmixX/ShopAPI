package com.example.retmix.models.enums;

public enum AvailablePermission {
    ADD_PRODUCT("ADD_PRODUCT"),
    REMOVE_PRODUCT("REMOVE_PRODUCT"),
    EDIT_PRODUCT("EDIT_PRODUCT"),

    ADD_PRODUCT_TO_CART("ADD_PRODUCT_TO_CART"),
    REMOVE_PRODUCT_FROM_CART("REMOVE_PRODUCT_FROM_CART"),
    ADD_PERMISSION("ADD_PERMISSION"),
    REMOVE_PERMISSION("REMOVE_PERMISSION"),
    SHOW_PERMISSION("SHOW_PERMISSION"),
    PLACE_ON_ORDER("PLACE_ON_ORDER");

    private final String permission;

    AvailablePermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
