package com.ravensoftware.cakemanager.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ravensoftware.cakemanager.constants.MessageConstants;
import com.ravensoftware.cakemanager.model.dto.CakeModel;
import com.ravensoftware.cakemanager.model.entity.Cake;
import com.ravensoftware.cakemanager.repository.CakeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by bilga
 */
@Service
public class CakeService {

    @Autowired
    private CakeRepository cakeRepository;

    // save Cake
    public String save(CakeModel cakeModel){

        if (cakeModel.getTitle() == null || cakeModel.getTitle().isEmpty()){
            return MessageConstants.NULL_TITLE;
        } else {
            if(findCakeByTitle(cakeModel.getTitle()) != null){
                return MessageConstants.DUPLICATE_TITLE;
            }
        }

        if (cakeModel.getDescription() == null || cakeModel.getDescription().isEmpty()){
            return MessageConstants.NULL_DESCRIPTION;
        }
        if (cakeModel.getImage() == null || cakeModel.getImage().isEmpty()){
            return MessageConstants.NULL_IMAGE;
        }
        cakeRepository.save(convertCakeFromModel(cakeModel));

        return MessageConstants.SAVED;
    }

    public Cake findCakeByTitle(String title) {
        return cakeRepository.findByTitle(title);
    }


    // return Cake from CakeModel
    private Cake convertCakeFromModel(CakeModel model){
        Cake cake = new Cake();
        cake.setId(model.getId());
        cake.setTitle(model.getTitle());
        cake.setDescription(model.getDescription());
        cake.setImage(model.getImage());
        return cake;
    }

    public List<CakeModel> findAll() {
        return convertToResponse(cakeRepository.findAll());
    }

    // filter by Title
    public List<CakeModel> findByFilter(String search) {
        if (search == null || search.isEmpty()){
            return findAll();
        } else {
            return convertToResponse(cakeRepository.findByFilter(search));
        }
    }

    private List<CakeModel> convertToResponse(List<Cake> fields) {
        return fields.stream().map(this::fillResponse).collect(Collectors.toList());
    }

    // convert Cake to Cake Model
    private CakeModel fillResponse(Cake cake) {
        CakeModel response = new CakeModel();
        response.setId(cake.getId());
        response.setTitle(cake.getTitle());
        response.setDescription(cake.getDescription());
        response.setImage(cake.getImage());
        return response;
    }

    // file download
    public InputStreamResource downloadFile(){
        List<CakeModel> cakes = findAll();

        ObjectMapper objectMapper = new ObjectMapper();
        String arrayToJson = null;
        InputStreamResource resource = null;

        try {
            arrayToJson = objectMapper.writeValueAsString(cakes);
            BufferedWriter writer = new BufferedWriter(new FileWriter("cakes.txt"));
            writer.write(arrayToJson);
            writer.close();
            resource = new InputStreamResource(new ByteArrayInputStream(arrayToJson.getBytes("UTF-8")));

        } catch (Exception ex) {
            ex.printStackTrace();
            ex.getMessage();
        }
        return resource;
    }
}
