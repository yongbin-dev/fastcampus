package com.yb.example;

public class Main {
    public static void main(String[] args) {
        var publisher= new Publisher();



//        publisher.startFlux()
//                .subscribe(System.out::println);

        publisher.startMono2()
                .subscribe();

        System.out.println("Hello world!");
    }
}
