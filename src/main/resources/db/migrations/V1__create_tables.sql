create table tema (
  id bigint auto_increment primary key,
  nome varchar(255) not null
);

create table usuario (
  id bigint auto_increment primary key,
  username varchar(50) not null unique,
  senha varchar(255) not null,
  role varchar(20) not null
);

create table postagem (
  id bigint auto_increment primary key,
  titulo varchar(255),
  conteudo text,
  autor_id bigint,
  tema_id bigint,
  constraint fk_postagem_usuario foreign key (autor_id) references usuario(id),
  constraint fk_postagem_tema foreign key (tema_id) references tema(id)
);
