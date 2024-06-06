package com.spring.ecommerce.mapper;

import com.spring.ecommerce.dto.VariationDto;
import com.spring.ecommerce.dto.category.AddCategoryToParentDto;
import com.spring.ecommerce.dto.category.CategoryDto;
import com.spring.ecommerce.dto.category.ReturnCategoryDto;
import com.spring.ecommerce.dto.order.ReturnOrderDetailDto;
import com.spring.ecommerce.dto.order.ReturnOrderDto;
import com.spring.ecommerce.dto.product.ReturnProductDto;
import com.spring.ecommerce.dto.product.ReturnProductItemDto;
import com.spring.ecommerce.dto.rating.ReturnRatingDto;
import com.spring.ecommerce.dto.user.ReturnUserDto;
import com.spring.ecommerce.models.Category;
import com.spring.ecommerce.models.Order;
import com.spring.ecommerce.models.OrderDetail;
import com.spring.ecommerce.models.ParentCategory;
import com.spring.ecommerce.models.Product;
import com.spring.ecommerce.models.ProductItem;
import com.spring.ecommerce.models.Rating;
import com.spring.ecommerce.models.User;
import com.spring.ecommerce.models.Variation;
import com.spring.ecommerce.models.VariationValue;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

public class MapperTests {

    private final CategoryMapper categoryMapper = Mappers.getMapper(CategoryMapper.class);
    private final OrderDetailMapper orderDetailMapper = Mappers.getMapper(OrderDetailMapper.class);
    private final OrderMapper orderMapper = Mappers.getMapper(OrderMapper.class);
    private final ProductItemMapper productItemMapper = Mappers.getMapper(ProductItemMapper.class);
    private final ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);
    private final RatingMapper ratingMapper = Mappers.getMapper(RatingMapper.class);
    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);
    private final VariationMapper variationMapper = Mappers.getMapper(VariationMapper.class);

    @Test
    public void shouldMapCategoryToReturnCategoryDto() {
        Category category = new Category();
        category.setId(1L);
        category.setName("Electronics");
        category.setDescription("Electronic items");

        ReturnCategoryDto returnCategoryDto = categoryMapper.CategoryToReturnCategoryDto(category);

        assertThat(returnCategoryDto).isNotNull();
        assertThat(returnCategoryDto.getId()).isEqualTo(category.getId());
        assertThat(returnCategoryDto.getName()).isEqualTo(category.getName());
        assertThat(returnCategoryDto.getDescription()).isEqualTo(category.getDescription());
    }

    @Test
    public void shouldMapParentCategoryToReturnCategoryDto() {
        ParentCategory parentCategory = new ParentCategory();
        parentCategory.setId(1L);
        parentCategory.setParentCategoryName("Home Appliances");

        ReturnCategoryDto returnCategoryDto = categoryMapper.CategoryToReturnCategoryDto(parentCategory);

        assertThat(returnCategoryDto).isNotNull();
        assertThat(returnCategoryDto.getId()).isEqualTo(parentCategory.getId());
        assertThat(returnCategoryDto.getParentCategoryName()).isEqualTo(parentCategory.getParentCategoryName());
    }

    @Test
    public void shouldMapCategoryDtoToCategory() {
        CategoryDto categoryDto = CategoryDto.builder()
                .name("Electronics")
                .description("Electronic items")
                .build();

        ParentCategory parentCategory = categoryMapper.categoryDtoToCategory(categoryDto);

        assertThat(parentCategory).isNotNull();
        assertThat(parentCategory.getDescription()).isEqualTo(categoryDto.getDescription());
    }

    @Test
    public void shouldMapAddCategoryToParentDtoToCategory() {
        AddCategoryToParentDto addCategoryToParentDto = new AddCategoryToParentDto();
        addCategoryToParentDto.setName("Electronics");
        addCategoryToParentDto.setParentCategoryId(1L);

        Category category = categoryMapper.addCategoryToParentDtoToCategory(addCategoryToParentDto);

        assertThat(category).isNotNull();
        assertThat(category.getName()).isEqualTo(addCategoryToParentDto.getName());
    }

    @Test
    public void shouldMapOrderDetailToReturnOrderDetailDto() {
        Order order = new Order();
        order.setId(1L);

        Product product = new Product();
        product.setId(1L);
        product.setName("Product 1");
        product.setPrice(100.0);

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setId(1L);
        orderDetail.setOrder(order);
        orderDetail.setProduct(product);
        orderDetail.setQuantity(2);
        orderDetail.setPrice(200.0);

        ReturnOrderDetailDto returnOrderDetailDto = orderDetailMapper.orderDetailToReturnOrderDto(orderDetail);

        assertThat(returnOrderDetailDto).isNotNull();
        assertThat(returnOrderDetailDto.getId()).isEqualTo(orderDetail.getId());
        assertThat(returnOrderDetailDto.getQuantity()).isEqualTo(orderDetail.getQuantity());
        assertThat(returnOrderDetailDto.getPrice()).isEqualTo(orderDetail.getPrice());
    }

    @Test
    public void shouldMapOrderToReturnOrderDto() {
        Order order = new Order();
        order.setId(1L);
        order.setApproved(true);

        ReturnOrderDto returnOrderDto = orderMapper.orderToReturnOrderDto(order);

        assertThat(returnOrderDto).isNotNull();
        assertThat(returnOrderDto.getId()).isEqualTo(order.getId());
        assertThat(returnOrderDto.isApproved()).isEqualTo(order.isApproved());
    }

    @Test
    public void shouldMapProductItemToReturnProductItemDto() {
        ProductItem productItem = new ProductItem();
        productItem.setId(1L);
        productItem.setPrice(100.0);
        productItem.setAvailableStock(10);

        ReturnProductItemDto returnProductItemDto = productItemMapper.productItemToReturnProductItemDto(productItem);

        assertThat(returnProductItemDto).isNotNull();
        assertThat(returnProductItemDto.getId()).isEqualTo(productItem.getId());
        assertThat(returnProductItemDto.getPrice()).isEqualTo(productItem.getPrice());
        assertThat(returnProductItemDto.getAvailableStock()).isEqualTo(productItem.getAvailableStock());
    }

    @Test
    public void shouldMapProductToReturnProductDto() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Product 1");
        product.setPrice(100.0);

        ReturnProductDto returnProductDto = productMapper.productToReturnProductDto(product);

        assertThat(returnProductDto).isNotNull();
        assertThat(returnProductDto.getId()).isEqualTo(product.getId());
        assertThat(returnProductDto.getName()).isEqualTo(product.getName());
        assertThat(returnProductDto.getPrice()).isEqualTo(product.getPrice());
    }

    @Test
    public void shouldMapRatingToRatingDto() {
        Rating rating = new Rating();
        rating.setId(1L);
        rating.setRatingValue(5);
        rating.setReviewValue("Great product!");

        ReturnRatingDto ratingDto = ratingMapper.ratingToReturnRatingDto(rating);

        assertThat(ratingDto).isNotNull();
        assertThat(ratingDto.getId()).isEqualTo(rating.getId());
        assertThat(ratingDto.getRatingValue()).isEqualTo(rating.getRatingValue());
        assertThat(ratingDto.getReviewValue()).isEqualTo(rating.getReviewValue());
    }

    @Test
    public void shouldMapUserToReturnUserDto() {
        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setFirstName("John");
        user.setLastName("Doe");

        ReturnUserDto returnUserDto = userMapper.userToReturnUserDto(user);

        assertThat(returnUserDto).isNotNull();
        assertThat(returnUserDto.getId()).isEqualTo(user.getId());
        assertThat(returnUserDto.getEmail()).isEqualTo(user.getEmail());
        assertThat(returnUserDto.getFirstName()).isEqualTo(user.getFirstName());
        assertThat(returnUserDto.getLastName()).isEqualTo(user.getLastName());
    }

    @Test
    public void shouldMapVariationToVariationDto() {
        Variation variation = new Variation();
        variation.setId(1L);

        VariationDto variationDto = variationMapper.variationToVariationDto(variation);

        assertThat(variationDto).isNotNull();
        assertThat(variationDto.getId()).isEqualTo(variation.getId());
    }

    @Test
    public void shouldMapVariationValueToVariationDto() {
        VariationValue variationValue = new VariationValue();
        variationValue.setId(1L);
        variationValue.setVariationName("Large");

        VariationDto variationDto = variationMapper.variationToVariationDto(variationValue);

        assertThat(variationDto).isNotNull();
        assertThat(variationDto.getId()).isEqualTo(variationValue.getId());
        assertThat(variationDto.getVariationName()).isEqualTo(variationValue.getVariationName());
    }
}
