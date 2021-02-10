package com.wcx.lamdasinaction;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class FlatMapTest3 {
    public static void main(String[] args) {
        List<Integer> numbers1 = Arrays.asList(1, 2, 3);
        List<Integer> numbers2 = Arrays.asList(3, 4);
        List<int[]> pairs =
                numbers1.stream()
                        .flatMap(i -> numbers2.stream()
                                .map(j -> new int[]{i, j})
                        )
                        .collect(toList());


        Stream<int[]> flatStream = numbers1.stream()
                       .flatMap(i -> numbers2.stream()
                            .map(j -> new int[]{i, j})//将numbers2中的各个元素映射成流中的各个数组
                       );

        Stream<int[]> flatStream2
                = numbers1.stream()
                        .flatMap(i -> {
                                    Stream<Integer> stream = numbers2.stream();
                                    Stream<int[]> stream1 = stream.map(j -> new int[]{i, j});
                                    return stream1;//Stream<int[]>
                                }
                        );

        Stream<int[]> flatStream3
                = numbers1.stream()
                .flatMap(new Function<Integer, Stream<? extends int[]>>() {
                             @Override
                             public Stream<? extends int[]> apply(Integer element_in_nums1) {
                                 Stream<Integer> stream = numbers2.stream();
                                 Stream<int[]> int_arr_stream = stream.map(new Function<Integer, int[]>() {
                                     @Override
                                     public int[] apply(Integer element_in_nums2) {
                                         return new int[]{element_in_nums1,element_in_nums2};
                                     }

                                   /*  @Override
                                     public int[] apply(int j) {
                                         return new int[]{i, j};
                                     }*/
                                 });
                                 return int_arr_stream;
                             }
                         }
                );


        //map map
        Stream<Stream<int[]>> stream = numbers1.stream()
                .map(i -> { //将numbers1中的各个元素映射成各个数组流
                            Stream<Integer> stream1 = numbers2.stream();
                            Stream<int[]> stream2 = stream1.map(j -> new int[]{i, j});//将numbers2中的各个元素映射成流中的各个数组
                            return  stream2;//Stream<int[]>
                        }
                );
         stream.forEach(streamm -> {
             streamm.forEach(arr -> {
                 System.out.println(Arrays.toString(arr));
             });
         });
    }
}
