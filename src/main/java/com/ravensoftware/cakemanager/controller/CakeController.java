package com.ravensoftware.cakemanager.controller;

import com.ravensoftware.cakemanager.model.dto.CakeModel;
import com.ravensoftware.cakemanager.service.CakeService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by bilga
 */
@RestController
@CrossOrigin
@RequestMapping(path = "/")
public class CakeController {

    private CakeService cakeService;

    public CakeController(CakeService cakeService) {
        this.cakeService = cakeService;
    }

    // save  CakeModel as Cake to database
    @PostMapping(path = "cakes")
    public ResponseEntity save(@RequestBody @NotNull @Valid CakeModel cakeModel) {
        return ResponseEntity.ok(cakeService.save(cakeModel));
    }

    // download file including cake list on json format
    @GetMapping(path = "cakes")
    public ResponseEntity downloadFile() {
        InputStreamResource resource = cakeService.downloadFile();
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    // get cake lists
    @GetMapping()
    public ResponseEntity getCakeList() {
        return ResponseEntity.ok(cakeService.findAll());
    }

    // filter cakes by title.
    @GetMapping(path = "/filter")
    public ResponseEntity getCakeListByFilter(@RequestParam(required = false, name = "search") String search) {
        return ResponseEntity.ok(cakeService.findByFilter(search));
    }
}
