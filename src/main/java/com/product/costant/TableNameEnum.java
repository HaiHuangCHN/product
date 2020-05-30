package com.product.costant;

public enum TableNameEnum {

    USER("user", "user_arch"),
    CART("cart", "cart_arch"),
    ORDER_DETAIL("order_detail", "order_detail_arch"),
    PRODUCT_ITEM("product_item", "product_item_arch");

    private TableNameEnum(String activeTableName, String archiveTableName) {
        this.activeTableName = activeTableName;
        this.archiveTableName = archiveTableName;
    }

    private String activeTableName;

    private String archiveTableName;

    public String getActiveTableName() {
        return activeTableName;
    }

    public String getArchiveTableName() {
        return archiveTableName;
    }

}
