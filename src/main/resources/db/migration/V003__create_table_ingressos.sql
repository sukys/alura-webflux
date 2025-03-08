create table ingressos(
    id bigserial not null,
    envento_id bigint not null,
    numero_controle varchar(100) not null,
    data_venda date,

    primary key(id),
    CONSTRAINT fk_eventos_ingressos FOREIGN KEY(envento_id) REFERENCES eventos(id)
);