package com.harsukh.bidderservice;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class AuctionItem {
    @Id @GeneratedValue
    private int auctionItemId;
    private int reservePrice;
    private double currentBid;

    public int getReservePrice() {
        return reservePrice;
    }

    public double getCurrentBid() {
        return currentBid;
    }

    public int getAuctionItemId() {
        return auctionItemId;
    }
}
