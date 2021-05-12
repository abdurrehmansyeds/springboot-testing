package com.abdur.testing.service;

import com.abdur.testing.entity.manytomany.Post;
import com.abdur.testing.repository.PostRepository;
import com.abdur.testing.service.impl.PostServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    static List<Integer> integerList;
    @Mock
    PostRepository postRepository;
    @InjectMocks
    PostServiceImpl postService;

    @BeforeAll
    static void setup() {
        integerList = new ArrayList<>();
    }

    @Test
    public void shouldReturnPost_whenIdGiven() {
        Post post = new Post();
        post.setTitle("Title");
        Optional<Post> optionalPost = Optional.of(post);

        when(postRepository.findById(1L)).thenReturn(optionalPost);
        Optional<Post> actual = postService.get(1L);
        verify(postRepository, times(1)).findById(1L);
        assertThat(actual.get(), hasProperty("title", is("Title")));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Junit in Action", "Testing parameterized test"})
    public void wordsCount(String sentence) {
        assertThat(3, equalTo(sentence.split(" ").length));
    }

    //@RepeatedTest has access to 3 things displayName,currentRepetition,totalRepetitions
    @RepeatedTest(value = 5,
            name = "{displayName} - repetition {currentRepetition}/{totalRepetitions}")
    @DisplayName("Test add operation")
    public void addNumber() {
        assertThat(4, equalTo(2 + 2));
    }


    //TestReporter and TestInfo are ParameterResolver that gets injected by Junit
    //RepetitionInfo is special kind of PR that is instantiated when using @RepeatedTest
    @RepeatedTest(value = 4, name = "the list contains {currentRepetition} elements")
    void testAddingToCollections(TestReporter testReporter, RepetitionInfo repetitionInfo) {
        integerList.add(repetitionInfo.getCurrentRepetition());
        testReporter.publishEntry("Repetition Number",
                String.valueOf(repetitionInfo.getCurrentRepetition()));
        assertThat(repetitionInfo.getCurrentRepetition(), equalTo(integerList.size()));
    }
}
