<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>Admin - Đăng ký</title>
    <link href="${pageContext.request.contextPath}/assets/bootstrap.min.css" rel="stylesheet"/>
    <style>
        body { background:#f4f7fb; display:flex; align-items:center; justify-content:center; height:100vh; }
        .card { width:480px; padding:22px; background:#fff; border-radius:10px; box-shadow:0 6px 20px rgba(0,0,0,0.05); }
    </style>
</head>
<body>
<div class="card">
    <h4>Đăng ký nhân viên</h4>

    <c:if test="${not empty msg}">
        <div class="alert alert-danger">${msg}</div>
    </c:if>

    <form method="post" action="${pageContext.request.contextPath}/admin/do-register">
        <div class="mb-2"><input name="fullName" class="form-control" placeholder="Họ và tên" required/></div>
        <div class="mb-2"><input name="email" type="email" class="form-control" placeholder="Email" required/></div>
        <div class="mb-2">
            <select name="role" class="form-select">
                <option value="Staff">Nhân viên (Staff)</option>
                <option value="Admin">Quản trị viên (Admin)</option>
            </select>
        </div>
        <div class="mb-3"><input name="password" type="password" class="form-control" placeholder="Mật khẩu" required/></div>
        <button class="btn btn-primary w-100" type="submit">Tạo tài khoản</button>
    </form>
</div>
</body>
</html>
