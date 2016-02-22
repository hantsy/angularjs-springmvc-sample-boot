package com.hantsylabs.restexample.springmvc.model;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author Hantsy Bai<hantsy@gmail.com>
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentForm implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @NotNull
    @NotEmpty
    private String content;

}
