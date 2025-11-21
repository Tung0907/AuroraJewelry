<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="container py-4">
    <h3>Admin - Quản lý sản phẩm</h3>
    <form method="post" action="${pageContext.request.contextPath}/admin/products">
        <table class="table">
            <thead><tr><th>ID</th><th>Tên</th><th>Giá</th><th></th></tr></thead>
            <tbody>
            <c:forEach var="p" items="${products}">
                <tr>
                    <td>${p.productId}</td>
                    <td>${p.productName}</td>
                    <td>${p.price}₫</td>
                    <td>
                        <form method="post" style="display:inline">
                            <input type="hidden" name="action" value="delete"/>
                            <input type="hidden" name="id" value="${p.productId}"/>
                            <button class="btn btn-sm btn-danger">Xóa</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </form>
</div>
