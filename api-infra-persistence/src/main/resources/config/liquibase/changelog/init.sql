-- liquibase formatted sql
-- changeset ismyburguer:1
alter table if exists item_pedido drop constraint if exists fk_item_pedido_pedido;
drop table if exists item_pedido cascade;
drop table if exists pedido cascade;
drop table if exists produto cascade;
drop table if exists controle_pedido cascade;
create table pedido (valor_total numeric(38,2), cliente_id uuid, pedido_id uuid not null, status_pedido varchar(255) check (status_pedido in ('ABERTO','FECHADO','PAGO','AGUARDANDO_PAGAMENTO','PAGAMENTO_NAO_AUTORIZADO','RECEBIDO','EM_PREPARACAO','PRONTO','FINALIZADO')), primary key (pedido_id));
create table produto (ativo boolean not null, preco numeric(38,2) not null, produto_id uuid not null, categoria varchar(255) not null check (categoria in ('LANCHE','ACOMPANHAMENTO','BEBIDA','SOBREMESA')), descricao varchar(255), primary key (produto_id));
create table item_pedido (preco numeric(38,2) not null, quantidade integer not null, valor_total numeric(38,2) not null, item_pedido_id uuid not null, pedido_id uuid, produto_id uuid references produto(produto_id), primary key (item_pedido_id), constraint uk_item_pedido_produto unique (pedido_id, produto_id));
alter table if exists item_pedido add constraint fk_item_pedido_pedido foreign key (pedido_id) references pedido;
