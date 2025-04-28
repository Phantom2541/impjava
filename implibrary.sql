-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 28, 2025 at 06:23 AM
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
  `publisherId` bigint(20) UNSIGNED NOT NULL,
  `copyright` int(11) NOT NULL,
  `lcn` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `section` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `price` int(11) NOT NULL,
  `isActive` tinyint(1) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `books`
--

INSERT INTO `books` (`id`, `title`, `author`, `publisherId`, `copyright`, `lcn`, `section`, `price`, `isActive`, `created_at`, `updated_at`) VALUES
(6, 'Database Systems', 'R. Elmasri', 6, 2020, 'QA76.9.D3 E45', 'Technology', 1250, 0, NULL, NULL),
(7, 'Clean Code', 'Robert C. Martin', 11, 2008, 'QA76.76.M52 M37', 'Programming', 980, 0, NULL, NULL),
(8, 'Computer Networks', 'A. Tanenbaum', 6, 2011, 'TK5105.5 T36', 'Networking', 1130, 0, NULL, NULL),
(9, 'The Pragmatic Programmer', 'Andy Hunt', 7, 1999, 'QA76.6 H86', 'Software Eng', 1150, 0, NULL, NULL),
(10, 'Operating Systems', 'W. Stallings', 6, 2018, 'QA76.76.O63 S73', 'Systems', 1350, 0, NULL, NULL),
(11, 'Data Structures', 'S. Sahni', 12, 2015, 'QA76.73.J38 S34', 'Programming', 990, 0, NULL, NULL),
(12, 'Artificial Intelligence', 'Stuart Russell', 6, 2021, 'Q335 R87', 'AI', 1580, 0, NULL, NULL),
(13, 'Intro to Algorithms', 'Cormen et al.', 8, 2009, 'QA76.6 C662', 'Algorithms', 1450, 0, NULL, NULL),
(14, 'Web Development', 'Jon Duckett', 13, 2014, 'TK5105.888 D83', 'Web Dev', 1200, 0, NULL, NULL),
(15, 'UI Design Basics', 'Steve Krug', 14, 2013, 'TK5105.888 K78', 'Design', 890, 0, NULL, NULL),
(16, 'Modern Java', 'Raoul-Gabriel Urma', 9, 2017, 'QA76.73.J38 U75', 'Programming', 1100, 0, NULL, NULL),
(17, 'Python Crash Course', 'Eric Matthes', 10, 2019, 'QA76.73.P98 M38', 'Programming', 1050, 0, NULL, NULL),
(18, 'Learning PHP', 'David Sklar', 9, 2016, 'QA76.73.P224 S55', 'Web Dev', 960, 0, NULL, NULL),
(19, 'System Analysis', 'Kendall & Kendall', 6, 2014, 'QA76.9.S88 K46', 'IS', 980, 0, NULL, NULL),
(20, 'Cybersecurity 101', 'Mark Stanislav', 13, 2020, 'TK5105.59 S73', 'Security', 1350, 0, NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `borroweds`
--

CREATE TABLE `borroweds` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `bookId` bigint(20) UNSIGNED NOT NULL,
  `userId` bigint(20) UNSIGNED NOT NULL,
  `borrowedDate` date NOT NULL,
  `returnedDate` date NOT NULL,
  `fee` int(11) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `borroweds`
--

INSERT INTO `borroweds` (`id`, `bookId`, `userId`, `borrowedDate`, `returnedDate`, `fee`, `created_at`, `updated_at`) VALUES
(1, 6, 1, '2025-04-01', '2025-04-15', 0, NULL, NULL),
(2, 7, 3, '2025-04-03', '2025-04-17', 0, NULL, NULL),
(3, 8, 5, '2025-04-05', '2025-04-20', 0, NULL, NULL),
(4, 9, 6, '2025-04-06', '2025-04-21', 0, NULL, NULL),
(5, 10, 8, '2025-04-07', '2025-04-22', 0, NULL, NULL),
(6, 11, 10, '2025-04-08', '2025-04-23', 0, NULL, NULL),
(7, 12, 11, '2025-04-09', '2025-04-24', 0, NULL, NULL),
(8, 13, 13, '2025-04-10', '2025-04-25', 0, NULL, NULL),
(9, 14, 15, '2025-04-11', '2025-04-26', 0, NULL, NULL),
(10, 15, 1, '2025-04-12', '2025-04-27', 0, NULL, NULL),
(11, 16, 3, '2025-04-13', '2025-04-28', 0, NULL, NULL),
(12, 17, 5, '2025-04-14', '2025-04-29', 0, NULL, NULL),
(13, 18, 6, '2025-04-15', '2025-04-30', 0, NULL, NULL),
(14, 19, 8, '2025-04-16', '2025-05-01', 0, NULL, NULL),
(15, 20, 10, '2025-04-17', '2025-05-02', 0, NULL, NULL);

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
(5, '2025_03_11_063547_create_publishers_table', 1),
(6, '2025_03_12_090254_create_books_table', 1),
(7, '2025_03_12_090652_create_staffs_table', 1),
(8, '2025_04_21_063222_create_sales_table', 1),
(9, '2025_04_21_063642_create_borroweds_table', 1);

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
-- Table structure for table `publishers`
--

CREATE TABLE `publishers` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `subName` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `Address` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `publishers`
--

INSERT INTO `publishers` (`id`, `name`, `subName`, `Address`, `created_at`, `updated_at`) VALUES
(6, 'Pearson', 'Global Education', 'London, UK', NULL, NULL),
(7, 'Addison-Wesley', 'Professional', 'Boston, MA', NULL, NULL),
(8, 'MIT Press', 'Academic', 'Cambridge, MA', NULL, NULL),
(9, 'O’Reilly', 'Media Inc.', 'Sebastopol, CA', NULL, NULL),
(10, 'No Starch Press', 'Tech Books', 'San Francisco, CA', NULL, NULL),
(11, 'Prentice Hall', 'Education', 'New Jersey, USA', NULL, NULL),
(12, 'Oxford Press', 'Oxford Publishing', 'Oxford, UK', NULL, NULL),
(13, 'Wiley', 'Wiley Publications', 'Hoboken, NJ', NULL, NULL),
(14, 'New Riders', 'Voices That Matter', 'Berkeley, CA', NULL, NULL),
(15, 'McGraw-Hill', 'Education', 'New York, NY', NULL, NULL),
(16, 'Cengage', 'Learning', 'Boston, MA', NULL, NULL),
(17, 'Packt Publishing', 'Learning Ltd', 'Birmingham, UK', NULL, NULL),
(18, 'Springer', 'Science+Business Media', 'Berlin, Germany', NULL, NULL),
(19, 'CRC Press', 'Taylor & Francis', 'Boca Raton, FL', NULL, NULL),
(20, 'Elsevier', 'Academic Publishing', 'Amsterdam, Netherlands', NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `sales`
--

CREATE TABLE `sales` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `staffId` bigint(20) UNSIGNED NOT NULL,
  `customerId` bigint(20) UNSIGNED NOT NULL,
  `bookId` bigint(20) UNSIGNED NOT NULL,
  `qnty` int(11) NOT NULL,
  `amount` int(11) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `sales`
--

INSERT INTO `sales` (`id`, `staffId`, `customerId`, `bookId`, `qnty`, `amount`, `created_at`, `updated_at`) VALUES
(61, 1, 1, 6, 1, 1250, NULL, NULL),
(62, 2, 3, 7, 1, 980, NULL, NULL),
(63, 3, 5, 8, 2, 2260, NULL, NULL),
(64, 4, 6, 9, 1, 1150, NULL, NULL),
(65, 5, 8, 10, 1, 1350, NULL, NULL),
(66, 6, 10, 11, 3, 2970, NULL, NULL),
(67, 1, 11, 12, 1, 1580, NULL, NULL),
(68, 2, 13, 13, 2, 2900, NULL, NULL),
(69, 3, 15, 14, 1, 1200, NULL, NULL),
(70, 4, 1, 15, 1, 890, NULL, NULL),
(71, 5, 3, 16, 1, 1100, NULL, NULL),
(72, 6, 5, 17, 1, 1050, NULL, NULL),
(73, 1, 6, 18, 1, 960, NULL, NULL),
(74, 2, 8, 19, 2, 1960, NULL, NULL),
(75, 3, 10, 20, 1, 1350, NULL, NULL),
(76, 1, 1, 6, 1, 1250, NULL, NULL),
(77, 2, 3, 7, 1, 980, NULL, NULL),
(78, 3, 5, 8, 2, 2260, NULL, NULL),
(79, 4, 6, 9, 1, 1150, NULL, NULL),
(80, 5, 8, 10, 1, 1350, NULL, NULL),
(81, 6, 10, 11, 3, 2970, NULL, NULL),
(82, 1, 11, 12, 1, 1580, NULL, NULL),
(83, 2, 13, 13, 2, 2900, NULL, NULL),
(84, 3, 15, 14, 1, 1200, NULL, NULL),
(85, 4, 1, 15, 1, 890, NULL, NULL),
(86, 5, 3, 16, 1, 1100, NULL, NULL),
(87, 6, 5, 17, 1, 1050, NULL, NULL),
(88, 1, 6, 18, 1, 960, NULL, NULL),
(89, 2, 8, 19, 2, 1960, NULL, NULL),
(90, 3, 10, 20, 1, 1350, NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `staffs`
--

CREATE TABLE `staffs` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `userId` bigint(20) UNSIGNED NOT NULL,
  `position` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `staffs`
--

INSERT INTO `staffs` (`id`, `userId`, `position`, `created_at`, `updated_at`) VALUES
(1, 2, 'Librarian', NULL, NULL),
(2, 4, 'Sales Assistant', NULL, NULL),
(3, 7, 'Archivist', NULL, NULL),
(4, 9, 'Front Desk', NULL, NULL),
(5, 12, 'Inventory Manager', NULL, NULL),
(6, 14, 'Manager', NULL, NULL),
(7, 2, 'Cataloger', NULL, NULL),
(8, 4, 'Sales Clerk', NULL, NULL),
(9, 7, 'Research Assistant', NULL, NULL),
(10, 9, 'Data Analyst', NULL, NULL),
(11, 12, 'Customer Service', NULL, NULL),
(12, 14, 'Marketing Head', NULL, NULL),
(13, 2, 'Operations', NULL, NULL),
(14, 4, 'Assistant Librarian', NULL, NULL),
(15, 7, 'Cashier', NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `role` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'user',
  `email` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `dob` date NOT NULL,
  `email_verified_at` timestamp NULL DEFAULT NULL,
  `password` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `remember_token` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `name`, `role`, `email`, `dob`, `email_verified_at`, `password`, `remember_token`, `created_at`, `updated_at`) VALUES
(1, 'QuinnTimber', 'admin', 'quinntimber@gmail.com', '2005-12-24', NULL, '$2y$10$vrlz/LG21UJRdjtL76Jdlu4yHO.1VkOXprB14Bxv4BaWhT6WsV6vm', NULL, NULL, NULL),
(2, 'Alice Santos', 'customer', 'alice@example.com', '2000-03-15', NULL, 'pass123', NULL, NULL, NULL),
(3, 'Ben Cruz', 'staff', 'ben@example.com', '1995-08-21', NULL, 'pass123', NULL, NULL, NULL),
(4, 'Clara Gomez', 'customer', 'clara@example.com', '2002-11-10', NULL, 'pass123', NULL, NULL, NULL),
(5, 'David Lee', 'staff', 'david@example.com', '1990-04-30', NULL, 'pass123', NULL, NULL, NULL),
(6, 'Ella Reyes', 'customer', 'ella@example.com', '2001-12-20', NULL, 'pass123', NULL, NULL, NULL),
(7, 'Fred Navarro', 'customer', 'fred@example.com', '1999-07-14', NULL, 'pass123', NULL, NULL, NULL),
(8, 'Grace Lim', 'staff', 'grace@example.com', '1988-09-05', NULL, 'pass123', NULL, NULL, NULL),
(9, 'Hannah Sy', 'customer', 'hannah@example.com', '2003-06-22', NULL, 'pass123', NULL, NULL, NULL),
(10, 'Ian dela Cruz', 'staff', 'ian@example.com', '1992-01-19', NULL, 'pass123', NULL, NULL, NULL),
(11, 'Julia Tan', 'customer', 'julia@example.com', '2000-08-09', NULL, 'pass123', NULL, NULL, NULL),
(12, 'Kyle Torres', 'customer', 'kyle@example.com', '1998-03-11', NULL, 'pass123', NULL, NULL, NULL),
(13, 'Lara Cruz', 'staff', 'lara@example.com', '1997-10-13', NULL, 'pass123', NULL, NULL, NULL),
(14, 'Marco Javier', 'customer', 'marco@example.com', '2004-02-27', NULL, 'pass123', NULL, NULL, NULL),
(15, 'Nina Pascual', 'staff', 'nina@example.com', '1985-05-17', NULL, 'pass123', NULL, NULL, NULL),
(16, 'Oscar Santos', 'customer', 'oscar@example.com', '2000-07-25', NULL, 'pass123', NULL, NULL, NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `books`
--
ALTER TABLE `books`
  ADD PRIMARY KEY (`id`),
  ADD KEY `books_publisherid_foreign` (`publisherId`);

--
-- Indexes for table `borroweds`
--
ALTER TABLE `borroweds`
  ADD PRIMARY KEY (`id`),
  ADD KEY `borroweds_bookid_foreign` (`bookId`),
  ADD KEY `borroweds_userid_foreign` (`userId`);

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
-- Indexes for table `publishers`
--
ALTER TABLE `publishers`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `sales`
--
ALTER TABLE `sales`
  ADD PRIMARY KEY (`id`),
  ADD KEY `sales_staffid_foreign` (`staffId`),
  ADD KEY `sales_customerid_foreign` (`customerId`),
  ADD KEY `sales_bookid_foreign` (`bookId`);

--
-- Indexes for table `staffs`
--
ALTER TABLE `staffs`
  ADD PRIMARY KEY (`id`),
  ADD KEY `staffs_userid_foreign` (`userId`);

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
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT for table `borroweds`
--
ALTER TABLE `borroweds`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `failed_jobs`
--
ALTER TABLE `failed_jobs`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `migrations`
--
ALTER TABLE `migrations`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `personal_access_tokens`
--
ALTER TABLE `personal_access_tokens`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `publishers`
--
ALTER TABLE `publishers`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT for table `sales`
--
ALTER TABLE `sales`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=91;

--
-- AUTO_INCREMENT for table `staffs`
--
ALTER TABLE `staffs`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `books`
--
ALTER TABLE `books`
  ADD CONSTRAINT `books_publisherid_foreign` FOREIGN KEY (`publisherId`) REFERENCES `publishers` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `borroweds`
--
ALTER TABLE `borroweds`
  ADD CONSTRAINT `borroweds_bookid_foreign` FOREIGN KEY (`bookId`) REFERENCES `books` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `borroweds_userid_foreign` FOREIGN KEY (`userId`) REFERENCES `users` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `sales`
--
ALTER TABLE `sales`
  ADD CONSTRAINT `sales_bookid_foreign` FOREIGN KEY (`bookId`) REFERENCES `books` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `sales_customerid_foreign` FOREIGN KEY (`customerId`) REFERENCES `users` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `sales_staffid_foreign` FOREIGN KEY (`staffId`) REFERENCES `staffs` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `staffs`
--
ALTER TABLE `staffs`
  ADD CONSTRAINT `staffs_userid_foreign` FOREIGN KEY (`userId`) REFERENCES `users` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
