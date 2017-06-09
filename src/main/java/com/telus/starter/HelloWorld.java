package com.telus.starter;

import io.vertx.core.Vertx;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;

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
      rc.response().putHeader("content-type", "application/json; charset=utf-8").end(new JsonObject().put("status", "OK").encode());
    });

    vertx.createHttpServer()
        .requestHandler(router::accept)
        .listen(8080);
  }
}
