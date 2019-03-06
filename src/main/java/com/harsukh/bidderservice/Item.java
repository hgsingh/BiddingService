package com.harsukh.bidderservice;

public class Item {

    String itemId;
    String description;


    static boolean isValid(Item item){
        return !isItemEmpty(item);
    }

    public String getItemId() {
        return itemId;
    }

    public String getDescription() {
        return description;
    }

    private static boolean isItemEmpty(Item item){
        return item.getDescription() == null || item.getDescription().isEmpty()
                || item.getItemId() == null || item.getItemId().isEmpty();
    }

}
