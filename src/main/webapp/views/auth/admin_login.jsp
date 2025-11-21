
<meta charset="UTF-8">
<!doctype html>
<html lang="vi">
<head>
    <meta charset="UTF-8"/>
    <title>Admin - Đăng nhập</title>
    <link href="${pageContext.request.contextPath}/assets/bootstrap.min.css" rel="stylesheet"/>
</head>
<body>
<div class="card">
    <div class="text-center mb-3">
        <div class="brand">Aurora Jewelry — Admin</div>
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
