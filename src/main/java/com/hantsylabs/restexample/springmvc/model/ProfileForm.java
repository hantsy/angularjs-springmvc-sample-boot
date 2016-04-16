
package com.hantsylabs.restexample.springmvc.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author Hantsy Bai<hantsy@gmail.com>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileForm implements Serializable{

    private static final long serialVersionUID = 1L;
    
    @NotEmpty
    private String name;
    
    @NotEmpty
    private String email;

}
