create table readme (building_block_id binary(16) not null, building_block_version_number bigint not null, content blob, primary key (building_block_id, building_block_version_number)) engine=InnoDB;
