create database FerreteriaTop;
use FerreteriaTop;

create table clientes(
id_cliente int,
R_social varchar(15),
RFC varchar(20),
tipo varchar(10),
id_person_fk int);

create table persona(
id_person int,
nombre varchar(10),
apellido_1 varchar(10),
apellido_2 varchar(10),
direccion varchar(18),
colonia varchar(10),
correo varchar(20),
id_LTelefonica int,
fecha_nacimiento date,
id_trasaccionxP int);

create table proveedores(
id_proveedor int, 
ultima_visita date,
id_person_fk int);

create table TransxPerson(
id_person_fk int,
id_transaccion_fk int,
id_TxP int);

create table Usuario(
id_usuario int,
tipo varchar(10),
comision int,
id_person_fk int);

create table transaccion(
id_transaccion int,
fecha date,
id_TxP_fk int,
tipo varchar(10),
id_listaProd_fk int, 
precio_sin_iva int,
iva int,
precio_con_iva int,
forma_Pago varchar(10));

create table Producto(
ID_producto int,
nombre varchar(10),
descripcion varchar(100),
existencia int,
costo int,
precio int,
id_Prod_Proveedor_fk int,
inventario_max int,
inventario_min int);

create table Prod_proveedot(
id_proveedor_fk int,
id_producto_fk int,
id_Prod_proveedor int);

create table L_producto(
Id_lista_P int,
Id_producto_fk int,
cantidad int,
id_transaccion_fk int);

create table L_telefonos(
Id_lTelefonica int,
Id_persona int,
telefono int,
extension int);

show tables;
select*from Usuario;
alter table Usuario add primary key(id_usuario);
alter table clientes add primary key(id_cliente);
alter table persona add primary key(id_person);
alter table proveedores add primary key(id_proveedor);
alter table TransxPerson add primary key(id_TxP);
alter table transaccion add primary key(id_transaccion);
alter table Producto add primary key(ID_producto);
alter table Prod_proveedot add primary key(id_Prod_proveedor);
alter table L_producto add primary key(Id_lista_P);
alter table L_telefonos add primary key(Id_lTelefonica);

ALTER TABLE Usuario
ADD FOREIGN KEY (id_person_fk) REFERENCES 
persona(id_person);

ALTER TABLE Usuario auto_INCREMENT=1;
ALTER TABLE clientes auto_INCREMENT=1;
ALTER TABLE persona auto_INCREMENT=1;
ALTER TABLE proveedores auto_INCREMENT=1;
ALTER TABLE TransxPerson auto_INCREMENT=1;
ALTER TABLE transaccion auto_INCREMENT=1;
ALTER TABLE Producto auto_INCREMENT=1;
ALTER TABLE Prod_proveedot auto_INCREMENT=1;
ALTER TABLE L_producto auto_INCREMENT=1;
ALTER TABLE L_telefonos auto_INCREMENT=1;

insert into persona (nombre,apellido_1,apellido_2,direccion,
colonia,correo,fecha_nacimiento,tipo)
values('Sandra','Gonzalez','Valadez','presa allende',
'presitas del consuelo','sandra.valadez.dl@gmail.com','1997-04-09',
'usuario');
insert into persona (nombre,apellido_1,apellido_2,direccion,
colonia,correo,fecha_nacimiento,tipo)
values('Silvestre','Ramirez','Becerra','Door 123',
'House','S.R.Becerra@gmail.com','1996-11-26',
'usuario');
insert into persona (nombre,apellido_1,apellido_2,direccion,
colonia,correo,fecha_nacimiento,tipo)
values('Cristian','Alvarez','Trujillo','Mouse 565',
'Serve','RCA@hotmail.com','1997-9-15',
'usuario');
insert into persona (nombre,apellido_1,apellido_2,direccion,
colonia,correo,fecha_nacimiento,tipo)
values('Martin','Coss','Hernandez','Gto 6132',
'Guanajuato','MCoss@hotmail.com','1995-4-2',
'usuario');
insert into persona (nombre,apellido_1,apellido_2,direccion,
colonia,correo,fecha_nacimiento,tipo)
values('Hugo','González','Valadez','presa allende 132',
'Presitas','Glez_v@gmail.com','1998-4-30',
'usuario');

insert into Usuario(tipo,comision,id_person_fk,contraseña,estado) values('admin',10,1,'abcd','activo');
insert into Usuario(tipo,comision,id_person_fk,contraseña,estado) values('usuario',30,2,'1234','activo');
insert into Usuario(tipo,comision,id_person_fk,contraseña,estado) values('usuario',8,3,'cristian','activo');
insert into Usuario(tipo,comision,id_person_fk,contraseña,estado) values('usuario',20,4,'hola','activo');
insert into Usuario(tipo,comision,id_person_fk,contraseña,estado) values('admin',50,5,'gato','activo');

alter table persona change fecha_nacimiento fecha_nacimiento varchar(20);
alter table persona change id_person id_person int not null auto_increment;
alter table Usuario change id_usuario id_usuario int not null auto_increment;
alter table clientes change id_cliente id_cliente int not null auto_increment;
alter table proveedores change id_proveedor id_proveedor int not null auto_increment;
alter table TransxPerson change id_TxP id_TxP int not null auto_increment;
alter table transaccion change id_transaccion id_transaccion int not null auto_increment;
alter table Prod_proveedot change id_Prod_proveedor id_Prod_proveedor int not null auto_increment;
alter table L_producto change Id_lista_P Id_lista_P int not null auto_increment;
alter table L_telefonos change Id_lTelefonica Id_lTelefonica int not null auto_increment;

alter table Usuario add column contraseña varchar(20);
alter table Usuario add column estado varchar(20);
alter table persona add column tipo varchar(20);


delete from persona;
update persona set id_person=1 where nombre='Sandra';
set SQL_SAFE_UPDATES=0;

insert into Usuario(tipo,comision,id_person_fk)values('admin',212,1);
delete from Usuario where id_usuario=7;

select*from transaccion;
select* from persona;
select*from Usuario ;

-- join
select* from persona p join Usuario u where u.id_person_fk=p.id_person and id_usuario=1;

select* from persona p join Usuario u where u.id_person_fk=p.id_person and id_usuario=1;

