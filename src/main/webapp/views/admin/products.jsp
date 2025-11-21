<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="/views/admin/_admin_head.jsp" />

<h2>Quản lý Sản phẩm</h2>

<table class="table table-bordered table-striped">
    <thead>
    <tr>
        <th>ID</th><th>Tên</th><th>Giá</th><th>Danh mục</th><th>Ảnh</th><th>Hành động</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${products}" var="p">
        <tr>
            <td>${p.productId}</td>
            <td>${p.productName}</td>
            <td>${p.price}₫</td>
            <td>${p.categoryName}</td>
            <td>
                <c:choose>
                    <c:when test="${not empty p.images}">
                        <img src="${pageContext.request.contextPath}/${p.images[0]}" style="height:40px" />
                    </c:when>
                    <c:otherwise>
                        <img src="${pageContext.request.contextPath}/assets/images/default.png" style="height:40px" />
                    </c:otherwise>
                </c:choose>
            </td>
            <td>
                <a class="btn btn-sm btn-primary" href="${pageContext.request.contextPath}/admin/product/images?productId=${p.productId}">Upload ảnh</a>
                <a class="btn btn-sm btn-warning" href="${pageContext.request.contextPath}/admin/product/edit?productId=${p.productId}">Sửa</a>
                <form style="display:inline" method="post" action="${pageContext.request.contextPath}/admin/products">
                    <input type="hidden" name="action" value="delete" />
                    <input type="hidden" name="id" value="${p.productId}" />
                    <button class="btn btn-sm btn-danger" onclick="return confirm('Xác nhận xóa?')">Xóa</button>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<jsp:include page="/views/admin/_admin_foot.jsp" />
