package com.prateleiravirtual.core.config;

import com.prateleiravirtual.api.model.dto.input.AutorInput;
import com.prateleiravirtual.api.model.dto.input.ObraInput;

import com.prateleiravirtual.domain.model.Autor;
import com.prateleiravirtual.domain.model.Obra;

import org.modelmapper.ModelMapper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Classe destinada a configurações relacionadas ao mapeamento e conversão de
 * objetos.
 *
 * @author Jhansen Barreto
 */
@Configuration
public class ModelMapperConfig {

    /**
     * Método para inserir um Bean no contexto do Spring e permitir a injeção de
     * dependência para uma instância de ModelMapper.
     *
     * @return -> Instância de ModelMapper
     */
    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();

        /*  Ignorando os métodos 'setAutores' e 'setCapa' de um objeto Obra na converção ObraInput -> Obra
        
            MOTIVO 1: o atributo autores de ObraInput é apenas uma lista de Long, para representar ID's,
                      já o atributo autores de Obra é uma lista com objetos do tipo Autor. A solução para
                      fazer o mapeamento foi implementada nas classes ObraController e ObraService
        
            MOTIVO 2: a classe ObraInput não possui um atributo capa. Na conversão em uma atualização, o 
                      atributo capa em Obra recebe o valor 'null', pois não existe um atributo equivalente
                      na classe de origem. A imagem de capa de uma Obra é associada separadamente.
         */
        modelMapper.createTypeMap(ObraInput.class, Obra.class)
                .addMappings(mapper -> mapper.skip(Obra::setAutores))
                .addMappings(mapper -> mapper.skip(Obra::setCapa));

        /*  Ignorando o método setImagem de um objeto Autor na converção AutorInput -> Autor
        
            MOTIVO: a classe AutorInput não possui um atributo imagem. Na conversão em uma atualização, o 
                    atributo imagem em Autor recebe o valor 'null', pois não existe um atributo equivalente
                    na classe de origem.
         */
        modelMapper.createTypeMap(AutorInput.class, Autor.class)
                .addMappings(mapper -> mapper.skip(Autor::setImagem));

        //Servindo instância já configurada com os detalhes supracitados.
        return modelMapper;
    }
}
