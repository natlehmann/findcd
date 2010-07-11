USE cdcatalogue;

--drop table Resource;
--drop table Volume;
--drop table Category;

--delete from Resource;
--delete from Volume;
--delete from Category;


CREATE TABLE Category (
    categoryId int(11) PRIMARY KEY auto_increment NOT NULL,
    categoryName varchar(50) NOT NULL UNIQUE
) type=InnoDb;


CREATE TABLE Volume (
    volumeId int(11) PRIMARY KEY auto_increment NOT NULL,
    categoryId int(11) NULL,
    volumeName varchar(100) NOT NULL UNIQUE,
    CONSTRAINT fk_categoryId FOREIGN KEY (categoryId) REFERENCES Category(categoryId) ON DELETE NO ACTION ON UPDATE NO ACTION
) type=InnoDb;


CREATE TABLE Resource (
    resourceId int(11) PRIMARY KEY auto_increment NOT NULL,
    volumeId int(11) NOT NULL,
    resourceName varchar(512) NOT NULL,
    path varchar(2048) NOT NULL,
    resourceType varchar(20) NULL DEFAULT NULL,
    fileSize varchar(50) NULL DEFAULT NULL,
    modifiedDate timestamp NULL DEFAULT NULL,
    comments varchar(512) NULL DEFAULT NULL,
    CONSTRAINT fk_volumeId FOREIGN KEY (volumeId) REFERENCES Volume(volumeId) ON DELETE CASCADE ON UPDATE CASCADE
) type=InnoDb;

