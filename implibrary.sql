-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 11, 2025 at 04:55 PM
-- Server version: 10.4.24-MariaDB
-- PHP Version: 8.0.19

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `implibrary`
--

-- --------------------------------------------------------

--
-- Table structure for table `books`
--

CREATE TABLE `books` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `title` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `author` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `publisher` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `copyright` int(11) NOT NULL,
  `lcn` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `section` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `isActive` tinyint(1) NOT NULL DEFAULT 1,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `books`
--

INSERT INTO `books` (`id`, `title`, `author`, `publisher`, `copyright`, `lcn`, `section`, `isActive`, `created_at`, `updated_at`) VALUES
(1, 'sample', 'Maria cristina D. Ferrer', 'Mutya Publishing House', 2018, 'ISBN 978-971-821-808-2', 'contemp', 1, '2025-04-06 21:17:35', '2025-04-06 21:17:35'),
(2, 'The Contemporary World', 'Maria cristina D. Ferrer, John Rowell T. Obligado, Maria Ellen V. Pagtalunan', 'Mutya Publishing House', 2018, 'ISBN 978-971-821-808-2', 'contemp', 1, '2025-04-06 21:17:35', '2025-04-06 21:17:35'),
(3, 'The Contemporary World', 'Maria cristina D. Ferrer, John Rowell T. Obligado, Maria Ellen V. Pagtalunan', 'Mutya Publishing House', 2018, 'ISBN 978-971-821-808-2', 'contemp', 1, '2025-04-06 21:17:35', '2025-04-06 21:17:35'),
(4, 'The Contemporary World', 'Maria cristina D. Ferrer, John Rowell T. Obligado, Maria Ellen V. Pagtalunan', 'Mutya Publishing House', 2018, 'ISBN 978-971-821-808-2', 'contemp', 1, '2025-04-06 21:17:35', '2025-04-06 21:17:35'),
(5, 'The Contemporary World', 'Maria cristina D. Ferrer, John Rowell T. Obligado, Maria Ellen V. Pagtalunan', 'Mutya Publishing House', 2018, 'ISBN 978-971-821-808-2', 'contemp', 1, '2025-04-06 21:17:35', '2025-04-06 21:17:35'),
(6, 'The Contemporary World', 'Maria cristina D. Ferrer, John Rowell T. Obligado, Maria Ellen V. Pagtalunan', 'Mutya Publishing House', 2018, 'ISBN 978-971-821-808-2', 'contemp', 1, '2025-04-06 21:17:35', '2025-04-06 21:17:35'),
(7, 'The Contemporary World', 'Maria cristina D. Ferrer, John Rowell T. Obligado, Maria Ellen V. Pagtalunan', 'Mutya Publishing House', 2018, 'ISBN 978-971-821-808-2', 'contemp', 1, '2025-04-06 21:17:35', '2025-04-06 21:17:35'),
(8, 'The Contemporary World', 'Maria cristina D. Ferrer, John Rowell T. Obligado, Maria Ellen V. Pagtalunan', 'Mutya Publishing House', 2018, 'ISBN 978-971-821-808-2', 'contemp', 1, '2025-04-06 21:17:35', '2025-04-06 21:17:35'),
(9, 'The Contemporary World', 'Maria cristina D. Ferrer, John Rowell T. Obligado, Maria Ellen V. Pagtalunan', 'Mutya Publishing House', 2018, 'ISBN 978-971-821-808-2', 'contemp', 1, '2025-04-06 21:17:35', '2025-04-06 21:17:35'),
(10, 'The Contemporary World', 'Maria cristina D. Ferrer, John Rowell T. Obligado, Maria Ellen V. Pagtalunan', 'Mutya Publishing House', 2018, 'ISBN 978-971-821-808-2', 'contemp', 1, '2025-04-06 21:17:35', '2025-04-06 21:17:35'),
(11, 'The Contemporary World', 'Maria cristina D. Ferrer, John Rowell T. Obligado, Maria Ellen V. Pagtalunan', 'Mutya Publishing House', 2018, 'ISBN 978-971-821-808-2', 'contemp', 1, '2025-04-06 21:17:35', '2025-04-06 21:17:35'),
(12, 'The Contemporary World', 'Maria cristina D. Ferrer, John Rowell T. Obligado, Maria Ellen V. Pagtalunan', 'Mutya Publishing House', 2018, 'ISBN 978-971-821-808-2', 'contemp', 1, '2025-04-06 21:17:35', '2025-04-06 21:17:35'),
(13, 'The Contemporary World', 'Maria cristina D. Ferrer, John Rowell T. Obligado, Maria Ellen V. Pagtalunan', 'Mutya Publishing House', 2018, 'ISBN 978-971-821-808-2', 'contemp', 1, '2025-04-06 21:17:35', '2025-04-06 21:17:35'),
(14, 'The Contemporary World', 'Maria cristina D. Ferrer, John Rowell T. Obligado, Maria Ellen V. Pagtalunan', 'Mutya Publishing House', 2018, 'ISBN 978-971-821-808-2', 'contemp', 1, '2025-04-06 21:17:35', '2025-04-06 21:17:35'),
(15, 'The Contemporary World', 'Maria cristina D. Ferrer, John Rowell T. Obligado, Maria Ellen V. Pagtalunan', 'Mutya Publishing House', 2018, 'ISBN 978-971-821-808-2', 'contemp', 1, '2025-04-06 21:17:35', '2025-04-06 21:17:35'),
(16, 'The Contemporary World', 'Maria cristina D. Ferrer, John Rowell T. Obligado, Maria Ellen V. Pagtalunan', 'Mutya Publishing House', 2018, 'ISBN 978-971-821-808-2', 'contemp', 1, '2025-04-06 21:17:35', '2025-04-06 21:17:35'),
(17, 'The Contemporary World', 'Maria cristina D. Ferrer, John Rowell T. Obligado, Maria Ellen V. Pagtalunan', 'Mutya Publishing House', 2018, 'ISBN 978-971-821-808-2', 'contemp', 1, '2025-04-06 21:17:35', '2025-04-06 21:17:35'),
(18, 'The Contemporary World', 'Maria cristina D. Ferrer, John Rowell T. Obligado, Maria Ellen V. Pagtalunan', 'Mutya Publishing House', 2018, 'ISBN 978-971-821-808-2', 'contemp', 1, '2025-04-06 21:17:35', '2025-04-06 21:17:35'),
(19, 'The Contemporary World', 'Maria cristina D. Ferrer, John Rowell T. Obligado, Maria Ellen V. Pagtalunan', 'Mutya Publishing House', 2018, 'ISBN 978-971-821-808-2', 'contemp', 1, '2025-04-06 21:17:35', '2025-04-06 21:17:35'),
(20, 'The Contemporary World', 'Maria cristina D. Ferrer, John Rowell T. Obligado, Maria Ellen V. Pagtalunan', 'Mutya Publishing House', 2018, 'ISBN 978-971-821-808-2', 'contemp', 1, '2025-04-06 21:17:35', '2025-04-06 21:17:35'),
(21, 'The Contemporary World', 'Maria cristina D. Ferrer, John Rowell T. Obligado, Maria Ellen V. Pagtalunan', 'Mutya Publishing House', 2018, 'ISBN 978-971-821-808-2', 'contemp', 1, '2025-04-06 21:17:35', '2025-04-06 21:17:35'),
(22, 'The Contemporary World', 'Maria cristina D. Ferrer, John Rowell T. Obligado, Maria Ellen V. Pagtalunan', 'Mutya Publishing House', 2018, 'ISBN 978-971-821-808-2', 'contemp', 1, '2025-04-06 21:17:35', '2025-04-06 21:17:35'),
(23, 'The Contemporary World', 'Maria cristina D. Ferrer, John Rowell T. Obligado, Maria Ellen V. Pagtalunan', 'Mutya Publishing House', 2018, 'ISBN 978-971-821-808-2', 'contemp', 1, '2025-04-06 21:17:35', '2025-04-06 21:17:35'),
(24, 'The Contemporary World', 'Maria cristina D. Ferrer, John Rowell T. Obligado, Maria Ellen V. Pagtalunan', 'Mutya Publishing House', 2018, 'ISBN 978-971-821-808-2', 'contemp', 1, '2025-04-06 21:17:35', '2025-04-06 21:17:35');

-- --------------------------------------------------------

--
-- Table structure for table `failed_jobs`
--

CREATE TABLE `failed_jobs` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `uuid` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `connection` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `queue` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `payload` longtext COLLATE utf8mb4_unicode_ci NOT NULL,
  `exception` longtext COLLATE utf8mb4_unicode_ci NOT NULL,
  `failed_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `migrations`
--

CREATE TABLE `migrations` (
  `id` int(10) UNSIGNED NOT NULL,
  `migration` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `batch` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `migrations`
--

INSERT INTO `migrations` (`id`, `migration`, `batch`) VALUES
(1, '2014_10_12_000000_create_users_table', 1),
(2, '2014_10_12_100000_create_password_resets_table', 1),
(3, '2019_08_19_000000_create_failed_jobs_table', 1),
(4, '2019_12_14_000001_create_personal_access_tokens_table', 1),
(5, '2025_03_12_090254_create_books_table', 1),
(6, '2025_03_12_090652_create_staff_table', 1);

-- --------------------------------------------------------

--
-- Table structure for table `password_resets`
--

CREATE TABLE `password_resets` (
  `email` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `token` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `personal_access_tokens`
--

CREATE TABLE `personal_access_tokens` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `tokenable_type` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `tokenable_id` bigint(20) UNSIGNED NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `token` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `abilities` text COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `last_used_at` timestamp NULL DEFAULT NULL,
  `expires_at` timestamp NULL DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `staff`
--

CREATE TABLE `staff` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `role` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'user',
  `dob` date DEFAULT NULL,
  `email` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `email_verified_at` timestamp NULL DEFAULT NULL,
  `password` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `remember_token` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `name`, `role`, `dob`, `email`, `email_verified_at`, `password`, `remember_token`, `created_at`, `updated_at`) VALUES
(1, 'QuinnTimber', 'admin', '2005-12-24', 'quinntimber@gmail.com', NULL, 'password', NULL, '2025-03-11 17:34:18', '2025-03-11 17:34:18'),
(2, 'sample', 'user', '0002-03-01', 'sam@gmail.com', NULL, 'password', NULL, NULL, NULL),
(3, 'sample', 'user', '2000-12-21', 'lol@gmail.com', NULL, 'password', NULL, NULL, NULL),
(4, 'Here', 'user', '2005-12-24', 'samples@gmail.com', NULL, 'password', NULL, NULL, NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `books`
--
ALTER TABLE `books`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `failed_jobs`
--
ALTER TABLE `failed_jobs`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `failed_jobs_uuid_unique` (`uuid`);

--
-- Indexes for table `migrations`
--
ALTER TABLE `migrations`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `password_resets`
--
ALTER TABLE `password_resets`
  ADD PRIMARY KEY (`email`);

--
-- Indexes for table `personal_access_tokens`
--
ALTER TABLE `personal_access_tokens`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `personal_access_tokens_token_unique` (`token`),
  ADD KEY `personal_access_tokens_tokenable_type_tokenable_id_index` (`tokenable_type`,`tokenable_id`);

--
-- Indexes for table `staff`
--
ALTER TABLE `staff`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `users_email_unique` (`email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `books`
--
ALTER TABLE `books`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- AUTO_INCREMENT for table `failed_jobs`
--
ALTER TABLE `failed_jobs`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `migrations`
--
ALTER TABLE `migrations`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `personal_access_tokens`
--
ALTER TABLE `personal_access_tokens`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `staff`
--
ALTER TABLE `staff`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
