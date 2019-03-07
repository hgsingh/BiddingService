package com.harsukh.bidderservice;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class AuctionItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int auctionItemId;
    double reservePrice;
    Double currentBid;
    Item item;

    public AuctionItem(double reservePrice, Double currentBid, Item item) {
        this.item = item;
        this.reservePrice = reservePrice;
        this.currentBid = currentBid;
    }
}
