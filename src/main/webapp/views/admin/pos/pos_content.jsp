<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h2 class="mb-3">💳 Bán Hàng Tại Quầy (POS)</h2>

<div class="row">

    <!-- =================== PRODUCT LIST ===================== -->
    <div class="col-md-8">
        <div class="row">
            <c:forEach items="${products}" var="p">
                <div class="col-md-3 mb-3">
                    <div class="card" onclick="addToCart(${p.productId}, '${p.productName}', ${p.price})" style="cursor:pointer;">
                        <img src="${pageContext.request.contextPath}/${p.images[0]}" class="card-img-top">
                        <div class="card-body p-2 text-center">
                            <h6>${p.productName}</h6>
                            <p class="text-danger fw-bold">${p.price}₫</p>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>

    <!-- =================== CART ===================== -->
    <div class="col-md-4">
        <div class="card">
            <div class="card-header bg-dark text-white">🛒 Giỏ Hàng</div>
            <div class="card-body" id="cart"></div>
            <div class="card-footer">
                <button class="btn btn-primary w-100" onclick="checkout()">Thanh Toán</button>
            </div>
        </div>
    </div>

</div>

<script>
    let cart = [];

    function addToCart(id, name, price) {
        let item = cart.find(x => x.id === id);
        if (item) item.qty++;
        else cart.push({ id, name, price, qty: 1 });
        renderCart();
    }

    function removeItem(id) {
        cart = cart.filter(x => x.id !== id);
        renderCart();
    }

    function renderCart() {
        let html = "";
        let total = 0;

        cart.forEach(i => {
            total += i.qty * i.price;
            html += `
                <div class="d-flex justify-content-between border-bottom pb-1 mb-2">
                    <div>
                        <b>${i.name}</b><br>
                        SL: ${i.qty} × ${i.price}
                    </div>
                    <button class="btn btn-sm btn-danger" onclick="removeItem(${i.id})">X</button>
                </div>
            `;
        });

        html += `<h5 class="text-end mt-3">Tổng: <span class="text-danger fw-bold">${total}₫</span></h5>`;
        document.getElementById("cart").innerHTML = html;
    }

    function checkout() {
        if (cart.length === 0) {
            alert("Chưa có sản phẩm!");
            return;
        }

        // gửi đi servlet xử lý thanh toán
        let form = document.createElement("form");
        form.method = "post";
        form.action = "${pageContext.request.contextPath}/admin/pos/checkout";

        let input = document.createElement("input");
        input.type = "hidden";
        input.name = "cartData";
        input.value = JSON.stringify(cart);
        form.appendChild(input);

        document.body.appendChild(form);
        form.submit();
    }
</script>
