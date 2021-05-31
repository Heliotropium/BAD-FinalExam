-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 05, 2021 at 01:03 PM
-- Server version: 10.4.17-MariaDB
-- PHP Version: 8.0.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `snack_shop`
--

-- --------------------------------------------------------

--
-- Table structure for table `cart`
--

CREATE TABLE `cart` (
  `UserId` int(11) NOT NULL,
  `SnackId` int(11) NOT NULL,
  `Quantity` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `detailtransaction`
--

CREATE TABLE `detailtransaction` (
  `TransactionId` int(11) NOT NULL,
  `SnackId` int(11) NOT NULL,
  `Quantity` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `detailtransaction`
--

INSERT INTO `detailtransaction` (`TransactionId`, `SnackId`, `Quantity`) VALUES
(1, 1, 5),
(2, 1, 5),
(2, 2, 12),
(2, 3, 8),
(3, 3, 9),
(3, 6, 7),
(3, 10, 9),
(3, 12, 10),
(4, 2, 9),
(4, 5, 6),
(4, 11, 6),
(5, 10, 4),
(5, 13, 3),
(6, 1, 2),
(6, 10, 2),
(7, 2, 6),
(7, 4, 6),
(7, 10, 5),
(7, 13, 2),
(8, 11, 1),
(8, 13, 1),
(9, 2, 1),
(10, 1, 5),
(11, 12, 4),
(12, 13, 3),
(13, 3, 6),
(14, 3, 9),
(14, 5, 13),
(14, 10, 7),
(14, 12, 9),
(14, 13, 5);

-- --------------------------------------------------------

--
-- Table structure for table `headertransaction`
--

CREATE TABLE `headertransaction` (
  `TransactionId` int(11) NOT NULL,
  `UserId` int(11) NOT NULL,
  `TransactionDate` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `headertransaction`
--

INSERT INTO `headertransaction` (`TransactionId`, `UserId`, `TransactionDate`) VALUES
(1, 3, '2020-04-23'),
(2, 3, '2020-04-23'),
(3, 2, '2020-04-24'),
(4, 5, '2020-04-24'),
(5, 2, '2020-04-26'),
(6, 5, '2020-04-26'),
(7, 2, '2020-04-26'),
(8, 3, '2020-04-26'),
(9, 5, '2020-04-27'),
(10, 2, '2020-04-27'),
(11, 3, '2020-04-28'),
(12, 5, '2020-04-28'),
(13, 2, '2020-04-28'),
(14, 5, '2020-04-28');

-- --------------------------------------------------------

--
-- Table structure for table `role`
--

CREATE TABLE `role` (
  `RoleId` int(11) NOT NULL,
  `RoleName` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `role`
--

INSERT INTO `role` (`RoleId`, `RoleName`) VALUES
(1, 'User'),
(2, 'Admin');

-- --------------------------------------------------------

--
-- Table structure for table `snack`
--

CREATE TABLE `snack` (
  `SnackId` int(11) NOT NULL,
  `SnackName` varchar(100) NOT NULL,
  `SnackPrice` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `snack`
--

INSERT INTO `snack` (`SnackId`, `SnackName`, `SnackPrice`) VALUES
(1, 'Chitato 200gr', 5000),
(2, 'Chitato 500gr', 10000),
(3, 'Lays Original 500gr', 21000),
(4, 'Basreng 100gr', 11000),
(5, 'Mybizcuit isi pasta kacang', 55000),
(6, 'Roma kelapa Kaleng', 28600),
(10, 'Soes isi coklat dan green tea', 20000),
(11, 'BITI 20 G', 1900),
(12, 'FITBAR Fruits 24 gr', 3600),
(13, 'REMPEYEK KACANG HIJAU', 30000);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `UserId` int(11) NOT NULL,
  `RoleId` int(11) NOT NULL,
  `UserName` varchar(100) NOT NULL,
  `UserGender` varchar(15) NOT NULL,
  `UserDOB` date NOT NULL,
  `UserAddress` varchar(200) NOT NULL,
  `UserEmail` varchar(50) NOT NULL,
  `UserPassword` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`UserId`, `RoleId`, `UserName`, `UserGender`, `UserDOB`, `UserAddress`, `UserEmail`, `UserPassword`) VALUES
(1, 2, 'Admin Test', 'Male', '2000-12-03', 'Pondomoro Street', 'admin@admin.com', 'adminadmin'),
(2, 1, 'HelpTest', 'Male', '1975-06-06', 'HelpTest Street', 'HelpTest@HelpTest.com', 'HelpTest'),
(3, 1, 'User1', 'Female', '2013-06-06', 'User1 Street', 'user1@user1.com', 'User1User1'),
(4, 2, 'Pararam', 'Male', '2019-12-31', 'RamParam Street', 'para@para.com', 'parapam'),
(5, 1, 'Minion', 'Female', '2014-12-24', 'Minion Street', 'minion@mini.com', 'Minion'),
(6, 1, 'shanta', 'Female', '1975-01-01', 'jln sjhdhwihdc Street', 'shantdd@gmail.com', '12345'),
(7, 1, 'shanta ', 'Female', '1975-01-01', 'jln Street', 'shantaddd@gmail.com', 'shanta123'),
(8, 1, 'userbro', 'Male', '2020-01-01', 'users Street', 'user@u.com', 'user'),
(9, 1, 'shalala', 'Female', '1975-01-01', 'Jln ijiji huhu Street', 'shalala@gmail.com', '1234');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `cart`
--
ALTER TABLE `cart`
  ADD PRIMARY KEY (`UserId`,`SnackId`),
  ADD KEY `fk_snack` (`SnackId`);

--
-- Indexes for table `detailtransaction`
--
ALTER TABLE `detailtransaction`
  ADD PRIMARY KEY (`TransactionId`,`SnackId`),
  ADD KEY `fk_snack_key` (`SnackId`);

--
-- Indexes for table `headertransaction`
--
ALTER TABLE `headertransaction`
  ADD PRIMARY KEY (`TransactionId`),
  ADD KEY `fk_user_key` (`UserId`);

--
-- Indexes for table `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`RoleId`);

--
-- Indexes for table `snack`
--
ALTER TABLE `snack`
  ADD PRIMARY KEY (`SnackId`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`UserId`),
  ADD KEY `fk_role` (`RoleId`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `cart`
--
ALTER TABLE `cart`
  ADD CONSTRAINT `fk_snack` FOREIGN KEY (`SnackId`) REFERENCES `snack` (`SnackId`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_user` FOREIGN KEY (`UserId`) REFERENCES `user` (`UserId`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `detailtransaction`
--
ALTER TABLE `detailtransaction`
  ADD CONSTRAINT `fk_snack_key` FOREIGN KEY (`SnackId`) REFERENCES `snack` (`SnackId`),
  ADD CONSTRAINT `fk_tran` FOREIGN KEY (`TransactionId`) REFERENCES `headertransaction` (`TransactionId`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `headertransaction`
--
ALTER TABLE `headertransaction`
  ADD CONSTRAINT `fk_user_key` FOREIGN KEY (`UserId`) REFERENCES `user` (`UserId`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `fk_role` FOREIGN KEY (`RoleId`) REFERENCES `role` (`RoleId`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
