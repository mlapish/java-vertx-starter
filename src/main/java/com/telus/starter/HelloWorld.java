package com.telus.starter;

import io.vertx.core.Vertx;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.AbstractVerticle;

/**
 * @author Michael Lapish
 */
public class HelloWorld extends AbstractVerticle {
  public void start() {
    Router router = Router.router(vertx);
    
    router.get("/").handler(rc -> {
      rc.response().end("Hello World");
    });
    
    router.get("/health").handler(rc -> {
      rc.response().end("OK");
    });

    vertx.createHttpServer()
        .requestHandler(router::accept)
        .listen(8080);
  }
}
