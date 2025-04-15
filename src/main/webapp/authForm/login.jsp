<%@ include file="../WEB-INF/header.jsp"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt"%>
<body>
    <div class="row justify-content-center">
        <div class="col-md-6">
            <h3 class="text-center"><fmt:message key="title.login" /></h3>
            <form action="j_security_check" method="post">
                <div class="form-group">
                    <label for="username"><fmt:message key="label.username" /></label>
                    <input type="text" name="j_username" id="username" class="form-control" required>
                </div>
                <div class="form-group">
                    <label for="password"><fmt:message key="label.password" /></label>
                    <input type="password" name="j_password" id="password" class="form-control" required>
                </div>
                <div class="d-flex justify-content-between">
                    <button type="submit" class="btn btn-primary"><fmt:message key="button.login" /></button>
                    <a href="signup" class="btn btn-secondary"><fmt:message key="button.signup" /></a>
                </div>
            </form>
        </div>
    </div>

<%@ include file="../WEB-INF/footer.jsp"%>
