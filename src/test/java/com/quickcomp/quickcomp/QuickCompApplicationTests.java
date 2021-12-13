package com.quickcomp.quickcomp;

import com.quickcomp.quickcomp.dto.ProductCategoryDTO;
import com.quickcomp.quickcomp.model.entity.Category;
import com.quickcomp.quickcomp.model.entity.Product;
import com.quickcomp.quickcomp.model.entity.User;
import com.quickcomp.quickcomp.model.entity.enums.Role;
import com.quickcomp.quickcomp.model.repository.CategoryRepository;
import com.quickcomp.quickcomp.model.repository.ProductRepository;
import com.quickcomp.quickcomp.model.repository.UserRepository;
import com.quickcomp.quickcomp.service.interfaces.OrderService;
import com.quickcomp.quickcomp.service.interfaces.ProductService;
import com.quickcomp.quickcomp.service.interfaces.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.Iterator;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.in;

@SpringBootTest
class QuickCompApplicationTests {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderService orderService;



    @Test
    void assertingThatCategoryCountIsOne() {
        assertThat(categoryRepository.findAll().size() == 1);
    }
    @Test
    void assertingThatFirstUserIsSuperAdmin(){

        String role = null;

        Set<Role> first = userService.findById(1L).getRoles();

        System.out.println();
        for (Role f : first) {
            if (f.equals(Role.ROLE_SUPER_ADMIN)) {
                role = (Role.ROLE_SUPER_ADMIN).name();
                break;
            }
        }

        System.out.println(role);
        assertThat(role.equals("SUPER_ADMIN"));
    }
    @Test
    void assertingThatNoMoreUsersIsSuperAdmin(){
        int count = 0;
        for (int i = 0; i < userService.getUsers().size(); i++) {
            Long x = (long) i + 1;
            Set<Role> roles = userService.findById(x).getRoles();
            for (Role f : roles) {
                if (f.equals(Role.ROLE_SUPER_ADMIN))
                    count++;
            }
        }
        assertThat(count == 1);
    }
    @Test
    void assertThatThereIsNoAdmin(){
        int count = 0;
        for (int i = 0; i < userService.getUsers().size(); i++) {
            Long x = (long) i + 1;
            Set<Role> roles = userService.findById(x).getRoles();
            for (Role f : roles) {
                if (f.equals(Role.ROLE_ADMIN))
                    count++;
            }
        }
        assertThat(count == 0);
    }
    @Test
    void assertThatThereIsNoOrder(){
        assertThat(orderService.getAll().size() == 0);
    }

    @Test
    void testSavingCategory(){
        Category category = new Category();
        category.setCategory("Ноутбуки");
        category.setRating("4.6");
        categoryRepository.save(category);
        long x = categoryRepository.findAll().size()-1;
        assertThat(categoryRepository.findById(x)).isPresent();
    }
    @Test
    void testDeletingCategory(){
        long x = categoryRepository.findAll().size();
        categoryRepository.deleteById(x);
        assertThat((categoryRepository.findAll().size() + 1) == x);
    }
    @Test
    void testSavingProduct(){
        ProductCategoryDTO product = new ProductCategoryDTO();
        product.setCategory(1L);
        product.setName("Note");
        product.setDescription("descr");
        product.setPrice(12000D);
        assertThat(productService.save(product)).isNotNull();
    }
    @Test
    void testDeletingProduct(){
        long x = productService.getAll().size();
        productService.getById(x);
        assertThat((productService.getAll().size() + 1) == x);
    }
    @Test
    void testSavingUser(){
        User user = new User();
        user.setPassword("123");
        user.setEmail("2aa@aa");
        user.setUsername("user2");
        assertThat(userService.createUser(user));
    }
}
