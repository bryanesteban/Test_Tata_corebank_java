/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     11/9/2024 17:22:51                           */
/*==============================================================*/


drop table if exists CLIENTE;

drop table if exists CUENTA;

drop table if exists MOVIMIENTOS;

drop table if exists PERSONA;


/*==============================================================*/
/* Table: CLIENTE                                               */
/*==============================================================*/
create table CLIENTE
(
   CLIENTEID            int not null auto_increment,
   IDPERSONA            int,
   CONTRASENA           varchar(30) not null,
   ESTADO               varchar(20) not null,
   primary key (CLIENTEID)
);

/*==============================================================*/
/* Table: CUENTA                                                */
/*==============================================================*/
create table CUENTA
(
   NUMEROCUENTA         varchar(30) not null,
   CLIENTEID            int not null,
   TIPOCUENTA           varchar(20) not null,
   SALDO         		varchar(30) not null,
   ESTADO               varchar(20) not null,
   primary key (NUMEROCUENTA)
);

/*==============================================================*/
/* Table: MOVIMIENTOS                                           */
/*==============================================================*/
create table MOVIMIENTOS
(
   IDMOVIMIENTO         int not null auto_increment,
   NUMEROCUENTA         varchar(30) not null,
   FECHAMOVIMIENTO      varchar(30) not null,
   TIPOMOVIMIENTO       varchar(30) not null,
   SALDOINICIAL         varchar(30) not null,
   VALOR                varchar(30) not null,
   SALDO                varchar(30) not null,
   primary key (IDMOVIMIENTO)
);

/*==============================================================*/
/* Table: PERSONA                                               */
/*==============================================================*/
create table PERSONA
(
   IDPERSONA            int not null auto_increment,
   NOMBRE               varchar(50) not null,
   GENERO               varchar(20),
   EDAD                 int,
   IDENTIFICACION       varchar(20) not null,
   DIRECCION            varchar(60) not null,
   TELEFONO             varchar(60) not null,
   primary key (IDPERSONA)
);


alter table CLIENTE add constraint FK_PERSONAXCLIENTE2 foreign key (IDPERSONA)
      references PERSONA (IDPERSONA) on delete restrict on update restrict;

alter table CUENTA add constraint FK_CLIENTEXCUENTA foreign key (CLIENTEID)
      references CLIENTE (CLIENTEID) on delete restrict on update restrict;

alter table MOVIMIENTOS add constraint FK_RELATIONSHIP_3 foreign key (NUMEROCUENTA)
      references CUENTA (NUMEROCUENTA) on delete restrict on update restrict;