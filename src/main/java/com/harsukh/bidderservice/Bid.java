package com.harsukh.bidderservice;

import javax.persistence.*;

@Entity
public class Bid {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    int bidId;
    String bidderName;
    int auctionItemId;
    double maxAutoBidAmount;
}
