//package com.sonat.blog.controller.rest.interceptor;
//
//import java.util.HashSet;
//import java.util.Set;
//
//import javax.ws.rs.container.ContainerRequestFilter;
//import javax.ws.rs.ext.Provider;
//
//import org.jboss.resteasy.annotations.interception.ServerInterceptor;
//import org.jboss.resteasy.core.ServerResponse;
//
//@Provider
//public class CorsInterceptor implements ContainerRequestFilter{
//
///**
//     * The Origin header set by the browser at each request.
//     */
//    private static final String ORIGIN = "Origin";
//
//
// /**
//     * The Access-Control-Allow-Origin header indicates which origin a resource it is specified for can be
//     * shared with. ABNF: Access-Control-Allow-Origin = "Access-Control-Allow-Origin" ":" source origin string | "*"
//     */
//    private static final String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
//
//
//   //
//    private static final ThreadLocal<String> REQUEST_ORIGIN = new ThreadLocal<String>();
//    //
//    private final Set<String> allowedOrigins;
//
//
// public CorsInterceptor(){
//   this.allowedOrigins = new HashSet<String>();
//   this.allowedOrigins.add("*");
// }
//
//   @Override
//    public ServerResponse preProcess(HttpRequest request, ResourceMethod method) throws Failure, WebApplicationException {
//        if (!allowedOrigins.isEmpty()) {
//            REQUEST_ORIGIN.set(request.getHttpHeaders().getRequestHeaders().getFirst(ORIGIN));
//        }
//        return null;
//    }
//
//
//@Over