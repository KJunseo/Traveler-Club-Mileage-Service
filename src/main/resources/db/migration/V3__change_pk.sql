-- remove constraint
alter table `event` drop foreign key `fk_event_to_review`;
alter table `review` drop foreign key `fk_review_to_place`;
alter table `review` drop foreign key `fk_review_to_users`;
alter table `review_image` drop foreign key `fk_review_image_to_review`;
alter table `point_history` drop foreign key `fk_point_history_to_user`;
alter table `point_history` drop foreign key `fk_point_history_to_review`;

-- add Long id pk
alter table `event` change column `id` `uuid` BINARY(16) not null;
alter table `event` drop primary key;
alter table `event` add column `id` BIGINT not null;
alter table `event` add primary key(`id`);
alter table `event` modify `review_id` BIGINT not null;
alter table `event` modify `id` BIGINT not null auto_increment;

alter table `place` change column `id` `uuid` BINARY(16) not null;
alter table `place` drop primary key;
alter table `place` add column `id` BIGINT not null;
alter table `place` add primary key(`id`);
alter table `place` modify `id` BIGINT not null auto_increment;

alter table `review` change column `id` `uuid` BINARY(16) not null;
alter table `review` drop primary key;
alter table `review` add column `id` BIGINT not null;
alter table `review` add primary key(`id`);
alter table `review` modify `place_id` BIGINT not null;
alter table `review` modify `user_id` BIGINT not null;
alter table `review` modify `id` BIGINT not null auto_increment;

alter table `review_image` change column `id` `uuid` BINARY(16) not null;
alter table `review_image` drop primary key;
alter table `review_image` add column `id` BIGINT not null;
alter table `review_image` add primary key(`id`);
alter table `review_image` modify `review_id` BIGINT not null;
alter table `review_image` modify `id` BIGINT not null auto_increment;

alter table `users` change column `id` `uuid` BINARY(16) not null;
alter table `users` drop primary key;
alter table `users` add column `id` BIGINT not null;
alter table `users` add primary key(`id`);
alter table `users` modify `id` BIGINT not null auto_increment;

alter table `point_history` change column `id` `uuid` BINARY(16) not null;
alter table `point_history` drop primary key;
alter table `point_history` add column `id` BIGINT not null;
alter table `point_history` add primary key(`id`);
alter table `point_history` drop `uuid`;
alter table `point_history` modify `user_id` BIGINT not null;
alter table `point_history` modify `review_id` BIGINT not null;
alter table `point_history` modify `id` BIGINT not null auto_increment;

-- new constraint
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
