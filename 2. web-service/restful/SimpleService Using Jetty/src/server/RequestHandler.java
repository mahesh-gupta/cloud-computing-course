package server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;

public class RequestHandler extends AbstractHandler {

	@Override
	public void handle(String arg0, Request req, HttpServletRequest req2,			
			HttpServletResponse resp) throws IOException, ServletException {
			
			//System.out.println("request received...");
			
		//	resp.setStatus(HttpServletResponse.SC_ACCEPTED);
		//	req.setHandled(true);
			String str = req.getParameter("val");
			if(str != null) {
				String respStr = str.toUpperCase();
				resp.getWriter().write("writing: "+respStr);
			}
			//resp.getOutputStream().write(("Response is : "+str.toUpperCase()).getBytes());
			resp.flushBuffer();
			req.setHandled(true);
			
	}

	
	public static void main(String[] args) throws Exception {
		Server jettyServer = new Server();
		
		ServerConnector connector1 = new ServerConnector(jettyServer);
        connector1.setHost("127.0.0.1");
        connector1.setPort(8888);
		
        jettyServer.setConnectors(new Connector[]{connector1});
		jettyServer.setHandler(new RequestHandler());
		
		jettyServer.start();
	}

}
