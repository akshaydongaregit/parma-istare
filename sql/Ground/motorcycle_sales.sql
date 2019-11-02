
create table motorcycle_model 
(
	id int ,
	name varchar(20) ,
	price int
);

create table country (
 id int ,
 name varchar(20) 
);

create table sales (
 model_id int , 
 country_id int ,
 quantity int ,
 sales_date date
);

insert into motorcycle_model values( 1 , 'MOT' , 50000 ) , ( 2 , 'YZM' , 70000 ) ;
insert into country values( 1 , 'Domnica' ) , ( 2 , 'Algeria' ) , ( 3 , 'Sao' ) ; 
insert into sales values ( 2 , 3, 4,'2018-01-29') , ( 2 , 1 , 2 ,'2018-01-19') , ( 2 , 1 , 2 ,'2017-12-18') , ( 1 , 2, 1,'2017-12-18'); 
insert into sales values ( 1 , 3 , 5 , '2017-12-10') , ( 1 ,2 , 5 , '2017-12-17' ) , ( 1 , 1 , 5 , '2018-01-10' ) , ( 2 , 3 ,4 , '2018-01-01' ) , ( 2 ,2 , 3 ,'2017-01-01' ) , ( 2 ,1 , 4 , '2017-12-14' );  
