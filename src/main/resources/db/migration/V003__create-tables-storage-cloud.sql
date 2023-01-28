CREATE TABLE cloud_client (
    id BIGINT NOT NULL AUTO_INCREMENT, 
    bytes MEDIUMBLOB NOT NULL,
   
    PRIMARY KEY(id)
)ENGINE=InnoDB CHARSET=UTF8;

CREATE TABLE cloud_folder (
    id VARCHAR(20) NOT NULL,
    name_folder VARCHAR(20) NOT NULL,

    PRIMARY KEY(id)
)ENGINE=InnoDB CHARSET=UTF8;

CREATE TABLE cloud_file (
    id VARCHAR(20) NOT NULL,
    name_file VARCHAR(100) NOT NULL,
    cloud_folder_id VARCHAR(20) NOT NULL,
    url VARCHAR(255) NOT NULL,

    PRIMARY KEY(id),
    CONSTRAINT fk_folder_file FOREIGN KEY (cloud_folder_id) REFERENCES cloud_folder (id)
)ENGINE=InnoDB CHARSET=UTF8;
