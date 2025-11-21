<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/views/layout/header.jsp" %>  <!-- Sửa lại đường dẫn -->
<div class="container">
    <h2>Chào mừng ${customer.fullName}</h2>
    <div class="row">
        <c:forEach var="product" items="${products}">
            <div class="col-md-4">
                <div class="card">
                    <img src="${pageContext.request.contextPath}/${product.imageUrl}" class="card-img-top" alt="${product.name}" style="height:160px;object-fit:cover">
                    <div class="card-body">
                        <h5 class="card-title">${product.name}</h5>
                        <p class="card-text">${product.price}₫</p>
                        <a href="${pageContext.request.contextPath}/product/${product.id}" class="btn btn-primary">Xem chi tiết</a>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
<%@ include file="/views/layout/header.jsp" %>


