SET foreign_key_checks = 0;

DELETE FROM editora;
DELETE FROM genero_obra;
DELETE FROM tipo_obra;
DELETE FROM obra_autor;
DELETE FROM autor;
DELETE FROM obra;
DELETE FROM usuario;
DELETE FROM imagem_autor;
DELETE FROM imagem_obra;
DELETE FROM imagem_usuario;
DELETE FROM leitura_feita;
DELETE FROM leitura_a_fazer;
DELETE FROM leitura_em_andamento;
DELETE FROM ativacao_usuario;
DELETE FROM historico_ativacao_desativacao_usuario;
DELETE FROM cloud_client;

SET foreign_key_checks = 1;

ALTER TABLE editora AUTO_INCREMENT = 1;
ALTER TABLE genero_obra AUTO_INCREMENT = 1;
ALTER TABLE tipo_obra AUTO_INCREMENT = 1;
ALTER TABLE autor AUTO_INCREMENT = 1;
ALTER TABLE obra AUTO_INCREMENT = 1;
ALTER TABLE usuario AUTO_INCREMENT = 1;
ALTER TABLE historico_ativacao_desativacao_usuario AUTO_INCREMENT = 1;
ALTER TABLE cloud_client AUTO_INCREMENT = 1;

INSERT INTO genero_obra (descricao) 
    VALUES ('AUTOAJUDA'), ('ROMANCE'), ('BIOGRAFIA'), ('THRILLER'), ('FANTASIA');

INSERT INTO tipo_obra (descricao) 
    VALUES ('LIVRO'), ('HQ');

INSERT INTO editora (nome)
    VALUES ('ARQUEIRO'), ('GLOBO LIVROS'), ('DIALÉTICO'), ('DARKSIDE BOOKS'), ('SUMA');

INSERT INTO autor (nome)
    VALUES ('STEPHEN KING'), ('DAN BROWN'), ('J. K. ROWLING'), ('J. R. R. TOLKIEN');

INSERT INTO obra (titulo, subtitulo, ano, descricao, genero_obra_id, tipo_obra_id, paginas, editora_id)
    VALUES ('IT', 'A Coisa', 1986, 'A história segue as experiências de sete crianças, que são aterrorizadas por uma entidade maligna que explora os medos de suas vítimas para se disfarçar enquanto caçam suas presas. `IT´ aparece principalmente na forma do palhaço Pennywise para atrair sua presa preferida: crianças pequenas.', 5, 1, 1104, 5);

INSERT INTO obra (titulo, ano, descricao, genero_obra_id, tipo_obra_id, paginas, editora_id)
    VALUES ('Anjos e Demônios', 2000, 'Às vésperas do conclave que vai eleger o novo Papa, Langdon é chamado às pressas para analisar um misterioso símbolo marcado a fogo no peito de um físico assassinado em um grande centro de pesquisas na Suíça.', 3, 1, 480, 1);

INSERT INTO obra (titulo, ano, descricao, genero_obra_id, tipo_obra_id, paginas, editora_id)
    VALUES ('O Código da Vinci', 2003, 'O Código Da Vinci segue o simbologista Robert Langdon e a criptologista Sophie Neveu após um assassinato no Museu do Louvre, em Paris, fazendo que eles se envolvam com o Priorado de Sião e com o Opus Dei.', 3, 1, 432, 1);

INSERT INTO obra_autor (obra_id, autor_id)
    VALUES (1,1), (2,2), (3,2);

INSERT INTO usuario (username, email, senha, ativo, data_criacao, data_atualizacao)
    VALUES ('master', 'master@prateleiravirtual.com', '$2a$12$TvlppgPCzjbPS0L5CE6G6eBYoR2sBaedMmFzxvU5ApgCht6f.cFMK', 0, UTC_TIMESTAMP, UTC_TIMESTAMP);

INSERT INTO usuario (username, email, senha, ativo, data_criacao, data_atualizacao)
    VALUES ('teste', 'teste@prateleiravirtual.com', '$2a$12$tKQnNLX1Xs4igjrAW9UN8OvE73334Y3aIqsJVRz7ty4v7wXaCEA/q', 0, UTC_TIMESTAMP, UTC_TIMESTAMP);

INSERT INTO usuario (username, email, senha, ativo, data_criacao, data_atualizacao)
    VALUES ('user', 'user@prateleiravirtual.com', '$2a$12$aFuTFo5c4gLmyvu6MGgszOTao/j3dIAjY2ojHyIRgw7OjLaNqiliG', 0, UTC_TIMESTAMP, UTC_TIMESTAMP);

INSERT INTO leitura_feita (usuario_id, obra_id)
    VALUES (1,3);

INSERT INTO leitura_a_fazer (usuario_id, obra_id)
    VALUES (1,1);

INSERT INTO leitura_em_andamento (usuario_id, obra_id)
    VALUES (1,2);