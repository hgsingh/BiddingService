package com.harsukh.bidderservice;

import javax.persistence.*;

@Entity
public class ItemReserve {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int id;
    @OneToOne(cascade = {CascadeType.ALL})
    public Item item;
    public double reservePrice;
}
