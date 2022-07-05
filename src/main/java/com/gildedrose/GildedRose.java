package com.gildedrose;

import java.util.Arrays;

enum SpecialProduct {
    AGED_BRIE("Aged Brie"),
    SULFURAS("Sulfuras, Hand of Ragnaros"),
    BACKSTAGE_PASSES("Backstage passes to a TAFKAL80ETC concert"),
    CONJURED("Conjured Mana Cake");

    private String name;

    SpecialProduct(String name) {
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

}

public class GildedRose {
    private static final int MAX_QUALITY = 50;
    private static final int MIN_QUALITY = 0;
    private static final int NORMAL_QUALITY_CHANGE = 1;
    private static final int AFTER_SELL_IN_QUALITY_CHANGE = 2;
    private static final int CONJURED_MULTIPLE = 2;
    private static final int SELL_IN_10 = 10;
    private static final int SELL_IN_5 = 10;
    private static final int SELL_IN_D_DAY = 0;


    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        Arrays.stream(items).forEach(item -> {
            if (!item.name.equals(SpecialProduct.SULFURAS.getName())){
                updateQualityForProduct(item);
            }
        });
    }

    private void updateQualityForProduct(Item item) {
        if (item.name.equals(SpecialProduct.AGED_BRIE.getName())){
            updateQualityForAGED_BRIE(item);
        }
        else if (item.name.equals(SpecialProduct.BACKSTAGE_PASSES.getName())) {
            updateQualityForBACKSTAGE_PASSES(item);
        }
        else if (item.name.equals(SpecialProduct.SULFURAS.getName())){
            return;
        }
        else if (item.name.equals(SpecialProduct.CONJURED.getName())){
            updateQualityForCONJUREDProduct(item);
        }
        else {
            updateQualityForNormalProduct(item);
        }

        normalizeQuality(item);
        item.sellIn--;
    }

    private void updateQualityForAGED_BRIE(Item item) {
        if (item.sellIn > SELL_IN_D_DAY){
            item.quality += NORMAL_QUALITY_CHANGE;
        }
        else {
            item.quality += AFTER_SELL_IN_QUALITY_CHANGE;
        }
    }

    private void updateQualityForBACKSTAGE_PASSES(Item item) {
        if (item.sellIn > SELL_IN_10){
            item.quality += 1;
        }
        else if (item.sellIn > SELL_IN_5){
            item.quality += 2;
        }
        else if (item.sellIn > SELL_IN_D_DAY){
            item.quality += 3;
        }
        else {
            item.quality = MIN_QUALITY;
        }
    }

    private void updateQualityForNormalProduct(Item item) {
        if (item.sellIn > SELL_IN_D_DAY) {
            item.quality -= NORMAL_QUALITY_CHANGE;
        }
        else {
            item.quality -= AFTER_SELL_IN_QUALITY_CHANGE;
        }
    }

    private void updateQualityForCONJUREDProduct(Item item) {
        if (item.sellIn > SELL_IN_D_DAY) {
            item.quality -= NORMAL_QUALITY_CHANGE * CONJURED_MULTIPLE;
        }
        else {
            item.quality -= AFTER_SELL_IN_QUALITY_CHANGE * CONJURED_MULTIPLE;
        }
    }

    private void normalizeQuality(Item item){
        if (item.quality < MIN_QUALITY){
            item.quality = MIN_QUALITY;
        }
        if (item.quality > MAX_QUALITY) {
            item.quality = MAX_QUALITY;
        }
    }

}
