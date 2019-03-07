package com.harsukh.bidderservice;


import org.springframework.data.jpa.repository.JpaRepository;


public interface AuctionItemRepository extends JpaRepository<AuctionItem, Integer> {
}
