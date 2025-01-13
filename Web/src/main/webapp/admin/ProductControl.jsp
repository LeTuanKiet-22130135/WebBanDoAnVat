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
						href="ProductServlet"> <i class="fas fa-boxes"></i> Product
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
					<h1 class="text-center">Product Management</h1>
					<div class="row">
						<!-- Add Product Link -->
						<div class="col-lg-12 mb-4 text-right">
							<a href="AddProductServlet" class="btn btn-success">Add New
								Product</a>
						</div>
						<!-- Product List -->
						<div class="col-lg-12">
							<!-- Search Form -->
							<form action="ProductControl" method="get" class="mb-4">
								<div class="input-group">
									<input type="text" name="query" class="form-control"
										placeholder="Search by product name..." value="${param.query}">
									<div class="input-group-append">
										<button type="submit" class="btn btn-primary">Search</button>
									</div>
								</div>
							</form>
							<table class="table table-bordered table-hover">
								<thead class="thead-dark">
									<tr>
										<th>ID</th>
										<th>Image</th>
										<th>Name</th>
										<th>Price</th>
										<th>Quantity</th>
										<th>Actions</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="product" items="${products}">
										<tr>
											<td>${product.id}</td>
											<td><img
												src="${pageContext.request.contextPath}/${product.imageUrl}"
												alt="${product.name}" class="img-thumbnail"
												style="max-width: 75px; max-height: 75px;"></td>
											<td>${product.name}</td>
											<td>${product.price}</td>
											<td>${product.quantity}</td>
											<td><a href="UpdateProductServlet?id=${product.id}"
												class="btn btn-sm btn-primary">Edit</a> <a
												href="ProductControl?action=delete&id=${product.id}"
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
</body>
