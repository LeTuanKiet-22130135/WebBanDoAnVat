<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="header.jsp" %>

<div class="container mt-5">
    <h1 class="text-center">Profile</h1>
    <form action="profile" method="post" onsubmit="return validateForm()">
        <div class="bg-light p-30 mb-5">
            <div class="row">
                <div class="col-md-6 form-group">
                    <label>First Name</label>
                    <input class="form-control" type="text" name="firstName" value="${profile.firstName}" required>
                </div>
                <div class="col-md-6 form-group">
                    <label>Last Name</label>
                    <input class="form-control" type="text" name="lastName" value="${profile.lastName}" required>
                </div>
                <div class="col-md-6 form-group">
                    <label>E-mail</label>
                    <input class="form-control" type="email" name="email" value="${profile.email}" required>
                </div>
                <div class="col-md-6 form-group">
                    <label>Mobile No</label>
                    <input class="form-control" type="text" name="mobileNo" value="${profile.mobileNo}">
                </div>
                <div class="col-md-6 form-group">
                    <label>Address Line 1</label>
                    <input class="form-control" type="text" name="addressLine1" value="${profile.addressLine1}">
                </div>
                <div class="col-md-6 form-group">
                    <label>Address Line 2</label>
                    <input class="form-control" type="text" name="addressLine2" value="${profile.addressLine2}">
                </div>
                <div class="col-md-6 form-group">
                    <label>Country</label>
                    <input class="form-control" type="text" name="country" value="${profile.country}">
                </div>
                <div class="col-md-6 form-group">
                    <label>City</label>
                    <input class="form-control" type="text" name="city" value="${profile.city}">
                </div>
                <div class="col-md-6 form-group">
                    <label>State</label>
                    <input class="form-control" type="text" name="state" value="${profile.state}">
                </div>
                <div class="col-md-6 form-group">
                    <label>ZIP Code</label>
                    <input class="form-control" type="text" name="zipCode" value="${profile.zipCode}">
                </div>
            </div>
        </div>
        <button type="submit" class="btn btn-primary">Update Profile</button>
    </form>
</div>