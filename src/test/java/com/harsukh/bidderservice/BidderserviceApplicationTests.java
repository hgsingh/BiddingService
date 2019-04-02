package com.harsukh.bidderservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.NestedServletException;

import java.nio.charset.Charset;
import java.util.Arrays;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BidderserviceApplication.class)
@WebAppConfiguration
public class BidderserviceApplicationTests {

    @Test
    public void contextLoads() {
    }

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    private HttpMessageConverter mappingJackson2HttpMessageConverter;


    @Autowired
    private AuctionItemRepository auctionItemRepository;

    @Autowired
    private BiddingRepository biddingRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        this.auctionItemRepository.deleteAll();
        this.biddingRepository.deleteAll();
    }


    @Test
    public void auctionItemEmpty() throws Exception {
        mockMvc.perform(get("/auctionItems"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void testPostItemAndReceiveValidReserveId() throws Exception {
        Item item = new Item();
        item.description = "test value";
        item.itemId = "abcd";
        ItemReserve itemReserve = new ItemReserve();
        itemReserve.item = item;
        itemReserve.reservePrice = 100.00;
        mockMvc.perform(post("/auctionItems").contentType(contentType)
                .content(objectMapper.writeValueAsString(itemReserve)).accept(contentType))
                .andExpect(status().is2xxSuccessful());
        Assert.assertTrue(auctionItemRepository.count() == 1L);
    }

    @Test
    public void testGetOrder() throws Exception {
        Item item = new Item();
        item.description = "test value";
        item.itemId = "abcd";
        ItemReserve itemReserve = new ItemReserve();
        itemReserve.item = item;
        itemReserve.reservePrice = 100.00;
        MvcResult result = mockMvc.perform(post("/auctionItems").contentType(contentType)
                .content(objectMapper.writeValueAsString(itemReserve)).accept(contentType))
                .andExpect(status().is2xxSuccessful()).andReturn();
        MockHttpServletResponse response = result.getResponse();



    }

    @Test
    public void testBid() {

    }
}
