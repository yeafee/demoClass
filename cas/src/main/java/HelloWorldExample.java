import java.io.*;
import java.util.*;
import java.util.Map.Entry;

import javax.servlet.*;
import javax.servlet.http.*;

import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.util.AbstractCasFilter;
import org.jasig.cas.client.validation.Assertion;

/**
 * CAS simple Servlet
 *
 * @author <a href="http://www.micmiu.com">;Michael Sun</a>;
 */

public class HelloWorldExample extends HttpServlet {

	private static final long serialVersionUID = -6593274907821061823L;

	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		ResourceBundle rb = ResourceBundle.getBundle("LocalStrings",
				request.getLocale());
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		out.println("<html>");
		out.println("<head>");

		String title = rb.getString("helloworld.title");

		out.println("<title>" + title + "</title>");
		out.println("</head>");
		out.println("<body bgcolor=\"white\">");

		out.println("<a href=\"../helloworld.html\">");
		out.println("<img src=\"../images/code.gif\" height=24 "
				+ "width=24 align=right border=0 alt=\"view code\"></a>");
		out.println("<a href=\"../index.html\">");
		out.println("<img src=\"../images/return.gif\" height=24 "
				+ "width=24 align=right border=0 alt=\"return\"></a>");
		out.println("<h1>" + title + "</h1>");

		Assertion assertion = (Assertion) request.getSession().getAttribute(
				AbstractCasFilter.CONST_CAS_ASSERTION);

		if (null != assertion) {
			out.println(" Log | ValidFromDate =:"
					+ assertion.getValidFromDate() + "<br>");
			out.println(" Log | ValidUntilDate =:"
					+ assertion.getValidUntilDate() + "<br>");
			Map<String, Object> attMap = assertion.getAttributes();
			out.println(" Log | getAttributes Map size = " + attMap.size()
					+ "<br>");
			for (Entry<String, Object>  entry : attMap.entrySet()) {
				out.println("     | " + entry.getKey() + "=:"
						+ entry.getValue() + "<br>");
			}
			AttributePrincipal principal = assertion.getPrincipal();

			// AttributePrincipal principal = (AttributePrincipal) request
			// .getUserPrincipal();

			String username = null;
			out.print(" Log | UserName:");
			if (null != principal) {
				username = principal.getName();
				out.println("<span style='color:red'>" + username
						+ "</span><br>");
			}

		}

		out.println("</body>");
		out.println("</html>");
	}
}