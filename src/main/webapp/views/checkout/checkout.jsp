<%@ page contentType="text/html;charset=UTF-8" %>
<link href="${pageContext.request.contextPath}/assets/bootstrap.min.css" rel="stylesheet" />
<div class="container mt-4">
    <h3>Thanh toán</h3>
    <form method="post" action="${pageContext.request.contextPath}/checkout">
        <div class="mb-2">
            <label>Họ và tên</label>
            <input name="fullname" class="form-control" required />
        </div>
        <div class="mb-2">
            <label>Địa chỉ giao hàng</label>
            <input name="address" class="form-control" required />
        </div>
        <div class="mb-2">
            <label>Phương thức thanh toán</label>
            <select name="paymentMethod" class="form-select">
                <option value="CashOnDelivery">Thanh toán khi nhận hàng</option>
                <option value="OnlineCard">Thanh toán online (giả lập)</option>
            </select>
        </div>
        <button class="btn btn-primary">Xác nhận & Thanh toán</button>
    </form>
</div>
