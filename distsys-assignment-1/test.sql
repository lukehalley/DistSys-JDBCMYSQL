-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Oct 09, 2018 at 11:51 AM
-- Server version: 5.7.23
-- PHP Version: 7.2.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `test`
--

-- --------------------------------------------------------

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
CREATE TABLE IF NOT EXISTS `employee` (
  `id` int(5) NOT NULL,
  `ssn` int(4) NOT NULL,
  `bdate` date NOT NULL,
  `name` varchar(20) NOT NULL,
  `address` varchar(40) NOT NULL,
  `sex` char(1) NOT NULL,
  `salary` int(8) NOT NULL,
  UNIQUE KEY `ssn` (`ssn`),
  UNIQUE KEY `ssn_2` (`ssn`),
  UNIQUE KEY `ssn_3` (`ssn`),
  UNIQUE KEY `ssn_4` (`ssn`),
  UNIQUE KEY `ssn_5` (`ssn`),
  UNIQUE KEY `ssn_6` (`ssn`),
  UNIQUE KEY `ssn_7` (`ssn`),
  UNIQUE KEY `ssn_8` (`ssn`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `employee`
--

INSERT INTO `employee` (`id`, `ssn`, `bdate`, `name`, `address`, `sex`, `salary`) VALUES
(1, 1234, '1999-12-07', 'Jake Halley', 'Waterford', 'M', 9393943),
(2, 4562, '1992-11-02', 'Luke Halley', 'Clark Street', 'M', 3425),
(3, 970, '2001-01-03', 'Tia Beans', '102 Lark Street', 'F', 456),
(4, 912, '1990-09-21', 'Sarah Que', 'Nightstreet Avenue', 'F', 23178),
(5, 8, '2007-01-14', 'Tom Appleseed', 'Spain', 'M', 234);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
