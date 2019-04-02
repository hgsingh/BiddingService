package com.harsukh.bidderservice;

import javax.persistence.*;

@Entity
public class AuctionItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int auctionItemId;
    double reservePrice;
    Double currentBid;
    @OneToOne(cascade = CascadeType.ALL)
    Item item;

    public AuctionItem(double reservePrice, Double currentBid, Item item) {
        this.item = item;
        this.reservePrice = reservePrice;
        this.currentBid = currentBid;
    }
}
