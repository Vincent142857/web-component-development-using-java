<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
</head>

<body>
	<div class="container my-5">
		<% if(request.getAttribute("product")==null){ %>
			<h1>Add product</h1>
			<% }else{%>
			<h1>Edit product</h1>
		<% }%>
		<div class="container my-5">
			
			<% if(request.getAttribute("product")!=null){ %>
				<form action="${pageContext.request.contextPath}/update" method="post" 
				 	enctype="multipart/form-data" class="col-md-6">
				 	<input type="hidden" name="id" value="${product.getId()}"  />
			<% }else{%>
			 	<form action="${pageContext.request.contextPath}/insert" method="post" enctype="multipart/form-data" class="col-md-6">
		     <% }%>
		        <div class="form-group mb-3">
		            <label for="name">Name</label>
		             <input type="text" name="name" id="name" class="form-control" placeholder="Enter name" required 
		             value="${product.getName()}" />
		        </div>
		        <div class="form-group mb-3">
		            <label for="price">Price</label>
		             <input type="number" name="price" id="price" class="form-control" placeholder="Enter price" required
		             value="${product.getPrice()}" />
		        </div>
		        <div class="form-group mb-3">
					<label>Photo</label>
					<input type="file" id="up_image" name="photo" accept="image/png, image/jpeg" 
					value="${product.getImage()}" />
					<div id="imageGrid"></div>
				</div>
		        <div class="form-group mb-3">
		            <% if(request.getAttribute("product")==null){ %>
		            	<button type="submit" class="btn btn-primary w-50">Add product</button>
					<% }else{%>
						<button type="submit" class="btn btn-warning w-50">Edit product</button>
					<% }%>
		        </div>
		    </form>
		</div>
		
		<a href="${pageContext.request.contextPath}/">Back to list</a>
	</div>

<!-- 	<script>
		 const imageGrid = document.getElementById('image-grid');
		 const upImage = document.getElementById("up_image");
	     upImage.addEventListener("change",function (e) {
	         const files = e.target.files;
	         let reader = new FileReader();
	         for (const file of files) {
	             const img = document.createElement('img');
	             imageGrid.appendChild(img);
	             img.src = URL.createObjectURL(file);
	             img.alt = file.name;
	             img.style.width = '85px';
	             img.style.height = '85px';
	         }
	     });
     </script>
 -->	
</body>
</html>