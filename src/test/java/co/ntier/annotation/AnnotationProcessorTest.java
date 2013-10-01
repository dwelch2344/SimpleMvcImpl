package co.ntier.annotation;

import co.ntier.SampleController;
import co.ntier.annotation.AnnotationProcessor;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * @Author dave 10/1/13 4:17 PM
 */

public class AnnotationProcessorTest {

    // @Test
    public void matchTest(){
        if( !"{something}".matches("\\{[^/]*\\}") ){
            Assert.fail("Should have matched...");
        }
    }

    @Test
    public void doTest(){
        AnnotationProcessor p = new AnnotationProcessor();
        AnnotationProcessor.HandlerPairings pairings = p.process(SampleController.class);

        List<AnnotationProcessor.HandlerPairingMatch> match = pairings.matches("/hello/world");

        match = pairings.matches("/hello/user/billy");

        match = pairings.matches("/hello/foobar");

        System.out.println("Done");
    }
}
