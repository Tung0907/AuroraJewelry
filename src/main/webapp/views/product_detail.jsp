<meta charset="UTF-8">
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
<div class="container py-4">
    <div class="row">
        <div class="col-md-6">
            <div id="carouselProduct" class="carousel slide" data-bs-ride="carousel">
                <div class="carousel-inner">
                    <c:forEach var="img" items="${images}" varStatus="st">
                        <div class="carousel-item ${st.first ? 'active' : ''}">
                            <img src="${pageContext.request.contextPath}/${img}" class="d-block w-100" style="height:400px; object-fit:cover;" />
                        </div>
                    </c:forEach>
                </div>
                <button class="carousel-control-prev" type="button" data-bs-target="#carouselProduct" data-bs-slide="prev">
                    <span class="carousel-control-prev-icon"></span>
                </button>
                <button class="carousel-control-next" type="button" data-bs-target="#carouselProduct" data-bs-slide="next">
                    <span class="carousel-control-next-icon"></span>
                </button>
            </div>
        </div>

        <div class="col-md-6">
            <h3>${product.productName}</h3>
            <p class="text-muted">${product.material} • ${product.weight}g</p>
            <h4 class="text-danger">${product.price}₫</h4>
            <p>${product.description}</p>

            <form method="post" action="${pageContext.request.contextPath}/cart">
                <input type="hidden" name="action" value="add"/>
                <input type="hidden" name="productId" value="${product.productId}"/>
                <div class="mb-2">
                    <label>Size / Variant</label>
                    <select name="variantId" class="form-select">
                        <option value="">-- Chọn --</option>
                        <c:forEach var="v" items="${variants}">
                            <option value="${v.variantId}">${v.size} (${v.additionalPrice})</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="mb-2">
                    <label>Số lượng</label>
                    <input type="number" name="qty" value="1" min="1" class="form-control" style="width:100px;"/>
                </div>

                <button class="btn btn-primary">Thêm vào giỏ</button>
            </form>
        </div>
    </div>
</div>
