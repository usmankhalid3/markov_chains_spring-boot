package alogic.markov;

import org.springframework.boot.SpringApplication;

import alogic.markov.api.MarkovController;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(MarkovController.class, args);
    }
}
