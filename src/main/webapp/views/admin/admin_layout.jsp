<meta charset="UTF-8">
<%@ page contentType="text/html;charset=UTF-8" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/bootstrap.min.css">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css">

<style>
    body {
        background: #f5f5f5;
    }
    .sidebar {
        width: 230px;
        height: 100vh;
        position: fixed;
        left: 0;
        top: 0;
        background: #212529;
        color: white;
        padding-top: 20px;
    }
    .sidebar a {
        color: #ddd;
        padding: 12px 20px;
        display: block;
        text-decoration: none;
        font-size: 16px;
    }
    .sidebar a:hover {
        background: #343a40;
        color: #fff;
    }
    .content {
        margin-left: 240px;
        padding: 20px;
    }
    .topbar {
        background: white;
        padding: 12px 20px;
        border-radius: 8px;
        margin-bottom: 20px;
    }
</style>

<div class="sidebar">
    <h4 class="text-center mb-4">Aurora POS</h4>

    <a href="${pageContext.request.contextPath}/admin/pos"><i class="bi bi-cash-stack"></i> Bán hàng</a>
    <a href="${pageContext.request.contextPath}/admin/products"><i class="bi bi-box"></i> Sản phẩm</a>
    <a href="${pageContext.request.contextPath}/admin/categories"><i class="bi bi-list-ul"></i> Danh mục</a>
    <a href="${pageContext.request.contextPath}/admin/orders"><i class="bi bi-receipt"></i> Hóa đơn</a>
    <a href="${pageContext.request.contextPath}/admin/customers"><i class="bi bi-people"></i> Khách hàng</a>
</div>

<div class="content">
</div> <!-- content -->
