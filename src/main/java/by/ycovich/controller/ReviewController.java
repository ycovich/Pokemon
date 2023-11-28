package by.ycovich.controller;

import by.ycovich.dto.ReviewDTO;
import by.ycovich.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ReviewController {
    private final ReviewService reviewService;
    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/pokemon/{pokemonId}/reviews")
    public ResponseEntity<ReviewDTO> createReview(@PathVariable(value = "pokemonId") int pokemonId,
                                                  @RequestBody ReviewDTO reviewDto) {
        return new ResponseEntity<>(reviewService.createReview(pokemonId, reviewDto), HttpStatus.CREATED);
    }

    @GetMapping("/pokemon/{pokemonId}/reviews")
    public List<ReviewDTO> getReviewsByPokemonId(@PathVariable(value = "pokemonId") int pokemonId) {
        return reviewService.getReviewsByPokemonId(pokemonId);
    }

    @GetMapping("/pokemon/{pokemonId}/reviews/{reviewId}")
    public ResponseEntity<ReviewDTO> getReviewById(@PathVariable(value = "pokemonId") int pokemonId,
                                                   @PathVariable(value = "reviewId") int reviewId) {
        ReviewDTO reviewDto = reviewService.getReviewById(pokemonId, reviewId);
        return new ResponseEntity<>(reviewDto, HttpStatus.OK);
    }

    @PutMapping("/pokemon/{pokemonId}/reviews/{reviewId}")
    public ResponseEntity<ReviewDTO> updateReview(@PathVariable(value = "pokemonId") int pokemonId,
                                                  @PathVariable(value = "reviewId") int reviewId,
                                                  @RequestBody ReviewDTO reviewDto) {
        ReviewDTO updatedReview = reviewService.updateReview(pokemonId, reviewId, reviewDto);
        return new ResponseEntity<>(updatedReview, HttpStatus.OK);
    }

    @DeleteMapping("/pokemon/{pokemonId}/reviews/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable(value = "pokemonId") int pokemonId,
                                               @PathVariable(value = "reviewId") int reviewId) {
        reviewService.deleteReview(pokemonId, reviewId);
        return new ResponseEntity<>("review deleted successfully", HttpStatus.OK);
    }
}
