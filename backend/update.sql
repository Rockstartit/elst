alter table building_block_properties modify column building_block_version_number bigint not null;
alter table building_blocks modify column building_block_version_number bigint not null;
alter table course_unit_learning_goals modify column course_version_number bigint not null;
alter table course_units modify column course_version_number bigint not null;
alter table courses modify column course_version_number bigint not null;
alter table page_building_blocks modify column building_block_version_number bigint not null;
