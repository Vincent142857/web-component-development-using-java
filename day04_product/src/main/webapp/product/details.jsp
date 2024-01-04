<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Details</title>
<!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
</head>
<body>
	
	<section class="container my-5">
		
		<h1>Information of product</h1>
		
<% if(request.getAttribute("product")!=null){ %>

		<div class="card w-75">
		    
		    <img class="card-img-top" src="${pageContext.request.contextPath}/image?imageName=${ product.getImage() }" alt="${ product.getImage() }" width="100" height="auto">
		    
		    <div class="card-body">
		        <div class="com-md-6">
		            <hr />
		            <dl class="row">
		                <dt class="col-sm-3">Name:</dt>
		                <dd class="col-sm-9">
		                   ${ product.getName() }
		                </dd>
		                <dt class="col-sm-3">Price:</dt>
		                <dd class="col-sm-9">
		                    ${ product.getPrice() }
		                </dd>
		            </dl>
		        </div>
		    </div>
		</div>
	<% } %>

	<a href="${pageContext.request.contextPath}/">Back to list</a>
	</section>
	
</body>
</html>