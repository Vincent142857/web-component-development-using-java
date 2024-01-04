<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="vn.aptech.dto.ProductDto" %>
<%@ page import="java.util.List"  %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
<!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
</head>
<body class="container">
	<h1 class="mt-3">Product list</h1>
	<div class="my-3">
		<div class="d-inline-block">
			<form action="${pageContext.request.contextPath}/search" method="get">
				<input name="keyword" placeholder="Search" class="" />
				<button class="btn btn-secondary" type="submit">Search</button>
			</form>
		</div>
		<div class="float-right">
			<!-- <a class="btn btn-success" href="Controller?a=DisplayCreate">Create</a> -->
			<a class="btn btn-success" href="${pageContext.request.contextPath}/new">Create</a>
		</div>
	</div>
<%-- <div>${data }</div> --%>


	<% if(request.getAttribute("message")!=null){ %>
		<div class="alert">${ message }</div>
	<%} %>
	<table class="table table-striped table-inverse text-center">
		<thead>
			<tr>
				<th>ID</th>
				<th>NAME</th>
				<th>PRICE</th>
				<th>IMAGE</th>
				<th>Actions</th>
			</tr>
		</thead>
		<tbody>
			<%
			if(request.getAttribute("prods")!=null){
				List<ProductDto> prods = (List<ProductDto>) request.getAttribute("prods");
				for(ProductDto p : prods){
			%>
				<tr>
					<td><%= p.getId() %></td>
					<td>
						<a href="${pageContext.request.contextPath}/details?id=<%= p.getId() %>"><%= p.getName() %></a>
					</td>
					<td><%= p.getPrice() %></td>
					<td>
						<img src="${pageContext.request.contextPath}/image?imageName=<%= p.getImage() %>" alt="<%= p.getImage() %>" width="100px" height="100px" />
					</td>
					<td>
						<form action="${pageContext.request.contextPath}/delete" method="post" onsubmit="return confirm('Delete this?')">
							<a href="${pageContext.request.contextPath}/edit?id=<%= p.getId() %>" class="btn btn-warning">Edit</a>
							<input type="hidden" value="<%= p.getId() %>" name="id" />
							<button type="submit" class="btn btn-danger">Delete</button>
						</form>
					</td>
			</tr>
			<% }} %>
			
		</tbody>
	</table>
</body>
</html>