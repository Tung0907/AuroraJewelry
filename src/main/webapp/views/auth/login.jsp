<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Đăng nhập hệ thống</title>
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.2/css/bootstrap.min.css"/>

    <style>
        body {
            background: #f2f6fc;
            display: flex;
            align-items: center;
            justify-content: center;
            height: 100vh;
            font-family: Arial, sans-serif;
        }
        .login-box {
            width: 400px;
            background: white;
            padding: 30px;
            border-radius: 12px;
            box-shadow: 0 4px 18px rgba(0,0,0,0.1);
            text-align: center;
        }
        .btn-primary {
            width: 100%;
            margin-top: 10px;
        }
        a {
            text-decoration: none;
        }
    </style>
</head>
<body>

<div class="login-box">
    <h4 class="text-primary mb-3">ĐĂNG NHẬP HỆ THỐNG</h4>

    <form method="post" action="${pageContext.request.contextPath}/do-login">

        <div class="mb-3 text-start">
            <label>Email:</label>
            <input type="email" name="email" class="form-control" required/>
        </div>

        <div class="mb-3 text-start">
            <label>Mật khẩu:</label>
            <input type="password" name="password" class="form-control" required/>
        </div>

        <button class="btn btn-primary">Đăng nhập</button>

        <p class="mt-3 text-muted">
            Không có tài khoản? <a href="${pageContext.request.contextPath}/register">Đăng ký</a>
        </p>

        <c:if test="${not empty msg}">
            <p class="text-danger mt-2">${msg}</p>
        </c:if>

    </form>
</div>

</body>
</html>
