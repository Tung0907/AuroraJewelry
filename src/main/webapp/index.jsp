<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="vi">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chào mừng đến với Aurora Jewelry</title>
    <link href="${pageContext.request.contextPath}/assets/bootstrap.min.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/assets/style.css" rel="stylesheet" />
</head>

<body>
<!-- Header with Navigation -->
<nav class="navbar navbar-expand-lg navbar-dark bg-primary fixed-top">
    <div class="container-fluid">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/index.jsp">
            <img src="${pageContext.request.contextPath}/uploads/Logo1.png" alt="Aurora Jewelry" width="100">
        </a>
        <div class="d-flex">
            <a href="${pageContext.request.contextPath}/register" class="btn btn-light mx-2">Đăng ký</a>
            <a href="${pageContext.request.contextPath}/login" class="btn btn-light">Đăng nhập</a>
            <!-- If user is logged in -->
            <span class="navbar-text ml-2" id="user-name">
                    Chào, ${sessionScope.user.fullName}
                </span>
        </div>
    </div>
</nav>

<!-- Welcome Section -->
<div class="container my-5 text-center" style="margin-top: 120px;">
    <h2 class="display-4">Chào mừng đến với Aurora Jewelry</h2>
    <p class="lead text-muted">Khám phá những bộ sưu tập trang sức tinh tế nhất từ chúng tôi.</p>
</div>

<!-- Filter and Sorting Section -->
<div class="container">
    <div class="row my-3">
        <div class="col-md-3">
            <form method="get" action="${pageContext.request.contextPath}/index.jsp">
                <div class="form-group">
                    <label for="category">Danh mục</label>
                    <select name="category" class="form-control">
                        <option value="">Tất cả</option>
                        <c:forEach items="${categories}" var="category">
                            <option value="${category.categoryID}">${category.categoryName}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label for="price">Giá từ</label>
                    <input type="number" name="priceMin" class="form-control" placeholder="Giá từ...">
                </div>

                <div class="form-group">
                    <label for="price">Đến</label>
                    <input type="number" name="priceMax" class="form-control" placeholder="Giá đến...">
                </div>

                <button type="submit" class="btn btn-primary w-100 mt-3">Áp dụng bộ lọc</button>
            </form>
        </div>

        <div class="col-md-9">
            <h3 class="text-center my-5">Sản phẩm nổi bật</h3>
            <div class="row">
                <!-- Display products dynamically here -->
                <c:forEach items="${products}" var="product">
                    <div class="col-md-4 mb-4">
                        <div class="card shadow-sm">
                            <img src="${pageContext.request.contextPath}/${product.firstImage}" class="card-img-top"
                                 alt="${product.productName}" />
                            <div class="card-body">
                                <h5 class="card-title">${product.productName}</h5>
                                <p class="card-text">${product.price}₫</p>
                                <a href="${pageContext.request.contextPath}/product-detail?id=${product.productID}"
                                   class="btn btn-primary w-100">Xem chi tiết</a>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
</div>

<!-- Footer -->
<footer class="bg-dark text-white text-center py-3 mt-5">
    <p>&copy; 2025 Aurora Jewelry. All rights reserved.</p>
</footer>

<!-- Bootstrap JS -->
<script src="${pageContext.request.contextPath}/assets/bootstrap.bundle.min.js"></script>
</body>

</html>
