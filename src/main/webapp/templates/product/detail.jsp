<%--
  Created by IntelliJ IDEA.
  User: nowgnas
  Date: 2023/08/21
  Time: 17:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Product Details</title>
</head>
<body>
<h1>Product Details</h1>

<div>
    <img src="${product.image}" alt="Product Image" width="200" height="200">
</div>

<h2>${product.name}</h2>
<p>Price: $${product.price}</p>

<form action="/buy" method="post">
    <label for="quantity">Quantity:</label>
    <input type="number" id="quantity" name="quantity" value="1" min="1">
    <input type="hidden" name="productId" value="${product.id}">
    <button type="submit">Buy</button>
</form>

<p>Categories: ${product.categories}</p>
<p>Description: ${product.description}</p>

<p>Likes: ${product.likes}</p>

<a href="/home">Back to Home</a>
</body>
</html>
