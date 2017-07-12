-- MySQL dump 10.13  Distrib 5.6.24, for Win64 (x86_64)
--
-- Host: localhost    Database: brigaderia
-- ------------------------------------------------------
-- Server version	5.6.26-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `bairro`
--

DROP TABLE IF EXISTS `bairro`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bairro` (
  `codigo` int(4) NOT NULL,
  `nome` varchar(45) NOT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bairro`
--

LOCK TABLES `bairro` WRITE;
/*!40000 ALTER TABLE `bairro` DISABLE KEYS */;
INSERT INTO `bairro` VALUES (1,'Vila Nova'),(2,'Glória');
/*!40000 ALTER TABLE `bairro` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cidade`
--

DROP TABLE IF EXISTS `cidade`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cidade` (
  `codigo` int(4) NOT NULL,
  `nome` varchar(45) NOT NULL,
  `estado` int(2) NOT NULL,
  PRIMARY KEY (`codigo`),
  KEY `fk_cidade_estado_idx` (`estado`),
  CONSTRAINT `fk_cidade_estado` FOREIGN KEY (`estado`) REFERENCES `estado` (`codigo`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cidade`
--

LOCK TABLES `cidade` WRITE;
/*!40000 ALTER TABLE `cidade` DISABLE KEYS */;
INSERT INTO `cidade` VALUES (4413,'Abdon Batista',24),(4414,'Abelardo Luz',24),(4415,'Agrolândia',24),(4416,'Agronômica',24),(4417,'Água Doce',24),(4418,'Águas de Chapecó',24),(4419,'Águas Frias',24),(4420,'Águas Mornas',24),(4421,'Alfredo Wagner',24),(4422,'Alto Bela Vista',24),(4423,'Anchieta',24),(4424,'Angelina',24),(4425,'Anita Garibaldi',24),(4426,'Anitápolis',24),(4427,'Antônio Carlos',24),(4428,'Apiúna',24),(4429,'Arabutã',24),(4430,'Araquari',24),(4431,'Araranguá',24),(4432,'Armazém',24),(4433,'Arroio Trinta',24),(4434,'Arvoredo',24),(4435,'Ascurra',24),(4436,'Atalanta',24),(4437,'Aurora',24),(4438,'Balneário Arroio do Silva',24),(4439,'Balneário Barra do Sul',24),(4440,'Balneário Camboriú',24),(4441,'Balneário Gaivota',24),(4442,'Bandeirante',24),(4443,'Barra Bonita',24),(4444,'Barra Velha',24),(4445,'Bela Vista do Toldo',24),(4446,'Belmonte',24),(4447,'Benedito Novo',24),(4448,'Biguaçu',24),(4449,'Blumenau',24),(4450,'Bocaina do Sul',24),(4451,'Bom Jardim da Serra',24),(4452,'Bom Jesus',24),(4453,'Bom Jesus do Oeste',24),(4454,'Bom Retiro',24),(4455,'Bombinhas',24),(4456,'Botuverá',24),(4457,'Braço do Norte',24),(4458,'Braço do Trombudo',24),(4459,'Brunópolis',24),(4460,'Brusque',24),(4461,'Caçador',24),(4462,'Caibi',24),(4463,'Calmon',24),(4464,'Camboriú',24),(4465,'Campo Alegre',24),(4466,'Campo Belo do Sul',24),(4467,'Campo Erê',24),(4468,'Campos Novos',24),(4469,'Canelinha',24),(4470,'Canoinhas',24),(4471,'Capão Alto',24),(4472,'Capinzal',24),(4473,'Capivari de Baixo',24),(4474,'Catanduvas',24),(4475,'Caxambu do Sul',24),(4476,'Celso Ramos',24),(4477,'Cerro Negro',24),(4478,'Chapadão do Lageado',24),(4479,'Chapecó',24),(4480,'Cocal do Sul',24),(4481,'Concórdia',24),(4482,'Cordilheira Alta',24),(4483,'Coronel Freitas',24),(4484,'Coronel Martins',24),(4485,'Correia Pinto',24),(4486,'Corupá',24),(4487,'Criciúma',24),(4488,'Cunha Porã',24),(4489,'Cunhataí',24),(4490,'Curitibanos',24),(4491,'Descanso',24),(4492,'Dionísio Cerqueira',24),(4493,'Dona Emma',24),(4494,'Doutor Pedrinho',24),(4495,'Entre Rios',24),(4496,'Ermo',24),(4497,'Erval Velho',24),(4498,'Faxinal dos Guedes',24),(4499,'Flor do Sertão',24),(4500,'Florianópolis',24),(4501,'Formosa do Sul',24),(4502,'Forquilhinha',24),(4503,'Fraiburgo',24),(4504,'Frei Rogério',24),(4505,'Galvão',24),(4506,'Garopaba',24),(4507,'Garuva',24),(4508,'Gaspar',24),(4509,'Governador Celso Ramos',24),(4510,'Grão Pará',24),(4511,'Gravatal',24),(4512,'Guabiruba',24),(4513,'Guaraciaba',24),(4514,'Guaramirim',24),(4515,'Guarujá do Sul',24),(4516,'Guatambú',24),(4517,'Herval d`Oeste',24),(4518,'Ibiam',24),(4519,'Ibicaré',24),(4520,'Ibirama',24),(4521,'Içara',24),(4522,'Ilhota',24),(4523,'Imaruí',24),(4524,'Imbituba',24),(4525,'Imbuia',24),(4526,'Indaial',24),(4527,'Iomerê',24),(4528,'Ipira',24),(4529,'Iporã do Oeste',24),(4530,'Ipuaçu',24),(4531,'Ipumirim',24),(4532,'Iraceminha',24),(4533,'Irani',24),(4534,'Irati',24),(4535,'Irineópolis',24),(4536,'Itá',24),(4537,'Itaiópolis',24),(4538,'Itajaí',24),(4539,'Itapema',24),(4540,'Itapiranga',24),(4541,'Itapoá',24),(4542,'Ituporanga',24),(4543,'Jaborá',24),(4544,'Jacinto Machado',24),(4545,'Jaguaruna',24),(4546,'Jaraguá do Sul',24),(4547,'Jardinópolis',24),(4548,'Joaçaba',24),(4549,'Joinville',24),(4550,'José Boiteux',24),(4551,'Jupiá',24),(4552,'Lacerdópolis',24),(4553,'Lages',24),(4554,'Laguna',24),(4555,'Lajeado Grande',24),(4556,'Laurentino',24),(4557,'Lauro Muller',24),(4558,'Lebon Régis',24),(4559,'Leoberto Leal',24),(4560,'Lindóia do Sul',24),(4561,'Lontras',24),(4562,'Luiz Alves',24),(4563,'Luzerna',24),(4564,'Macieira',24),(4565,'Mafra',24),(4566,'Major Gercino',24),(4567,'Major Vieira',24),(4568,'Maracajá',24),(4569,'Maravilha',24),(4570,'Marema',24),(4571,'Massaranduba',24),(4572,'Matos Costa',24),(4573,'Meleiro',24),(4574,'Mirim Doce',24),(4575,'Modelo',24),(4576,'Mondaí',24),(4577,'Monte Carlo',24),(4578,'Monte Castelo',24),(4579,'Morro da Fumaça',24),(4580,'Morro Grande',24),(4581,'Navegantes',24),(4582,'Nova Erechim',24),(4583,'Nova Itaberaba',24),(4584,'Nova Trento',24),(4585,'Nova Veneza',24),(4586,'Novo Horizonte',24),(4587,'Orleans',24),(4588,'Otacílio Costa',24),(4589,'Ouro',24),(4590,'Ouro Verde',24),(4591,'Paial',24),(4592,'Painel',24),(4593,'Palhoça',24),(4594,'Palma Sola',24),(4595,'Palmeira',24),(4596,'Palmitos',24),(4597,'Papanduva',24),(4598,'Paraíso',24),(4599,'Passo de Torres',24),(4600,'Passos Maia',24),(4601,'Paulo Lopes',24),(4602,'Pedras Grandes',24),(4603,'Penha',24),(4604,'Peritiba',24),(4605,'Petrolândia',24),(4606,'Piçarras',24),(4607,'Pinhalzinho',24),(4608,'Pinheiro Preto',24),(4609,'Piratuba',24),(4610,'Planalto Alegre',24),(4611,'Pomerode',24),(4612,'Ponte Alta',24),(4613,'Ponte Alta do Norte',24),(4614,'Ponte Serrada',24),(4615,'Porto Belo',24),(4616,'Porto União',24),(4617,'Pouso Redondo',24),(4618,'Praia Grande',24),(4619,'Presidente Castelo Branco',24),(4620,'Presidente Getúlio',24),(4621,'Presidente Nereu',24),(4622,'Princesa',24),(4623,'Quilombo',24),(4624,'Rancho Queimado',24),(4625,'Rio das Antas',24),(4626,'Rio do Campo',24),(4627,'Rio do Oeste',24),(4628,'Rio do Sul',24),(4629,'Rio dos Cedros',24),(4630,'Rio Fortuna',24),(4631,'Rio Negrinho',24),(4632,'Rio Rufino',24),(4633,'Riqueza',24),(4634,'Rodeio',24),(4635,'Romelândia',24),(4636,'Salete',24),(4637,'Saltinho',24),(4638,'Salto Veloso',24),(4639,'Sangão',24),(4640,'Santa Cecília',24),(4641,'Santa Helena',24),(4642,'Santa Rosa de Lima',24),(4643,'Santa Rosa do Sul',24),(4644,'Santa Terezinha',24),(4645,'Santa Terezinha do Progresso',24),(4646,'Santiago do Sul',24),(4647,'Santo Amaro da Imperatriz',24),(4648,'São Bento do Sul',24),(4649,'São Bernardino',24),(4650,'São Bonifácio',24),(4651,'São Carlos',24),(4652,'São Cristovão do Sul',24),(4653,'São Domingos',24),(4654,'São Francisco do Sul',24),(4655,'São João Batista',24),(4656,'São João do Itaperiú',24),(4657,'São João do Oeste',24),(4658,'São João do Sul',24),(4659,'São Joaquim',24),(4660,'São José',24),(4661,'São José do Cedro',24),(4662,'São José do Cerrito',24),(4663,'São Lourenço do Oeste',24),(4664,'São Ludgero',24),(4665,'São Martinho',24),(4666,'São Miguel da Boa Vista',24),(4667,'São Miguel do Oeste',24),(4668,'São Pedro de Alcântara',24),(4669,'Saudades',24),(4670,'Schroeder',24),(4671,'Seara',24),(4672,'Serra Alta',24),(4673,'Siderópolis',24),(4674,'Sombrio',24),(4675,'Sul Brasil',24),(4676,'Taió',24),(4677,'Tangará',24),(4678,'Tigrinhos',24),(4679,'Tijucas',24),(4680,'Timbé do Sul',24),(4681,'Timbó',24),(4682,'Timbó Grande',24),(4683,'Três Barras',24),(4684,'Treviso',24),(4685,'Treze de Maio',24),(4686,'Treze Tílias',24),(4687,'Trombudo Central',24),(4688,'Tubarão',24),(4689,'Tunápolis',24),(4690,'Turvo',24),(4691,'União do Oeste',24),(4692,'Urubici',24),(4693,'Urupema',24),(4694,'Urussanga',24),(4695,'Vargeão',24),(4696,'Vargem',24),(4697,'Vargem Bonita',24),(4698,'Vidal Ramos',24),(4699,'Videira',24),(4700,'Vitor Meireles',24),(4701,'Witmarsum',24),(4702,'Xanxerê',24),(4703,'Xavantina',24),(4704,'Xaxim',24),(4705,'Zortéa',24);
/*!40000 ALTER TABLE `cidade` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cliente`
--

DROP TABLE IF EXISTS `cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cliente` (
  `codigo` int(6) NOT NULL AUTO_INCREMENT,
  `nome` varchar(45) NOT NULL,
  `rg` varchar(9) DEFAULT NULL,
  `cpf` varchar(14) DEFAULT NULL,
  `endereco` varchar(60) DEFAULT NULL,
  `numero` varchar(5) DEFAULT NULL,
  `complemento` varchar(40) DEFAULT NULL,
  `cep` int(8) DEFAULT NULL,
  `bairro` int(6) DEFAULT NULL,
  `cidade` int(6) DEFAULT NULL,
  `aniversario` date DEFAULT NULL,
  `datacadastro` date DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `telefone` bigint(20) DEFAULT NULL,
  `celular` bigint(20) DEFAULT NULL,
  `ativo` char(1) NOT NULL,
  PRIMARY KEY (`codigo`),
  KEY `fk_cliente_bairro_idx` (`bairro`),
  KEY `fk_cliente_cidade_idx` (`cidade`),
  CONSTRAINT `fk_cliente_bairro` FOREIGN KEY (`bairro`) REFERENCES `bairro` (`codigo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_cliente_cidade` FOREIGN KEY (`cidade`) REFERENCES `cidade` (`codigo`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=326 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cliente`
--

LOCK TABLES `cliente` WRITE;
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
INSERT INTO `cliente` VALUES (324,'Guilherme','','','Rua Maria Santa Correa','','',0,1,4413,'2016-09-01','2016-09-26','',0,0,'S'),(325,'Nra','','','','','',0,1,4414,NULL,'2016-10-31','',0,0,'S');
/*!40000 ALTER TABLE `cliente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `compra`
--

DROP TABLE IF EXISTS `compra`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `compra` (
  `numero` int(6) NOT NULL AUTO_INCREMENT,
  `data` date DEFAULT NULL,
  `total` decimal(7,2) DEFAULT NULL,
  PRIMARY KEY (`numero`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `compra`
--

LOCK TABLES `compra` WRITE;
/*!40000 ALTER TABLE `compra` DISABLE KEYS */;
INSERT INTO `compra` VALUES (17,'2016-09-26',85.00),(18,'2016-10-03',6100.00),(19,'2016-10-03',61500.00);
/*!40000 ALTER TABLE `compra` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `estado`
--

DROP TABLE IF EXISTS `estado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `estado` (
  `codigo` int(2) NOT NULL,
  `nome` varchar(45) NOT NULL,
  `uf` char(2) NOT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estado`
--

LOCK TABLES `estado` WRITE;
/*!40000 ALTER TABLE `estado` DISABLE KEYS */;
INSERT INTO `estado` VALUES (1,'Acre','AC'),(2,'Alagoas','AL'),(3,'Amazonas','AM'),(4,'Amapá','AP'),(5,'Bahia','BA'),(6,'Ceará','CE'),(7,'Distrito Federal','DF'),(8,'Espírito Santo','ES'),(9,'Goiás','GO'),(10,'Maranhão','MA'),(11,'Minas Gerais','MG'),(12,'Mato Grosso do Sul','MS'),(13,'Mato Grosso','MT'),(14,'Pará','PA'),(15,'Paraíba','PB'),(16,'Pernambuco','PE'),(17,'Piauí','PI'),(18,'Paraná','PR'),(19,'Rio de Janeiro','RJ'),(20,'Rio Grande do Norte','RN'),(21,'Rondônia','RO'),(22,'Roraima','RR'),(23,'Rio Grande do Sul','RS'),(24,'Santa Catarina','SC'),(25,'Sergipe','SE'),(26,'São Paulo','SP'),(27,'Tocantins','TO');
/*!40000 ALTER TABLE `estado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fichatecnica`
--

DROP TABLE IF EXISTS `fichatecnica`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fichatecnica` (
  `codigo` int(6) NOT NULL AUTO_INCREMENT,
  `produto` int(6) NOT NULL,
  `qtde` decimal(7,3) NOT NULL,
  `procedimento` text,
  `totalcusto` decimal(7,2) NOT NULL,
  PRIMARY KEY (`codigo`),
  UNIQUE KEY `produto_UNIQUE` (`produto`),
  CONSTRAINT `fk_fichatecnica_produto` FOREIGN KEY (`produto`) REFERENCES `produto` (`codigo`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fichatecnica`
--

LOCK TABLES `fichatecnica` WRITE;
/*!40000 ALTER TABLE `fichatecnica` DISABLE KEYS */;
INSERT INTO `fichatecnica` VALUES (5,110,1.000,'teste',10.00);
/*!40000 ALTER TABLE `fichatecnica` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `itemcompra`
--

DROP TABLE IF EXISTS `itemcompra`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `itemcompra` (
  `numero` int(6) NOT NULL,
  `produto` int(6) NOT NULL,
  `qtde` float NOT NULL,
  `unitario` decimal(7,2) NOT NULL,
  `total` decimal(7,2) NOT NULL,
  PRIMARY KEY (`numero`,`produto`),
  KEY `fk_itemcompra_produto_idx` (`produto`),
  CONSTRAINT `fk_itemcompra_compra` FOREIGN KEY (`numero`) REFERENCES `compra` (`numero`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_itemcompra_produto` FOREIGN KEY (`produto`) REFERENCES `produto` (`codigo`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `itemcompra`
--

LOCK TABLES `itemcompra` WRITE;
/*!40000 ALTER TABLE `itemcompra` DISABLE KEYS */;
INSERT INTO `itemcompra` VALUES (17,108,5,10.00,50.00),(17,109,5,7.00,35.00),(18,108,500,5.00,2500.00),(18,109,600,6.00,3600.00),(19,109,123,500.00,61500.00);
/*!40000 ALTER TABLE `itemcompra` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `itemfichatecnica`
--

DROP TABLE IF EXISTS `itemfichatecnica`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `itemfichatecnica` (
  `fichatecnica` int(6) NOT NULL,
  `ingrediente` int(6) NOT NULL,
  `qtde` float DEFAULT NULL,
  PRIMARY KEY (`fichatecnica`,`ingrediente`),
  KEY `fk_itemfichatecnica_ingrediente_idx` (`ingrediente`),
  CONSTRAINT `fk_itemfichatecnica_fichatecnica` FOREIGN KEY (`fichatecnica`) REFERENCES `fichatecnica` (`codigo`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_itemfichatecnica_produto` FOREIGN KEY (`ingrediente`) REFERENCES `produto` (`codigo`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `itemfichatecnica`
--

LOCK TABLES `itemfichatecnica` WRITE;
/*!40000 ALTER TABLE `itemfichatecnica` DISABLE KEYS */;
INSERT INTO `itemfichatecnica` VALUES (5,108,2),(5,109,2);
/*!40000 ALTER TABLE `itemfichatecnica` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `itemordemproducao`
--

DROP TABLE IF EXISTS `itemordemproducao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `itemordemproducao` (
  `ordemproducao` int(6) NOT NULL,
  `produto` int(6) NOT NULL,
  `qtde` float NOT NULL,
  PRIMARY KEY (`ordemproducao`,`produto`),
  KEY `fk_itemordemproducao_produto_idx` (`produto`),
  CONSTRAINT `fk_itemordemproducao_ordemproducao` FOREIGN KEY (`ordemproducao`) REFERENCES `ordemproducao` (`numero`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_itemordemproducao_produto` FOREIGN KEY (`produto`) REFERENCES `produto` (`codigo`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `itemordemproducao`
--

LOCK TABLES `itemordemproducao` WRITE;
/*!40000 ALTER TABLE `itemordemproducao` DISABLE KEYS */;
INSERT INTO `itemordemproducao` VALUES (2,110,1);
/*!40000 ALTER TABLE `itemordemproducao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `itempedido`
--

DROP TABLE IF EXISTS `itempedido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `itempedido` (
  `numero` int(6) NOT NULL,
  `produto` int(6) NOT NULL,
  `qtde` float NOT NULL,
  `unitario` decimal(7,2) NOT NULL,
  `custo` decimal(7,2) NOT NULL,
  `totalcusto` decimal(7,2) DEFAULT NULL,
  `total` decimal(7,2) NOT NULL,
  PRIMARY KEY (`numero`,`produto`),
  KEY `fk_itempedido_produto_idx` (`produto`),
  CONSTRAINT `fk_itempedido_pedido` FOREIGN KEY (`numero`) REFERENCES `pedido` (`numero`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_itempedido_produto` FOREIGN KEY (`produto`) REFERENCES `produto` (`codigo`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `itempedido`
--

LOCK TABLES `itempedido` WRITE;
/*!40000 ALTER TABLE `itempedido` DISABLE KEYS */;
INSERT INTO `itempedido` VALUES (3,110,10,1.00,1.00,1.00,10.00),(4,110,1,20.00,0.00,40.00,20.00),(5,110,5,1.00,0.00,0.00,5.00),(21,110,12,1.00,10.00,120.00,12.00);
/*!40000 ALTER TABLE `itempedido` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `itemperda`
--

DROP TABLE IF EXISTS `itemperda`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `itemperda` (
  `numero` int(6) NOT NULL AUTO_INCREMENT,
  `produto` int(6) NOT NULL,
  `qtde` float NOT NULL,
  `unitario` decimal(7,2) DEFAULT NULL,
  `total` decimal(7,2) DEFAULT NULL,
  PRIMARY KEY (`numero`,`produto`),
  KEY `fk_itemperda_produto_idx` (`produto`),
  CONSTRAINT `fk_itemperda_perda` FOREIGN KEY (`numero`) REFERENCES `perda` (`numero`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_itemperda_produto` FOREIGN KEY (`produto`) REFERENCES `produto` (`codigo`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `itemperda`
--

LOCK TABLES `itemperda` WRITE;
/*!40000 ALTER TABLE `itemperda` DISABLE KEYS */;
/*!40000 ALTER TABLE `itemperda` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ordemproducao`
--

DROP TABLE IF EXISTS `ordemproducao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ordemproducao` (
  `numero` int(6) NOT NULL AUTO_INCREMENT,
  `inicio` timestamp NULL DEFAULT NULL,
  `fim` timestamp NULL DEFAULT NULL,
  `duracao` varchar(15) DEFAULT NULL,
  `data` date NOT NULL,
  `emproducao` char(1) NOT NULL,
  `produzida` char(1) NOT NULL,
  PRIMARY KEY (`numero`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ordemproducao`
--

LOCK TABLES `ordemproducao` WRITE;
/*!40000 ALTER TABLE `ordemproducao` DISABLE KEYS */;
INSERT INTO `ordemproducao` VALUES (2,'2016-12-05 23:12:46',NULL,NULL,'2016-12-05','S','N');
/*!40000 ALTER TABLE `ordemproducao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pedido`
--

DROP TABLE IF EXISTS `pedido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pedido` (
  `numero` int(6) NOT NULL AUTO_INCREMENT,
  `cliente` int(6) NOT NULL,
  `emissao` date NOT NULL,
  `datafaturado` date DEFAULT NULL,
  `faturado` char(1) NOT NULL,
  `produzido` char(1) NOT NULL,
  `cancelado` char(1) NOT NULL,
  `total` decimal(7,2) NOT NULL,
  `ordemproducao` int(6) DEFAULT NULL,
  PRIMARY KEY (`numero`),
  KEY `fk_pedido_cliente_idx` (`cliente`),
  KEY `fk_pedido_ordemproducao_idx` (`ordemproducao`),
  CONSTRAINT `fk_pedido_cliente` FOREIGN KEY (`cliente`) REFERENCES `cliente` (`codigo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_pedido_ordemproducao` FOREIGN KEY (`ordemproducao`) REFERENCES `ordemproducao` (`numero`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pedido`
--

LOCK TABLES `pedido` WRITE;
/*!40000 ALTER TABLE `pedido` DISABLE KEYS */;
INSERT INTO `pedido` VALUES (3,324,'2016-11-01','2016-11-01','S','N','N',10.00,NULL),(4,325,'2016-11-01','2016-11-01','S','N','N',20.00,NULL),(5,324,'2016-11-01','2016-11-01','S','N','N',5.00,NULL),(20,324,'2016-12-05',NULL,'N','N','N',1.00,NULL),(21,324,'2016-12-05',NULL,'N','N','N',12.00,NULL);
/*!40000 ALTER TABLE `pedido` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `perda`
--

DROP TABLE IF EXISTS `perda`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `perda` (
  `numero` int(6) NOT NULL AUTO_INCREMENT,
  `data` date NOT NULL,
  `total` decimal(7,2) DEFAULT NULL,
  PRIMARY KEY (`numero`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `perda`
--

LOCK TABLES `perda` WRITE;
/*!40000 ALTER TABLE `perda` DISABLE KEYS */;
/*!40000 ALTER TABLE `perda` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `produto`
--

DROP TABLE IF EXISTS `produto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `produto` (
  `codigo` int(6) NOT NULL AUTO_INCREMENT,
  `descricao` varchar(45) NOT NULL,
  `estoque` decimal(7,2) NOT NULL,
  `unestoque` char(2) NOT NULL,
  `valorcusto` decimal(7,2) NOT NULL,
  `margem` decimal(7,2) NOT NULL,
  `valorvenda` decimal(7,2) NOT NULL,
  `tipoitem` int(1) NOT NULL,
  `datacadastro` date NOT NULL,
  `ativo` char(1) NOT NULL,
  `qtdeentrada` float NOT NULL,
  `unentrada` char(2) NOT NULL,
  PRIMARY KEY (`codigo`),
  KEY `fk_produto_tipoitem_idx` (`tipoitem`),
  CONSTRAINT `fk_produto_tipoitem` FOREIGN KEY (`tipoitem`) REFERENCES `tipoitem` (`codigo`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=111 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `produto`
--

LOCK TABLES `produto` WRITE;
/*!40000 ALTER TABLE `produto` DISABLE KEYS */;
INSERT INTO `produto` VALUES (108,'Leite Condensado',0.00,'cx',5.01,-80.04,1.00,2,'2016-09-26','S',1,'cx'),(109,'Chocolate Granulado',0.00,'cx',209.90,-99.52,1.00,2,'2016-09-26','S',1,'cx'),(110,'Brigadeiro Chocolate',984.00,'cx',10.00,0.00,1.00,1,'2016-09-26','S',1,'cx');
/*!40000 ALTER TABLE `produto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipoitem`
--

DROP TABLE IF EXISTS `tipoitem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tipoitem` (
  `codigo` int(1) NOT NULL,
  `tipo` varchar(20) NOT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipoitem`
--

LOCK TABLES `tipoitem` WRITE;
/*!40000 ALTER TABLE `tipoitem` DISABLE KEYS */;
INSERT INTO `tipoitem` VALUES (1,'Produto'),(2,'Ingrediente'),(3,'Embalagem');
/*!40000 ALTER TABLE `tipoitem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuario` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `senha` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'teste','teste@teste','e10adc3949ba59abbe56e057f20f883e');
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-12-05 22:02:32
