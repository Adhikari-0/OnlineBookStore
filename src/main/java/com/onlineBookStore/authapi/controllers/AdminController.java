package com.onlineBookStore.authapi.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

@PreAuthorize("hasRole('ADMIN')")
@Controller
public class AdminController {

}
