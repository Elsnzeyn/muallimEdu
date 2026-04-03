package org.example.muallimeduazbackend.dto.response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewsResponseDto {
    private int id;
    private String title;
    private String content;
    private int viewCount;
    private int likeCount;
    private int categoryId;
    private String categoryName;
}
