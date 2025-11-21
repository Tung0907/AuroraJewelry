<meta charset="UTF-8">
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.example.aurorajewelry.dao.OrderDetailDAO,org.example.aurorajewelry.dao.OrderDAO,org.example.aurorajewelry.model.Order" %>
<%@ include file="/views/admin/_admin_header.jsp" %>

<div class="container mt-4">
    <div class="card">
        <div class="card-body">
            <h4>Thanh toán thành công</h4>
            <p>Mã đơn hàng: <strong>${orderId}</strong></p>
            <p>In hóa đơn và trả đồ cho khách</p>
            <a href="${pageContext.request.contextPath}/pos" class="btn btn-primary">Quay về POS</a>
        </div>
    </div>
</div>

<%@ include file="/views/admin/_admin_footer.jsp" %>
