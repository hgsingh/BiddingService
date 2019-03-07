package com.harsukh.bidderservice;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Item {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
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
