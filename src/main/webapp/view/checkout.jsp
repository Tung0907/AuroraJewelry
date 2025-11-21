<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
<div class="container py-4">
    <h3>Thanh toán</h3>
    <c:if test="${not empty msg}"><div class="alert alert-danger">${msg}</div></c:if>

    <form method="post" action="${pageContext.request.contextPath}/checkout">
        <div class="mb-3">
            <label>Họ tên</label>
            <input name="fullname" class="form-control" required/>
        </div>
        <div class="mb-3">
            <label>Số điện thoại</label>
            <input name="phone" class="form-control" required/>
        </div>
        <div class="mb-3">
            <label>Email</label>
            <input name="email" class="form-control" required/>
        </div>
        <div class="mb-3">
            <label>Địa chỉ</label>
            <input name="address" class="form-control" required/>
        </div>
        <button class="btn btn-primary">Xác nhận & Thanh toán</button>
    </form>
</div>
