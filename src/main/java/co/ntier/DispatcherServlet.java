package co.ntier;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author dave 10/1/13 2:42 PM
 */
public class DispatcherServlet extends GenericServlet {

    public DispatcherServlet() {
        Class<?> clazz = SampleController.class;


    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        res.getWriter().write("Hello dispatcher " + request.getRequestURI());
    }


    public static class Model{
        private Map<String, Object> map = new HashMap<String, Object>();

        public Model put(String key, Object value){
            map.put(key, value);
            return this;
        }

        private Map<String, Object> getMap(){
            return map;
        }
    }

}
