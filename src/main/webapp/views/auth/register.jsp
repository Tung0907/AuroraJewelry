<meta charset="UTF-8">
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Đăng ký – Aurora Jewelry</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.2/css/bootstrap.min.css" rel="stylesheet"/>
    <style>
        body {
            background: linear-gradient(135deg, #4dd0e1, #26c6da);
            display: flex;
            align-items: center;
            justify-content: center;
            height: 100vh;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }
        .register-container {
            width: 450px;
            background: #fff;
            padding: 30px 40px;
            border-radius: 15px;
            box-shadow: 0 8px 30px rgba(0,0,0,0.15);
        }
        .register-container h2 {
            color: #444;
            margin-bottom: 20px;
            text-align: center;
        }
        .btn-register {
            background: #00897b;
            color: #fff;
        }
        .btn-register:hover {
            background: #00695c;
        }
        .link-login {
            text-align: center;
            margin-top: 15px;
        }
        .link-login a {
            color: #00897b;
            text-decoration: none;
        }
        .link-login a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
<div class="register-container">
    <h2>Đăng ký tài khoản</h2>

    <c:if test="${not empty msg}">
        <div class="alert alert-danger">${msg}</div>
    </c:if>

    <form method="post" action="${pageContext.request.contextPath}/do-register">
        <div class="mb-3">
            <input name="fullName" type="text" class="form-control" placeholder="Họ và tên" required/>
        </div>
        <div class="mb-3">
            <input name="email" type="email" class="form-control" placeholder="Email" required/>
        </div>
        <div class="mb-3">
            <input name="phone" type="text" class="form-control" placeholder="Số điện thoại"/>
        </div>
        <div class="mb-3">
            <input name="password" type="password" class="form-control" placeholder="Mật khẩu" required/>
        </div>
        <button type="submit" class="btn btn-register w-100">Đăng ký</button>
    </form>

    <div class="link-login">
        Đã có tài khoản? <a href="${pageContext.request.contextPath}/login">Đăng nhập</a>
    </div>
</div>
</body>
</html>
