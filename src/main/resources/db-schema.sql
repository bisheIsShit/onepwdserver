create schema if not exists test;

/* H2 doesn't support "ON UPDATE" */

CREATE TABLE if not exists `account` (
  `uid` int(32) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(32) NOT NULL,
  `password` varchar(128),
  PRIMARY KEY (`uid`),
  UNIQUE INDEX username_idx(`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE if not exists account_verify (
  `uid` int(32) unsigned NOT NULL,
  `sina_uid` bigint unsigned ,
  `email` varchar(32) ,
  `weixin_uid` varchar(32) ,
  `username` varchar(32) NOT NULL,
  `password` varchar(128),
  `avatar` varchar(512) ,
  gender tinyint ,
  age SMALLINT,
  intro text,
  height SMALLINT DEFAULT 175,
  weight SMALLINT,
  body_fat_rate SMALLINT,
  memo1 char(255),
  memo2 char(255),
  role_id tinyint NOT NULL default 0,/*0:user,1:coach,2:Fitness mechanism,3:businessman*/
  `latitude` float ,
  `longitude` float,
  location VARCHAR (128),
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp,
  review_passed BIT(1) NOT NULL DEFAULT 0,
  push_token varchar(256),
  card_rate float,
  exercise_rate float,
  PRIMARY KEY (`uid`),
  UNIQUE INDEX email_idx_verify(`email`),
  UNIQUE INDEX username_idx_verify(`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table if not exists account_avatar_verify (
  uid int(32) unsigned NOT NULL ,
  sina_uid bigint unsigned ,
  email varchar(32) ,
  weixin_uid varchar(32) ,
  username varchar(32) NOT NULL,
  avatar varchar(512) NOT NULL,
  PRIMARY KEY (uid),
  UNIQUE INDEX email_idx_avatar_verify(email),
  UNIQUE INDEX username_idx_avatar_verify(username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table if not exists account_deletion (
  uid int(32) unsigned NOT NULL ,
  sina_uid bigint unsigned ,
  email varchar(32) ,
  weixin_uid varchar(32) ,
  username varchar(32) NOT NULL,
  password varchar(128),
  PRIMARY KEY (uid),
  UNIQUE INDEX email_idx_deletion(email),
  UNIQUE INDEX username_idx_deletion(username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/* FIXME: following 2 tables must have same layout */
create table if not exists photo (
  photo_id bigint NOT NULL,
  filter_id SMALLINT DEFAULT 0,
  user_id BIGINT NOT NULL,
  description VARCHAR(280),
  tags varchar(5120) NOT NULL DEFAULT '',
  with_uids varchar(256) NOT NULL DEFAULT '',
  latitude float,
  longitude float,
  location_desc varchar(256),
  url VARCHAR(280),
  video_url VARCHAR(280),
  cards int ,
  created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamp,
  PRIMARY KEY (photo_id),
  UNIQUE INDEX note_user_id_index (user_id,photo_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table if not exists photo_verify (
  photo_id bigint NOT NULL,
  filter_id SMALLINT DEFAULT 0,
  user_id BIGINT NOT NULL,
  description VARCHAR(280),
  tags varchar(5120) NOT NULL DEFAULT '',
  with_uids varchar(256) NOT NULL DEFAULT '',
  latitude float,
  longitude float,
  location_desc varchar(256),
  url VARCHAR(280),
  video_url VARCHAR(280),
  cards int ,
  created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamp,
  PRIMARY KEY (photo_id),
  UNIQUE INDEX note_user_id_index_verify (user_id,photo_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table if not exists photo_report (
  photo_id bigint NOT NULL,
  filter_id SMALLINT DEFAULT 0,
  user_id BIGINT NOT NULL,
  description VARCHAR(280),
  url VARCHAR(280),
  video_url VARCHAR(280),
  cards int ,
  created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (photo_id),
  UNIQUE INDEX note_user_id_index_report (user_id,photo_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table if not exists photo_at_user (
  id bigint not null,
  photo_id bigint NOT NULL,
  user_id bigint NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB;

create table if not exists user_follow (
  id bigint not null,
  from_uid bigint not null,
  to_uid bigint not null,
  created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY(id),
  unique INDEX from_uid_idx (from_uid,to_uid),
  unique INDEX to_uid_idx (to_uid,from_uid)
) ENGINE=InnoDB;

create table if not exists photo_like (
  id bigint not null,
  user_id bigint not null,
  target_id bigint not null,
  created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY(id),
  INDEX uid_idx (user_id,target_id,id),
  INDEX photo_id_idx (target_id,id),
  UNIQUE KEY photo_uid_unique (user_id, target_id)
) ENGINE=InnoDB;

/* FIXME: following 2 tables must have same layout */
create table if not exists photo_comment (
  id bigint not null,
  user_id bigint not null,
  target_id bigint not null,
  content varchar(140) not null default '',
  reply_to_id bigint,
  created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY(id),
  INDEX comment_uid_idx (user_id),
  INDEX comment_photo_id_idx (target_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table if not exists activity (
  id bigint not null,
  user_id bigint not null,
  type tinyint not null,
  created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY(id),
  INDEX uid_acid_idx (user_id,id)
)ENGINE=InnoDB;

create table if not exists like_counter (
  id bigint not null,
  counter int not null,
  PRIMARY KEY(id),
  unique index like_counter_idx(counter,id)
)ENGINE=InnoDB;

create table if not exists following_counter (
  id bigint not null,
  counter int not null,
  PRIMARY KEY(id)
)ENGINE=InnoDB;

create table if not exists follower_counter (
  id bigint not null,
  counter int not null,
  PRIMARY KEY(id)
)ENGINE=InnoDB;

create table if not exists user_photo_counter (
  id bigint not null,
  counter int not null,
  PRIMARY KEY(id)
)ENGINE=InnoDB;

create table if not exists photo_comment_counter (
  id bigint not null,
  counter int not null,
  PRIMARY KEY(id)
)ENGINE=InnoDB;

create table if not exists photo_hot (
  id bigint unsigned not null,
  photo_id bigint not null,
  primary key(id),
  unique index hot_photo_id_uidx(photo_id)
) ENGINE=InnoDB;

create table if not exists notify (
  id bigint not null,
  user_id bigint not null,
  type tinyint not null,
  created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  comment_id bigint,
  PRIMARY KEY(id),
  INDEX tuid_acid_idx (user_id,id)
)ENGINE=InnoDB;

create table if not exists id_gen(
  id bigint unsigned not null  AUTO_INCREMENT,
   PRIMARY KEY(id)
);

create table if not exists last_photo (
  user_id bigint not null,
  photo_id bigint not null,
  created_time timestamp not null default current_timestamp,
  counter int not null default 0,
  PRIMARY KEY(user_id)
)ENGINE=InnoDB;

create table if not EXISTS hot_tag(
  id BIGINT UNSIGNED NOT NULL,
  tag VARCHAR(20) NOT NULL DEFAULT '',
  PRIMARY KEY(id),
  unique key(tag)
)ENGINE = InnoDB  DEFAULT CHARSET=utf8;

create table if not exists article (
  article_id bigint NOT NULL,
  user_id BIGINT NOT NULL default 0,
  url VARCHAR(280) NOT NULL default '',
  title VARCHAR(120) not null,
  photo_url VARCHAR(280),
  description VARCHAR(280) not null,
  created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (article_id),
  UNIQUE INDEX article_user_id_index (user_id,article_id)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

create table if not exists article_like_counter (
  id bigint not null,
  counter int not null,
  PRIMARY KEY(id)
)ENGINE=InnoDB;

create table if not exists article_comment_counter (
  id bigint not null,
  counter int not null,
  PRIMARY KEY(id)
)ENGINE=InnoDB;

create table if not exists article_like (
  id bigint not null,
  user_id bigint not null,
  target_id bigint not null,
  created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY(id),
  INDEX art_uid_idx (user_id,target_id,id),
  INDEX art_id_idx (target_id,id),
  UNIQUE KEY art_uid_unique (user_id, target_id)
) ENGINE=InnoDB;

create table if not exists article_comment (
  id bigint not null,
  user_id bigint not null,
  target_id bigint not null,
  content varchar(140) not null default '',
  reply_to_id bigint,
  created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY(id),
  INDEX art_comment_uid_idx (user_id),
  INDEX art_comment_photo_id_idx (target_id)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

create table if not exists dAuser_recommend (
  id bigint unsigned not null,
  user_id bigint not null,
  title varchar(128) not null default '',
  primary key(id)
) ENGINE=InnoDB;

create table if not exists user_coach (
  id bigint unsigned not null AUTO_INCREMENT,
  coach_id bigint not null,
  class_online_id int not null,
  class_offline_id int not null,
  class_online_count tinyint not null,
  class_offline_count tinyint not null,
  specialty varchar(128) not null default '',
  authen varchar(128) not null default '', /*认证信息*/
  auth_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  INDEX coach_idx (coach_id)
) ENGINE=InnoDB;

create table if not exists coach_recommend (
  id bigint unsigned not null,
  user_id bigint not null,
  primary key(id)
) ENGINE=InnoDB;

create table if not EXISTS tag_photo(
  tag VARCHAR(20) NOT NULL DEFAULT '',
  type tinyint not null default 0,
  photo_id bigint not null,
  PRIMARY key (tag,type,photo_id)
)ENGINE = InnoDB  DEFAULT CHARSET=utf8;

create table if not EXISTS tag_photo_hot(
  tag VARCHAR(20) NOT NULL DEFAULT '',
  type tinyint not null default 0,
  photo_id bigint not null,
  PRIMARY key (tag,type,photo_id)
)ENGINE = InnoDB  DEFAULT CHARSET=utf8;

create table if not EXISTS tag_count(
  tag VARCHAR(48) NOT NULL ,
  type tinyint not null default 0,
  counter bigint not null,
  primary key(tag,type),
  index counter_tag_idx(counter,type,tag)
)ENGINE = InnoDB  DEFAULT CHARSET=utf8;

CREATE TABLE if not EXISTS user_tag_count(
  user_id bigint not null,
  tag varchar(20) not null default '',
  counter bigint not null,
  PRIMARY KEY (user_id, tag)
)ENGINE = InnoDB  DEFAULT CHARSET=utf8;

create table if not exists notify_like_count (
  id bigint not null,
  counter int not null,
  PRIMARY KEY(id)
)ENGINE=InnoDB;

create table if not exists notify_comment_count (
  id bigint not null,
  counter int not null,
  PRIMARY KEY(id)
)ENGINE=InnoDB;

create table if not exists notify_follow_count (
  id bigint not null,
  counter int not null,
  PRIMARY KEY(id)
)ENGINE=InnoDB;

create table if not EXISTS clazz (
  id bigint not null,
  user_id bigint not null,
  photo_url VARCHAR (280) ,
  video_url VARCHAR (280) not null,
  class_level tinyint not null,--1 初级，2中级，3高级
  class_days tinyint not null,--不是计划的都是0
  class_appliance tinyint not null,--
  class_title VARCHAR (100) not null DEFAULT  '',
  class_introduction text,
  class_type tinyint not null, --1是计划，2是指导，3子课程
  created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  guide_ids VARCHAR (100) , --格式[2,3,4]用逗号作分隔
  lyric text,
  PRIMARY KEY (id),
  INDEX class_userid_idx (user_id)
) ENGINE=InnoDB;

create table if not EXISTS clazz_unpublished (
  id bigint not null,
  user_id bigint not null,
  photo_url VARCHAR (280) ,
  video_url VARCHAR (280) not null,
  class_level tinyint not null,
  class_days tinyint not null,
  class_appliance tinyint not null,
  class_title VARCHAR (100) not null DEFAULT  '',
  class_introduction text,
  class_type tinyint not null, --1是计划，2是指导
  created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  guide_ids VARCHAR (100) , --格式[2,3,4]用逗号作分隔
  lyric text ,
  PRIMARY KEY (id),
  INDEX class_unpublished_userid_idx (user_id)
) ENGINE=InnoDB;

create table if not EXISTS clazz_comment (
  id bigint not null,
  user_id bigint not null,
  target_id bigint not null,
  content varchar(140) not null default '',
  reply_to_id bigint,
  created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY(id),
  INDEX class_comment_uid_idx (user_id),
  INDEX comment_clazz_comment_id_idx (target_id)
) ENGINE=InnoDB;

create table if not exists clazz_comment_counter (
  id bigint not null,
  counter int not null,
  PRIMARY KEY(id)
)ENGINE=InnoDB;

create table if not EXISTS clazz_member_counter (
  id bigint not null,
  counter int not null,
  PRIMARY KEY(id)
) ENGINE=InnoDB;

create table if not EXISTS clazz_like_count (
  id bigint not null,
  counter int not null,
  PRIMARY KEY(id)
) ENGINE=InnoDB;

create table if not EXISTS user_clazz (
  user_id bigint not null,
  class_id bigint not null,
  class_type tinyint not null,
  progress tinyint not null,
  created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (user_id, class_id)
) ENGINE=InnoDB;

create table if not EXISTS clazz_sub (
  class_id bigint not null,
  subclass_id bigint not null,
  video_url VARCHAR (280) not null,
  actions tinyint not null,
  cost_time tinyint not null,
  PRIMARY KEY (class_id, subclass_id),
  INDEX class_sub_classid_idx (class_id)
) ENGINE=InnoDB;

create table if not EXISTS clazz_sub_unpublished (
  class_id bigint not null,
  subclass_id bigint not null,
  video_url VARCHAR (280) not null,
  actions tinyint not null,
  cost_time tinyint not null,
  PRIMARY KEY (class_id, subclass_id),
  INDEX class_sub_unpublished_classid_idx (class_id)
) ENGINE=InnoDB;

create table if not EXISTS clazz_recommend (
  id bigint not null,
  class_id bigint not null ,
  PRIMARY KEY (id)
) ENGINE=InnoDB;

create table if not EXISTS user_sub_clazz (
  user_id bigint not null,
  class_id bigint not null,
  sub_class_id bigint not null,
  created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (user_id, class_id,sub_class_id)
) ENGINE=InnoDB;

create table if not EXISTS  ads (
  id bigint not null,
  url VARCHAR (280) not null,
  photo_url VARCHAR (280) not null,
  title VARCHAR (32) NOT NULL ,
  created_at timestamp NOT NULL ,
  PRIMARY KEY (id)
) ENGINE=InnoDB;

create table if not EXISTS  ads_unpublished (
  id bigint not null AUTO_INCREMENT,
  url VARCHAR (280) not null,
  photo_url VARCHAR (280) not null,
  title VARCHAR (32) NOT NULL ,
  created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
) ENGINE=InnoDB;
