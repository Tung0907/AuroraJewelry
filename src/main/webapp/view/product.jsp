<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
<div class="container py-5">
    <h2 class="mb-4">Sản phẩm</h2>
    <div class="row">
        <c:forEach var="p" items="${products}">
            <div class="col-md-4 mb-4">
                <div class="card h-100 shadow-sm">
                    <c:set var="img" value="${empty p.images ? '/assets/images/default.png' : p.images[0]}"/>
                    <img src="${pageContext.request.contextPath}/${img}" class="card-img-top" style="height:280px; object-fit:cover;" alt="${p.productName}">
                    <div class="card-body d-flex flex-column">
                        <h5 class="card-title">${p.productName}</h5>
                        <p class="card-text text-muted small">${p.material} • ${p.weight}g</p>
                        <p class="mt-auto fw-bold">${p.price}₫</p>
                        <a href="${pageContext.request.contextPath}/product-detail?id=${p.productId}" class="btn btn-outline-primary btn-sm mt-2">Xem chi tiết</a>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
