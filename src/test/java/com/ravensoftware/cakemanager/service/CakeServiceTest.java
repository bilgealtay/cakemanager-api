package com.ravensoftware.cakemanager.service;

import com.ravensoftware.cakemanager.constants.MessageConstants;
import com.ravensoftware.cakemanager.model.dto.CakeModel;
import com.ravensoftware.cakemanager.model.entity.Cake;
import com.ravensoftware.cakemanager.repository.CakeRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;


/**
 * Created by bilga
 */
@RunWith(SpringRunner.class)
public class CakeServiceTest {

    @TestConfiguration
    static class GameServicesImplTestContextConfiguration {

        @Bean
        public CakeService cakeService() {
            return new CakeService();
        }
    }

    @Autowired
    private CakeService cakeService;

    @MockBean
    private CakeRepository cakeRepository;

    @Test
    public void should_return_null_title_message_when_title_is_null() throws Exception {
        CakeModel cakeModel = new CakeModel(null, "description", "imagePath");
        String result = cakeService.save(cakeModel);
        Assert.assertEquals(MessageConstants.NULL_TITLE, result);
    }

    @Test
    public void should_return_null_description_message_when_description_is_null() throws Exception {
        CakeModel cakeModel = new CakeModel("title", null, "imagePath");
        String result = cakeService.save(cakeModel);
        Assert.assertEquals(MessageConstants.NULL_DESCRIPTION, result);
    }

    @Test
    public void should_return_null_image_message_when_image_is_null() throws Exception {
        CakeModel cakeModel = new CakeModel("title", "description", null);
        String result = cakeService.save(cakeModel);
        Assert.assertEquals(MessageConstants.NULL_IMAGE, result);
    }

    @Test
    public void should_return_duplicate_message_when_title_is_exist() throws Exception {
        CakeModel cakeModel = new CakeModel("title", "description", "image");

        Cake cake = new Cake("title", "description", "image");

        when( cakeRepository.findByTitle(cakeModel.getTitle()) )
                .thenReturn(cake);

        String result = cakeService.save(cakeModel);
        Assert.assertEquals(MessageConstants.DUPLICATE_TITLE, result);
    }

    @Test
    public void should_return_saved_message_when_cake_is_ok() throws Exception {
        CakeModel cakeModel = new CakeModel("title", "description", "imagePath");
        String result = cakeService.save(cakeModel);
        Assert.assertEquals(MessageConstants.SAVED, result);
    }

    @Test
    public void should_not_return_cake_when_title_does_not_exist() throws Exception {
        String titleStr = "cake title";

        when( cakeRepository.findByTitle(titleStr) )
                .thenReturn(null);

        Cake cake = cakeService.findCakeByTitle(titleStr);

        Assert.assertNull(cake);
    }

    @Test
    public void should_return_cake_when_title_exists() throws Exception {
        String titleStr = "cake title";
        Cake cake = new Cake("cake title", "description", "image");

        when( cakeRepository.findByTitle(titleStr) )
                .thenReturn(cake);

        Cake cake2 = cakeService.findCakeByTitle(titleStr);

        Assert.assertNotNull(cake2);
    }

    @Test
    public void should_return_all_cakes() throws Exception {
        List<Cake> cakes = new ArrayList<>();
        cakes.add(new Cake("title", "description", "imagePath"));
        cakes.add(new Cake("title2", "description2", "imagePath2"));

        when( cakeRepository.findAll() )
                .thenReturn(cakes);

        List<CakeModel> cakeModelList = cakeService.findAll();

        Assert.assertTrue(cakeModelList.size() == cakes.size());
    }

    @Test
    public void should_return_all_cakes_when_search_test_is_empty() throws Exception {
        String searchStr = "";

        List<Cake> cakes = new ArrayList<>();
        cakes.add(new Cake("title", "description", "imagePath"));
        cakes.add(new Cake("title2", "description2", "imagePath2"));

        when( cakeRepository.findAll() )
                .thenReturn(cakes);

        List<CakeModel> cakeModelList = cakeService.findByFilter(searchStr);

        Assert.assertTrue(cakeModelList.size() == cakes.size());
    }

    @Test
    public void should_return_filtered_cakes_when_search_test_is_not_empty() throws Exception {
        String searchStr = "title";

        List<Cake> cakes = new ArrayList<>();
        cakes.add(new Cake("title", "description", "imagePath"));
        cakes.add(new Cake("title2", "description2", "imagePath2"));

        when( cakeRepository.findByFilter(searchStr) )
                .thenReturn(cakes);

        List<CakeModel> cakeModelList = cakeService.findByFilter(searchStr);

        Assert.assertTrue(cakeModelList.size() == cakes.size());
    }

}