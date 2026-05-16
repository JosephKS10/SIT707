package web.handler;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import web.service.LoginService;
import web.service.MathQuestionService;

@Controller
@RequestMapping("/")
public class RoutingServlet {

	@GetMapping("/")
	public String welcome() {
		return "view-welcome";
	}

	@GetMapping("/login")
	public String loginView() {
		return "view-login";
	}

	@PostMapping("/login")
	public RedirectView login(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		String username = request.getParameter("username");
		String password = request.getParameter("passwd");
		String dob = request.getParameter("dob");

		if (LoginService.login(username, password, dob)) {
			return new RedirectView("/q1", true);
		}
		redirectAttributes.addFlashAttribute("message", "Incorrect credentials.");
		return new RedirectView("/login", true);
	}

	@GetMapping("/q1")
	public String q1View() {
		return "view-q1";
	}

	@PostMapping("/q1")
	public RedirectView q1(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		return evaluateAnswer(
				MathQuestionService.q1Addition(
						request.getParameter("number1"),
						request.getParameter("number2")),
				request.getParameter("result"),
				"/q1", "/q2", redirectAttributes);
	}

	@GetMapping("/q2")
	public String q2View() {
		return "view-q2";
	}

	@PostMapping("/q2")
	public RedirectView q2(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		return evaluateAnswer(
				MathQuestionService.q2Subtraction(
						request.getParameter("number1"),
						request.getParameter("number2")),
				request.getParameter("result"),
				"/q2", "/q3", redirectAttributes);
	}

	@GetMapping("/q3")
	public String q3View() {
		return "view-q3";
	}

	@PostMapping("/q3")
	public RedirectView q3(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		return evaluateAnswer(
				MathQuestionService.q3Multiplication(
						request.getParameter("number1"),
						request.getParameter("number2")),
				request.getParameter("result"),
				"/q3", "/success", redirectAttributes);
	}

	@GetMapping("/success")
	public String successView() {
		return "view-success";
	}

	/**
	 * Shared evaluation logic for math question pages.
	 * - calculatedResult null  -> input validation error, stay on current page
	 * - user result unparsable -> validation error, stay on current page
	 * - mismatch               -> wrong-answer error, stay on current page
	 * - match                  -> move to next page
	 */
	private RedirectView evaluateAnswer(Double calculatedResult, String resultUser,
			String currentPath, String nextPath, RedirectAttributes redirectAttributes) {

		if (calculatedResult == null) {
			redirectAttributes.addFlashAttribute("message",
					"Please enter valid numbers in both fields.");
			return new RedirectView(currentPath, true);
		}

		Double userResult;
		try {
			userResult = Double.valueOf(resultUser);
		} catch (NumberFormatException | NullPointerException e) {
			redirectAttributes.addFlashAttribute("message",
					"Please enter a valid numeric answer.");
			return new RedirectView(currentPath, true);
		}

		if (calculatedResult.doubleValue() == userResult.doubleValue()) {
			return new RedirectView(nextPath, true);
		}
		redirectAttributes.addFlashAttribute("message", "Wrong answer, try again.");
		return new RedirectView(currentPath, true);
	}
}