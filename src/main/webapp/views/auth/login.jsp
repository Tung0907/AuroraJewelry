<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login – Aurora Jewelry</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.2/css/bootstrap.min.css" rel="stylesheet"/>
    <style>
        body {
            background: linear-gradient(135deg, #f06292, #ba68c8);
            display: flex;
            align-items: center;
            justify-content: center;
            height: 100vh;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }
        .login-container {
            width: 400px;
            background: #fff;
            padding: 30px 35px;
            border-radius: 15px;
            box-shadow: 0 8px 30px rgba(0,0,0,0.12);
        }
        .login-container h2 {
            color: #444;
            margin-bottom: 25px;
        }
        .btn-login {
            background: #8e24aa;
            color: #fff;
        }
        .btn-login:hover {
            background: #6a1b9a;
        }
        .link-register {
            display: block;
            margin-top: 15px;
            text-align: center;
        }
        .link-register a {
            color: #8e24aa;
            text-decoration: none;
        }
        .link-register a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
<div class="login-container">
    <h2 class="text-center">Đăng nhập hệ thống</h2>

    <form method="post" action="${pageContext.request.contextPath}/do-login">
        <div class="mb-3">
            <label for="email" class="form-label">Email</label>
            <input id="email" name="email" type="email" class="form-control" required />
        </div>
        <div class="mb-3">
            <label for="password" class="form-label">Mật khẩu</label>
            <input id="password" name="password" type="password" class="form-control" required />
        </div>
        <button type="submit" class="btn btn-login w-100">Đăng nhập</button>

        <c:if test="${not empty msg}">
            <div class="alert alert-danger mt-3">${msg}</div>
        </c:if>

        <div class="link-register">
            Bạn chưa có tài khoản? <a href="${pageContext.request.contextPath}/register">Đăng ký</a>
        </div>
    </form>
</div>
</body>
</html>
