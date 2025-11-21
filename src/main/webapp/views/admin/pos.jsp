<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/views/admin/_admin_header.jsp" %>

<link href="${pageContext.request.contextPath}/assets/bootstrap.min.css" rel="stylesheet"/>

<div class="container-fluid mt-2">
    <div class="row">
        <!-- Products left -->
        <div class="col-lg-8">
            <div class="row g-3">
                <c:forEach items="${products}" var="p">
                    <div class="col-md-4">
                        <div class="card h-100">
                            <img src="${pageContext.request.contextPath}/${p.firstImage}" class="card-img-top" style="height:160px;object-fit:cover" alt="${p.productName}">
                            <div class="card-body">
                                <h6 class="card-title">${p.productName}</h6>
                                <p class="mb-1"><strong>${p.price}₫</strong></p>

                                <form method="post" action="${pageContext.request.contextPath}/pos" class="d-flex gap-2 align-items-center">
                                    <input type="hidden" name="action" value="add"/>
                                    <input type="hidden" name="productId" value="${p.productId}" />
                                    <input type="number" name="qty" value="1" min="1" class="form-control form-control-sm" style="width:80px"/>
                                    <button class="btn btn-sm btn-primary">Thêm</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>

        <!-- Cart right -->
        <div class="col-lg-4">
            <div class="card">
                <div class="card-header"><h5 class="mb-0">Giỏ hàng</h5></div>
                <div class="card-body">
                    <%
                        Map<String, org.example.aurorajewelry.model.CartItem> posCart =
                                (Map<String, org.example.aurorajewelry.model.CartItem>) session.getAttribute("posCart");
                        if (posCart == null || posCart.isEmpty()) {
                    %>
                    <p class="text-muted">Giỏ hàng trống</p>
                    <%
                    } else {
                        java.math.BigDecimal total = java.math.BigDecimal.ZERO;
                    %>
                    <table class="table table-sm">
                        <thead><tr><th>Sản phẩm</th><th>SL</th><th>Giá</th><th></th></tr></thead>
                        <tbody>
                        <%
                            for (Map.Entry<String, org.example.aurorajewelry.model.CartItem> e : posCart.entrySet()) {
                                org.example.aurorajewelry.model.CartItem it = e.getValue();
                                java.math.BigDecimal line = it.getUnitPrice().multiply(new java.math.BigDecimal(it.getQuantity()));
                                total = total.add(line);
                        %>
                        <tr>
                            <td style="min-width:140px"><small><%= it.getProductName() %></small></td>
                            <td><%= it.getQuantity() %></td>
                            <td><small><%= it.getUnitPrice() %>₫</small></td>
                            <td>
                                <form method="post" action="${pageContext.request.contextPath}/pos" style="display:inline">
                                    <input type="hidden" name="action" value="remove"/>
                                    <input type="hidden" name="key" value="<%= e.getKey() %>"/>
                                    <button class="btn btn-sm btn-outline-danger">Xóa</button>
                                </form>
                            </td>
                        </tr>
                        <%
                            }
                        %>
                        </tbody>
                    </table>

                    <div class="d-flex justify-content-between align-items-center mb-2">
                        <strong>Tổng:</strong>
                        <strong><%= total %>₫</strong>
                    </div>

                    <form method="post" action="${pageContext.request.contextPath}/pos">
                        <input type="hidden" name="action" value="checkout" />
                        <div class="mb-2">
                            <label class="form-label small mb-1">Hình thức thanh toán</label>
                            <select name="paymentMethod" class="form-select form-select-sm">
                                <option value="Cash">Tiền mặt</option>
                                <option value="CardOnSwipe">Thẻ quẹt (POS)</option>
                            </select>
                        </div>
                        <button type="submit" class="btn btn-success w-100">Thanh toán</button>
                    </form>
                    <%
                        }
                    %>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="/views/admin/_admin_footer.jsp" %>
