<%@ include file="header.jsp"%>
<body>
	<div class="container mt-5">
		<div class="row justify-content-center">
			<div class="col-md-6">
				<h3 class="text-center">Sign Up</h3>
				<form action="signup" method="post">
					<div class="form-group">
						<label for="username">Username</label> <input type="text"
							name="username" id="username" class="form-control" required>
					</div>
					<div class="form-group">
						<label for="password">Password</label> <input type="password"
							name="password" id="password" class="form-control" required>
					</div>
					<div class="form-group">
						<label for="repeatPassword">Repeat Password</label> <input
							type="password" name="repeatPassword" id="repeatPassword"
							class="form-control" required>
					</div>
					<div class="form-group">
						<label for="firstName">First Name</label> <input type="text"
							name="firstName" id="firstName" class="form-control" required>
					</div>
					<div class="form-group">
						<label for="lastName">Last Name</label> <input type="text"
							name="lastName" id="lastName" class="form-control" required>
					</div>
					<div class="form-group">
						<label for="email">Email</label> <input type="email" name="email"
							id="email" class="form-control" required>
					</div>
					<button type="submit" class="btn btn-primary btn-block">Sign
						Up</button>
				</form>

				<c:if test="${not empty errorMessage}">
					<div class="alert alert-danger mt-3">${errorMessage}</div>
				</c:if>

			</div>
		</div>
	</div>
	<%@ include file="footer.jsp"%>