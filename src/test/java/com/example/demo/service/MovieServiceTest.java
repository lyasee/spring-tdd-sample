package com.example.demo.service;

import com.example.demo.dto.Movie;
import com.example.demo.repository.MovieRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class MovieServiceTest {

    @Autowired
    private MovieService movieService;

    @Autowired
    private MovieRepository movieRepository;

    @Test
    void 영화목록을_가져온다() {
         // given
        Movie movie = new Movie("aquaman", 8.2);
        movieRepository.save(movie);

        // when
        List<Movie> movieList = movieService.getMovies();
        Movie lastMovie = movieList.get(movieList.size() - 1);

         // then
        assertEquals(movie.getName(), lastMovie.getName());
        assertEquals(movie.getRating(), lastMovie.getRating());
    }

    @Test
    void 영화를_추가한다 () {
        // given
        Movie movie = new Movie("aquaman", 6.5);

        // when
        Movie resultMovie = movieService.addMovie(movie);

        // then
        assertEquals(resultMovie.getName(), movie.getName());
        assertEquals(resultMovie.getRating(), movie.getRating());
    }

    @Test
    @Transactional
    void 영화를_가져온다 () {
        Movie movie = new Movie(0,"aquaman", 8.2);
        movieRepository.save(movie);

        Movie resultMovie = movieService.getMovie(movie.getId());

        assertEquals(resultMovie.getName(), movie.getName());
        assertEquals(resultMovie.getRating(), movie.getRating());
    }

    @Test
    void when_verify_sample () {
        Movie movie = mock(Movie.class);

        when(movie.getName()).thenReturn("aquaman");
        when(movie.getRating()).thenReturn(8.8);

        assertEquals(movie.getName(), "aquaman");
        assertEquals(movie.getRating(), 8.8);

        // verifiy() 는 해당 구문이 호출 되었는지를 체크합니다.
        verify(movie, times(1)).getName();
        verify(movie, times(1)).getRating();
    }
}
