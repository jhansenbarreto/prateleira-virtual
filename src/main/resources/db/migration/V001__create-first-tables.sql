CREATE TABLE editora (
    id BIGINT NOT NULL AUTO_INCREMENT, 
    nome VARCHAR(50) NOT NULL,
   
    PRIMARY KEY(id)
)ENGINE=InnoDB CHARSET=UTF8;

CREATE TABLE genero_obra (
    id BIGINT NOT NULL AUTO_INCREMENT, 
    descricao VARCHAR(50) NOT NULL,
   
    PRIMARY KEY(id)
)ENGINE=InnoDB CHARSET=UTF8;

CREATE TABLE tipo_obra (
    id BIGINT NOT NULL AUTO_INCREMENT, 
    descricao VARCHAR(50) NOT NULL,
   
    PRIMARY KEY(id)
)ENGINE=InnoDB CHARSET=UTF8;

CREATE TABLE imagem_autor (
    autor_id BIGINT NOT NULL, 
    nome_arquivo VARCHAR(255) NOT NULL,
    content_type VARCHAR(20) NOT NULL,
    tamanho BIGINT NOT NULL,
    url VARCHAR(255),
   
    PRIMARY KEY(autor_id)
)ENGINE=InnoDB CHARSET=UTF8;

CREATE TABLE autor (
    id BIGINT NOT NULL AUTO_INCREMENT, 
    nome VARCHAR(50) NOT NULL,
    imagem_autor_id BIGINT,
   
    PRIMARY KEY(id),
    CONSTRAINT fk_imagem_autor FOREIGN KEY (imagem_autor_id) REFERENCES imagem_autor (autor_id)
)ENGINE=InnoDB CHARSET=UTF8;

CREATE TABLE imagem_obra (
    obra_id BIGINT NOT NULL, 
    nome_arquivo VARCHAR(255) NOT NULL,
    content_type VARCHAR(20) NOT NULL,
    tamanho BIGINT NOT NULL,
    url VARCHAR(255),
   
    PRIMARY KEY(obra_id)
)ENGINE=InnoDB CHARSET=UTF8;

CREATE TABLE obra (
    id BIGINT NOT NULL AUTO_INCREMENT, 
    titulo VARCHAR(50) NOT NULL,
    subtitulo VARCHAR(50),
    ano INT NOT NULL,
    descricao TEXT NOT NULL,
    tipo_obra_id BIGINT NOT NULL,
    genero_obra_id BIGINT NOT NULL,
    paginas INT,
    editora_id BIGINT NOT NULL,
    imagem_obra_id BIGINT,
   
    PRIMARY KEY(id),
    CONSTRAINT fk_obra_tipo_obra FOREIGN KEY (tipo_obra_id) REFERENCES tipo_obra (id),
    CONSTRAINT fk_obra_genero_obra FOREIGN KEY (genero_obra_id) REFERENCES genero_obra (id),
    CONSTRAINT fk_obra_editora FOREIGN KEY (editora_id) REFERENCES editora (id),
    CONSTRAINT fk_imagem_obra FOREIGN KEY (imagem_obra_id) REFERENCES imagem_obra (obra_id)
)ENGINE=InnoDB CHARSET=UTF8;

CREATE TABLE obra_autor (
    obra_id BIGINT NOT NULL,
    autor_id BIGINT NOT NULL, 

    PRIMARY KEY(obra_id, autor_id),
    CONSTRAINT fk_obra_autor_obra FOREIGN KEY (obra_id) REFERENCES obra (id),
    CONSTRAINT fk_obra_autor_autor FOREIGN KEY (autor_id) REFERENCES autor (id)
)ENGINE=InnoDB CHARSET=UTF8;