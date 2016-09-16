-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Sep 16, 2016 at 08:42 AM
-- Server version: 10.1.13-MariaDB
-- PHP Version: 5.6.23

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `notices`
--

-- --------------------------------------------------------

--
-- Table structure for table `admindata`
--

CREATE TABLE `admindata` (
  `uniqueid` varchar(100) NOT NULL,
  `name` varchar(256) NOT NULL,
  `branch` varchar(10) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(80) NOT NULL,
  `salt` varchar(10) NOT NULL,
  `createdat` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updatedat` timestamp NULL DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `admindata`
--

INSERT INTO `admindata` (`uniqueid`, `name`, `branch`, `email`, `password`, `salt`, `createdat`, `updatedat`) VALUES
('57db85496cfd35.37282920', 'ankit', 'it', 'ankschamp.ac@gmail.com', 'y0lZ2/T4xVWUJIf+HSFHnFbB7xBkYzA0MjFkOTc1', 'dc0421d975', '2016-09-16 05:38:17', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `noticedata`
--

CREATE TABLE `noticedata` (
  `uniqueid` varchar(100) NOT NULL,
  `title` varchar(256) NOT NULL,
  `description` varchar(10000) NOT NULL,
  `createdat` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updatedat` timestamp NULL DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `noticedata`
--

INSERT INTO `noticedata` (`uniqueid`, `title`, `description`, `createdat`, `updatedat`) VALUES
('57db92f7aa6866.20059400', 'hello world', 'fbasbfuiashfoiafgaiusbaiufguaisfauifgaoigrwqiugqw8gfrwyufgafgauiysgfyasgfhasgfuyagfdyaewsiugishrohritr4ti0ihfhkfohjgfiu90trhfuifdhuidf', '2016-09-16 06:36:39', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `studentdata`
--

CREATE TABLE `studentdata` (
  `id` int(100) NOT NULL,
  `uniqueid` varchar(100) NOT NULL,
  `name` varchar(256) NOT NULL,
  `year` varchar(16) NOT NULL,
  `branch` varchar(10) NOT NULL,
  `rollno` text NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(80) NOT NULL,
  `salt` varchar(10) NOT NULL,
  `createdat` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updatedat` timestamp NULL DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `studentdata`
--

INSERT INTO `studentdata` (`id`, `uniqueid`, `name`, `year`, `branch`, `rollno`, `email`, `password`, `salt`, `createdat`, `updatedat`) VALUES
(10, '57d9666ab46589.06634229', 'burhanuddin rampurawala', 'SE', 'CMPN-2', '76', 'burhanuddinzrampurawala@gmail.com', 'MTuNH2GSEGXPzP0I3U1Nk5qQZGlmM2RjOWUyNGVk', 'f3dc9e24ed', '2016-09-14 15:02:02', NULL),
(11, '57db82f34fcc95.95079272', 'Ankit', 'Third', 'IT', '5', 'ankschamp.ac@gmail.com', 'RWbTRiXpJMwr5A8YJ42jcLBtcPdkZDIwYmEyZmQ5', 'dd20ba2fd9', '2016-09-16 05:28:19', NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admindata`
--
ALTER TABLE `admindata`
  ADD UNIQUE KEY `uniqueid` (`uniqueid`);

--
-- Indexes for table `noticedata`
--
ALTER TABLE `noticedata`
  ADD UNIQUE KEY `uniqueid` (`uniqueid`);

--
-- Indexes for table `studentdata`
--
ALTER TABLE `studentdata`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `uniqueindex` (`uniqueid`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `studentdata`
--
ALTER TABLE `studentdata`
  MODIFY `id` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
