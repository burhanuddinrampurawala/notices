-- phpMyAdmin SQL Dump
-- version 4.5.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Sep 20, 2016 at 02:41 PM
-- Server version: 5.7.11
-- PHP Version: 5.6.19

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
('57db85496cfd35.37282920', 'ankit', 'it', 'ankschamp.ac@gmail.com', 'y0lZ2/T4xVWUJIf+HSFHnFbB7xBkYzA0MjFkOTc1', 'dc0421d975', '2016-09-16 00:08:17', NULL);

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
('57db92f7aa6866.20059400', 'hello wrold part 2', 'some description', '2016-09-16 01:06:39', '2016-09-19 15:29:57'),
('57e144a1ee55b0.15823341', 'sum up', 'adds it up', '2016-09-20 14:16:02', NULL),
('57dff0bd654ec6.56307568', 'title', 'description', '2016-09-19 14:05:49', '2016-09-19 15:15:58');

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
(10, '57d9666ab46589.06634229', 'burhanuddin rampurawala', 'SE', 'CMPN-2', '76', 'burhanuddinzrampurawala@gmail.com', 'MTuNH2GSEGXPzP0I3U1Nk5qQZGlmM2RjOWUyNGVk', 'f3dc9e24ed', '2016-09-14 15:02:02', NULL);

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
  ADD PRIMARY KEY (`uniqueid`),
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
  MODIFY `id` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
