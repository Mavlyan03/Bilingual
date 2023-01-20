drop table if exists users;
drop table if exists clients;
drop table if exists contents;
drop table if exists options;
drop table if exists questions;
drop table if exists results;
drop table if exists tests;
drop table if exists question_answers;
drop table if exists question_answers_options;
create sequence if not exists users_seq;
create sequence if not exists client_seq;
create sequence if not exists content_seq;
create sequence if not exists option_seq;
create sequence if not exists result_seq;
create sequence if not exists test_seq;
create sequence if not exists question_eq;
create sequence if not exists answer_seq;

create table users
(
    id       bigserial not null
        primary key,
    email    varchar(255),
    password varchar(255),
    role     varchar(255)
);

create table clients
(
    id         bigserial not null
        primary key,
    first_name varchar(255),
    last_name  varchar(255),
    user_id    bigserial
        constraint fktiuqdledq2lybrds2k3rfqrv4
            references users
);

create table contents
(
    id             bigserial not null
        primary key,
    content        varchar(255),
    content_format varchar(255)
);

create table options
(
    id          bigserial not null
        primary key,
    is_true     boolean,
    option      varchar(255),
    title       varchar(255),
    question_id bigserial
        constraint fk5bmv46so2y5igt9o9n9w4fh6y
            references questions
);

create table questions
(
    id                bigserial not null
        primary key,
    correct_answer    varchar(255),
    duration          integer,
    is_active         boolean,
    min_words         integer,
    number_of_replays integer,
    option_type       varchar(255),
    passage           varchar(1000),
    question_number   integer,
    question_type     varchar(255),
    statement         varchar(1000),
    title             varchar(255),
    content_id        bigserial
        constraint fk93r2rqdwq1ki0l45w8v4s2uoo
            references contents,
    test_id           bigserial
        constraint fkoc6xkgj16nhyyes4ath9dyxxw
            references tests
);

(
    id                 bigserial not null
    primary key,
    date_of_submission timestamp,
    score              real,
    status             varchar(255),
    client_id          bigserial
    constraint fk2gs18ufsnwjlm5qbjpknpm3e
    references clients,
    test_id            bigserial
    constraint fke9uvk96os1lxpp8pf93p13lmv
    references tests
);

create table tests
(
    id          bigserial not null
        primary key,
    description varchar(255),
    is_active   boolean,
    title       varchar(255)
);

create table question_answers
(
    id                 bigserial not null
        primary key,
    count_of_plays     integer,
    number_of_words    integer,
    score              real,
    seen               boolean,
    status             varchar(255),
    text_response_user varchar(255),
    content_id         bigserial
        constraint fk3p55kpyvbi53w4c4q75f8gupo
            references contents,
    question_id        bigserial
        constraint fkrms3u35c10orgjqyw03ajd7x7
            references questions,
    result_id          bigserial
        constraint fk9hxsb9u0vmaxe9kmbrngf0xv4
            references results
);

create table question_answers_options
(
    question_answer_id bigserial not null
        constraint fk2lq849dkvyh777or840b9s8yf
            references question_answers,
    options_id         bigserial not null
        constraint fkay62gg09ornlw8gr0rk9vx2fb
            references options,
    primary key (question_answer_id, options_id)
);