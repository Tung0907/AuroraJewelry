<%@ include file="_admin_head.jsp" %>
<h3>Orders</h3>
<table class="table">
    <thead><tr><th>ID</th><th>Customer</th><th>Total</th><th>Status</th><th>Date</th><th>Actions</th></tr></thead>
    <tbody>
    <c:forEach items="${orders}" var="o">
        <tr>
            <td>${o.orderId}</td>
            <td>${o.customerId}</td>
            <td>${o.totalAmount}</td>
            <td>${o.status}</td>
            <td>${o.createdAt}</td>
            <td>
                <form method="post" style="display:inline">
                    <input type="hidden" name="orderId" value="${o.orderId}" />
                    <input type="hidden" name="action" value="updateStatus" />
                    <select name="status" class="form-select form-select-sm d-inline" style="width:auto">
                        <option value="Pending" ${o.status=='Pending'?'selected':''}>Pending</option>
                        <option value="Confirmed" ${o.status=='Confirmed'?'selected':''}>Confirmed</option>
                        <option value="Shipping" ${o.status=='Shipping'?'selected':''}>Shipping</option>
                        <option value="Completed" ${o.status=='Completed'?'selected':''}>Completed</option>
                    </select>
                    <button class="btn btn-sm btn-primary">Update</button>
                </form>

                <form method="post" style="display:inline">
                    <input type="hidden" name="orderId" value="${o.orderId}" />
                    <input type="hidden" name="action" value="cancel" />
                    <button class="btn btn-sm btn-danger">Cancel</button>
                </form>

                <form method="post" style="display:inline">
                    <input type="hidden" name="orderId" value="${o.orderId}" />
                    <input type="hidden" name="action" value="return" />
                    <input name="reason" placeholder="Reason" required style="width:120px" />
                    <button class="btn btn-sm btn-warning">Return</button>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<%@ include file="_admin_footer.jsp" %>
