package com.product;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { Application.class }, webEnvironment = SpringBootTest.WebEnvironment.MOCK) // specify boot class
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integration-test.properties")
public class ApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    /**
     * Web Application Context
     */
    @Autowired
    private WebApplicationContext webApplicationContext;

    /**
     * Will execute before ALL unit test
     */
    @Before
    public void before() {
        // Get mockMvc instance
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

//    /**
//     * Test add product
//     * 
//     * @throws Exception
//     */
//    @Test
//    public void addGood() throws Exception {
//        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/good/save").param("name", "西瓜").param("unit", "斤").param("price", "12.88"))
//                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().is(200)).andReturn();
//        result.getResponse().setCharacterEncoding("UTF-8");
//        System.out.println(result.getResponse().getContentAsString());
//    }

//    /**
//     * Test add schedule job
//     * 
//     * @throws Exception
//     */
//    @Test
//    public void addGood() throws Exception {
//        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/good/save").param("name", "西瓜").param("unit", "斤").param("price", "12.88"))
//                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().is(200)).andReturn();
//        result.getResponse().setCharacterEncoding("UTF-8");
//        System.out.println(result.getResponse().getContentAsString());
//    }

}