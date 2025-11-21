<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
<div class="banner text-white d-flex align-items-center justify-content-center" style="height:60vh; background:url('${pageContext.request.contextPath}/assets/images/banner3.png') center/cover no-repeat;">
    <h1 class="display-4 fw-bold">Bộ sưu tập Xuân 2025</h1>
</div>

<div class="container py-5">
    <div class="row mb-4">
        <div class="col-md-3">
            <h5>Lọc sản phẩm</h5>
            <form method="get" action="${pageContext.request.contextPath}/product">
                <div class="mb-3">
                    <label>Danh mục</label>
                    <select name="category" class="form-select">
                        <option value="">Tất cả</option>
                        <c:forEach var="c" items="${categories}">
                            <option value="${c.id}" ${c.id == param.category ? 'selected' : ''}>${c.name}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="mb-3">
                    <label>Chất liệu</label>
                    <input type="text" name="material" value="${param.material}" class="form-control" placeholder="Ví dụ: Vàng">
                </div>
                <div class="mb-3">
                    <label>Giới tính</label>
                    <select name="gender" class="form-select">
                        <option value="">Tất cả</option>
                        <option value="Nữ" ${param.gender=='Nữ' ? 'selected' : ''}>Nữ</option>
                        <option value="Nam" ${param.gender=='Nam' ? 'selected' : ''}>Nam</option>
                        <option value="Unisex" ${param.gender=='Unisex' ? 'selected' : ''}>Unisex</option>
                    </select>
                </div>
                <div class="mb-3">
                    <label>Giá từ</label><input type="number" name="minPrice" value="${param.minPrice}" class="form-control">
                </div>
                <div class="mb-3">
                    <label>đến</label><input type="number" name="maxPrice" value="${param.maxPrice}" class="form-control">
                </div>
                <div class="mb-3">
                    <label>Sắp xếp</label>
                    <select name="sort" class="form-select">
                        <option value="">Mặc định</option>
                        <option value="price_asc" ${param.sort=='price_asc' ? 'selected':''}>Giá: Thấp → Cao</option>
                        <option value="price_desc" ${param.sort=='price_desc' ? 'selected':''}>Giá: Cao → Thấp</option>
                        <option value="name_asc" ${param.sort=='name_asc' ? 'selected':''}>Tên A → Z</option>
                        <option value="name_desc" ${param.sort=='name_desc' ? 'selected':''}>Tên Z → A</option>
                        <option value="newest" ${param.sort=='newest' ? 'selected':''}>Mới nhất</option>
                    </select>
                </div>
                <button class="btn btn-primary w-100">Áp dụng</button>
            </form>
        </div>

        <div class="col-md-9">
            <div class="row g-4">
                <c:forEach var="p" items="${products}">
                    <div class="col-sm-6 col-lg-4">
                        <div class="card h-100 shadow-sm">
                            <c:set var="img" value="${empty p.images ? '/assets/images/default.png' : p.images[0]}"/>
                            <img src="${pageContext.request.contextPath}/${img}" class="card-img-top" style="height:230px; object-fit:cover;" alt="${p.productName}">
                            <div class="card-body d-flex flex-column">
                                <h5 class="card-title">${p.productName}</h5>
                                <p class="card-text text-muted small">${p.material} • ${p.weight}g</p>
                                <p class="fw-bold text-danger mb-2">${p.price}₫</p>
                                <a href="${pageContext.request.contextPath}/product-detail?id=${p.productId}" class="btn btn-outline-primary mt-auto">Xem chi tiết</a>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
</div>
