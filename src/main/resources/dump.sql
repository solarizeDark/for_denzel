create table if not exists users
(
    id serial
    constraint users_pkey
    primary key,
    first_name varchar(50),
    last_name varchar(50),
    login varchar(50),
    password_hash varchar(255)
    );

alter table users owner to postgres;

create table if not exists auth
(
    id serial
    constraint auth_pkey
    primary key,
    user_id bigint
    constraint auth_user_id_fkey
    references users,
    cookie_value varchar(255)
);

alter table auth owner to postgres;

create table audios (

    id serial,
    constraint pk_audios primary key (id),

    name varchar
);

create table favorites (

    audio_id integer not null,
        constraint favorites_audio_id_fk foreign key(audio_id) references audios(id),

    user_id integer not null,
        constraint favorites_user_id_fk foreign key(user_id) references users(id)

);
