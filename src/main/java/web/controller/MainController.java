package web.controller;

import web.ControllerFrame;
import web.dispatcher.Navi;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainController implements ControllerFrame {

  public MainController() {}

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String next = Navi.FORWARD_MAIN;

    return next;
  }
}
