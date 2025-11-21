<meta charset="UTF-8">
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>Admin - Đăng nhập</title>
    <link href="${pageContext.request.contextPath}/assets/bootstrap.min.css" rel="stylesheet"/>
    <style>
        body { background: linear-gradient(135deg,#f6f9ff,#eef6ff); height:100vh; display:flex; align-items:center; justify-content:center; }
        .card { width:420px; border-radius:12px; box-shadow:0 8px 26px rgba(20,30,60,0.08); padding:24px; background:white; }
        .brand { font-weight:700; color:#0d6efd; letter-spacing:1px; }
        .small-muted { color:#6c757d; font-size:0.9rem; }
    </style>
</head>
<body>
<div class="card">
    <div class="text-center mb-3">
        <div class="brand">Aurora Jewelry — Admin</div>
        <small class="small-muted">Quản trị viên / Nhân viên bán hàng</small>
    </div>

    <form method="post" action="${pageContext.request.contextPath}/admin/do-login">
        <div class="mb-2">
            <label class="form-label">Email</label>
            <input type="email" name="email" class="form-control" required />
        </div>
        <div class="mb-3">
            <label class="form-label">Mật khẩu</label>
            <input type="password" name="password" class="form-control" required />
        </div>
        <button class="btn btn-primary w-100">Đăng nhập</button>
    </form>

    <c:if test="${not empty msg}">
        <div class="alert alert-danger mt-3">${msg}</div>
    </c:if>

    <div class="mt-3 d-flex justify-content-between">
        <a href="${pageContext.request.contextPath}/admin/register">Tạo tài khoản nhân viên</a>
        <a href="${pageContext.request.contextPath}/">Về trang chủ</a>
    </div>
</div>
</body>
</html>
