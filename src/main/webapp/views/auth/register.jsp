<%@ page contentType="text/html;charset=UTF-8" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8" />
    <title>Đăng ký nhân viên</title>
    <link href="${pageContext.request.contextPath}/assets/bootstrap.min.css" rel="stylesheet"/>
    <style>
        body { background: #f3f5f7; min-height:100vh; display:flex; align-items:center; justify-content:center; }
        .card { width:420px; padding:24px; border-radius:10px; background:white; box-shadow: 0 6px 20px rgba(0,0,0,.06); }
    </style>
</head>
<body>
<div class="card">
    <h4>Đăng ký nhân viên</h4>
    <form action="${pageContext.request.contextPath}/do-register" method="post">
        <div class="mb-2"><input name="fullName" class="form-control" placeholder="Họ và tên" required></div>
        <div class="mb-2"><input name="email" type="email" class="form-control" placeholder="Email" required></div>
        <div class="mb-2"><input name="password" type="password" class="form-control" placeholder="Mật khẩu" required></div>
        <div class="mb-2">
            <select name="role" class="form-select">
                <option value="Staff">Nhân viên (POS)</option>
                <option value="Admin">Quản trị (Admin)</option>
            </select>
        </div>
        <button class="btn btn-primary w-100">Đăng ký</button>
    </form>
</div>
</body>
</html>
