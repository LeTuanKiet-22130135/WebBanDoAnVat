<%@ include file="header.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<body>
	<div class="container mt-5">
		<div class="row justify-content-center">
			<div class="col-md-6">
				<h3 class="text-center">Login</h3>
				<!-- The form action points to the default FORM authentication endpoint -->
				<form action="j_security_check" method="post">
					<div class="form-group">
						<label for="username">Username</label> <input type="text"
							name="j_username" id="username" class="form-control" required>
					</div>
					<div class="form-group">
						<label for="password">Password</label> <input type="password"
							name="j_password" id="password" class="form-control" required>
					</div>
					<button type="submit" class="btn btn-primary btn-block">Login</button>
				</form>
				<!-- Display error messages if redirected back to login -->
				<c:if test="${not empty param.error}">
					<div class="alert alert-danger mt-3">Invalid username or
						password.</div>
				</c:if>
			</div>
		</div>
	</div>
</body>
</html>
