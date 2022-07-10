create table event
(
    id        BINARY(16) not null,
    action    varchar(255),
    type      varchar(255),
    review_id BINARY(16),
    primary key (id)
);

create table place
(
    id BINARY(16) not null,
    primary key (id)
);

create table review
(
    id       BINARY(16) not null,
    content  varchar(255),
    place_id BINARY(16),
    user_id  BINARY(16),
    primary key (id)
);

create table review_image
(
    id        BINARY(16) not null,
    review_id BINARY(16),
    primary key (id)
);

create table users
(
    id    BINARY(16) not null,
    point integer not null,
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
