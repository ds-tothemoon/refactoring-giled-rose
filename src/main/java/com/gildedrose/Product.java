package com.gildedrose;

enum Product {
    AGED_BRIE("Aged Brie"),
    SULFURAS("Sulfuras, Hand of Ragnaros"),
    BACKSTAGE_PASSES("Backstage passes to a TAFKAL80ETC concert"),
    CONJURED("Conjured Mana Cake"),
    NORMAL("normal");

    private String name;

    Product(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public static Product getValue(String name) {
        for (Product product : Product.values()) {
            if (name.equals(product.getName())) {
                return product;
            }
        }
        return NORMAL;
    }

}
