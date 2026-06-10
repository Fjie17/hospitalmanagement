create table admin
(
    id       bigint auto_increment
        primary key,
    username varchar(255) not null,
    password varchar(255) not null,
    name     varchar(255) null,
    phone    varchar(255) null,
    constraint username
        unique (username)
);

create table contact_messages
(
    id           bigint auto_increment
        primary key,
    name         varchar(255)                        null,
    email        varchar(255)                        null,
    message      text                                not null,
    submitted_at timestamp default CURRENT_TIMESTAMP null
);

create table department
(
    id          bigint auto_increment
        primary key,
    name        varchar(255) null,
    description varchar(255) null
);

create table doctor
(
    id            bigint auto_increment
        primary key,
    username      varchar(255) null,
    password      varchar(255) not null,
    name          varchar(255) null,
    gender        varchar(255) null,
    phone         varchar(255) null,
    department_id bigint       null,
    title         varchar(255) null,
    constraint username
        unique (username),
    constraint doctor_ibfk_1
        foreign key (department_id) references department (id)
);

create index department_id
    on doctor (department_id);

create table health_tip
(
    id          bigint auto_increment comment '主键ID'
        primary key,
    title       varchar(255)                       not null comment '标题',
    summary     text                               null comment '简要内容或描述',
    image_url   varchar(255)                       null comment '封面图片 URL',
    video_url   varchar(255)                       null comment '视频链接（可选）',
    category    varchar(255)                       null,
    create_time datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间'
);

create table notice
(
    id           bigint auto_increment
        primary key,
    title        varchar(200)                       not null,
    content      tinytext                           null,
    created_time datetime default CURRENT_TIMESTAMP null,
    created_by   bigint                             null
);

create table patient
(
    id         bigint auto_increment
        primary key,
    username   varchar(255) null,
    password   varchar(255) not null,
    name       varchar(255) null,
    gender     varchar(255) null,
    phone      varchar(255) null,
    birth_date date         null,
    constraint username
        unique (username)
);

create table appointment
(
    id               bigint auto_increment
        primary key,
    patient_id       bigint       not null,
    doctor_id        bigint       not null,
    appointment_time datetime     not null,
    status           varchar(255) null,
    note             varchar(255) null,
    department_id    bigint       null,
    constraint FKncums1tsqu7qx1qw5o2g4nj78
        foreign key (department_id) references department (id),
    constraint appointment_ibfk_1
        foreign key (patient_id) references patient (id),
    constraint appointment_ibfk_2
        foreign key (doctor_id) references doctor (id)
);

create index doctor_id
    on appointment (doctor_id);

create index patient_id
    on appointment (patient_id);

create table doctor_review
(
    id          bigint auto_increment
        primary key,
    patient_id  bigint                             not null,
    doctor_id   bigint                             not null,
    rating      int                                not null,
    comment     text                               null,
    review_time datetime default CURRENT_TIMESTAMP null,
    constraint doctor_review_ibfk_1
        foreign key (patient_id) references patient (id),
    constraint doctor_review_ibfk_2
        foreign key (doctor_id) references doctor (id)
);

create index doctor_id
    on doctor_review (doctor_id);

create index patient_id
    on doctor_review (patient_id);

create table medical_record
(
    id          bigint auto_increment
        primary key,
    patient_id  bigint                             not null,
    doctor_id   bigint                             not null,
    diagnosis   varchar(255)                       null,
    treatment   varchar(255)                       null,
    record_date datetime default CURRENT_TIMESTAMP null,
    constraint medical_record_ibfk_1
        foreign key (patient_id) references patient (id),
    constraint medical_record_ibfk_2
        foreign key (doctor_id) references doctor (id)
);

create index doctor_id
    on medical_record (doctor_id);

create index patient_id
    on medical_record (patient_id);

create table schedule
(
    id               bigint auto_increment
        primary key,
    doctor_id        bigint                  not null,
    work_date        date                    not null,
    time_slot        enum ('上午', '下午', '晚上') not null,
    max_patients     int default 10          null,
    current_patients int default 0           null,
    constraint schedule_ibfk_1
        foreign key (doctor_id) references doctor (id)
);

create index doctor_id
    on schedule (doctor_id);


