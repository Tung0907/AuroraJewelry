<%@ include file="_admin_head.jsp" %>
<h3><c:choose><c:when test="${not empty product}">Edit product</c:when><c:otherwise>New product</c:otherwise></c:choose></h3>

<form method="post" action="${pageContext.request.contextPath}/admin/product/new">
    <input type="hidden" name="productId" value="${product.productId}" />
    <div class="mb-3">
        <label>Tên</label>
        <input class="form-control" name="productName" value="${product.productName}"/>
    </div>
    <div class="mb-3">
        <label>Danh mục</label>
        <select class="form-select" name="categoryId">
            <c:forEach items="${categories}" var="c">
                <option value="${c.id}" ${c.id==product.categoryId?'selected':''}>${c.name}</option>
            </c:forEach>
        </select>
    </div>
    <div class="row">
        <div class="col-md-4 mb-3"><label>Giá</label><input class="form-control" name="price" value="${product.price}"/></div>
        <div class="col-md-4 mb-3"><label>Trọng lượng</label><input class="form-control" name="weight" value="${product.weight}"/></div>
        <div class="col-md-4 mb-3"><label>Giới tính</label><input class="form-control" name="gender" value="${product.gender}"/></div>
    </div>
    <div class="mb-3"><label>Mô tả</label><textarea class="form-control" name="description">${product.description}</textarea></div>
    <button class="btn btn-primary">Save</button>
    <a class="btn btn-secondary" href="${pageContext.request.contextPath}/admin/products">Cancel</a>
</form>

<%@ include file="_admin_footer.jsp" %>
