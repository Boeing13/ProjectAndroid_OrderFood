USE [master]
GO
/****** Object:  Database [YummyDB]    Script Date: 7/11/2019 10:38:37 PM ******/
CREATE DATABASE [YummyDB]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'YummyDB', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL14.MSSQLSERVER\MSSQL\DATA\YummyDB.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'YummyDB_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL14.MSSQLSERVER\MSSQL\DATA\YummyDB_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
GO
ALTER DATABASE [YummyDB] SET COMPATIBILITY_LEVEL = 140
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [YummyDB].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [YummyDB] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [YummyDB] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [YummyDB] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [YummyDB] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [YummyDB] SET ARITHABORT OFF 
GO
ALTER DATABASE [YummyDB] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [YummyDB] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [YummyDB] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [YummyDB] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [YummyDB] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [YummyDB] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [YummyDB] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [YummyDB] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [YummyDB] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [YummyDB] SET  DISABLE_BROKER 
GO
ALTER DATABASE [YummyDB] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [YummyDB] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [YummyDB] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [YummyDB] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [YummyDB] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [YummyDB] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [YummyDB] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [YummyDB] SET RECOVERY FULL 
GO
ALTER DATABASE [YummyDB] SET  MULTI_USER 
GO
ALTER DATABASE [YummyDB] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [YummyDB] SET DB_CHAINING OFF 
GO
ALTER DATABASE [YummyDB] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [YummyDB] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [YummyDB] SET DELAYED_DURABILITY = DISABLED 
GO
EXEC sys.sp_db_vardecimal_storage_format N'YummyDB', N'ON'
GO
ALTER DATABASE [YummyDB] SET QUERY_STORE = OFF
GO
USE [YummyDB]
GO
/****** Object:  Table [dbo].[Categories]    Script Date: 7/11/2019 10:38:38 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Categories](
	[categoryID] [int] IDENTITY(1,1) NOT NULL,
	[name] [nvarchar](500) NOT NULL,
	[image] [nvarchar](500) NOT NULL,
 CONSTRAINT [PK_Categories] PRIMARY KEY CLUSTERED 
(
	[categoryID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Food]    Script Date: 7/11/2019 10:38:38 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Food](
	[foodID] [int] IDENTITY(1,1) NOT NULL,
	[name] [nvarchar](500) NOT NULL,
	[description] [nvarchar](500) NULL,
	[image] [nvarchar](500) NOT NULL,
	[price] [float] NOT NULL,
	[categoryID] [int] NOT NULL,
 CONSTRAINT [PK_Food] PRIMARY KEY CLUSTERED 
(
	[foodID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[OrderDetails]    Script Date: 7/11/2019 10:38:38 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[OrderDetails](
	[orderID] [int] NOT NULL,
	[foodID] [int] NOT NULL,
	[quantity] [int] NULL,
	[price] [float] NULL,
 CONSTRAINT [PK_OrderDetails] PRIMARY KEY CLUSTERED 
(
	[orderID] ASC,
	[foodID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Orders]    Script Date: 7/11/2019 10:38:38 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Orders](
	[orderID] [int] IDENTITY(1,1) NOT NULL,
	[phone] [nvarchar](50) NOT NULL,
	[username] [nvarchar](50) NULL,
	[address] [nvarchar](500) NULL,
	[totalPayment] [float] NULL,
	[status] [nvarchar](50) NULL,
 CONSTRAINT [PK_Orders] PRIMARY KEY CLUSTERED 
(
	[orderID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Restaurants]    Script Date: 7/11/2019 10:38:38 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Restaurants](
	[restaurantID] [int] IDENTITY(1,1) NOT NULL,
	[name] [nvarchar](250) NOT NULL,
	[phone] [nvarchar](50) NULL,
	[address] [nvarchar](500) NULL,
 CONSTRAINT [PK_Restaurants] PRIMARY KEY CLUSTERED 
(
	[restaurantID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Users]    Script Date: 7/11/2019 10:38:38 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Users](
	[userID] [int] IDENTITY(1,1) NOT NULL,
	[phone] [nvarchar](50) NOT NULL,
	[username] [nvarchar](250) NOT NULL,
	[password] [nvarchar](50) NOT NULL,
	[isStaff] [bit] NOT NULL,
 CONSTRAINT [PK_User] PRIMARY KEY CLUSTERED 
(
	[phone] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[Categories] ON 

INSERT [dbo].[Categories] ([categoryID], [name], [image]) VALUES (1, N'Italian food', N'italianfood.jpg')
INSERT [dbo].[Categories] ([categoryID], [name], [image]) VALUES (2, N'Vietnamese food', N'vietnamesefood.jpg')
INSERT [dbo].[Categories] ([categoryID], [name], [image]) VALUES (3, N'French food', N'frenchfood.jpg')
INSERT [dbo].[Categories] ([categoryID], [name], [image]) VALUES (4, N'Chinese food', N'chinesefood.jpg')
INSERT [dbo].[Categories] ([categoryID], [name], [image]) VALUES (5, N'Japanese food', N'japanesefood.jpg')
INSERT [dbo].[Categories] ([categoryID], [name], [image]) VALUES (6, N'Steak', N'steakfood.jpg')
INSERT [dbo].[Categories] ([categoryID], [name], [image]) VALUES (7, N'Vegetarian food', N'vegetarianfood.jpg')
INSERT [dbo].[Categories] ([categoryID], [name], [image]) VALUES (8, N'Dessert', N'dessertfood.jpg')
INSERT [dbo].[Categories] ([categoryID], [name], [image]) VALUES (9, N'Drinks', N'drinks.jpg')
SET IDENTITY_INSERT [dbo].[Categories] OFF
SET IDENTITY_INSERT [dbo].[Food] ON 

INSERT [dbo].[Food] ([foodID], [name], [description], [image], [price], [categoryID]) VALUES (1, N'Pasta all Norma', N'orem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque nec eros in nisi blandit condimentum id quis sapien. Integer malesuada cursus odio nec ornare. Donec dapibus dignissim massa sit amet vulputate.', N'spaghetti1.jpg', 20, 1)
INSERT [dbo].[Food] ([foodID], [name], [description], [image], [price], [categoryID]) VALUES (3, N'Amatriciana', N'Etiam luctus tortor at erat facilisis, eget interdum risus blandit. Pellentesque felis enim, varius vel lacus at, vestibulum auctor elit. Aenean eu tellus sed elit interdum ullamcorper. ', N'spaghetti2.jpg', 25, 1)
INSERT [dbo].[Food] ([foodID], [name], [description], [image], [price], [categoryID]) VALUES (4, N'Macaroni lasagna', N'Aenean eu tellus sed elit interdum ullamcorper. Pellentesque eleifend quis lacus in imperdiet. Suspendisse non finibus velit, cursus maximus velit. Ut et lacinia nisi.', N'lasagna.jpeg', 30, 1)
INSERT [dbo].[Food] ([foodID], [name], [description], [image], [price], [categoryID]) VALUES (6, N'Skillet Lasagna', N'Vivamus quis odio nec nulla tristique tristique vel feugiat nisl. Mauris sodales purus dolor, eget rhoncus lacus iaculis a. Integer nisl ex, feugiat quis porta quis, fermentum ut nunc.', N'lasagna2.jpg', 20, 1)
INSERT [dbo].[Food] ([foodID], [name], [description], [image], [price], [categoryID]) VALUES (8, N'Nem', N'Quisque sed ullamcorper dolor. Cras vitae ante ex. Sed euismod ut ante eget cursus. Aenean ac tempus tortor. Duis urna massa, venenatis sed tellus quis, dictum tempus arcu.', N'nem.jpg', 20, 2)
INSERT [dbo].[Food] ([foodID], [name], [description], [image], [price], [categoryID]) VALUES (9, N'Phở bò', N'Vestibulum lectus arcu, consequat quis scelerisque nec, pulvinar vel lacus. Vivamus nec dictum arcu, ut scelerisque urna.', N'phobo.jpg', 25, 2)
INSERT [dbo].[Food] ([foodID], [name], [description], [image], [price], [categoryID]) VALUES (10, N'Bún bò Huế', N'Mauris eu sollicitudin eros, sed pellentesque nisi. Maecenas scelerisque elementum suscipit. Etiam et tempus orci. Maecenas vehicula ut urna a iaculis.', N'bunbohue.jpg', 30, 2)
INSERT [dbo].[Food] ([foodID], [name], [description], [image], [price], [categoryID]) VALUES (11, N'Ratatouille', N'Nam congue sollicitudin dolor, eget condimentum libero condimentum id. Cras eros enim, ultricies eget purus non, interdum condimentum erat.', N'ratatouille.jpg', 40, 3)
SET IDENTITY_INSERT [dbo].[Food] OFF
INSERT [dbo].[OrderDetails] ([orderID], [foodID], [quantity], [price]) VALUES (1, 3, 1, 25)
SET IDENTITY_INSERT [dbo].[Orders] ON 

INSERT [dbo].[Orders] ([orderID], [phone], [username], [address], [totalPayment], [status]) VALUES (1, N'0123456789', N'Nom', N'Lombard Street', 25, N'shipped')
SET IDENTITY_INSERT [dbo].[Orders] OFF
SET IDENTITY_INSERT [dbo].[Restaurants] ON 

INSERT [dbo].[Restaurants] ([restaurantID], [name], [phone], [address]) VALUES (1, N'Yummy', N'19001234', N'60 Street, New York, US')
SET IDENTITY_INSERT [dbo].[Restaurants] OFF
SET IDENTITY_INSERT [dbo].[Users] ON 

INSERT [dbo].[Users] ([userID], [phone], [username], [password], [isStaff]) VALUES (3, N'01234', N'Edward', N'01234', 0)
INSERT [dbo].[Users] ([userID], [phone], [username], [password], [isStaff]) VALUES (1, N'0123456789', N'Nom', N'Nom', 0)
INSERT [dbo].[Users] ([userID], [phone], [username], [password], [isStaff]) VALUES (4, N'1111', N'Margaery', N'1111', 0)
INSERT [dbo].[Users] ([userID], [phone], [username], [password], [isStaff]) VALUES (2, N'9999', N'Admin', N'9999', 1)
SET IDENTITY_INSERT [dbo].[Users] OFF
ALTER TABLE [dbo].[Food]  WITH CHECK ADD  CONSTRAINT [FK_Food_Categories] FOREIGN KEY([categoryID])
REFERENCES [dbo].[Categories] ([categoryID])
GO
ALTER TABLE [dbo].[Food] CHECK CONSTRAINT [FK_Food_Categories]
GO
ALTER TABLE [dbo].[OrderDetails]  WITH CHECK ADD  CONSTRAINT [FK_OrderDetails_Food] FOREIGN KEY([foodID])
REFERENCES [dbo].[Food] ([foodID])
GO
ALTER TABLE [dbo].[OrderDetails] CHECK CONSTRAINT [FK_OrderDetails_Food]
GO
ALTER TABLE [dbo].[OrderDetails]  WITH CHECK ADD  CONSTRAINT [FK_OrderDetails_Orders] FOREIGN KEY([orderID])
REFERENCES [dbo].[Orders] ([orderID])
GO
ALTER TABLE [dbo].[OrderDetails] CHECK CONSTRAINT [FK_OrderDetails_Orders]
GO
ALTER TABLE [dbo].[Orders]  WITH CHECK ADD  CONSTRAINT [FK_Orders_Users] FOREIGN KEY([phone])
REFERENCES [dbo].[Users] ([phone])
GO
ALTER TABLE [dbo].[Orders] CHECK CONSTRAINT [FK_Orders_Users]
GO
USE [master]
GO
ALTER DATABASE [YummyDB] SET  READ_WRITE 
GO
