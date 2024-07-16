package com.yb.operator;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

public class Operator2 {

    // concatMap
    public Flux<Integer> fluxConcatMap() {
        return Flux.range(1, 10)
                .concatMap(i -> Flux.range(i * 10, 10)
                        .delayElements(Duration.ofMillis(100)))

                .log();
    }

    // flatMapMany
    public Flux<Integer> monoFlatMapMany(){
        return Mono.just(10)
                .flatMapMany(i -> Flux.range(1, i))
                .log();
    }

    // switchIfEmpty / defaultEmpty

    public Mono<Integer> defaultIfEmpty1(){
        return Mono.just(100)
                .filter(i -> i> 100)
                .defaultIfEmpty(30);
    }


    public Mono<Integer> switchIfEmpty(){
        return Mono.just(100)
                .filter(i -> i> 100)
                .switchIfEmpty(Mono.just(30).map(i -> i * 2));
    }


    public Mono<Integer> switchIfEmpty2(){
        return Mono.just(100)
                .filter(i -> i> 100)
                .switchIfEmpty(Mono.error(new Exception("error")));
    }



    // merge / zip

    public Flux<String> FluxMerge(){
        return Flux.merge(
                    Flux.fromIterable(List.of("1","2","3")), Flux.fromIterable(List.of("4","5","6")))
                .log();
    }


    public Flux<String> MonoMerge(){
        return Mono.just("1").mergeWith(Mono.just("2")).mergeWith(Mono.just("3"));
    }


    public Flux<String> FluxZip(){
        return Flux.zip(
                    Flux.just("a","b","c") ,
                    Flux.just("d","e","f")
                ).map((i -> i.getT1() + i.getT2()))
                .log();
    }

    public Mono<Integer> MonoZip(){
        return Mono.zip(Mono.just(1),Mono.just(2),Mono.just(3)).map((i -> i.getT1() + i.getT2()+ i.getT3()))
                .log();
    }


}


