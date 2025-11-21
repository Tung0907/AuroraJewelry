
<meta charset="UTF-8">
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="/views/admin/_admin_head.jsp" />

<h2>Upload Ảnh Sản Phẩm</h2>

<c:if test="${param.error == 'no_file'}">
  <div class="alert alert-warning">Bạn chưa chọn file.</div>
</c:if>

<form method="post" action="${pageContext.request.contextPath}/admin/product/upload-image"
      enctype="multipart/form-data" class="mb-3">
  <input type="hidden" name="productId" value="${productId}" />
  <div class="mb-2">
    <input type="file" name="imageFile" required class="form-control-file" />
  </div>
  <button type="submit" class="btn btn-primary">Upload</button>
  <a href="${pageContext.request.contextPath}/admin/products" class="btn btn-secondary">Quay lại</a>
</form>

<hr>

<h4>Danh sách ảnh:</h4>

<div class="row">
  <c:forEach items="${images}" var="img">
    <div class="col-md-3 mb-3 text-center">
      <img src="${pageContext.request.contextPath}/${img}" class="img-fluid rounded border" style="max-height:200px" />
      <form action="${pageContext.request.contextPath}/admin/product/delete-image" method="post" class="mt-2">
        <input type="hidden" name="imageUrl" value="${img}">
        <input type="hidden" name="productId" value="${productId}">
        <button class="btn btn-danger btn-sm">Xóa</button>
      </form>
    </div>
  </c:forEach>
</div>

<jsp:include page="/views/admin/_admin_foot.jsp" />
