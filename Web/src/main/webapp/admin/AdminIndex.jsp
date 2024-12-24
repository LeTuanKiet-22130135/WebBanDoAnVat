<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="header.jsp"%>

<body>
	<div class="container-fluid p-0">
		<div class="row no-gutters">
			<!-- Sidebar Menu -->
			<nav class="col-md-3 col-lg-2 bg-dark text-light sidebar py-5">
				<ul class="nav flex-column">
					<li class="nav-item"><a class="nav-link text-light"
						href="UserControl"> <i class="fas fa-users"></i> User
							Control
					</a></li>
					<li class="nav-item"><a class="nav-link text-light"
						href="ProductControl"> <i class="fas fa-boxes"></i>
							Product List Control
					</a></li>
					<li class="nav-item"><a class="nav-link text-light"
						href="../index.jsp"> <i class="fas fa-home"></i> Go to Home
					</a></li>
				</ul>
			</nav>

			<!-- Main Content -->
			<main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-md-4 py-4">
				<h1 class="h2 mb-4">Admin Dashboard</h1>
				<p class="lead">Welcome to the Admin Dashboard. Use the menu to
					manage users or products.</p>

				<div class="row">
					<!-- User Control Section -->
					<div class="col-md-6 mb-4">
						<div class="card shadow-sm">
							<div class="card-header bg-primary text-white">
								<h5 class="mb-0">User Control</h5>
							</div>
							<div class="card-body">
								<p>Manage registered users, update their roles, or remove
									accounts.</p>
								<a href="userControl.jsp" class="btn btn-primary">Go to User
									Control</a>
							</div>
						</div>
					</div>

					<!-- Product List Control Section -->
					<div class="col-md-6 mb-4">
						<div class="card shadow-sm">
							<div class="card-header bg-success text-white">
								<h5 class="mb-0">Product List Control</h5>
							</div>
							<div class="card-body">
								<p>View, add, update, or delete products in your catalog.</p>
								<a href="ProductControl" class="btn btn-success">Go
									to Product List Control</a>
							</div>
						</div>
					</div>

					<!-- Product List Control Section -->
					<div class="col-md-6 mb-4">
						<div class="card shadow-sm">
							<div class="card-header bg-danger text-white">
								<h5 class="mb-0">Product List Control</h5>
							</div>
							<div class="card-body">
								<p>View, add, update, or delete products in your catalog.</p>
								<a href="productListControl.jsp" class="btn btn-danger">Go
									to Product List Control</a>
							</div>
						</div>
					</div>

				</div>
			</main>	
		</div>
	</div>

	<%@ include file="footer.jsp"%>