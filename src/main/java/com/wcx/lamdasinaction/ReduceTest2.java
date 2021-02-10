package com.wcx.lamdasinaction;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class ReduceTest2 {
    public static void main(String[] args) {
        reduceTest5();
        reduceTest6();
        reduceTest8();
    }


    private static void reduceTest1() {
        List<Integer> numbers = Arrays.asList(1,2,3);
        int total = 0;
        for(int i: numbers){
            total += i;
        }
        System.out.println(total);
    }

    private static void reduceTest2() {
        List<Integer> numbers = Arrays.asList(1,2,3);
        int total = numbers.stream()
                .reduce(0, (i,j) -> i+j);
        System.out.println(total);
    }

    private static void reduceTest3() {
        List<Integer> numbers = Arrays.asList(1,2,3);
        int total = numbers.stream()
                .reduce(0,(i,j)->{
                    System.out.printf("In accumulator i=%d j=%d -> return: %d \n", i, j, i+j);
                    return i+j;
                });
        System.out.printf("total : %d\n",total);
    }

    private static void reduceTest4() {
        List<Integer> numbers = Arrays.asList(1,2,3);
        Optional<Integer> total = numbers.stream()
                .reduce((i,j)->{
                    System.out.printf("In accumulator i=%d j=%d -> return: %d \n", i, j, i+j);
                    return i+j;
                });
        total.ifPresent(s->System.out.printf("total : %d",s));
    }

    private static void reduceTest5() {
        List<String> numbers = Arrays.asList("a", "aa", "aaa","aaaa","aaaaa");
        Integer sum = numbers.stream()
                .reduce(0,
                        (i,s)->{
                            System.out.printf("In accumulator i=%d s=%s -> return: %d \n", i, s, i+s.length());
                            return i + s.length();
                        },
                        (i,j)->{
                            System.out.printf("In combiner i=%d j=%d -> return: %d\n", i, j, i+j);
                            return i + j;
                        });
        System.out.printf("sum: %d\n",sum);
    }

    private static void reduceTest6() {
        List<String> numbers = Arrays.asList("a", "aa", "aaa","aaaa","aaaaa");
        Integer sum = numbers.parallelStream()
                .reduce(0,
                        (i,s)->{
                            System.out.printf("In accumulator i=%d s=%s -> return: %d  Thread: %s \n", i, s, i+s.length(),Thread.currentThread().getName());
                            return i + s.length();
                        },
                        (i,j)->{
                            System.out.printf("In combiner i=%d j=%d -> return: %d Thread: %s \n", i, j, i+j,Thread.currentThread().getName());
                            return i + j;
                        });
        System.out.printf("sum: %d\n",sum);
    }
    private static void reduceTest7(){
        int length = Arrays.asList("one", "two","three","four")
                .parallelStream()
                .reduce(0,
                        (accumulatedInt, str) -> accumulatedInt + str.length(),     //accumulator
                        (accumulatedInt, accumulatedInt2) -> accumulatedInt + accumulatedInt2);
    }

    private static void reduceTest8(){
        int length = Arrays.asList("one", "two","three","four")
                .parallelStream()
                .reduce(0,
                        (accumulatedInt, str) -> {
                            System.out.printf("In accumulator accumulatedInt=%d str=%s -> return: %d  Thread: %s\n",
                                                       accumulatedInt, str, accumulatedInt + str.length(),Thread.currentThread().getName());
                            return accumulatedInt + str.length();
                        },     //accumulator
                        (accumulatedInt, accumulatedInt2) -> {
                            System.out.printf("In combiner accumulatedInt=%d accumulatedInt2=%s -> return: %d ===> Thread: %s \n",
                                                       accumulatedInt,accumulatedInt2,accumulatedInt + accumulatedInt2,Thread.currentThread().getName());
                            return accumulatedInt + accumulatedInt2;
                        });
        System.out.println(length);
    }

    private  static void reduceTest9(){
        List<Integer> list = Collections.synchronizedList(new ArrayList());

        AtomicInteger atomicInteger = new AtomicInteger(0);
        AtomicInteger atomicInteger2 = new AtomicInteger(0);
        List<Integer> reduce = IntStream.range(0, 100)
                .boxed()
                .parallel()
                .reduce(list, (i, j) -> {
                    atomicInteger2.incrementAndGet();
                    i.add(j);
                    return i;
                }, (i, j) -> {
                    atomicInteger.incrementAndGet();
                   // System.out.println(StrUtil.format("{},name:{}", i == j, Thread.currentThread().getName()));
                    return i;
                });

        System.out.println(reduce.size());
        System.out.println(atomicInteger.get() + "次！");
        System.out.println(atomicInteger2.get() + "次！");
    }
}
