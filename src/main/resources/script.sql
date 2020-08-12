insert into field (active, label, required, type)
values (true, 'Full name', true, 'SINGLE_LINE_TEXT'),
       (true, 'Phone', true, 'SINGLE_LINE_TEXT'),
       (true, 'Date of birth', true, 'DATE'),
       (true, 'About me', true, 'MULTILINE_TEXT'),
       (true, 'Education', true, 'CHECKBOX'),
       (true, 'Sex', true, 'RADIO_BUTTON');

insert into users (email, password)
values ('user', '123'),
       ('u', '321');

insert into fieldoption (option, field_id)
values ('student', 5),
       ('bachelor', 5),
       ('master', 5),
       ('PhD', 5),
       ('no education', 5),
       ('male', 6),
       ('female', 6);