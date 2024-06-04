alter table pages add column notes varchar(512);
alter table pages add column implementation_status tinyint default 0;

create table page_building_block_property_values (property_value varchar(512), key_name varchar(255) not null, page_building_block_id binary(16) not null, primary key (key_name, page_building_block_id));

alter table page_building_blocks add column order_index tinyint default 0;
alter table teaching_phase add column teacher_presence tinyint default 0;

create table users (user_id varchar(255), first_name varchar(255), last_name varchar(255), primary key (user_id));