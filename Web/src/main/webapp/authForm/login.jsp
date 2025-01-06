<%@ include file="../header.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<body>
	<div class="row justify-content-center">
    <div class="col-md-6">
        <h3 class="text-center">Login</h3>
        <form action="j_security_check" method="post">
            <div class="form-group">
                <label for="username">Username</label>
                <input type="text" name="j_username" id="username" class="form-control" required>
            </div>
            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" name="j_password" id="password" class="form-control" required>
            </div>
            <div class="d-flex justify-content-between">
                <button type="submit" class="btn btn-primary">Login</button>
                <a href="signup" class="btn btn-secondary">Sign Up</a>
            </div>
        </form>
    </div>
</div>

<%@ include file="../footer.jsp"%>
