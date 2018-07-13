insert into country (country_Name)
values('INDIA');

insert into state (state_name, country_id)
values('Karnataka', 1);

insert into city (city_ame, state_id)
values('Bangalore', 2);

INSERT INTO apartment (apartment_name, Address1, Address2, HasBlocks, state_id, city_id, country_id, pin_code)
VALUES ('SLS Signature', '74/1,2 & 3, Vidya Vikas School Road, Kaverappa Layout', 'Panathur, New Kaverappa Layout', true, 2,1,4,560103)

INSERT INTO roles (role_name)
VALUES ('Super Admin');

INSERT INTO roles (role_name)
VALUES ('Admin');

INSERT INTO roles (role_name)
VALUES ('Owner');

INSERT INTO roles (role_name)
VALUES ('Member');

INSERT INTO roles (role_name)
VALUES ('Vendor');

INSERT INTO roles (role_name)
VALUES ('Security');

INSERT INTO features (featurename, description)
VALUES ('Add Notice', 'Can add notice');

INSERT INTO features (featurename, description)
VALUES ('Add Vehicle', 'Can add Vehicle');

INSERT INTO features (featurename, description)
VALUES ('View Vehicle', 'Can search vehicle');

INSERT INTO features (featurename, description)
VALUES ('Assign role', 'Can assign roles');




INSERT InTo users (Block, emailId, flat_number, name, password, phone_number, user_id, created_at, updated_at, apartment, role)
VALUES ('B', 'vivit1006117@gmail.com', 'c234', 'vivek kumar', 'nkKwsDTsAGUx/RRE45SaxQ==', 345, '2018-07-08 18:59:58', '2018-07-08 18:59:58', 1, 1)
