<meta charset="UTF-8">
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>Đăng ký khách hàng</title>
    <link href="${pageContext.request.contextPath}/assets/bootstrap.min.css" rel="stylesheet"/>
    <style>
        body { background: #f5f8fb; display:flex; align-items:center; justify-content:center; height:100vh; }
        .card { width:420px; padding:22px; background:#fff; border-radius:10px; box-shadow:0 8px 20px rgba(0,0,0,0.04); }
    </style>
</head>
<body>
<div class="card">
    <h4 class="mb-3">Tạo tài khoản</h4>

    <c:if test="${not empty msg}"><div class="alert alert-danger">${msg}</div></c:if>

    <form method="post" action="${pageContext.request.contextPath}/do-register">
        <div class="mb-2"><input name="fullName" class="form-control" placeholder="Họ và tên" required/></div>
        <div class="mb-2"><input name="email" type="email" class="form-control" placeholder="Email" required/></div>
        <div class="mb-2"><input name="phone" class="form-control" placeholder="Số điện thoại"/></div>
        <div class="mb-3"><input name="password" type="password" class="form-control" placeholder="Mật khẩu" required/></div>
        <button class="btn btn-primary w-100">Đăng ký</button>
    </form>
</div>
</body>
</html>
