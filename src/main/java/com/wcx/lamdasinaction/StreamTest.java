package com.wcx.lamdasinaction;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class StreamTest {
    public static void main(String[] args) {
        MapTest3();


    }

    private static void MapTest1() {
        List<String> words = Arrays.asList("Hello", "World");
        Stream<String> stream = words.stream();
        Stream<String[]> stream1 = stream.map(word -> word.split(""));
        //stream1.forEach(System.out::println);
        System.out.println("**********************************************");
        Stream<String[]> distinct = stream1.distinct();
        //distinct.forEach(System.out::println);
        List<String[]> list = distinct.collect(toList());
        for(String[] arr : list){
            for(String s : arr){
               System.out.print(s);
            }
            System.out.println();
        }
    }

    private static void MapTest2() {
        List<String> words = Arrays.asList("Hello", "World");
        for(String s : words){
            System.out.print(s);
        }
        System.out.println();
        Stream<String> stream = words.stream();
        stream.forEach(e -> {
            System.out.print(e + "&&&");
        });
        System.out.println("*****************");
        String[] words2 = {"Hello","World"};
        Stream<String> streamm = Arrays.stream(words2);
        streamm.forEach(e -> {
            System.out.print(e + "***");
        });


        /*Stream<String[]> stream1 = stream.map(w -> w.split(""));
        Stream<Stream<String>> streamStream = stream1.map(Arrays::stream);
        Stream<Stream<String>> distinct = streamStream.distinct();
        List<Stream<String>> collect = distinct.collect(toList());
       for(Stream<String> st : collect){
           st.forEach(System.out::print);
           System.out.println("   ");
       }*/
    }

    private static void MapTest3() {
        List<String> words = Arrays.asList("Hello", "World");
        Stream<String> stream = words.stream();
        Stream<String[]> stream1 = stream.map(s -> s.split(""));
        //Stream<String> stream2 = stream1.flatMap(Arrays::stream);
        Stream<String> stream2 = stream1.flatMap(strings -> Arrays.stream(strings));
        Stream<String> distinct = stream2.distinct();
        List<String> res = distinct.collect(toList());
        System.out.println(res);
    }
}
