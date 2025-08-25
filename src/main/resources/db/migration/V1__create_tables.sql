CREATE TABLE tb_usuario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    apelido VARCHAR(100),
    profissao VARCHAR(255),
    foto VARCHAR(255),
    usuario VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL
);

CREATE TABLE tb_tema (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    status BOOLEAN NOT NULL,
    palavra_chave VARCHAR(100) NOT NULL
);

CREATE TABLE tb_postagem (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    categoria VARCHAR(255),
    titulo VARCHAR(100) NOT NULL,
    texto MEDIUMTEXT NOT NULL,
    midia VARCHAR(255),
    data DATE NOT NULL,
    tema_id BIGINT NOT NULL,
    usuario_id BIGINT NOT NULL,
    CONSTRAINT fk_postagem_tema FOREIGN KEY (tema_id) REFERENCES tb_tema(id),
    CONSTRAINT fk_postagem_usuario FOREIGN KEY (usuario_id) REFERENCES tb_usuario(id)
);
