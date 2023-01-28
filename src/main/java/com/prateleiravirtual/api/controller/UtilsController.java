package com.prateleiravirtual.api.controller;

import com.prateleiravirtual.api.controller.utils.Utilidades;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Classe criada apenas para testes
 *
 * @author Jhansen Barreto
 */
@Hidden //ANOTAÇÃO PARA EXCLUIR ESSE END POINT DA DOCUMENTAÇÃO
@RestController
@RequestMapping("/utilities")
public class UtilsController {

    @Autowired
    private Utilidades utils;

    @GetMapping("/codigo")
    public String convert() {
        return utils.gerarCharsAleatorios(8);
    }
}
