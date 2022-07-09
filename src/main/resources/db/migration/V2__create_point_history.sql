create table point_history
(
    id        BINARY(16) not null,
    user_id    BINARY(16),
    review_id    BINARY(16),
    type      varchar(255),
    point integer not null,
    primary key (id)
);

alter table point_history
    add constraint fk_point_history_to_user
        foreign key (user_id)
            references users (id);

alter table point_history
    add constraint fk_point_history_to_review
        foreign key (review_id)
            references review (id);
