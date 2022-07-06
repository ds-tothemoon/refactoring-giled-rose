package com.gildedrose;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {

    private static final String NORMAL = "normal";
    private static final String AGED_BRIE = "Aged Brie";
    private static final String SULFURAS = "Sulfuras, Hand of Ragnaros";
    private static final String BACKSTAGE_PASSES = "Backstage passes to a TAFKAL80ETC concert";
    private static final String CONJURED = "Conjured Mana Cake";

    private static final int DEFAULT_QUALITY = 4;
    private static final int MAX_QUALITY = 50;
    private static final int MIN_QUALITY = 0;
    private static final int SULFURAS_QUALITY = 80;

    private static final int NOT_EXPIRED_SELL_IN = 16;
    private static final int EXPIRED_SELL_IN = -2;
    private static final int SELL_IN_BETWEEN_0_AND_5 = 3;
    private static final int SELL_IN_BETWEEN_5_AND_10 = 7;
    private static final int SELL_IN_GREATER_THAN_10 = 15;


    @Nested
    class NormalProductTest {
        @Test
        void 일반상품일때_1만큼_감가상각_처리() {
            Item[] items = new Item[] { new Item(NORMAL, NOT_EXPIRED_SELL_IN, DEFAULT_QUALITY) };
            GildedRose app = new GildedRose(items);

            app.updateQuality();

            assertEquals(NOT_EXPIRED_SELL_IN - 1, app.items[0].sellIn);
            assertEquals(DEFAULT_QUALITY - 1, app.items[0].quality);
        }

        @Test
        void 가치가_0인_일반상품일때_1만큼_감가상각_처리() {
            Item[] items = new Item[] { new Item(NORMAL, NOT_EXPIRED_SELL_IN, MIN_QUALITY) };
            GildedRose app = new GildedRose(items);

            app.updateQuality();

            assertEquals(NOT_EXPIRED_SELL_IN - 1, app.items[0].sellIn);
            assertEquals(MIN_QUALITY, app.items[0].quality);
        }

        @Test
        void 판매기간이_지난_일반상품일때_2만큼_감가상각_처리() {
            Item[] items = new Item[] { new Item(NORMAL, EXPIRED_SELL_IN, DEFAULT_QUALITY) };
            GildedRose app = new GildedRose(items);

            app.updateQuality();

            assertEquals(EXPIRED_SELL_IN - 1, app.items[0].sellIn);
            assertEquals(DEFAULT_QUALITY - 2, app.items[0].quality);
        }

    }

    @Nested
    class AGED_BRIEProductTest {

        @Test
        void AGED_BRIE일때_1만큼_가치증가_처리(){
            Item[] items = new Item[] { new Item(AGED_BRIE, NOT_EXPIRED_SELL_IN, DEFAULT_QUALITY) };
            GildedRose app = new GildedRose(items);

            app.updateQuality();

            assertEquals(NOT_EXPIRED_SELL_IN - 1, app.items[0].sellIn);
            assertEquals(DEFAULT_QUALITY + 1, app.items[0].quality);
        }

        @Test
        void 가치가_최대인_AGED_BRIE일때_0만큼_가치증가_처리(){
            Item[] items = new Item[] { new Item(AGED_BRIE, NOT_EXPIRED_SELL_IN, MAX_QUALITY) };
            GildedRose app = new GildedRose(items);

            app.updateQuality();

            assertEquals(NOT_EXPIRED_SELL_IN - 1, app.items[0].sellIn);
            assertEquals(MAX_QUALITY, app.items[0].quality);
        }

        @Test
        void 판매기간이_지난_AGED_BRIE일때_2만큼_가치증가_처리(){
            Item[] items = new Item[] { new Item(AGED_BRIE, EXPIRED_SELL_IN, DEFAULT_QUALITY) };
            GildedRose app = new GildedRose(items);

            app.updateQuality();

            assertEquals(EXPIRED_SELL_IN - 1, app.items[0].sellIn);
            assertEquals(DEFAULT_QUALITY + 2, app.items[0].quality);
        }

    }

    @Nested
    class SULFURASProductTest {

        @Test
        void SULFURAS일때_가치불변_처리(){
            Item[] items = new Item[] { new Item(SULFURAS, NOT_EXPIRED_SELL_IN, SULFURAS_QUALITY) };
            GildedRose app = new GildedRose(items);

            app.updateQuality();

            assertEquals(NOT_EXPIRED_SELL_IN, app.items[0].sellIn);
            assertEquals(SULFURAS_QUALITY, app.items[0].quality);
        }

        @Test
        void 판매기간이_지난_SULFURAS일때_가치불변_처리(){
            Item[] items = new Item[] { new Item(SULFURAS, EXPIRED_SELL_IN, SULFURAS_QUALITY) };
            GildedRose app = new GildedRose(items);

            app.updateQuality();

            assertEquals(EXPIRED_SELL_IN, app.items[0].sellIn);
            assertEquals(SULFURAS_QUALITY, app.items[0].quality);
        }
    }

    @Nested
    class BACKSTAGE_PASSESProductTest {
        @Test
        void 판매기간이_10이상인_BACKSTAGE_PASSES일때_1만큼_가치증가_처리(){
            Item[] items = new Item[] { new Item(BACKSTAGE_PASSES, SELL_IN_GREATER_THAN_10, DEFAULT_QUALITY) };
            GildedRose app = new GildedRose(items);

            app.updateQuality();

            assertEquals(SELL_IN_GREATER_THAN_10 - 1, app.items[0].sellIn);
            assertEquals(DEFAULT_QUALITY + 1, app.items[0].quality);
        }

        @Test
        void 판매기간이_5초과_10이하인_BACKSTAGE_PASSES일때_2만큼_가치증가_처리(){
            Item[] items = new Item[] { new Item(BACKSTAGE_PASSES, SELL_IN_BETWEEN_5_AND_10, DEFAULT_QUALITY) };
            GildedRose app = new GildedRose(items);

            app.updateQuality();

            assertEquals(SELL_IN_BETWEEN_5_AND_10 - 1, app.items[0].sellIn);
            assertEquals(DEFAULT_QUALITY + 2, app.items[0].quality);
        }

        @Test
        void 가치가_최대이고_판매기간이_5초과_10이하인_BACKSTAGE_PASSES일때_0만큼_가치증가_처리(){
            Item[] items = new Item[] { new Item(BACKSTAGE_PASSES, SELL_IN_BETWEEN_5_AND_10, MAX_QUALITY) };
            GildedRose app = new GildedRose(items);

            app.updateQuality();

            assertEquals(SELL_IN_BETWEEN_5_AND_10 - 1, app.items[0].sellIn);
            assertEquals(MAX_QUALITY, app.items[0].quality);
        }

        @Test
        void 판매기간이_0초과_5이하인_BACKSTAGE_PASSES일때_3만큼_가치증가_처리(){
            Item[] items = new Item[] { new Item(BACKSTAGE_PASSES, SELL_IN_BETWEEN_0_AND_5, DEFAULT_QUALITY) };
            GildedRose app = new GildedRose(items);

            app.updateQuality();

            assertEquals(SELL_IN_BETWEEN_0_AND_5 - 1, app.items[0].sellIn);
            assertEquals(DEFAULT_QUALITY + 3, app.items[0].quality);
        }
        @Test
        void 판매기간이_지난_BACKSTAGE_PASSES일때_가치_0_처리(){
            Item[] items = new Item[] { new Item(BACKSTAGE_PASSES, EXPIRED_SELL_IN, DEFAULT_QUALITY) };
            GildedRose app = new GildedRose(items);

            app.updateQuality();

            assertEquals(EXPIRED_SELL_IN - 1, app.items[0].sellIn);
            assertEquals(MIN_QUALITY, app.items[0].quality);
        }
    }


    @Nested
    class CONJUREDProductTest {
        @Test
        void CONJURED일때_2만큼_감가상각_처리() {
            Item[] items = new Item[] { new Item(CONJURED, NOT_EXPIRED_SELL_IN, DEFAULT_QUALITY) };
            GildedRose app = new GildedRose(items);

            app.updateQuality();

            assertEquals(NOT_EXPIRED_SELL_IN - 1, app.items[0].sellIn);
            assertEquals(DEFAULT_QUALITY - 2, app.items[0].quality);
        }

        @Test
        void 가치가_0인_CONJURED일때_0만큼_감가상각_처리() {
            Item[] items = new Item[] { new Item(CONJURED, NOT_EXPIRED_SELL_IN, MIN_QUALITY) };
            GildedRose app = new GildedRose(items);

            app.updateQuality();

            assertEquals(NOT_EXPIRED_SELL_IN - 1, app.items[0].sellIn);
            assertEquals(MIN_QUALITY, app.items[0].quality);
        }

        @Test
        void 판매기간이_지난_CONJURED일때_4만큼_감가상각_처리() {
            Item[] items = new Item[] { new Item(CONJURED, EXPIRED_SELL_IN, DEFAULT_QUALITY) };
            GildedRose app = new GildedRose(items);

            app.updateQuality();

            assertEquals(EXPIRED_SELL_IN - 1, app.items[0].sellIn);
            assertEquals(DEFAULT_QUALITY - 4, app.items[0].quality);
        }

    }
}
