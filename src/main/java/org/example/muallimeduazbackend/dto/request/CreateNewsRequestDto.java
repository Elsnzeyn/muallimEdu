package org.example.muallimeduazbackend.dto.request;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.muallimeduazbackend.constant.TextConstants;
import org.example.muallimeduazbackend.entity.Category;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateNewsRequestDto {
    private String title;
    private String content;
    private int categoryId;
}
