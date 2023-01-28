package com.prateleiravirtual.api.controller;

import com.prateleiravirtual.domain.service.storage.CloudStorageService;
import io.swagger.v3.oas.annotations.Hidden;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Hidden //ANOTAÇÃO PARA EXCLUIR ESSE END POINT DA DOCUMENTAÇÃO
@RestController
@RequestMapping("/box-drive")
public class BoxDriveController {

    @Autowired
    private CloudStorageService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void post(@RequestBody String json) {
        service.saveBoxConfig(json);
    }
}
