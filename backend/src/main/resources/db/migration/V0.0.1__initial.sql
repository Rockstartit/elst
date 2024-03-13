create table building_block_properties (`order` integer, building_block_version_number integer not null, building_block_id binary(16) not null, display_name varchar(255), `key` varchar(255)) engine=InnoDB;
create table building_blocks (release_status tinyint, building_block_version_number integer not null, building_block_id binary(16) not null, name varchar(50) not null, description varchar(255), primary key (building_block_version_number, building_block_id)) engine=InnoDB;
create table course_unit_learning_goals (course_version_number integer not null, course_id binary(16) not null, value varchar(255)) engine=InnoDB;
create table course_unit_sub_topics (date date, subtopic_id binary(16) not null, topic_id binary(16) not null, content varchar(255), description varchar(255), title varchar(255), primary key (subtopic_id)) engine=InnoDB;
create table course_unit_topics (`date` date, course_unit_id binary(16) not null, topic_id binary(16) not null, content varchar(255), description varchar(255), title varchar(255), primary key (topic_id)) engine=InnoDB;
create table course_units (course_version_number integer not null, course_id binary(16) not null, course_unit_id binary(16) not null, bibliography varchar(255), description varchar(255), e_book varchar(255), e_reading varchar(255), related_links varchar(255), primary key (course_unit_id)) engine=InnoDB;
create table courses (grad_required bit, course_version_number integer not null, course_id binary(16) not null, code varchar(255), credit_points varchar(255), degree varchar(255), knowledge varchar(255), name varchar(255), semester varchar(255), skills varchar(255), value varchar(255), primary key (course_version_number, course_id)) engine=InnoDB;
create table page_building_blocks (building_block_version_number integer not null, building_block_id binary(16) not null, page_building_block_id binary(16) not null, page_page_id binary(16) not null, primary key (page_building_block_id)) engine=InnoDB;
create table pages (course_unit_course_unit_id binary(16) not null, page_id binary(16) not null, title varchar(255), primary key (page_id)) engine=InnoDB;

alter table course_units add constraint UK_slffmnlgsp4yi3d5dvlvpnx10 unique (course_id, course_version_number);
alter table building_block_properties add constraint FKsa46ycrttslf80o4rxs1pfiy3 foreign key (building_block_version_number, building_block_id) references building_blocks (building_block_version_number, building_block_id);
alter table course_unit_sub_topics add constraint FK2tf22yji48c04l4ixvydf128o foreign key (topic_id) references course_unit_topics (topic_id);
alter table course_unit_topics add constraint FK11ko3yu3d24jy5gj3krwm0n4a foreign key (course_unit_id) references course_units (course_unit_id);
alter table course_units add constraint FK43tfhp5q9wfyj1mtuk0ck3jju foreign key (course_version_number, course_id) references courses (course_version_number, course_id);
alter table page_building_blocks add constraint FKepc9fkt6pcsjh33o9dcdf5j1a foreign key (page_page_id) references pages (page_id);
alter table pages add constraint FK1gcntb182i0x522cv5ypyrajo foreign key (course_unit_course_unit_id) references course_units (course_unit_id);
