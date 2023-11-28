package by.ycovich.dto;

import lombok.Data;

@Data
public class ReviewDTO {
    private int id;
    private String title;
    private String content;
    private int stars;
}
