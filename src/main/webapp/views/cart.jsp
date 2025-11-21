<meta charset="UTF-8">
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
<div class="container py-4">
  <h3>Giỏ hàng</h3>
  <c:choose>
    <c:when test="${empty sessionScope.cart}">
      <p>Giỏ hàng trống. <a href="${pageContext.request.contextPath}/product">Tiếp tục mua sắm</a></p>
    </c:when>
    <c:otherwise>
      <table class="table">
        <thead>
        <tr><th>Ảnh</th><th>Sản phẩm</th><th>Size</th><th>Giá</th><th>Số lượng</th><th>Thành tiền</th><th></th></tr>
        </thead>
        <tbody>
        <c:forEach var="entry" items="${sessionScope.cart}">
          <c:set var="key" value="${entry.key}"/>
          <c:set var="item" value="${entry.value}"/>
          <tr>
            <td><img src="${pageContext.request.contextPath}/${item.imageUrl}" style="height:60px;"/></td>
            <td>${item.productName}</td>
            <td>${item.variantSize}</td>
            <td>${item.unitPrice}₫</td>
            <td>
              <form method="post" action="${pageContext.request.contextPath}/cart" class="d-inline">
                <input type="hidden" name="action" value="update"/>
                <input type="hidden" name="key" value="${key}"/>
                <input type="number" name="qty" value="${item.quantity}" min="0" style="width:80px"/>
                <button class="btn btn-sm btn-secondary">Cập nhật</button>
              </form>
            </td>
            <td>${item.total}₫</td>
            <td>
              <form method="post" action="${pageContext.request.contextPath}/cart">
                <input type="hidden" name="action" value="remove"/>
                <input type="hidden" name="key" value="${key}"/>
                <button class="btn btn-sm btn-danger">Xóa</button>
              </form>
            </td>
          </tr>
        </c:forEach>
        </tbody>
      </table>

      <div class="text-end">
        <a class="btn btn-success" href="${pageContext.request.contextPath}/checkout">Thanh toán</a>
      </div>
    </c:otherwise>
  </c:choose>
</div>
