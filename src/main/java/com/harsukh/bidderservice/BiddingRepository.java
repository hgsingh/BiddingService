package com.harsukh.bidderservice;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BiddingRepository extends JpaRepository<Bid, Integer> {
}
