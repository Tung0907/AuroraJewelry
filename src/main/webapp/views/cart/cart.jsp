<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.Map" %>
<%@ page import="org.example.aurorajewelry.model.CartItem" %>
<link href="${pageContext.request.contextPath}/assets/bootstrap.min.css" rel="stylesheet" />
<div class="container mt-4">
    <h3>Giỏ hàng</h3>
    <%
        Map<String, CartItem> cart = (Map<String, CartItem>) session.getAttribute("cart");
        if (cart == null || cart.isEmpty()) {
    %>
    <div class="alert alert-info">Giỏ hàng trống</div>
    <% } else { %>
    <table class="table">
        <thead><tr><th>Ảnh</th><th>Sản phẩm</th><th>SL</th><th>Đơn giá</th><th>Thành tiền</th><th></th></tr></thead>
        <tbody>
        <%
            java.math.BigDecimal total = java.math.BigDecimal.ZERO;
            for (Map.Entry<String, CartItem> e : cart.entrySet()) {
                CartItem it = e.getValue();
                java.math.BigDecimal line = it.getUnitPrice().multiply(new java.math.BigDecimal(it.getQuantity()));
                total = total.add(line);
        %>
        <tr>
            <td><img src="${pageContext.request.contextPath}/${it.getImageUrl()}" style="width:60px;height:60px;object-fit:cover" /></td>
            <td><%= it.getProductName() %></td>
            <td><%= it.getQuantity() %></td>
            <td><%= it.getUnitPrice() %>₫</td>
            <td><%= line %>₫</td>
            <td>
                <form method="post" action="${pageContext.request.contextPath}/cart/remove">
                    <input type="hidden" name="key" value="<%= e.getKey() %>" />
                    <button class="btn btn-sm btn-danger">Xóa</button>
                </form>
            </td>
        </tr>
        <% } %>
        </tbody>
    </table>
    <div class="d-flex justify-content-between align-items-center">
        <h5>Tổng: <strong><%= total %>₫</strong></h5>
        <a href="${pageContext.request.contextPath}/checkout" class="btn btn-success">Thanh toán</a>
    </div>
    <% } %>
</div>
