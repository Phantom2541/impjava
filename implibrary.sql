-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 16, 2025 at 05:27 AM
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
  `isbn` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `genre` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `qnty` int(11) NOT NULL,
  `isActive` tinyint(1) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `books`
--

INSERT INTO `books` (`id`, `title`, `author`, `publisherId`, `copyright`, `isbn`, `genre`, `qnty`, `isActive`, `created_at`, `updated_at`) VALUES
(15, 'The Great Gatsby', 'F. Scott Fitzgerald', 1, 1925, '9780743273565', 'Classic', 10, 1, NULL, NULL),
(16, '1984', 'George Orwell', 2, 1949, '9780451524935', 'Dystopian', 12, 1, NULL, NULL),
(17, 'The Catcher in the Rye', 'J.D. Salinger', 3, 1951, '9780316769488', 'Classic', 8, 1, NULL, NULL),
(18, 'Becoming', 'Michelle Obama', 4, 2018, '9781524763138', 'Memoir', 7, 1, NULL, NULL),
(19, 'The Alchemist', 'Paulo Coelho', 5, 1988, '9780061122415', 'Adventure', 15, 1, NULL, NULL),
(20, 'Sapiens', 'Yuval Noah Harari', 6, 2011, '9780062316097', 'History', 11, 1, NULL, NULL),
(21, 'The Book Thief', 'Markus Zusak', 7, 2005, '9780375842207', 'Historical Fiction', 9, 1, NULL, NULL),
(22, 'Harry Potter and the Sorcerer\'s Stone', 'J.K. Rowling', 8, 1997, '9780590353427', 'Fantasy', 20, 1, NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `borroweds`
--

CREATE TABLE `borroweds` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `bookId` bigint(20) UNSIGNED NOT NULL,
  `userId` bigint(20) UNSIGNED NOT NULL,
  `approvedId` bigint(20) UNSIGNED DEFAULT NULL,
  `approvedDate` date DEFAULT NULL,
  `borrowedDate` date DEFAULT NULL,
  `returnedDate` date DEFAULT NULL,
  `status` enum('pending','approved','denied','returned') COLLATE utf8mb4_unicode_ci DEFAULT 'pending',
  `fee` int(11) DEFAULT NULL,
  `remarks` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `borroweds`
--

INSERT INTO `borroweds` (`id`, `bookId`, `userId`, `approvedId`, `approvedDate`, `borrowedDate`, `returnedDate`, `status`, `fee`, `remarks`, `created_at`, `updated_at`) VALUES
(1, 15, 2, 1, '2025-04-01', '2025-04-02', '2025-04-09', 'returned', 0, 'Returned on time', '2025-05-13 04:10:42', '2025-05-13 04:10:42'),
(2, 16, 2, 1, '2025-04-20', '2025-04-21', '0000-00-00', 'approved', 0, 'Due next week', '2025-05-13 04:10:42', '2025-05-13 04:10:42'),
(3, 15, 2, 1, '2025-04-01', '2025-04-02', '2025-04-09', 'returned', 0, 'Returned on time', '2025-05-13 04:11:34', '2025-05-13 04:11:34'),
(4, 16, 2, 1, '2025-04-20', '2025-04-21', NULL, 'approved', 0, 'Due next week', '2025-05-13 04:11:34', '2025-05-13 04:11:34'),
(5, 17, 2, NULL, NULL, NULL, NULL, 'pending', NULL, 'Awaiting approval', '2025-05-13 04:11:34', '2025-05-13 04:11:34'),
(6, 18, 2, 1, '2025-04-18', NULL, NULL, 'denied', NULL, 'Exceeded borrow limit', '2025-05-13 04:11:34', '2025-05-13 04:11:34'),
(7, 19, 2, 1, '2025-03-01', '2025-03-02', '2025-03-20', 'returned', 30, 'Returned 5 days late', '2025-05-13 04:11:34', '2025-05-13 04:11:34'),
(8, 20, 2, 1, '2025-02-10', '2025-02-11', '2025-02-18', 'returned', 0, 'On-time return', '2025-05-13 04:11:34', '2025-05-13 04:11:34'),
(9, 21, 2, 1, '2025-05-10', '2025-05-11', NULL, 'approved', 0, 'Still reading', '2025-05-13 04:11:34', '2025-05-13 04:11:34'),
(10, 22, 2, NULL, NULL, NULL, NULL, 'pending', NULL, 'Pending librarian review', '2025-05-13 04:11:34', '2025-05-13 04:11:34'),
(11, 18, 2, 1, '2025-01-05', '2025-01-06', '2025-02-06', 'returned', 100, 'Returned 3 weeks late', '2025-05-13 04:11:34', '2025-05-13 04:11:34'),
(12, 16, 2, 1, '2025-05-01', NULL, NULL, 'denied', NULL, 'User has unpaid fees', '2025-05-13 04:11:34', '2025-05-13 04:11:34');

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
(1, 'Scribner', 'A Division of Simon & Schuster', '123 Main St, New York, NY', NULL, NULL),
(2, 'Plume', 'Penguin Publishing Group', '345 Park Ave, New York, NY', NULL, NULL),
(3, 'Little, Brown and Company', 'Hachette Book Group', '1290 Avenue of the Americas, NY', NULL, NULL),
(4, 'Crown Publishing', 'Penguin Random House', '1745 Broadway, New York, NY', NULL, NULL),
(5, 'HarperOne', 'HarperCollins Publishers', '195 Broadway, New York, NY', NULL, NULL),
(6, 'Harper', 'HarperCollins', '195 Broadway, New York, NY', NULL, NULL),
(7, 'Knopf Books', 'Random House', '1745 Broadway, New York, NY', NULL, NULL),
(8, 'Scholastic Inc.', 'Arthur A. Levine Books', '557 Broadway, New York, NY', NULL, NULL);

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
(1, 'QuinnTimber', 'librarian', 'quinntimber@gmail.com', '2005-12-24', NULL, 'password', NULL, NULL, NULL),
(2, 'Alice Santos', 'student', 'alice@example.com', '2000-03-15', NULL, 'password', NULL, NULL, NULL),
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
(16, 'Oscar Santos', 'customer', 'oscar@example.com', '2000-07-25', NULL, 'pass123', NULL, NULL, NULL),
(17, 'thom', 'user', 'thom@gmail.com', '2005-12-24', NULL, 'password', NULL, NULL, NULL),
(18, 'quinn', 'user', 'quinn@gmail.com', '1900-01-01', NULL, 'password', NULL, NULL, NULL),
(19, 'asdsddasd', 'user', 'uytr', '2000-12-12', NULL, 'Passw0rd', NULL, NULL, NULL),
(20, 'ginger123', 'user', 'ginger@gmail.com', '2009-01-01', NULL, 'Passw0rd', NULL, NULL, NULL);

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
  ADD KEY `borroweds_userid_foreign` (`userId`),
  ADD KEY `borroweds_approvedid_foreign` (`approvedId`);

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
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT for table `borroweds`
--
ALTER TABLE `borroweds`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

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
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `sales`
--
ALTER TABLE `sales`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `staffs`
--
ALTER TABLE `staffs`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

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
  ADD CONSTRAINT `borroweds_approvedid_foreign` FOREIGN KEY (`approvedId`) REFERENCES `users` (`id`) ON DELETE CASCADE,
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
