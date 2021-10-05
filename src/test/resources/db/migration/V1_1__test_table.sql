create table test_table
(
    id        bigserial not null
        constraint test_table_pk
            primary key,
    text_data text      not null,
    num_data  bigint    not null
);

insert into test_table (text_data, num_data) values ('test1', 1);
insert into test_table (text_data, num_data) values ('test2', 2);
insert into test_table (text_data, num_data) values ('test3', 3);
