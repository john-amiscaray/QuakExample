package io.john.amiscaray.test;

import io.john.amiscaray.quak.web.application.WebStarter;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        var application = WebStarter.beginWebApplication(Main.class, args)
                .get(10, TimeUnit.SECONDS);

        application.await();
    }
}