package com.yb.operator;

import org.apache.tomcat.util.modeler.OperationInfo;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

class Operator2Test {

    private Operator2 operator2 = new Operator2();

    @Test
    void fluxConcatMap() {
        StepVerifier.create(operator2.fluxConcatMap()).expectNextCount(100).verifyComplete();
    }

    @Test
    void monoFlatMapMany() {
        StepVerifier.create(operator2.monoFlatMapMany()).expectNextCount(10).verifyComplete();
    }

    @Test
    void defaultIfEmpty1() {
        StepVerifier.create(operator2.defaultIfEmpty1())
                .expectNext(30)
                .verifyComplete();
    }

    @Test
    void switchIfEmpty() {
        StepVerifier.create(operator2.switchIfEmpty())
                .expectNext(60)
                .verifyComplete();
    }


    @Test
    void switchIfEmpty2() {
        StepVerifier.create(operator2.switchIfEmpty2())
                .expectError()
                .verify();
    }

    @Test
    void FluxMerge(){
        StepVerifier.create(operator2.FluxMerge())
                .expectNext("1","2","3","4","5","6")
                .verifyComplete();
    }

    @Test
    void MonoMerge(){
        StepVerifier.create(operator2.MonoMerge())
                .expectNext("1","2","3")
                .verifyComplete();
    }

    @Test
    void FluxZip(){
        StepVerifier.create(operator2.FluxZip())
                .expectNext("ad","be","cf")
                .verifyComplete();
    }


    @Test
    void MonoZip(){
        StepVerifier.create(operator2.MonoZip())
                .expectNext(6)
                .verifyComplete();
    }
}