package com.wcx.lamdasinaction;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import static java.util.Comparator.comparing;

public class LamdaUsage {

    public static void main(String[] args) {
        Function<String,Integer> fun = s -> Integer.parseInt(s);

        List<Apple> inventory = new ArrayList<>();
        Function<Apple,Integer> fun2 = a -> a.getWeight();
        inventory.sort(comparing(fun2));

        inventory.sort(comparing(aa -> aa.getWeight()));

        Comparator<Apple> c = (a1,a2) -> a1.getWeight().compareTo(a2.getWeight());


    }
    Integer apply(Apple a){
        return a.getWeight();
    }
    <T,U extends Comparable<? super U>> Comparator<T> comparing2(Function<? super T, ? extends U> getKey){
        Objects.requireNonNull(getKey);
        Comparator<T> c = (e1,e2) -> getKey.apply(e1).compareTo(getKey.apply(e2));
        return c;
    }
    public static class Apple {
        private Integer weight = 0;
        private String color = "";

        public Apple(Integer weight, String color){
            this.weight = weight;
            this.color = color;
        }

        public Integer getWeight() {
            return weight;
        }

        public void setWeight(Integer weight) {
            this.weight = weight;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String toString() {
            return "Apple{" +
                    "color='" + color + '\'' +
                    ", weight=" + weight +
                    '}';
        }
    }
}
