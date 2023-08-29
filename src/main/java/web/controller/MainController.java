package web.controller;

import app.service.category.CategoryService;
import app.service.category.CategoryServiceImpl;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import web.ControllerFrame;
import web.dispatcher.Navi;

public class MainController implements ControllerFrame {

  private final CategoryService categoryService = CategoryServiceImpl.getInstance();
  Logger log = Logger.getLogger("MainController");

  public MainController() {}

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String next = Navi.FORWARD_MAIN;
    return next;
  }
}
