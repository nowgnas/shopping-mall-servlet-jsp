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
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
<div class="container">
    <h1 class="mt-4">Product Details</h1>

    <div class="row mt-4">
        <div class="col-md-4">
            <img src="${product.image}" alt="Product Image" class="img-fluid">
        </div>

        <div class="col-md-8">
            <h2>${product.name}</h2>
            <p class="lead">Price: $${product.price}</p>

            <form action="/buy" method="post">
                <div class="form-group">
                    <label for="quantity">Quantity:</label>
                    <input type="number" id="quantity" name="quantity" value="1" min="1"
                           class="form-control">
                    <input type="hidden" name="productId" value="${product.id}">
                </div>
                <button type="submit" class="btn btn-primary">Buy</button>
            </form>
        </div>
    </div>

    <div class="row mt-4">
        <div class="col-md-12">
            <h3>Categories: ${product.categories}</h3>
            <p>Description: ${product.description}</p>

            <p>Likes: ${product.likes}</p>
        </div>
    </div>

    <div class="row mt-4">
        <div class="col-md-12">
            <a href="/home" class="btn btn-secondary">Back to Home</a>
        </div>
    </div>
</div>
</body>
</html>

