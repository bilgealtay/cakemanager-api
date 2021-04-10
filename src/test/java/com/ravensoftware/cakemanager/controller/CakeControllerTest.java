package com.ravensoftware.cakemanager.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ravensoftware.cakemanager.constants.MessageConstants;
import com.ravensoftware.cakemanager.model.dto.CakeModel;
import com.ravensoftware.cakemanager.service.CakeService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by bilga
 */
@RunWith(SpringRunner.class)
@WebMvcTest(CakeController.class)
public class CakeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CakeService cakeService;

    ObjectMapper mapper =  new ObjectMapper();

    @Test
    public void should_return_bad_request_when_body_is_empty() throws Exception {

        CakeModel cakeModel = new CakeModel();

        mockMvc.perform(post("/cakes")
                .content(mapper.writeValueAsString(cakeModel))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void should_return_bad_request_when_title_is_null() throws Exception {

        CakeModel cakeModel = new CakeModel(null, "description", "imagePath");

        mockMvc.perform(post("/cakes")
                .content(mapper.writeValueAsString(cakeModel))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void should_return_bad_request_when_description_is_null() throws Exception {

        CakeModel cakeModel = new CakeModel("title", null, "imagePath");

        mockMvc.perform(post("/cakes")
                .content(mapper.writeValueAsString(cakeModel))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void should_return_bad_request_when_image_is_null() throws Exception {

        CakeModel cakeModel = new CakeModel("title", "description", null);

        mockMvc.perform(post("/cakes")
                .content(mapper.writeValueAsString(cakeModel))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void should_return_duplicate_message_when_title_is_duplicate() throws Exception {

        CakeModel cakeModel = new CakeModel("title", "description", "imagePath");

        when( cakeService.save( cakeModel ) )
                .thenReturn(MessageConstants.DUPLICATE_TITLE);

        MvcResult result = mockMvc.perform(post("/cakes")
                .content(mapper.writeValueAsString(cakeModel))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();

        Assert.assertEquals(MessageConstants.DUPLICATE_TITLE, content);
    }

    @Test
    public void should_return_save_message_when_cake_is_ok() throws Exception {

        CakeModel cakeModel = new CakeModel("title", "description", "imagePath");

        when( cakeService.save(cakeModel) )
                .thenReturn(MessageConstants.SAVED);

        MvcResult result = mockMvc.perform(post("/cakes")
                .content(mapper.writeValueAsString(cakeModel))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();

        Assert.assertEquals(MessageConstants.SAVED, content);
    }

    @Test
    public void should_return_empty_list_when_cake_does_not_exist() throws Exception {

        List<CakeModel> tt = new ArrayList<>();
        when( cakeService.findByFilter(anyString()))
                .thenReturn(tt);

        MvcResult result = mockMvc.perform(get("/?search={searchText}", "blabla")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        List<CakeModel> content = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<CakeModel>>(){});

        Assert.assertTrue(content.isEmpty());
    }

    @Test
    public void should_return_all_cakes() throws Exception {

        List<CakeModel> cakes = new ArrayList<>();
        cakes.add(new CakeModel("title", "description", "imagePath"));
        cakes.add(new CakeModel("title2", "description2", "imagePath2"));
        when( cakeService.findAll())
                .thenReturn(cakes);

        MvcResult result = mockMvc.perform(get("/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        List<CakeModel> content = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<CakeModel>>(){});

        Assert.assertTrue(content.size() == 2);
    }

    @Test
    public void should_return_all_cakes_when_search_is_empty() throws Exception {

        List<CakeModel> cakes = new ArrayList<>();
        cakes.add(new CakeModel("title", "description", "imagePath"));
        cakes.add(new CakeModel("title2", "description2", "imagePath2"));
        when( cakeService.findByFilter(anyString()))
                .thenReturn(cakes);

        MvcResult result = mockMvc.perform(get("/filter/?search={searchText}", "")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        List<CakeModel> content = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<CakeModel>>(){});

        Assert.assertTrue(content.size() == 2);
    }

    @Test
    public void should_return_filtered_cakes_when_search_is_not_empty() throws Exception {

        String searchText = "title2";

        List<CakeModel> filteredCakes = new ArrayList<>();
        filteredCakes.add(new CakeModel("title2", "description2", "imagePath2"));
        filteredCakes.add(new CakeModel("title21", "description2", "imagePath2"));

        when( cakeService.findByFilter(searchText))
                .thenReturn(filteredCakes);

        MvcResult result = mockMvc.perform(get("/filter/?search={searchText}", searchText)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        List<CakeModel> content = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<CakeModel>>(){});

        Assert.assertTrue(content.size() == 2);
    }


}
