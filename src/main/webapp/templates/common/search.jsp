<%--
  Created by IntelliJ IDEA.
  User: nowgnas
  Date: 2023/08/27
  Time: 15:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!-- Search Begin -->
<div class="search-model">
    <div class="h-100 d-flex align-items-center justify-content-center">
        <div class="search-close-switch">+</div>
        <input placeholder="Search here... " id="search-input"/>
    </div>
</div>
<!-- Search End -->

<script>
  const search = document.getElementById("search-input");
  search.addEventListener("keydown", (event) => {
    if (event.key === "Enter") {
      event.preventDefault(); // Prevent form submission
      const inputData = document.getElementById("search-input").value;
      window.location.href = `/product.bit?view=search&keyword=` + inputData + `&curPage=0`;
    }
  })
</script>