package com.wcx.lamdasinaction;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class CreateStream {

    public static void main(String[] args) {
        Stream<String> stream = CreateStreamFromCollection();
        System.out.println(stream);//java.util.stream.ReferencePipeline$Head@7ea987ac
        /*stream.forEach(e -> {
            System.out.print(e + " ");
        });*/

        Stream<String> stream2 = CreateStreamFromValues();
        System.out.println(stream2);//java.util.stream.ReferencePipeline$Head@12a3a380
        //stream.forEach(System.out::println);

        Stream<String> stream3 = CreateStreamFromArrays();
        System.out.println(stream3);//java.util.stream.ReferencePipeline$Head@29453f44
        //stream.forEach(System.out::println);

        Stream<String> stream4 = createStreamFromFile();
        stream.forEach(System.out::println);

        createFromGenerate().forEach(System.out::println);
        createFromIterate().forEach(System.out::println);

    }

    private static Stream<String> CreateStreamFromCollection(){
        List<String> list = Arrays.asList("hello", "alex", "world", "stream");
        return list.stream();
    }

    private static Stream<String> CreateStreamFromValues(){
        return Stream.of("hello", "alex", "world", "stream");
    }

    private static Stream<String> CreateStreamFromArrays(){
       // return Arrays.stream(new String[]{"hello", "alex", "world", "stream"});
        String[] strings = new String[]{"hello", "alex", "world", "stream"};
        return Arrays.stream(strings);
    }

    private static Stream<String> createStreamFromFile(){
        Path path = Paths.get("E:\\idea_workspace\\Java8InAction\\src\\main\\java\\lambdasinaction\\chap3\\Sorting.java");
        try(Stream<String> streamFromFile = Files.lines(path)){
             streamFromFile.forEach(System.out::println);
             return streamFromFile;
        }catch (IOException e){
            throw new RuntimeException(e);
        }

    }

    private static Stream<Integer> createFromIterate(){
        Stream<Integer> stream = Stream.iterate(0,n -> n+2);//0,2,4,6,8......
        return stream;
    }

    private static Stream<Double> createFromGenerate(){
        return Stream.generate(Math::random);
    }

    private  static  Stream<Obj> createObjStreamFromGenerate(){
        return Stream.generate(new ObjSupplier()).limit(10);
    }

    static class Obj{
        private int id;
        private String name;

        public Obj(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public Obj() {
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Obj{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    static class ObjSupplier implements Supplier<Obj>{
        private int index = 0;
        private Random random = new Random(System.currentTimeMillis());

        @Override
        public Obj get() {
            index = random.nextInt(100);
            return new Obj(index,"Name->" + index);
        }
    }



}
