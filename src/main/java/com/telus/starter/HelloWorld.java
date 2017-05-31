package com.telus.starter;

import io.vertx.core.Vertx;

/**
 * @author Michael Lapish
 */
public class HelloWorld extends AbstractVerticle {
  public void start() {
    vertx.createHttpServer().requestHandler(req -> {
      req.response()
        .putHeader("content-type", "text/plain")
        .end("Hello World");
    }).listen(8080);
  }
}
