package co.ntier.annotation;

import co.ntier.ReflectionUtils;
import com.google.appengine.repackaged.com.google.common.collect.Lists;
import com.google.appengine.repackaged.com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * @Author dave 10/1/13 4:22 PM
 */
public class AnnotationProcessor {

    public HandlerPairings process(Class<?> clazz){
        List<HandlerPairing> pairings = Lists.newArrayList();

        String prefix = "";
        MvcPath annotation = clazz.getAnnotation(MvcPath.class);
        if( annotation != null ){
            prefix = annotation.value();
        }

        Method[] methods = ReflectionUtils.getAllDeclaredMethods(clazz);
        for(Method method : methods){
            for(Annotation a : method.getAnnotations()){
                if( a instanceof MvcPath){
                    MvcPath path = (MvcPath) a;
                    String value = path.value();
                    value = prefix + value;

                    HandlerPairing pairing = new HandlerPairing(value, method);
                    pairings.add(pairing);
                }
            }
        }

        return new HandlerPairings(pairings);
    }

    @AllArgsConstructor
    @Getter
    public static class HandlerPairing{
        private final String path;
        private final Method method;
    }

    @AllArgsConstructor
    @Getter
    public static class HandlerPairingMatch{
        private final HandlerPairing pairing;
        private Map<String, String> params;
    }

    @AllArgsConstructor
    public static class HandlerPairings{
        private final List<HandlerPairing> pairings;

        public List<HandlerPairingMatch> matches(String path){
            List<HandlerPairingMatch> result = Lists.newArrayList();
            for(HandlerPairing pairing : pairings){
                MatchResult mr = doesMatch(pairing, path);
                if( mr.isValid() ){
                    HandlerPairingMatch match = new HandlerPairingMatch(pairing, mr.getParameters());
                    result.add(match);
                }
            }

            return result;
        }

        private MatchResult doesMatch(HandlerPairing pairing, String path) {
            String[] routes = pairing.getPath().split("/");
            String[] paths = path.split("/");

            Map<String, String> params = Maps.newHashMap();

            if( routes.length != paths.length ){
                return NO_MATCH;
            }

            for(int i = 0; i < routes.length; i++){
                // if it's a substitution, it's valid
                if( routes[i].matches("\\{[^/]*\\}") ){
                    String key = routes[i].substring(1, routes[i].length() - 1);
                    params.put(key, paths[i]);
                    continue;
                }

                if( !routes[i].equals(paths[i]) ){
                    return NO_MATCH;
                }
            }

            return new MatchResult(true, params);
        }
    }

    private static final MatchResult NO_MATCH = new MatchResult(false, null);

    @AllArgsConstructor @Getter
    private static class MatchResult {
        private boolean valid;
        private Map<String, String> parameters;
    }


}
