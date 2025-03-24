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
						href="UserControl"> <i class="fas fa-users"></i> User Control
					</a></li>
					<li class="nav-item"><a class="nav-link text-light"
						href="ProductControl"> <i class="fas fa-boxes"></i> Product
							List Control
					</a></li>
					<li class="nav-item"><a class="nav-link text-light"
						href="AdminIndex.jsp"> <i class="fas fa-home"></i> Go to Home
					</a></li>
				</ul>
			</nav>

			<!-- Main Content -->
			<main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-md-4 py-4">
				<div class="container mt-5">
					<h1 class="text-center">User Management</h1>
					<!-- Search Form -->
					<form action="UserControl" method="get" class="mb-4" style="margin-top: 5vh">
						<div class="input-group">
							<input type="text" name="query" class="form-control"
								placeholder="Search by username..." value="${param.query}">
							<div class="input-group-append">
								<button type="submit" class="btn btn-primary">Search</button>
							</div>
						</div>
					</form>
					<div class="row">
						<!-- User List -->
						<div class="col-lg-12">
							<table class="table table-bordered table-hover">
								<thead class="thead-dark">
									<tr>
										<th>ID</th>
										<th>Username</th>
										<th>Actions</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="user" items="${users}">
										<tr>
											<td>${user.id}</td>
											<td>${user.username}</td>
											<td><a href="EditPassword?id=${user.id}"
												class="btn btn-warning btn-sm">Edit Password</a> <a
												href="UserServlet?action=delete&id=${user.id}"
												class="btn btn-sm btn-danger">Delete</a></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</main>
		</div>
	</div>

	<%@ include file="footer.jsp"%>