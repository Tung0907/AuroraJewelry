<meta charset="UTF-8">
<%@ include file="_admin_head.jsp" %>

<h3 class="mb-3">Quản lý đơn hàng</h3>

<table class="table table-bordered table-hover">
    <thead class="table-light">
    <tr>
        <th>ID</th>
        <th>Khách hàng</th>
        <th>Tổng tiền</th>
        <th>Trạng thái</th>
        <th>Ngày tạo</th>
        <th style="width: 220px;">Thao tác</th>
    </tr>
    </thead>

    <tbody>
    <c:forEach items="${orders}" var="o">
        <tr>
            <td>${o.orderId}</td>
            <td>${o.customerId}</td>
            <td>${o.totalAmount}</td>
            <td><span class="badge bg-info">${o.status}</span></td>
            <td>${o.createdAt}</td>

            <td>

                <!-- Nút đổi trạng thái -->
                <button class="btn btn-sm btn-primary"
                        data-bs-toggle="modal"
                        data-bs-target="#statusModal${o.orderId}">
                    Cập nhật
                </button>

                <!-- Nút huỷ -->
                <button class="btn btn-sm btn-danger"
                        data-bs-toggle="modal"
                        data-bs-target="#cancelModal${o.orderId}">
                    Huỷ
                </button>

                <!-- Nút trả hàng -->
                <button class="btn btn-sm btn-warning"
                        data-bs-toggle="modal"
                        data-bs-target="#returnModal${o.orderId}">
                    Trả hàng
                </button>


                <!-- ================== MODAL UPDATE STATUS ================== -->
                <div class="modal fade" id="statusModal${o.orderId}" tabindex="-1">
                    <div class="modal-dialog">
                        <div class="modal-content">

                            <form action="${pageContext.request.contextPath}/admin/orders" method="post">
                                <input type="hidden" name="action" value="updateStatus">
                                <input type="hidden" name="orderId" value="${o.orderId}">

                                <div class="modal-header">
                                    <h5 class="modal-title">Cập nhật trạng thái đơn #${o.orderId}</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                                </div>

                                <div class="modal-body">
                                    <select name="status" class="form-control">
                                        <option value="Pending"   ${o.status=='Pending'?'selected':''}>Pending</option>
                                        <option value="Confirmed" ${o.status=='Confirmed'?'selected':''}>Confirmed</option>
                                        <option value="Shipping"  ${o.status=='Shipping'?'selected':''}>Shipping</option>
                                        <option value="Completed" ${o.status=='Completed'?'selected':''}>Completed</option>
                                    </select>
                                </div>

                                <div class="modal-footer">
                                    <button class="btn btn-primary">Lưu</button>
                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                                </div>
                            </form>

                        </div>
                    </div>
                </div>


                <!-- ================== MODAL CANCEL ORDER ================== -->
                <div class="modal fade" id="cancelModal${o.orderId}" tabindex="-1">
                    <div class="modal-dialog">
                        <div class="modal-content">

                            <form action="${pageContext.request.contextPath}/admin/orders" method="post">
                                <input type="hidden" name="action" value="cancel">
                                <input type="hidden" name="orderId" value="${o.orderId}">

                                <div class="modal-header">
                                    <h5 class="modal-title">Huỷ đơn #${o.orderId}</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                                </div>

                                <div class="modal-body">
                                    Bạn có chắc muốn huỷ đơn hàng này không?
                                </div>

                                <div class="modal-footer">
                                    <button class="btn btn-danger">Huỷ đơn</button>
                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                                </div>
                            </form>

                        </div>
                    </div>
                </div>


                <!-- ================== MODAL RETURN ORDER ================== -->
                <div class="modal fade" id="returnModal${o.orderId}" tabindex="-1">
                    <div class="modal-dialog">
                        <div class="modal-content">

                            <form action="${pageContext.request.contextPath}/admin/orders" method="post">
                                <input type="hidden" name="action" value="return">
                                <input type="hidden" name="orderId" value="${o.orderId}">

                                <div class="modal-header">
                                    <h5 class="modal-title">Trả hàng – Đơn #${o.orderId}</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                                </div>

                                <div class="modal-body">
                                    <label>Lý do trả hàng:</label>
                                    <textarea name="reason" class="form-control" required></textarea>
                                </div>

                                <div class="modal-footer">
                                    <button class="btn btn-warning">Xác nhận trả hàng</button>
                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                                </div>
                            </form>

                        </div>
                    </div>
                </div>


            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<%@ include file="_admin_footer.jsp" %>
