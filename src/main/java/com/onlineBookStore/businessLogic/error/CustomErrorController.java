package com.onlineBookStore.businessLogic.error;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

	public String handleError(HttpServletRequest request, Model model) {
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

		if (status != null) {
			Integer statusCode = Integer.valueOf(status.toString());
			model.addAttribute("code", statusCode);

			if (statusCode == 404) {
				model.addAttribute("message", "Page not found");
				return "error/404";
			} else if (statusCode == 500) {
				model.addAttribute("message", "Internal server error");
				return "error/500";
			}
		}

		model.addAttribute("message", "Unknown error");
		return "error";
	}

}
