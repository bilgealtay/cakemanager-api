package com.ravensoftware.cakemanager.controller;

import com.ravensoftware.cakemanager.model.dto.CakeModel;
import com.ravensoftware.cakemanager.service.CakeService;
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

    @PostMapping(path = "cakes")
    public ResponseEntity save(@RequestBody @NotNull @Valid CakeModel cakeModel) {
        return ResponseEntity.ok(cakeService.save(cakeModel));
    }

    @GetMapping()
    public ResponseEntity getCakeList() {
        return ResponseEntity.ok(cakeService.findAll());
    }

    @GetMapping(path = "/filter")
    public ResponseEntity getCakeListByFilter(@RequestParam(required = false, name = "search") String search) {
        return ResponseEntity.ok(cakeService.findByFilter(search));
    }
}
