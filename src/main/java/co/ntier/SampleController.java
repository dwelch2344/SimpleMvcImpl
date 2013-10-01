package co.ntier;

import co.ntier.annotation.MvcPath;

/**
 * @Author dave 10/1/13 2:48 PM
 */
@MvcPath("/hello")
public class SampleController {


    @MvcPath("/world")
    public String doFoobar(){
        return "index";
    }

    @MvcPath("/user/{id}")
    public String getUser(){
        return "blah";
    }
}
