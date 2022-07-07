package com.gildedrose;

enum ProductType {
    AGED_BRIE("Aged Brie") ,
    SULFURAS("Sulfuras, Hand of Ragnaros"),
    BACKSTAGE_PASSES("Backstage passes to a TAFKAL80ETC concert"),
    CONJURED("Conjured Mana Cake"),
    NORMAL("normal");

    private final String name;

    ProductType(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public static ProductType getValue(String name) {
        for (ProductType productType : ProductType.values()) {
            if (name.equals(productType.getName())) {
                return productType;
            }
        }
        return NORMAL;
    }

}
