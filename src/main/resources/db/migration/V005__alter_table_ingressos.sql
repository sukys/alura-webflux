ALTER TABLE ingressos ADD tipo varchar(30) not null;
ALTER TABLE ingressos ADD valor decimal(10, 2) not null;
ALTER TABLE ingressos ADD total int not null;