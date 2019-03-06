package com.harsukh.bidderservice;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuctionItemGenerator {
    final ItemService itemService = new ItemService();

    @RequestMapping(value = "/auctionItems", method = RequestMethod.POST)
    public ResponseEntity<String> persistPerson(@RequestBody Item item) {
        if (Item.isValid(item)) {
           String auctionItemId = itemService.persistItemAndGenerateAuctionItemId(item);
           return ResponseEntity.status(HttpStatus.ACCEPTED).body(auctionItemId);
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }


}
