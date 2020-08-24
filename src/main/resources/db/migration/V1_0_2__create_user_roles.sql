USE devcamp_db;
    
create table roles (
   id bigint not null auto_increment,
    authority varchar(255) not null,
    primary key (id)
) engine=InnoDB;
    
create table users_roles (
   user_id varchar(255) not null,
    role_id bigint not null,
    primary key (user_id, role_id)
) engine=InnoDB;
    
--alter table roles 
--   drop index UK_nb4h0p6txrmfc0xbrd1kglp9t;
    
alter table roles 
   add constraint UK_nb4h0p6txrmfc0xbrd1kglp9t unique (authority);
    
alter table users_roles 
   add constraint FKj6m8fwv7oqv74fcehir1a9ffy 
   foreign key (role_id) 
   references roles (id);
   
alter table users_roles 
   add constraint FK2o0jvgh89lemvvo17cbqvdxaa 
   foreign key (user_id) 
   references users (id);