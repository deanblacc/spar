package com.ragu.spar;

import com.ragu.spar.resources.GameResource;
import com.ragu.spar.resources.HelloWorldResource;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;


public class SparApplication extends Application<SparConfiguration> {
    public static void main(String[] args) throws Exception {
        new SparApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<SparConfiguration> bootstrap) {
        bootstrap.addBundle(new AssetsBundle("/dist", "/", "index.html"));
    }

    @Override
    public void run(SparConfiguration configuration, Environment environment) throws Exception {
        environment.jersey().register(new HelloWorldResource());
        environment.jersey().register(new GameResource());
        environment.jersey().setUrlPattern("/api/v1/*");
    }
}
