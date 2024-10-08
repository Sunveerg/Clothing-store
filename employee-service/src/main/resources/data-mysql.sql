 insert into departments(department_id, name, head_count) values('1048b354-c18f-4109-8282-2a85485bfa5a', 'sales and marketing', 2);
 insert into departments(department_id, name, head_count) values('cb346554-8526-4569-849d-6abf41bb7f76', 'customer relations', 3);
 insert into departments(department_id, name, head_count) values('d52bc94a-6312-4e83-a52d-432bf1fb38df', 'finance', 5);
 insert into departments(department_id, name, head_count) values('a1fdbe34-578a-409b-9235-3e41bfa25874', 'IT', 8);
 insert into departments(department_id, name, head_count) values('e2fc1423-37b8-47af-b0e3-3f6b56e39b6c', 'operations', 4);
 insert into departments(department_id, name, head_count) values('6783f2d9-cd9b-4cc2-ae3f-e3c2b43c45fe', 'human resources', 6);
 insert into departments(department_id, name, head_count) values('a452478e-b476-43f8-8bc6-94b9a90b4413', 'research and development', 10);
 insert into departments(department_id, name, head_count) values('f8f8e0a2-33c2-4c7c-8fb7-5f3d1bc4f8e2', 'marketing', 7);
 insert into departments(department_id, name, head_count) values('c8c103c0-71de-4d21-bd6a-8242d2e26a78', 'legal', 3);
 insert into departments(department_id, name, head_count) values('f1d5846b-ea42-4ec4-a414-cb132c193154', 'production', 5);

 insert into department_positions(department_id, title, code) values(1, 'manager', 'samm1');
 insert into department_positions(department_id, title, code) values(2, 'associate', 'sama1');
 insert into department_positions(department_id, title, code) values(3, 'supervisor', 'crs1');
 insert into department_positions(department_id, title, code) values(4, 'representative', 'crr1');
 insert into department_positions(department_id, title, code) values(5, 'finance manager', 'fm1');
 insert into department_positions(department_id, title, code) values(6, 'accountant', 'fa1');
 insert into department_positions(department_id, title, code) values(7, 'IT manager', 'itm1');
 insert into department_positions(department_id, title, code) values(8, 'software developer', 'itsd1');
 insert into department_positions(department_id, title, code) values(9, 'operations manager', 'om1');
 insert into department_positions(department_id, title, code) values(10, 'operations specialist', 'os1');

 insert into employees(employee_id, first_name, last_name, email_address, salary, currency, commission_rate, street_address, city, province, country, postal_code, department_id, position_title)
 values('e5913a79-9b1e-4516-9ffd-06578e7af261', 'Vilma', 'Chawner', 'vchawner0@phoca.cz', 34000.00, 'CAD', 0.0, '8452 Anhalt Park', 'Chambly', 'Québec', 'Canada', 'J3L 5Y6', '1048b354-c18f-4109-8282-2a85485bfa5a', 'associate');
 insert into employees(employee_id, first_name, last_name, email_address, salary, currency, commission_rate, street_address, city, province, country, postal_code, department_id, position_title)
 values('3aa1cac1-2538-4a51-89da-92e1809c0653', 'Winifred', 'Roy', 'wroy@phoca.cz', 75000.00, 'CAD', 0.0, '9343 Main Street', 'Montreal', 'Québec', 'Canada', 'H1V 5Y6', '1048b354-c18f-4109-8282-2a85485bfa5a', 'manager');
 insert into employees(employee_id, first_name, last_name, email_address, salary, currency, commission_rate, street_address, city, province, country, postal_code, department_id, position_title)
 values('a1aaabf3-50a3-47c3-9c3e-9658bbadcd02', 'Alonso', 'Galletley', 'agalletley0@merriam-webster.com', 67000.00, 'CAD', 0.02, '2 Northview Junction', 'Vancouver', 'British Columbia', 'Canada', 'V6T 1Z5', 'd52bc94a-6312-4e83-a52d-432bf1fb38df', 'accountant');
 insert into employees(employee_id, first_name, last_name, email_address, salary, currency, commission_rate, street_address, city, province, country, postal_code, department_id, position_title)
 values('d34e8e99-7337-43d3-b2c5-c31a0e76c1d9', 'Ginnifer', 'Willock', 'gwillock1@boston.com', 62000.00, 'CAD', 0.01, '0842 Linden Hill', 'Calgary', 'Alberta', 'Canada', 'T2V 4H2', 'a1fdbe34-578a-409b-9235-3e41bfa25874', 'software developer');
 insert into employees(employee_id, first_name, last_name, email_address, salary, currency, commission_rate, street_address, city, province, country, postal_code, department_id, position_title)
 values('ca8c110a-628e-4e23-a56a-0e52f0e3e25c', 'Rey', 'Ouldcott', 'rouldcott2@aboutads.info', 48000.00, 'CAD', 0.0, '2 Starling Point', 'Toronto', 'Ontario', 'Canada', 'M4T 1Z8', 'e2fc1423-37b8-47af-b0e3-3f6b56e39b6c', 'operations specialist');
 insert into employees(employee_id, first_name, last_name, email_address, salary, currency, commission_rate, street_address, city, province, country, postal_code, department_id, position_title)
 values('ef3aaf35-55db-45fb-b15c-af34de13ed66', 'Elnora', 'Delany', 'edelany3@chron.com', 58000.00, 'CAD', 0.01, '8794 Schmedeman Court', 'Mississauga', 'Ontario', 'Canada', 'L5A 3Z3', 'e2fc1423-37b8-47af-b0e3-3f6b56e39b6c', 'operations manager');
 insert into employees(employee_id, first_name, last_name, email_address, salary, currency, commission_rate, street_address, city, province, country, postal_code, department_id, position_title)
 values('d0f1494a-6de8-41c0-b240-299f4f8f5f93', 'Cristina', 'McKernan', 'cmckernan4@comsenz.com', 43000.00, 'CAD', 0.0, '23 Hayes Court', 'Winnipeg', 'Manitoba', 'Canada', 'R3C 3N1', '6783f2d9-cd9b-4cc2-ae3f-e3c2b43c45fe', 'human resources specialist');
 insert into employees(employee_id, first_name, last_name, email_address, salary, currency, commission_rate, street_address, city, province, country, postal_code, department_id, position_title)
 values('c441f2f9-4af4-47f4-8821-32cd1776a462', 'Nichole', 'Girard', 'ngirard5@whitehouse.gov', 54000.00, 'CAD', 0.02, '39025 Starling Circle', 'Ottawa', 'Ontario', 'Canada', 'K1P 1J1', '6783f2d9-cd9b-4cc2-ae3f-e3c2b43c45fe', 'human resources manager');
 insert into employees(employee_id, first_name, last_name, email_address, salary, currency, commission_rate, street_address, city, province, country, postal_code, department_id, position_title)
 values('b725ae22-929c-4e80-93a2-cf11d1e0dd3e', 'Lucius', 'Padrick', 'lpadrick6@nymag.com', 50000.00, 'CAD', 0.0, '89956 Hollow Ridge Hill', 'Hamilton', 'Ontario', 'Canada', 'L9C 0G9', 'a452478e-b476-43f8-8bc6-94b9a90b4413', 'researcher');
 insert into employees(employee_id, first_name, last_name, email_address, salary, currency, commission_rate, street_address, city, province, country, postal_code, department_id, position_title)
 values('2e675943-6211-4d1c-8de0-23f129eb7b27', 'Krissie', 'Hyslop', 'khyslop7@fotki.com', 67000.00, 'CAD', 0.01, '1 Schurz Plaza', 'London', 'Ontario', 'Canada', 'N5X 3T3', 'a452478e-b476-43f8-8bc6-94b9a90b4413', 'research and development manager');

 insert into employee_phonenumbers(employee_id, type, number) values(1, 'WORK', '515-555-5555');
 insert into employee_phonenumbers(employee_id, type, number) values(1, 'MOBILE', '514-555-4444');
 insert into employee_phonenumbers(employee_id, type, number) values(2, 'WORK', '515-555-5555');
 insert into employee_phonenumbers(employee_id, type, number) values(2, 'MOBILE', '514-555-1234');
 insert into employee_phonenumbers(employee_id, type, number) values(3, 'WORK', '416-555-1111');
 insert into employee_phonenumbers(employee_id, type, number) values(3, 'MOBILE', '416-555-2222');
 insert into employee_phonenumbers(employee_id, type, number) values(4, 'WORK', '403-555-1234');
 insert into employee_phonenumbers(employee_id, type, number) values(4, 'MOBILE', '403-555-5678');
 insert into employee_phonenumbers(employee_id, type, number) values(5, 'WORK', '450-555-9876');
 insert into employee_phonenumbers(employee_id, type, number) values(5, 'MOBILE', '450-555-5432');

