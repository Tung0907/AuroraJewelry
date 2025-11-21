<meta charset="UTF-8">
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>Đăng nhập khách hàng</title>
    <link href="${pageContext.request.contextPath}/assets/bootstrap.min.css" rel="stylesheet"/>
    <style>
        body { background: linear-gradient(180deg,#fff,#f7fbff); min-height:100vh; display:flex; align-items:center; justify-content:center; }
        .box { width:420px; padding:26px; background:white; border-radius:12px; box-shadow:0 8px 30px rgba(0,0,0,0.06); }
        .btn-primary { background:#0d6efd; border:none; }
    </style>
</head>
<body>
<div class="box">
    <h4 class="mb-3 text-center">Đăng nhập</h4>
    <form method="post" action="${pageContext.request.contextPath}/customer/do-login">
        <div class="mb-2"><input name="email" type="email" class="form-control" placeholder="Email" required/></div>
        <div class="mb-3"><input name="password" type="password" class="form-control" placeholder="Mật khẩu" required/></div>
        <button class="btn btn-primary w-100">Đăng nhập</button>
    </form>

    <div class="mt-3 text-center small-muted">
        Chưa có tài khoản? <a href="${pageContext.request.contextPath}/register">Đăng ký</a>
    </div>

    <c:if test="${not empty msg}">
        <div class="alert alert-danger mt-3">${msg}</div>
    </c:if>
</div>
</body>
</html>
