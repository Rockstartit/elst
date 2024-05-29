create table building_block_mockups (mockup_id binary(16) not null, building_block_id binary(16) not null, created_by varchar(255), description varchar(255), file_id binary(16), primary key (mockup_id)) engine=InnoDB;

create table readme (building_block_id binary(16) not null, content blob, primary key (building_block_id)) engine=InnoDB;

alter table teaching_units add column order_index varchar(255) default 0;
alter table teaching_phase add column order_index varchar(255) default 0;
alter table pages add column order_index varchar(255) default 0;

drop table building_block_properties;

create table building_block_properties (building_block_id binary(16) not null, display_name varchar(255), `key` varchar(255), `order` integer, type tinyint, description varchar(255), primary key (building_block_id, `key`)) engine=InnoDB;
alter table building_block_properties add constraint FKsa46ycrttslf80o4rxs1pfiy3 foreign key (building_block_id) references building_blocks (building_block_id);
