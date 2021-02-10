package com.wcx.lamdasinaction;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertTrue;

public class FlatMapTest2 {
    @Test
    public void flatMap() {

        List<Developer> team = new ArrayList<>();
        Stream<Developer> stream22 = team.stream();

        Developer developer1 = new Developer("esoteric");
        developer1.addLanguage("clojure");
        developer1.addLanguage("scala");
        developer1.addLanguage("groovy");
        developer1.addLanguage("go");

        Developer developer2 = new Developer("pragmatic");
        developer2.addLanguage("java");
        developer2.addLanguage("javascript");

        team.add(developer1);
        team.add(developer2);

        /*
            Stream<Developer> stream = team.stream();
            Stream<Set<String>> setStream = stream.map(d -> d.getLanguages());
            Stream<String> stream1 = setStream.flatMap(l -> l.stream());
            List<String> strings = stream1.collect(Collectors.toList());
        */
        Stream<Developer> stream = team.stream();
        Stream<Set<String>> setStream = stream.map(d -> d.getLanguages());

        setStream.map(new Function<Set<String>, Stream<String>>() {
            @Override
            public Stream<String> apply(Set<String> strings) {
                return strings.stream();
            }
        });
        /*Stream<Object> objectStream = setStream.map(new Function<Set<String>, Object>() {
            @Override
            public Object apply(Set<String> strings) {
                return strings.stream();
            }
        });*/
        Stream<String> stream1 = setStream.flatMap(strSet_lang -> {
                                                            Stream<String> stream2 = strSet_lang.stream();
                                                            return stream2;
                                                        }
                                                );
        //map方法将Stream<Set<String>>中的每个Set<String>分别映射成Stream<String>,n个Set得到n个流
        Stream<Stream<String>> streamStream = setStream.map(strSet_lang -> {
                    //Set<String> ---> Stream<String>
                    Stream<String> strStream = strSet_lang.stream();
                    return strStream;
                }
        );

        List<String> strings = stream1.collect(Collectors.toList());


        List<String> teamLanguages = team.stream().
                map(developer -> developer.getLanguages()).
                flatMap(languages -> languages.stream()).
                collect(Collectors.toList());
        assertTrue(teamLanguages.containsAll(developer1.getLanguages()));
        assertTrue(teamLanguages.containsAll(developer2.getLanguages()));
    }

}


class Developer {

    private String name;
    private Set<String> languages;

    public Developer(String name) {
        this.languages = new HashSet<>();
        this.name = name;
    }

    public void addLanguage(String language) {
        this.languages.add(language);
    }

    public Set<String> getLanguages() {
        return languages;
    }
}
