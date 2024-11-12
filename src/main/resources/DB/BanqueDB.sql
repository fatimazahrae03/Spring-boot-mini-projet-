-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : mar. 12 nov. 2024 à 10:41
-- Version du serveur : 10.4.32-MariaDB
-- Version de PHP : 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `fatima`
--

-- --------------------------------------------------------

--
-- Structure de la table `client`
--

CREATE TABLE `client` (
  `codeClient` bigint(20) NOT NULL,
  `nomClient` varchar(255) DEFAULT NULL,
  `passwordClient` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `client`
--

INSERT INTO `client` (`codeClient`, `nomClient`, `passwordClient`) VALUES
(2, 'Fatima', NULL),
(7, 'Youssra', NULL),
(9, 'Khaoula', ''),
(10, 'HH', NULL),
(11, 'zaid', NULL),
(12, 'jihad', NULL),
(13, 'Fouad', NULL);

-- --------------------------------------------------------

--
-- Structure de la table `compte`
--

CREATE TABLE `compte` (
  `TYPE_CPTE` varchar(2) NOT NULL,
  `codeCompte` varchar(255) NOT NULL,
  `dateCreation` datetime(6) DEFAULT NULL,
  `solde` double NOT NULL,
  `decouvert` double DEFAULT NULL,
  `taux` double DEFAULT NULL,
  `CODE_CLI` bigint(20) DEFAULT NULL,
  `CODE_EMP` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `compte`
--

INSERT INTO `compte` (`TYPE_CPTE`, `codeCompte`, `dateCreation`, `solde`, `decouvert`, `taux`, `CODE_CLI`, `CODE_EMP`) VALUES
('CC', '1a8f09a0-6a37-4183-95f8-94009896ff5b', '2024-11-12 00:53:10.000000', 300, 3000, NULL, 9, 1),
('CE', '37fa12ee-97af-4350-9e49-a82c2fba7af8', '2024-11-11 16:19:20.000000', 700, NULL, 200, 12, 2),
('CE', '61089e12-41e6-4979-9bdd-a3f9f8aa7163', '2024-11-09 15:42:58.000000', 5020, NULL, 1000, 2, 1),
('CC', 'cc9329b3-8b3d-4b77-adb5-a0c06eefd661', '2024-11-11 01:16:15.000000', 0, 600, NULL, 9, 2),
('CC', 'e9935129-2cae-4958-b7f1-d98af0dc35be', '2024-11-11 14:45:57.000000', 200, 2000, NULL, 2, 2);

-- --------------------------------------------------------

--
-- Structure de la table `employe`
--

CREATE TABLE `employe` (
  `codeEmploye` bigint(20) NOT NULL,
  `nomEmploye` varchar(255) DEFAULT NULL,
  `code_emp_sup` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `employe`
--

INSERT INTO `employe` (`codeEmploye`, `nomEmploye`, `code_emp_sup`) VALUES
(1, 'Aya', NULL),
(2, 'Wiam', NULL),
(11, 'Romayssae', 1),
(12, 'Hicham', 1),
(15, 'Sara', 1),
(16, 'siham', 1);

-- --------------------------------------------------------

--
-- Structure de la table `emp_gr`
--

CREATE TABLE `emp_gr` (
  `code_employe` bigint(20) NOT NULL,
  `code_groupe` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `emp_gr`
--

INSERT INTO `emp_gr` (`code_employe`, `code_groupe`) VALUES
(2, 9),
(11, 4),
(11, 11),
(12, 4),
(12, 11);

-- --------------------------------------------------------

--
-- Structure de la table `groupe`
--

CREATE TABLE `groupe` (
  `codeGroupe` bigint(20) NOT NULL,
  `nomGroupe` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `groupe`
--

INSERT INTO `groupe` (`codeGroupe`, `nomGroupe`) VALUES
(4, 'ttt'),
(9, 'dev'),
(11, 'Test');

-- --------------------------------------------------------

--
-- Structure de la table `operation`
--

CREATE TABLE `operation` (
  `DTYPE` varchar(1) NOT NULL,
  `numeroOperation` bigint(20) NOT NULL,
  `dateOperation` datetime(6) DEFAULT NULL,
  `montant` double NOT NULL,
  `CODE_CPTE` varchar(255) DEFAULT NULL,
  `CODE_EMP` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `operation`
--

INSERT INTO `operation` (`DTYPE`, `numeroOperation`, `dateOperation`, `montant`, `CODE_CPTE`, `CODE_EMP`) VALUES
('V', 1, '2024-11-09 17:30:07.000000', 20, '61089e12-41e6-4979-9bdd-a3f9f8aa7163', 1),
('V', 2, '2024-11-11 18:08:07.000000', 2, 'cc9329b3-8b3d-4b77-adb5-a0c06eefd661', 2),
('R', 3, '2024-11-11 18:32:22.000000', 2, 'cc9329b3-8b3d-4b77-adb5-a0c06eefd661', 1),
('V', 4, '2024-11-12 00:54:03.000000', 20, '1a8f09a0-6a37-4183-95f8-94009896ff5b', 1),
('R', 5, '2024-11-12 00:54:32.000000', 20, '1a8f09a0-6a37-4183-95f8-94009896ff5b', 1),
('R', 6, '2024-11-12 00:55:59.000000', 200, 'cc9329b3-8b3d-4b77-adb5-a0c06eefd661', 1),
('V', 7, '2024-11-12 00:55:59.000000', 200, '1a8f09a0-6a37-4183-95f8-94009896ff5b', 1),
('R', 8, '2024-11-12 10:25:44.000000', 100, '1a8f09a0-6a37-4183-95f8-94009896ff5b', 2),
('V', 9, '2024-11-12 10:25:44.000000', 100, '37fa12ee-97af-4350-9e49-a82c2fba7af8', 2);

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `client`
--
ALTER TABLE `client`
  ADD PRIMARY KEY (`codeClient`);

--
-- Index pour la table `compte`
--
ALTER TABLE `compte`
  ADD PRIMARY KEY (`codeCompte`),
  ADD KEY `FKrdyejrs6gonmbrj8ng6hbja2x` (`CODE_CLI`),
  ADD KEY `FKtlp908kqct8na9m3digl9drsu` (`CODE_EMP`);

--
-- Index pour la table `employe`
--
ALTER TABLE `employe`
  ADD PRIMARY KEY (`codeEmploye`),
  ADD KEY `FKcc9qi7b57vl1xie16v6feoml4` (`code_emp_sup`);

--
-- Index pour la table `emp_gr`
--
ALTER TABLE `emp_gr`
  ADD KEY `FKq3c1ulki9rbosnfhvngo6mf5e` (`code_groupe`),
  ADD KEY `FK1qeour7iqsblpu6rnrmvaenbf` (`code_employe`);

--
-- Index pour la table `groupe`
--
ALTER TABLE `groupe`
  ADD PRIMARY KEY (`codeGroupe`);

--
-- Index pour la table `operation`
--
ALTER TABLE `operation`
  ADD PRIMARY KEY (`numeroOperation`),
  ADD KEY `FKstqv8hj656rx4jppey6rq6mfd` (`CODE_CPTE`),
  ADD KEY `FK72qc0gb62t0rpbq9ux99ruiks` (`CODE_EMP`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `client`
--
ALTER TABLE `client`
  MODIFY `codeClient` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT pour la table `employe`
--
ALTER TABLE `employe`
  MODIFY `codeEmploye` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT pour la table `groupe`
--
ALTER TABLE `groupe`
  MODIFY `codeGroupe` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT pour la table `operation`
--
ALTER TABLE `operation`
  MODIFY `numeroOperation` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `compte`
--
ALTER TABLE `compte`
  ADD CONSTRAINT `FKrdyejrs6gonmbrj8ng6hbja2x` FOREIGN KEY (`CODE_CLI`) REFERENCES `client` (`codeClient`),
  ADD CONSTRAINT `FKtlp908kqct8na9m3digl9drsu` FOREIGN KEY (`CODE_EMP`) REFERENCES `employe` (`codeEmploye`);

--
-- Contraintes pour la table `employe`
--
ALTER TABLE `employe`
  ADD CONSTRAINT `FKcc9qi7b57vl1xie16v6feoml4` FOREIGN KEY (`code_emp_sup`) REFERENCES `employe` (`codeEmploye`);

--
-- Contraintes pour la table `emp_gr`
--
ALTER TABLE `emp_gr`
  ADD CONSTRAINT `FK1qeour7iqsblpu6rnrmvaenbf` FOREIGN KEY (`code_employe`) REFERENCES `employe` (`codeEmploye`),
  ADD CONSTRAINT `FKq3c1ulki9rbosnfhvngo6mf5e` FOREIGN KEY (`code_groupe`) REFERENCES `groupe` (`codeGroupe`);

--
-- Contraintes pour la table `operation`
--
ALTER TABLE `operation`
  ADD CONSTRAINT `FK72qc0gb62t0rpbq9ux99ruiks` FOREIGN KEY (`CODE_EMP`) REFERENCES `employe` (`codeEmploye`),
  ADD CONSTRAINT `FKstqv8hj656rx4jppey6rq6mfd` FOREIGN KEY (`CODE_CPTE`) REFERENCES `compte` (`codeCompte`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
