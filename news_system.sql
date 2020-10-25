-- phpMyAdmin SQL Dump
-- version 4.9.3
-- https://www.phpmyadmin.net/
--
-- Host: localhost:8889
-- Generation Time: Oct 25, 2020 at 10:44 PM
-- Server version: 5.7.26
-- PHP Version: 7.4.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `news_system`
--

-- --------------------------------------------------------

--
-- Table structure for table `news`
--

CREATE TABLE `news` (
  `id_news` int(10) NOT NULL,
  `titre` varchar(255) NOT NULL,
  `content` mediumtext NOT NULL,
  `date_pub` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `theme` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `news`
--

INSERT INTO `news` (`id_news`, `titre`, `content`, `date_pub`, `theme`) VALUES
(1, 'Covid-19 : la liste des aides prévues en France pour faire face à la crise', 'L’interview d’Emmanuel Macron mercredi soir le confirme : l’épidémie de Covid-19 et ses effets sur l’économie sont loin d’être terminés. Pour limiter la crise économique et sociale engendrée par les nombreuses restrictions sanitaires, le gouvernement a déployé plusieurs dispositifs d’urgence ou d’aide à moyen terme. Après une réduction de ces aides pendant l’été en raison du reflux de l’épidémie, l’exécutif a décidé ou prolongé plusieurs mesures à la rentrée. Bruno Le Maire et Jean Castex ont ainsi détaillé, jeudi 15 octobre, des aides pour les foyers les plus précaires et les entreprises dans les zones concernées par le couvre-feu, qui entrera en vigueur dans la nuit de vendredi à samedi.', '2020-10-16 13:32:15', 1);

-- --------------------------------------------------------

--
-- Table structure for table `news_tags`
--

CREATE TABLE `news_tags` (
  `news` int(10) NOT NULL,
  `tag` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `news_tags`
--

INSERT INTO `news_tags` (`news`, `tag`) VALUES
(1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `tags`
--

CREATE TABLE `tags` (
  `id_tag` int(10) NOT NULL,
  `libelle` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tags`
--

INSERT INTO `tags` (`id_tag`, `libelle`) VALUES
(1, 'Covid'),
(2, 'Trump'),
(3, 'Manifestation'),
(4, 'Paris'),
(5, 'Immobilier');

-- --------------------------------------------------------

--
-- Table structure for table `themes`
--

CREATE TABLE `themes` (
  `id_theme` int(10) NOT NULL,
  `libelle` varchar(60) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `themes`
--

INSERT INTO `themes` (`id_theme`, `libelle`) VALUES
(1, 'economie'),
(2, 'politique'),
(3, 'sante'),
(4, 'medias'),
(5, 'environnement');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `news`
--
ALTER TABLE `news`
  ADD PRIMARY KEY (`id_news`),
  ADD KEY `theme` (`theme`);

--
-- Indexes for table `news_tags`
--
ALTER TABLE `news_tags`
  ADD PRIMARY KEY (`news`,`tag`),
  ADD KEY `news` (`news`),
  ADD KEY `tag` (`tag`);

--
-- Indexes for table `tags`
--
ALTER TABLE `tags`
  ADD PRIMARY KEY (`id_tag`);

--
-- Indexes for table `themes`
--
ALTER TABLE `themes`
  ADD PRIMARY KEY (`id_theme`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `news`
--
ALTER TABLE `news`
  MODIFY `id_news` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `tags`
--
ALTER TABLE `tags`
  MODIFY `id_tag` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `themes`
--
ALTER TABLE `themes`
  MODIFY `id_theme` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `news`
--
ALTER TABLE `news`
  ADD CONSTRAINT `news_ibfk_1` FOREIGN KEY (`theme`) REFERENCES `themes` (`id_theme`);

--
-- Constraints for table `news_tags`
--
ALTER TABLE `news_tags`
  ADD CONSTRAINT `news_tags_ibfk_1` FOREIGN KEY (`news`) REFERENCES `news` (`id_news`) ON DELETE CASCADE,
  ADD CONSTRAINT `news_tags_ibfk_2` FOREIGN KEY (`tag`) REFERENCES `tags` (`id_tag`) ON DELETE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
