package com.harsukh.bidderservice;


import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

@RestController
public class AuctionController {
    AuctionItemRepository auctionItemRepository;
    BiddingRepository biddingRepository;

    @Autowired
    public AuctionController(AuctionItemRepository auctionItemRepository, BiddingRepository biddingRepository) {
        this.auctionItemRepository = auctionItemRepository;
        this.biddingRepository = biddingRepository;
    }

    /**
     * Example Request Body:
     * {
     * “reservePrice”: 10450.00,
     * “item”: {
     * “itemId”: “abcd”,
     * “description”: “item description”
     * }
     * }
     * <p>
     * Example Response:
     * {
     * "auctionItemId": "1234"
     * }
     */

    @RequestMapping(value = "/auctionItems", method = RequestMethod.POST)
    public ResponseEntity<String> postItem(@RequestBody ItemReserve itemReserve) {
        boolean alreadyPosted = auctionItemRepository.findAll().stream()
                .anyMatch(predItem -> predItem.item.itemId.equals(itemReserve.item.itemId));
        if (Item.isValid(itemReserve.item) && !alreadyPosted) {
            AuctionItem auctionItem = new AuctionItem(itemReserve.reservePrice, null, itemReserve.item);
            auctionItemRepository.save(auctionItem);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(String.valueOf(auctionItem.auctionItemId));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * {
     * “auctionItemId”: “1234”,
     * “maxAutoBidAmount”: 9500.00,
     * “bidderName”: “ABC Dealership”
     * }
     */
    @RequestMapping(value = "/bids", method = RequestMethod.POST)
    public ResponseEntity<String> postBid(@RequestBody Bid bid) {
        Optional<AuctionItem> auctionItemOptional =
                auctionItemRepository.findById(bid.auctionItemId);
        if (auctionItemOptional.isPresent()) {
            AuctionItem auctionItem = auctionItemOptional.get();
            if (auctionItem.currentBid == null
                    ||  auctionItem.reservePrice - bid.maxAutoBidAmount > 0) {
                //current bid had not been set yet
                auctionItem.currentBid = auctionItem.currentBid == null || bid.maxAutoBidAmount >
                        auctionItem.currentBid ? bid.maxAutoBidAmount : auctionItem.currentBid;
                auctionItemRepository.save(auctionItem);
                if (auctionItem.reservePrice > bid.maxAutoBidAmount) {
                    throw new IllegalArgumentException("Reserve price not met");
                } else {
                    biddingRepository.save(bid);
                    return ResponseEntity.status(HttpStatus.ACCEPTED).build();
                }
            } else {
                if (auctionItem.reservePrice < bid.maxAutoBidAmount
                        && Double.compare(biddingRepository.getMaxBid() - bid.maxAutoBidAmount, 1.00) >= 0) {
                    auctionItemRepository.save(auctionItem);
                    return ResponseEntity.status(HttpStatus.ACCEPTED).build();
                } else {
                    throw new IllegalArgumentException("You've been outbid");
                }
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * {
     * [
     * {
     * “auctionItemId”: “1234”,
     * “currentBid”: 0.00,
     * “reservePrice”: 10450.00,
     * “item”: {
     * “itemId”: “abcd”,
     * “description”: “item description”
     * }
     * },....]}
     */
    @RequestMapping(value = "/auctionItems", method = RequestMethod.GET)
    public ResponseEntity<List<AuctionItem>> getAuctionItems() {
        return ResponseEntity.ok(auctionItemRepository.findAll());
    }

    /**
     * {
     * “auctionItemId”: “1234”,
     * “currentBid”: 0.00,
     * “reservePrice”: 10450.00,
     * “item”: {
     * “itemId”: “abcd”,
     * “description”: “item description”
     * }
     * }
     */
    @RequestMapping(value = "/auctionItems/{auctionItemId}", method = RequestMethod.GET)
    public AuctionItem getOrder(@PathVariable int auctionItemId) {
        return auctionItemRepository.findById(auctionItemId)
                .orElseThrow(() -> new ObjectNotFoundException(auctionItemId, "Error: auction item not found"));
    }

}
