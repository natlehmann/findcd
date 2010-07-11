INSERT INTO Category(categoryId,categoryName) VALUES (10,'CategoriaPrueba');
INSERT INTO Category(categoryId,categoryName) VALUES (11,'OtraCategoriaPrueba');

INSERT INTO Volume(volumeId,volumeName,categoryId) VALUES(10000,'VolumePrueba',10);
INSERT INTO Volume(volumeId,volumeName,categoryId) VALUES(10001,'ZOtroVolumePrueba',11);

INSERT INTO Resource(resourceName,path,resourceType,fileSize,modifiedDate,comments,volumeId) VALUES ('ResourcePrueba','este/es/el/path','.type','1234Kb','2003-01-14 00:00:00.0','comentarios',10000);
INSERT INTO Resource(resourceName,path,resourceType,fileSize,modifiedDate,comments,volumeId) VALUES ('ResourcePruebaNoEntra','otro/path','.type','1234Kb','2003-01-14 00:00:00.0','comentarios',10000);
INSERT INTO Resource(resourceName,path,resourceType,fileSize,modifiedDate,comments,volumeId) VALUES ('ZOtroResource','zzz/otro/path','.zzz','9999Kb','2003-01-15 00:00:00.0','Zcomentarios',10001);

--DELETE FROM Resource WHERE volumeId IN (10000,10001);
--DELETE FROM Volume WHERE volumeId IN (10000,10001);
--DELETE FROM Category WHERE categoryId IN (10,11);