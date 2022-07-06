package com.gildedrose;

import java.util.Arrays;

public class GildedRose {
    private static final int MAX_QUALITY = 50;
    private static final int MIN_QUALITY = 0;
    private static final int NORMAL_QUALITY_CHANGE = 1;
    private static final int AFTER_SELL_IN_QUALITY_CHANGE = 2;
    private static final int CONJURED_MULTIPLE = 2;
    private static final int SELL_IN_10 = 10;
    private static final int SELL_IN_5 = 5;
    private static final int SELL_IN_D_DAY = 0;


    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        Arrays.stream(items).forEach(item -> {
            if (!item.name.equals(Product.SULFURAS.getName())){
                updateQualityForProduct(item);
            }
        });
    }

    private void updateQualityForProduct(Item item) {
        Product itemName = Product.getValue(item.name);
        switch (itemName){
            case AGED_BRIE:
                updateQualityForAGED_BRIE(item);
                break;
            case BACKSTAGE_PASSES:
                updateQualityForBACKSTAGE_PASSES(item);
                break;
            case SULFURAS:
                break;
            case CONJURED:
                updateQualityForCONJUREDProduct(item);
                break;
            case NORMAL:
            default:
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
