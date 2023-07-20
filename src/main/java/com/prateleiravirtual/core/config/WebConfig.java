package com.prateleiravirtual.core.config;

import jakarta.servlet.Filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Classe destinada a configurações relacionadas ao consumo da API via Web.
 *
 * @author Jhansen Barreto
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Método que adiciona configurações de Cross-Origin Resource Sharing (CORS)
     * à API. CORS é um mecanismo que permite os recursos restritos em uma
     * página da web serem recuperados por outro domínio fora do domínio ao qual
     * pertence o recurso que será recuperado.
     *
     * Para informações mais detalhadas acesse:
     * https://pt.wikipedia.org/wiki/Cross-origin_resource_sharing
     *
     * @param registry (parâmetro injetado pelo contexto do próprio Spring)
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedMethods("*");
    }

    /**
     * Este método adiciona um filtro no contexto do Spring para verificar as
     * solicitações dos clientes (consumidores da API). Uma vez solicitado um
     * recurso, o mesmo é servido com um ETag no cabeçalho da resposta HTTP,
     * onde um hash está associado. O cliente guarda este recurso em seu cache
     * local e caso necessite fazer uma nova chamada pelo mesmo recurso, o valor
     * hash da ETag é enviado no cabeçalho da requisição com um IF_NONE_MATCH. O
     * servidor apenas verifica se o recurso mudou ou continua o mesmo desde a
     * última chamada verificando justamente esse hash. Caso não haja mudança, o
     * servidor retorna apenas um status 304, avisando que o recurso não foi
     * modificado. Assim, o recurso armazenado no cache do cliente pode
     * continuar sendo utilizado, caso contrário, o servidor envia o novo
     * recurso (atualizado) com uma nova ETag (um novo hash).
     *
     * Para informações mais detalhadas acesse:
     * https://www.baeldung.com/etags-for-rest-with-spring
     *
     * @return -> Nova instância de ShallowEtagHeaderFilter
     */
    @Bean
    public Filter shallowEtagHeaderFilter() {
        return new ShallowEtagHeaderFilter();
    }
}
