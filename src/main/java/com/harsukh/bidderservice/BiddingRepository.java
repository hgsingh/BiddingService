package com.harsukh.bidderservice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BiddingRepository extends JpaRepository<Bid, Integer> {
    @Query(value = "SELECT max(maxAutoBidAmount) from Bid")
    double getMaxBid();
}
