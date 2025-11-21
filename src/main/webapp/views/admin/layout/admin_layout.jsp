<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Aurora Admin</title>
    <link href="${pageContext.request.contextPath}/assets/bootstrap.min.css" rel="stylesheet" />
    <style>
        body { background: #f5f6fa; }
        .sidebar {
            width: 240px;
            height: 100vh;
            position: fixed;
            left: 0; top: 0;
            background: #2c3e50;
            padding-top: 20px;
            color: white;
        }
        .sidebar a {
            display: block;
            padding: 12px 20px;
            color: #ecf0f1;
            text-decoration: none;
        }
        .sidebar a:hover {
            background: #34495e;
        }
        .content {
            margin-left: 260px;
            padding: 20px;
        }
        .topbar {
            background: #fff;
            padding: 10px 20px;
            margin-bottom: 20px;
            border-radius: 8px;
            box-shadow: 0 0 4px rgba(0,0,0,0.1);
        }
    </style>
</head>

<body>

<div class="sidebar">
    <h4 class="text-center mb-4">Aurora Admin</h4>
    <a href="${pageContext.request.contextPath}/admin/dashboard">Dashboard</a>
    <a href="${pageContext.request.contextPath}/admin/pos">💳 POS (Bán tại quầy)</a>
    <a href="${pageContext.request.contextPath}/admin/products">Products</a>
    <a href="${pageContext.request.contextPath}/admin/categories">Categories</a>
    <a href="${pageContext.request.contextPath}/admin/orders">Orders</a>
    <a href="${pageContext.request.contextPath}/admin/customers">Customers</a>
    <a href="${pageContext.request.contextPath}/admin/employees">Employees</a>
    <a href="${pageContext.request.contextPath}/logout">Logout</a>
</div>

<div class="content">
    <div class="topbar">
        <span>Xin chào, <b>${sessionScope.employee.fullName}</b></span>
    </div>

    <!-- NỘI DUNG TỪ FILE GỌI INCLUDE SẼ ĐƯỢC HIỂN THỊ Ở ĐÂY -->
    <jsp:include page="${contentPage}" />
</div>

</body>
</html>
