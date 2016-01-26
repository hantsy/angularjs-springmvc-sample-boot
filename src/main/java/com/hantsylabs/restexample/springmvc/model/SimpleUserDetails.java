
package com.hantsylabs.restexample.springmvc.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Hantsy Bai<hantsy@gmail.com>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SimpleUserDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String username;

    private String name;

}
