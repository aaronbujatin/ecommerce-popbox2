package org.xyz.chatsvc.component;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;
import org.xyz.chatsvc.dto.ProductDto;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Component
public class ProductTools {

    @Tool(description =
            """
                Search Popbox products by name or keyword.
                Use this when the customer asks about products,
                prices, or availability.
            """
    )
    public List<ProductDto> searchProducts(String query) {
        return List.of(
                new ProductDto(1L, "Skull panda", BigDecimal.valueOf(1000)),
                new ProductDto(2L, "Hirono", BigDecimal.valueOf(2000)),
                new ProductDto(3L, "Crybabay", BigDecimal.valueOf(300))
        );
    }


}
