package com.example.demo.controller;

import com.example.demo.dto.Movie;
import com.example.demo.service.MovieService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@WebMvcTest
public class MovieControllerTest {

    private Movie movie;
    private List<Movie> movieList = new ArrayList<>();

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private MovieService movieService;

    @Before
    public void setup() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
        movie = new Movie();
        movieList.clear();
    }

    @Test
    void 영화목록을_가져온다() throws Exception {
        movieList.add(new Movie("superman", 8.5));
        movieList.add(new Movie("superman2", 9.0));
        when(movieService.getMovies()).thenReturn(movieList);

        mockMvc.perform(MockMvcRequestBuilders.get("/movies")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(jsonPath("$", hasSize(2))).andDo(print());
    }

    @Test
    void 영화를_추가한다() throws Exception {
        movie = new Movie("aquaman", 6.5);
        when(movieService.addMovie(any(Movie.class))).thenReturn(movie);
        ObjectMapper objectMapper = new ObjectMapper();
        String movieJSON = objectMapper.writeValueAsString(movie);

        ResultActions result = mockMvc.perform(post("/movie")
                .contentType(MediaType.APPLICATION_JSON)
                .content(movieJSON)
        );

        result.andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(movie.getName()))
                .andExpect(jsonPath("$.rating").value(movie.getRating()))
                .andDo(print());
    }
}
