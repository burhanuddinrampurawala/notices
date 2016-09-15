-- phpMyAdmin SQL Dump
-- version 4.5.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Sep 15, 2016 at 11:42 AM
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
