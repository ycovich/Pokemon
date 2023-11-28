package by.ycovich.util.mapper;

import by.ycovich.dto.ReviewDTO;
import by.ycovich.model.Review;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {
    public ReviewDTO mapToDTO(Review review) {
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setId(review.getId());
        reviewDTO.setTitle(review.getTitle());
        reviewDTO.setContent(review.getContent());
        reviewDTO.setStars(review.getStars());
        return reviewDTO;
    }

    public Review mapToEntity(ReviewDTO reviewDTO) {
        Review review = new Review();
        review.setId(reviewDTO.getId());
        review.setTitle(reviewDTO.getTitle());
        review.setContent(reviewDTO.getContent());
        review.setStars(reviewDTO.getStars());
        return review;
    }
}
