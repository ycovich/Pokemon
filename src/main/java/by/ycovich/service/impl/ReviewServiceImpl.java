package by.ycovich.service.impl;

import by.ycovich.dto.ReviewDTO;
import by.ycovich.exception.PokemonNotFoundException;
import by.ycovich.exception.ReviewNotFoundException;
import by.ycovich.model.Pokemon;
import by.ycovich.model.Review;
import by.ycovich.repository.PokemonRepository;
import by.ycovich.repository.ReviewRepository;
import by.ycovich.service.ReviewService;
import by.ycovich.util.mapper.ReviewMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final PokemonRepository pokemonRepository;
    private final ReviewMapper reviewMapper;
    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository, PokemonRepository pokemonRepository, ReviewMapper reviewMapper) {
        this.reviewRepository = reviewRepository;
        this.pokemonRepository = pokemonRepository;
        this.reviewMapper = reviewMapper;
    }

    @Override
    public ReviewDTO createReview(int pokemonId, ReviewDTO reviewDTO) {
        Review review = reviewMapper.mapToEntity(reviewDTO);

        Pokemon pokemon = pokemonRepository
                .findById(pokemonId)
                .orElseThrow(() -> new PokemonNotFoundException("pokemon with associated review not found"));

        review.setPokemon(pokemon);

        Review newReview = reviewRepository.save(review);

        return reviewMapper.mapToDTO(newReview);
    }

    @Override
    public List<ReviewDTO> getReviewsByPokemonId(int id) {
        List<Review> reviews = reviewRepository.findByPokemonId(id);

        return reviews
                .stream()
                .map(reviewMapper::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ReviewDTO getReviewById(int reviewId, int pokemonId) {
        Pokemon pokemon = pokemonRepository
                .findById(pokemonId)
                .orElseThrow(() -> new PokemonNotFoundException("pokemon with associated review not found"));

        Review review = reviewRepository
                .findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException("review with associate pokemon not found"));

        if(review.getPokemon().getId() != pokemon.getId()) {
            throw new ReviewNotFoundException("This review does not belong to a pokemon");
        }

        return reviewMapper.mapToDTO(review);
    }

    @Override
    public ReviewDTO updateReview(int pokemonId, int reviewId, ReviewDTO updatedReviewDTO) {
        Pokemon pokemon = pokemonRepository
                .findById(pokemonId)
                .orElseThrow(() -> new PokemonNotFoundException("pokemon with associated review not found"));

        Review reviewToBeUpdated = reviewRepository
                .findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException("Review with associate pokemon not found"));

        if(reviewToBeUpdated.getPokemon().getId() != pokemon.getId()) {
            throw new ReviewNotFoundException("This review does not belong to a pokemon");
        }

        reviewToBeUpdated.setTitle(updatedReviewDTO.getTitle());
        reviewToBeUpdated.setContent(updatedReviewDTO.getContent());
        reviewToBeUpdated.setStars(updatedReviewDTO.getStars());

        Review updateReview = reviewRepository.save(reviewToBeUpdated);

        return reviewMapper.mapToDTO(updateReview);
    }

    @Override
    public void deleteReview(int pokemonId, int reviewId) {
        Pokemon pokemon = pokemonRepository
                .findById(pokemonId)
                .orElseThrow(() -> new PokemonNotFoundException("pokemon with associated review not found"));

        Review reviewToBeDeleted = reviewRepository
                .findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException("Review with associate pokemon not found"));

        if(reviewToBeDeleted.getPokemon().getId() != pokemon.getId()) {
            throw new ReviewNotFoundException("This review does not belong to a pokemon");
        }

        reviewRepository.delete(reviewToBeDeleted);
    }

}
