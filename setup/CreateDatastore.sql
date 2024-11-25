create database if not exists onlinebookstore;

use onlinebookstore;

create table if not exists books(
    barcode varchar(100) primary key,
    name varchar(100),
    author varchar(100),
    price int,
    quantity int
                                );

insert into books values('9780134190563','The Go Programming Language','Alan A. A. Donovan and Brian W. Kernighan',400,8);
insert into books values('9780133053036','C++ Primer','Stanley Lippman and Josée Lajoie and Barbara Moo',976,13);
insert into books values('9781718500457','The Rust Programming Language','Steve Klabnik and Carol Nichols',560,12);
insert into books values('9781491910740','Head First Java','Kathy Sierra and Bert Bates and Trisha Gee',754,23);
insert into books values('9781492056300','Fluent Python','Luciano Ramalho',1014,5);
insert into books values('9781720043997','The Road to Learn React','Robin Wieruch',239,18);
insert into books values('9780132350884','Clean Code: A Handbook of Agile Software Craftsmanship','Robert C Martin',288,3);
insert into books values('9780132181273','Domain-Driven Design','Eric Evans',560,28);
insert into books values('9781951204006','A Programmers Guide to Computer Science','William Springer',188,4);
insert into books values('9780316204552','The Soul of a New Machine','Tracy Kidder',293,30);
insert into books values('9780132778046','Effective Java','Joshua Bloch',368,21);
insert into books values('9781484255995','Practical Rust Projects','Shing Lyu',257,15);


create table if not exists users(userID int primary key,
                                 email varchar(100),
                                 passwordUser varchar(100),
                                 firstname varchar(100),
                                 lastname varchar(100),
                                 phone varchar(100),
                                 address text,
                                 role varchar(10)
                                );

insert into users values(1,'qq','q','qq','qq','+3752912345','Lenina, 1','3');

create table if not exists orders(orderID int,
                                  customer int,
                                  barcode varchar(100),
                                  price double,
                                  quantity int,
                                  total double,
                                  manager int,
                                  status varchar(10)
                                 );

insert into orders values(1,123,'123',565,1,565, 1,'NEW');

CREATE TABLE IF NOT EXISTS posts (
                                     postID INT PRIMARY KEY,
                                     author INT,
                                     time BIGINT,
                                     header VARCHAR(255),
                                     body TEXT
);

CREATE TABLE IF NOT EXISTS comments (
                                        commentID INT,
                                        book VARCHAR(255),
                                        author INT,
                                        header VARCHAR(255),
                                        body TEXT,
                                        secretBody TEXT
);

commit;