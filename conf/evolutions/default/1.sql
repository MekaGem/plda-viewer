# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table email (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  content                   MEDIUMTEXT,
  constraint pk_email primary key (id))
;

create table email_topics_mapping (
  id                        bigint auto_increment not null,
  email_id                  bigint,
  topic_id                  bigint,
  probability               float,
  constraint pk_email_topics_mapping primary key (id))
;

create table topic (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  constraint pk_topic primary key (id))
;

create table topic_words_mapping (
  id                        bigint auto_increment not null,
  topic_id                  bigint,
  word                      varchar(255),
  probability               float,
  constraint pk_topic_words_mapping primary key (id))
;

alter table email_topics_mapping add constraint fk_email_topics_mapping_email_1 foreign key (email_id) references email (id) on delete restrict on update restrict;
create index ix_email_topics_mapping_email_1 on email_topics_mapping (email_id);
alter table email_topics_mapping add constraint fk_email_topics_mapping_topic_2 foreign key (topic_id) references topic (id) on delete restrict on update restrict;
create index ix_email_topics_mapping_topic_2 on email_topics_mapping (topic_id);
alter table topic_words_mapping add constraint fk_topic_words_mapping_topic_3 foreign key (topic_id) references topic (id) on delete restrict on update restrict;
create index ix_topic_words_mapping_topic_3 on topic_words_mapping (topic_id);



# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table email;

drop table email_topics_mapping;

drop table topic;

drop table topic_words_mapping;

SET FOREIGN_KEY_CHECKS=1;

