CREATE SEQUENCE if not exists hibernate_sequence START 1;

create table if not exists orders
(
    id                  bigint            primary key,
    version             bigint            ,
    order_state         varchar(100)      ,
    consumer_id         bigint            ,
    restaurant_id       bigint            ,
    delivery_time       date              ,
    delivery_address    varchar(500)      ,
    payment_token       varchar(100)
);


create table if not exists order_line_items
(
    order_id            bigint          ,
    menu_item_id        bigint          ,
    name                varchar(100)    ,
    price               decimal         ,
    quantity            int
);

create table if not exists order_service_restaurants
(
    id                  bigint          ,
    name                varchar(100)
);

create table if not exists order_service_restaurant_menu_items
(
    restaurant_id       bigint          ,
    menu_item_id        bigint          ,
    name                varchar(100)    ,
    price               decimal
);



CREATE TABLE if not exists saga_instance_participants (
                                                          saga_type VARCHAR(255) NOT NULL,
                                                          saga_id VARCHAR(100) NOT NULL,
                                                          destination VARCHAR(100) NOT NULL,
                                                          resource VARCHAR(100) NOT NULL,
                                                          PRIMARY KEY(saga_type, saga_id, destination, resource)
);

CREATE TABLE if not exists saga_instance(
                                            saga_type VARCHAR(255) NOT NULL,
                                            saga_id VARCHAR(100) NOT NULL,
                                            state_name VARCHAR(100) NOT NULL,
                                            last_request_id VARCHAR(100),
                                            end_state BOOLEAN,
                                            compensating BOOLEAN,
                                            saga_data_type VARCHAR(1000) NOT NULL,
                                            saga_data_json VARCHAR(1000) NOT NULL,
                                            PRIMARY KEY(saga_type, saga_id)
);

create table if not exists saga_lock_table(
                                              target VARCHAR(100) PRIMARY KEY,
                                              saga_type VARCHAR(255) NOT NULL,
                                              saga_Id VARCHAR(100) NOT NULL
);

create table if not exists saga_stash_table(
                                               message_id VARCHAR(100) PRIMARY KEY,
                                               target VARCHAR(100) NOT NULL,
                                               saga_type VARCHAR(255) NOT NULL,
                                               saga_id VARCHAR(100) NOT NULL,
                                               message_headers VARCHAR(1000) NOT NULL,
                                               message_payload VARCHAR(1000) NOT NULL
);


CREATE SEQUENCE if not exists message_table_id_sequence START 1;

select setval('message_table_id_sequence', (ROUND(EXTRACT(EPOCH FROM CURRENT_TIMESTAMP) * 1000))::BIGINT);

CREATE TABLE if not exists new_message (
                                           dbid BIGINT NOT NULL DEFAULT nextval('message_table_id_sequence') PRIMARY KEY,
                                           id VARCHAR(1000),
                                           destination TEXT NOT NULL,
                                           headers TEXT NOT NULL,
                                           payload TEXT NOT NULL,
                                           published SMALLINT DEFAULT 0,
                                           creation_time BIGINT
);

ALTER SEQUENCE if exists message_table_id_sequence OWNED BY new_message.dbid;

ALTER TABLE if exists new_message RENAME TO message;

CREATE SEQUENCE if not exists events_table_id_sequence START 1;

select setval('events_table_id_sequence', (ROUND(EXTRACT(EPOCH FROM CURRENT_TIMESTAMP) * 1000))::BIGINT);

CREATE TABLE if not exists new_events (
                                          id BIGINT NOT NULL DEFAULT nextval('events_table_id_sequence') PRIMARY KEY,
                                          event_id VARCHAR(1000),
                                          event_type VARCHAR(1000),
                                          event_data VARCHAR(1000) NOT NULL,
                                          entity_type VARCHAR(1000) NOT NULL,
                                          entity_id VARCHAR(1000) NOT NULL,
                                          triggering_event VARCHAR(1000),
                                          metadata VARCHAR(1000),
                                          published SMALLINT DEFAULT 0
);

ALTER SEQUENCE if exists events_table_id_sequence OWNED BY new_events.id;

DROP TABLE if exists events;

ALTER TABLE if exists new_events RENAME TO events;

CREATE INDEX if not exists events_idx ON events(entity_type, entity_id, id);
CREATE INDEX if not exists events_published_idx ON events(published, id);