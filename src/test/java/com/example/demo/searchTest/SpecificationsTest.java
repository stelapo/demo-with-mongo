package com.example.demo.searchTest;

import com.example.demo.model.SearchCriteria;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.specification.UserSpecification;
import com.example.demo.repository.specification.UserSpecificationsBuilder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.data.jpa.domain.Specification.where;

@DataJpaTest
@TestMethodOrder(MethodOrderer.MethodName.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SpecificationsTest {

    @Autowired
    UserRepository repository;

    private User userLP;
    private User userMR;


    @BeforeAll
    public void init() {
        userLP = new User();
        userLP.setName("Lapo");
        userLP.setSurname("Pancani");
        userLP.setEmail("lpancani@info.com");
        userLP.setAddress("Via Pinco, Firenze");
        repository.save(userLP);

        userMR = new User();
        userMR.setName("Mario");
        userMR.setSurname("Rossi");
        userMR.setEmail("mrossi@info.com");
        userMR.setAddress("Via Pallino, Firenze");
        repository.save(userMR);
    }

    @Test
    public void test01searchWith_Surname() {
        UserSpecification spec =
                new UserSpecification(new SearchCriteria("surname", ":", "pancani"));

        List<User> results = repository.findAll(spec);

        assertThat(userLP, is(in(results)));
        assertThat(userMR, not(is(in(results))));
        Assertions.assertThat(userMR).isNotIn(results);
    }

    @Test
    public void test02searchWith_Name() {
        UserSpecification spec =
                new UserSpecification(new SearchCriteria("name", ":", "ArI"));

        List<User> results = repository.findAll(spec);

        assertThat(userLP, not(is(in(results))));
        assertThat(userMR, is(in(results)));
    }

    @Test
    public void test03searchWith_Name_And_Surname() {
        UserSpecification spec1 =
                new UserSpecification(new SearchCriteria("name", ":", "ArI"));
        UserSpecification spec2 =
                new UserSpecification(new SearchCriteria("surname", ":", "rossi"));

        List<User> results = repository.findAll(where(spec1).and(spec2));

        assertThat(userLP, not(is(in(results))));
        assertThat(userMR, is(in(results)));
    }

    private static Stream<Arguments> test04searchWith_SearchString() {
        return Stream.of(
                Arguments.of("name:\"apo\"~surname:\"anc\"", 1),
                Arguments.of("name:\"ar\"~surname:\"ss\"", 2),
                Arguments.of("name:\"ma\"", 3),
                Arguments.of("surname:\"ncani\"", 4),
                Arguments.of("", 5)
        );
    }

    @ParameterizedTest
    @MethodSource
    public void test04searchWith_SearchString(String searchString, int index) {
        UserSpecificationsBuilder builder = new UserSpecificationsBuilder();
        Pattern pattern = Pattern.compile(SearchCriteria.searchStringPattern);
        Matcher matcher = pattern.matcher(searchString);
        while (matcher.find()) {
            builder.addSearchCriteria(matcher.group(1), matcher.group(2), matcher.group(3));
        }

        Specification<User> spec = builder.build();
        List<User> results = repository.findAll(spec);

        if (index == 1 || index == 4) {
            assertThat(userLP, is(in(results)));
            assertThat(userMR, not(is(in(results))));
        } else if (index == 2 || index == 3) {
            assertThat(userLP, not(is(in(results))));
            assertThat(userMR, is(in(results)));
        } else {
            assertThat(userLP, is(in(results)));
            assertThat(userMR, is(in(results)));
        }

    }
}
