CREATE TABLE imagem_usuario (
    usuario_id BIGINT NOT NULL, 
    nome_arquivo VARCHAR(255) NOT NULL,
    content_type VARCHAR(20) NOT NULL,
    tamanho BIGINT NOT NULL,
    url VARCHAR(255),
   
    PRIMARY KEY(usuario_id)
)ENGINE=InnoDB CHARSET=UTF8;

CREATE TABLE usuario (
    id BIGINT NOT NULL AUTO_INCREMENT,
    username VARCHAR(20) NOT NULL,
    email VARCHAR(255) NOT NULL,
    senha TEXT NOT NULL,
    ativo TINYINT(1) NOT NULL,
    imagem_usuario_id BIGINT,
    data_criacao DATETIME(6) NOT NULL,
    data_atualizacao DATETIME(6) NOT NULL,

    PRIMARY KEY(id),
    UNIQUE INDEX (username),
    UNIQUE INDEX (email),
    CONSTRAINT fk_imagem_usuario FOREIGN KEY (imagem_usuario_id) REFERENCES imagem_usuario (usuario_id)
)ENGINE=InnoDB CHARSET=UTF8;

CREATE TABLE redefinicao_senha (
    usuario_id BIGINT NOT NULL,
    codigo_redefinicao VARCHAR(100) NOT NULL,
    data_criacao DATETIME(6) NOT NULL,
    data_atualizacao DATETIME(6) NOT NULL,

    PRIMARY KEY(usuario_id),
    UNIQUE(codigo_redefinicao),
    CONSTRAINT fk_usuario_redefinicao_senha FOREIGN KEY (usuario_id) REFERENCES usuario (id)
)ENGINE=InnoDB CHARSET=UTF8;

CREATE TABLE ativacao_usuario (
    usuario_id BIGINT NOT NULL,
    codigo_ativacao VARCHAR(100) NOT NULL,
    data_criacao DATETIME(6) NOT NULL,
    data_atualizacao DATETIME(6) NOT NULL,

    PRIMARY KEY(usuario_id),
    UNIQUE(codigo_ativacao),
    CONSTRAINT fk_usuario_ativacao_usuario FOREIGN KEY (usuario_id) REFERENCES usuario (id)
)ENGINE=InnoDB CHARSET=UTF8;

CREATE TABLE historico_ativacao_desativacao_usuario (
    id BIGINT NOT NULL AUTO_INCREMENT,
    usuario_id BIGINT NOT NULL,
    acao VARCHAR(9) NOT NULL,
    data_criacao DATETIME(6) NOT NULL,

    PRIMARY KEY(id),
    CONSTRAINT fk_historico_usuario FOREIGN KEY (usuario_id) REFERENCES usuario (id)
)ENGINE=InnoDB CHARSET=UTF8;

CREATE TABLE leitura_feita (
    usuario_id BIGINT NOT NULL,
    obra_id BIGINT NOT NULL, 

    PRIMARY KEY(usuario_id, obra_id),
    CONSTRAINT fk_leitura_feita_usuario FOREIGN KEY (usuario_id) REFERENCES usuario (id),
    CONSTRAINT fk_leitura_feita_obra FOREIGN KEY (obra_id) REFERENCES obra (id)
)ENGINE=InnoDB CHARSET=UTF8;

CREATE TABLE leitura_a_fazer (
    usuario_id BIGINT NOT NULL,
    obra_id BIGINT NOT NULL, 

    PRIMARY KEY(usuario_id, obra_id),
    CONSTRAINT fk_leitura_a_fazer_usuario FOREIGN KEY (usuario_id) REFERENCES usuario (id),
    CONSTRAINT fk_leitura_a_fazer_obra FOREIGN KEY (obra_id) REFERENCES obra (id)
)ENGINE=InnoDB CHARSET=UTF8;

CREATE TABLE leitura_em_andamento (
    usuario_id BIGINT NOT NULL,
    obra_id BIGINT NOT NULL, 

    PRIMARY KEY(usuario_id, obra_id),
    CONSTRAINT fk_leitura_em_andamento_usuario FOREIGN KEY (usuario_id) REFERENCES usuario (id),
    CONSTRAINT fk_leitura_em_andamento_obra FOREIGN KEY (obra_id) REFERENCES obra (id)
)ENGINE=InnoDB CHARSET=UTF8;