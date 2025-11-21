<meta charset="UTF-8">
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Trang chủ - Aurora Jewelry</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/assets/css/style.css" rel="stylesheet" />
</head>
<body>
<!-- Header with Navigation -->
<nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
    <div class="container-fluid">
        <a class="navbar-brand" href="#">Aurora Jewelry</a>
        <div class="d-flex">
            <a href="${pageContext.request.contextPath}/admin/login" class="btn btn-light mx-2">Đăng nhập</a>
            <a href="${pageContext.request.contextPath}/admin/register" class="btn btn-light">Đăng ký</a>
        </div>
    </div>
</nav>

<!-- Banner -->
<div class="container my-5 text-center">
    <h2 class="display-4">Chào mừng đến với Aurora Jewelry</h2>
    <p class="lead">Khám phá những bộ sưu tập trang sức tinh tế nhất từ chúng tôi.</p>
</div>

<!-- Product Filter Section -->
<div class="container my-4">
    <h3 class="text-center">Lọc sản phẩm</h3>
    <form action="${pageContext.request.contextPath}/product" method="get">
        <div class="row">
            <div class="col-md-4">
                <select name="category" class="form-control">
                    <option value="">Tất cả danh mục</option>
                    <c:forEach items="${categories}" var="category">
                        <option value="${category.id}">${category.name}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-md-4">
                <input type="number" name="minPrice" class="form-control" placeholder="Giá tối thiểu">
            </div>
            <div class="col-md-4">
                <input type="number" name="maxPrice" class="form-control" placeholder="Giá tối đa">
            </div>
            <div class="col-12 text-center mt-3">
                <button type="submit" class="btn btn-primary">Lọc sản phẩm</button>
            </div>
        </div>
    </form>
</div>

<!-- Featured Products -->
<div class="container my-5">
    <h3 class="text-center">Sản phẩm nổi bật</h3>
    <div class="row">
        <c:forEach items="${products}" var="product">
            <div class="col-md-4 mb-4">
                <div class="card">
                    <img src="${pageContext.request.contextPath}/${product.firstImage}" class="card-img-top" alt="${product.productName}">
                    <div class="card-body">
                        <h5 class="card-title">${product.productName}</h5>
                        <p class="card-text">${product.price}₫</p>
                        <a href="${pageContext.request.contextPath}/product-detail?id=${product.productId}" class="btn btn-primary">Xem chi tiết</a>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

<!-- Footer -->
<footer class="bg-dark text-white text-center py-3">
    <p>&copy; 2025 Aurora Jewelry. Tất cả quyền lợi được bảo lưu.</p>
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
