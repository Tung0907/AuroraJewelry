<%@ include file="_admin_head.jsp" %>
<div class="row g-3">
    <div class="col-md-3"><div class="card-stat"><h6>Sản phẩm</h6><h3>${productCount}</h3></div></div>
    <div class="col-md-3"><div class="card-stat"><h6>Đơn hàng</h6><h3>${orderCount}</h3></div></div>
    <div class="col-md-6">
        <div class="card-stat">
            <h6>Doanh thu theo tháng</h6>
            <canvas id="revenueChart"></canvas>
        </div>
    </div>
</div>

<script>
    const revenueObj = {
        <c:forEach var="entry" items="${revenueByMonth}">
        "${entry.key}": ${entry.value},
        </c:forEach>
    };
    const labels = [...Array(12).keys()].map(i=>i+1);
    const data = labels.map(m => revenueObj[m] || 0);
    const ctx = document.getElementById('revenueChart').getContext('2d');
    new Chart(ctx,{type:'bar', data:{labels, datasets:[{label:'Doanh thu', data}]}, options:{scales:{y:{beginAtZero:true}}}});
</script>
<%@ include file="_admin_footer.jsp" %>
