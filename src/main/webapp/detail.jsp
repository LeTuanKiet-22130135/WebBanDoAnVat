<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt"%>
<%@ page import="java.util.List"%>
<%@ page import="model.Product"%>
<%@ include file="WEB-INF/header.jsp"%>

<!-- Shop Detail Start -->
<div class="container-fluid pb-5">
    <div class="row px-xl-5">
        <!-- Product Image -->
        <div class="col-lg-5 mb-30">
            <div id="product-carousel" class="carousel slide" data-ride="carousel">
                <div class="carousel-inner bg-light">
                    <img class="w-100 h-100" src="${pageContext.request.contextPath}/imglocation/${product.imageUrl}" alt="${product.name}">
                </div>
                <a class="carousel-control-prev" href="#product-carousel" data-slide="prev">
                    <i class="fa fa-2x fa-angle-left text-dark"></i>
                </a>
                <a class="carousel-control-next" href="#product-carousel" data-slide="next">
                    <i class="fa fa-2x fa-angle-right text-dark"></i>
                </a>
            </div>
        </div>

        <!-- Product Details -->
        <div class="col-lg-7 h-auto mb-30">
            <div class="h-100 bg-light p-30">
                <h3>${product.name}</h3>
                <h3 class="font-weight-semi-bold mb-4">${product.price} đ</h3>
                <p class="mb-4">${product.description}</p>
                
                <!-- Quantity Selector and Add to Cart Button -->
                <div class="d-flex align-items-center mb-4 pt-2">
                    <div class="input-group quantity mr-3" style="width: 130px;">
                        <!-- Minus Button -->
                        <div class="input-group-btn">
                            <button class="btn btn-primary btn-minus" 
                                ${product.quantity <= 0 ? 'disabled' : ''}>
                                <i class="fa fa-minus"></i>
                            </button>
                        </div>
                        <!-- Quantity Input -->
                        <input type="text"
                            class="form-control bg-secondary border-0 text-center"
                            value="1" readonly>
                        <!-- Plus Button -->
                        <div class="input-group-btn">
                            <button class="btn btn-primary btn-plus" 
                                ${product.quantity <= 0 ? 'disabled' : ''}>
                                <i class="fa fa-plus"></i>
                            </button>
                        </div>
                    </div>

                    <!-- Add to Cart Button -->
                    <form method="post" action="cart">
                        <input type="hidden" name="action" value="add">
                        <input type="hidden" name="productId" value="${product.id}">
                        <button type="submit" class="btn btn-primary px-3"
                            style="${product.quantity <= 0 ? 'background-color: grey; cursor: not-allowed;' : ''}"
                            ${product.quantity <= 0 ? 'disabled' : ''}>
                            <i class="fa fa-shopping-cart mr-1"></i> <fmt:message key="button.addToCart" />
                        </button>
                    </form>
                </div>

                <!-- Out of Stock Message -->
                <c:if test="${product.quantity <= 0}">
                    <div class="text-danger font-weight-bold mt-2">
                        <p><fmt:message key="label.outOfStock" /></p>
                    </div>
                </c:if>

                <!-- Share Options -->
                <div class="d-flex pt-2">
                    <strong class="text-dark mr-2"><fmt:message key="label.share" /></strong>
                    <div class="d-inline-flex">
                        <a class="text-dark px-2" href=""><i class="fab fa-facebook-f"></i></a>
                        <a class="text-dark px-2" href=""><i class="fab fa-twitter"></i></a>
                        <a class="text-dark px-2" href=""><i class="fab fa-linkedin-in"></i></a>
                        <a class="text-dark px-2" href=""><i class="fab fa-pinterest"></i></a>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Product Description Section -->
    <div class="row px-xl-5">
        <div class="col">
            <div class="bg-light p-30">
                <div class="nav nav-tabs mb-4">
                    <a class="nav-item nav-link text-dark active" data-toggle="tab" href="#tab-pane-1"><fmt:message key="tab.description" /></a>
                </div>
                <div class="tab-content">
                    <div class="tab-pane fade show active" id="tab-pane-1">
                        <h4 class="mb-3"><fmt:message key="label.productDescription" /></h4>
                        <p>${product.description}</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- Shop Detail End -->

<%@ include file="WEB-INF/footer.jsp"%>
