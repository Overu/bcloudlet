/********************************************************************/
/* IBM Confidential */
/*                                                                  */
/* OCO Source Materials */
/*                                                                  */
/*                                                                  */
/* Copyright IBM Corp. 2001, 2012 */
/*                                                                  */
/* The source code for this program is not published or otherwise */
/* divested of its trade secrets, irrespective of what has been */
/* deposited with the U.S. Copyright Office. */
/********************************************************************/
package org.cloudlet.core.servlet;

import com.google.inject.Singleton;

import junit.textui.TestRunner;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Singleton
public class JUnitServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  public static final String CLASS = "class";
  public static final String METHOD = "method";

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String clz = req.getParameter(CLASS);
    String method = req.getParameter(METHOD);
    if (clz == null) {
      PrintWriter w = resp.getWriter();
      w.write("No test class is specified.");
      return;
    }
    String[] args;
    if (method != null) {
      args = new String[4];
      args[0] = "-c";
      args[1] = clz;
      args[2] = "-m";
      args[3] = method;
    } else {
      args = new String[2];
      args[0] = "-c";
      args[1] = clz;
    }
    try {
      TestRunner runner = new TestRunner(new PrintStream(resp.getOutputStream()));
      runner.start(args);
    } catch (Exception e) {
      e.printStackTrace(resp.getWriter());
    }
  }

  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    doGet(req, resp);
  }

}
