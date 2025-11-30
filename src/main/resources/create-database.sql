create table user_entity (
    id uuid primary key default gen_random_uuid(),
    nickname varchar(100),
    password varchar(100),
    email varchar(100),
    balance double precision,
);

create table expense_category (
    id uuid primary key default gen_random_uuid(),
    name varchar(50),
    total_amount float default 0,
    user_id uuid,
    icon varchar(100),
    created_at timestamp default current_timestamp

    constraint fk_user_id_expense_category foreign key (user_id)
        references user_entity(id) on delete cascade
);

create table income_category (
    id uuid primary key default gen_random_uuid(),
    name varchar(50),
    total_amount float default 0,
    user_id uuid,
    icon varchar(100),
    created_at timestamp default current_timestamp

    constraint fk_user_id_income_category foreign key (user_id)
        references user_entity(id) on delete cascade
);

create table transaction (
    id uuid primary key default gen_random_uuid(),
    user_id uuid not null references user_entity(id),
    distributed_amount float default 0,

    expense_category_id uuid references expense_category(id),
    income_category_id uuid references income_category(id),

    title TEXT,
    type varchar(10) check (type in ('INCOME', 'EXPENSE')),
    date timestamp default current_timestamp,
    sum float not null,
    distributed_amount float,

    constraint chk_category_integrity check (
        (type = 'EXPENSE' and expense_category_id is not null and income_category_id is null) or
        (type = 'INCOME' and income_category_id is not null and expense_category_id is null))
);

create table save_goal (
    id uuid primary key default gen_random_uuid(),
    name varchar(50),
    title varchar(500),
    total_amount float,
    current_amount float default 0,
    created_at timestamp default current_timestamp,
    user_id uuid references user_entity(id)
);

create table transaction_saving_goal (
    id uuid primary key default gen_random_uuid(),
    transaction_id uuid not null,
    save_goal_id uuid not null,
    amount float not null,
    created_at timestamp default current_timestamp,

    constraint fk_transaction_id_transaction_saving_goal foreign key (transaction_id)
        references transaction(id) on delete cascade,

    constraint fk_save_goal_id_transaction_saving_goal foreign key (save_goal_id)
        references save_goal(id) on delete cascade
);
