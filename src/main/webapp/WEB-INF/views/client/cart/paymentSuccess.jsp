<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
            <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <!-- Tự chuyển về /thanks sau 3 giây -->
  <meta http-equiv="refresh" content="3;url=/thanks">
  <title>Payment successful</title>
  <style>body{font-family:sans-serif;margin:40px}</style>
</head>
<body>
  <h2>Thanh toán thành công ✅</h2>
  <p>Order #<span>${orderId}</span></p>
  <p>Trạng thái: <b>${paymentStatus}</b></p>
  <p>Session: ${sessionId}</p>
  <p><a href="${receiptUrl}">Xem hoá đơn (receipt)</a></p>
  <p>You will be redirected to another page shortly…/p>
  <script>
      setTimeout(() => { window.location.href = '/thanks'; }, 30000);
  </script>
</body>
</html>