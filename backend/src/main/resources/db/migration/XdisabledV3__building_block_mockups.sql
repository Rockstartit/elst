create table building_block_mockups (mockup_id binary(16) not null, building_block_id binary(16) not null, created_by varchar(255), description varchar(255), file_id binary(16), primary key (mockup_id)) engine=InnoDB;

create table readme (building_block_id binary(16) not null, content blob, primary key (building_block_id)) engine=InnoDB;

alter table teaching_units add column order_index varchar(255) default 0;
alter table teaching_phase add column order_index varchar(255) default 0;