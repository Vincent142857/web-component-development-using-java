package vn.aptech;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import vn.aptech.db.ProductDb;
import vn.aptech.dto.ProductDto;

/**
 * Servlet implementation class Controller
 */
@WebServlet("/")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 3 * 1024 * 1024, maxRequestSize = 3 * 5 * 1024 * 1024)
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Controller() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String a = request.getServletPath();
		try {
			switch (a) {
			case "/new":
				showNewForm(request, response);
				break;
			case "/insert":
				insertProduct(request, response);
				break;
			case "/delete":
				deleteProduct(request, response);
				break;
			case "/edit":
				showEditForm(request, response);
				break;
			case "/update":
				updateProduct(request, response);
				break;
			case "/search":
				searchProduct(request, response);
				break;
			case "/details":
				detailsProduct(request, response);
				break;
			default:
				listProduct(request, response);
				break;
			}
		} catch (SQLException ex) {
			throw new ServletException(ex);
		}

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	private void listProduct(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		List<ProductDto> products = ProductDb.getAll();
		request.setAttribute("prods", products);
		request.getRequestDispatcher("product/index.jsp").forward(request, response);
	}

	private void searchProduct(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		String name = request.getParameter("keyword");

		List<ProductDto> products = ProductDb.search(name);
		String message = !products.isEmpty() ? "Searching " + products.size() + " product(s)" : "No products found";
		request.setAttribute("message", message);
		request.setAttribute("prods", products);
		request.getRequestDispatcher("product/index.jsp").forward(request, response);
	}

	private void showNewForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("product/form.jsp");
		dispatcher.forward(request, response);
	}

	private void showEditForm(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		ProductDto existing = ProductDb.getById(id);
		RequestDispatcher dispatcher = request.getRequestDispatcher("product/form.jsp");
		request.setAttribute("product", existing);
		dispatcher.forward(request, response);
	}

	private void detailsProduct(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		ProductDto existing = ProductDb.getById(id);
		RequestDispatcher dispatcher = request.getRequestDispatcher("product/details.jsp");
		request.setAttribute("product", existing);
		dispatcher.forward(request, response);
	}

	private void insertProduct(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		try {
			// file image
			String pathFolder = request.getServletContext().getRealPath("") + File.separator + "images";
			File imgFolder = new File(pathFolder);
			if (!imgFolder.exists()) {
				imgFolder.mkdir();
			}
			String filename = null;
			for (Part part : request.getParts()) {
				filename = getFilename(part);
				if (filename != null) {
					part.write(pathFolder + File.separator + filename);
				}
			}

			// save product information
			String name = request.getParameter("name");
			int price = Integer.parseInt(request.getParameter("price"));

			ProductDto p = new ProductDto();
			p.setName(name);
			p.setPrice(price);
			p.setImage(filename);
			boolean result = ProductDb.create(p);
			if (result) {
				response.sendRedirect("Controller");
			} else {
				response.sendRedirect("product/form.jsp");
			}
		} catch (Exception e) {
			log(e.getMessage());
			response.sendRedirect("product/form.jsp");
		}
	}

	private void updateProduct(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {

		int id = Integer.parseInt(request.getParameter("id"));
		ProductDto existing = ProductDb.getById(id);

		try {
			// file image
			String pathFolder = request.getServletContext().getRealPath("") + File.separator + "images";
			File imgFolder = new File(pathFolder);
			if (!imgFolder.exists()) {
				imgFolder.mkdir();
			}

			String filename = null;

			for (Part part : request.getParts()) {
				filename = getFilename(part);
				if (filename != null) {
					part.write(pathFolder + File.separator + filename);
				}
			}

			String name = request.getParameter("name");
			int price = Integer.parseInt(request.getParameter("price"));

			if (name != null && existing.getName() != name) {
				existing.setName(name);
			}
			if (price >= 0 && existing.getPrice() != price) {
				existing.setPrice(price);
			}
			if (filename != null && existing.getImage() != filename) {
				// delete old file
				String imgPathFolder = request.getServletContext().getRealPath("") + File.separator + "images"
						+ File.separator + existing.getImage();

				File img = new File(imgPathFolder);
				if (img.exists()) {
					img.delete();
				}
				// save new filename
				existing.setImage(filename);
			}

			ProductDto p = new ProductDto(id, name, price, filename);
			ProductDb.update(p);

			response.sendRedirect("Controller");
		} catch (Exception e) {
			log(e.getMessage());
			request.setAttribute("product", existing);
			request.getRequestDispatcher("product/form.jsp").forward(request, response);
		}
	}

	private void deleteProduct(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		ProductDto product = ProductDb.getById(id);
		// delete the image file
		String imgPathFolder = request.getServletContext().getRealPath("") + File.separator + "images" + File.separator
				+ product.getImage();

		File img = new File(imgPathFolder);
		if (img.exists()) {
			img.delete();
		}
		// delete product in the database
		ProductDb.delete(id);
		response.sendRedirect("Controller");
	}

	// filename for image file
	private String getFilename(Part part) {
		String result = null;
		String header = part.getHeader("content-disposition");
		log(header);
		for (String s : header.split(";")) {
			if (s.trim().startsWith("filename")) {
				result = s.substring(s.indexOf("=") + 2, s.length() - 1);
				log(result);
			}
		}
		return result;
	}
}
