<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="header.jsp"%>

<div class="container mt-5">
	<div class="row justify-content-center">
		<div class="col-md-6">
			<h2 class="text-center">Verify Your Email</h2>
			<form action="verify" method="post">
				<!-- Show error message if verification fails -->
				<c:if test="${not empty errorMessage}">
					<div class="alert alert-danger">${errorMessage}</div>
				</c:if>

				<div class="form-group">
					<label for="verificationCode">Enter Verification Code</label> <input
						type="text" class="form-control" id="verificationCode"
						name="verificationCode" required>
				</div>

				<!-- Google reCAPTCHA -->
				<div class="form-group">
					<div class="g-recaptcha"
						data-sitekey="6LeIxAcTAAAAAJcZVRqyHh71UMIEGNQ_MXjiZKhI"
						data-callback="enableVerifyButton"></div>
					</div>

				<button type="submit" id="verifyButton" class="btn btn-primary btn-block" disabled>Verify</button>
			</form>
		</div>
	</div>
</div>

<!-- Load reCAPTCHA script -->
<script src="https://www.google.com/recaptcha/api.js" async defer></script>

<script>
    // This function will be called by reCAPTCHA once the user completes it
    function enableVerifyButton() {
        document.getElementById('verifyButton').disabled = false;
    }
</script>

<%@ include file="footer.jsp"%>
