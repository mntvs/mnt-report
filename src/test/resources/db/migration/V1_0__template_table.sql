create table report_template
(
    id   bigserial not null
        constraint report_template_pk
            primary key,
    tag  text      not null,
    data text      not null
);

create unique index report_template_tag_uindex
    on report_template (tag);
