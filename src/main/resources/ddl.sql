-- DDL
create table event
(
    id        bigint not null auto_increment,
    uuid      binary(16) not null,
    action    varchar(255),
    type      varchar(255),
    review_id bigint not null,
    primary key (id)
);

create table place
(
    id   bigint not null auto_increment,
    uuid binary(16) not null,
    primary key (id)
);

create table review
(
    id            bigint not null auto_increment,
    uuid          binary(16) not null,
    content       varchar(255),
    place_id      bigint not null,
    user_id       bigint not null,
    content_point int    not null,
    photo_point   int    not null,
    bonus_point   int    not null,
    primary key (id)
);

create table review_image
(
    id        bigint not null auto_increment,
    uuid      binary(16) not null,
    review_id bigint not null,
    primary key (id)
);

create table users
(
    id    bigint not null auto_increment,
    uuid  binary(16) not null,
    point int    not null,
    primary key (id)
);

create table point_history
(
    id        bigint not null auto_increment,
    user_id   bigint not null,
    review_id bigint not null,
    type      varchar(255),
    point     int    not null,
    primary key (id)
);

alter table event
    add constraint fk_event_to_review
        foreign key (review_id)
            references review (id);

alter table review
    add constraint fk_review_to_place
        foreign key (place_id)
            references place (id);

alter table review
    add constraint fk_review_to_users
        foreign key (user_id)
            references users (id);

alter table review_image
    add constraint fk_review_image_to_review
        foreign key (review_id)
            references review (id);

alter table point_history
    add constraint fk_point_history_to_user
        foreign key (user_id)
            references users (id);

alter table point_history
    add constraint fk_point_history_to_review
        foreign key (review_id)
            references review (id);

-- INDEX
create index `uuid` on users (uuid);
create index `uuid` on place (uuid);
create index `uuid` on review (uuid);
