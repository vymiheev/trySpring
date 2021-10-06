package com.example.appsmart;

import com.example.appsmart.controller.CustomerController;
import com.example.appsmart.converter.TokenService;
import com.example.appsmart.security.roles.UserRole;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.example.appsmart.RepositoryTest.init;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-test.properties")
public class SecurityTest {
    @Autowired
    private CustomerController controller;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TokenService tokenService;

    @Test
    public void allGetMethodsAreAccessible() throws Exception {
        init();
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customers"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(notNullValue()));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customers/475d9655-4630-4a8e-8b15-1f2d6849785b"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(notNullValue()));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customers/475d9655-4630-4a8e-8b15-1f2d6849785b/products"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(notNullValue()));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/products/cd8e82bf-2a8d-48c8-8d60-ace3a87a4a6b"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(notNullValue()));
    }

    @Test
    public void modifiedMethodsAreNotAccessible() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/customers/475d9655-4630-4a8e-8b15-1f2d6849785b"))
                .andDo(print()).andExpect(status().isForbidden())
                .andExpect(content().string(Matchers.blankString()));

        mockMvc.perform(post("/api/v1/customers/475d9655-4630-4a8e-8b15-1f2d6849785b/products"))
                .andDo(print()).andExpect(status().isForbidden())
                .andExpect(content().string(Matchers.blankString()));

        //...
    }

    @Test
    public void modifiedMethodsAreAccessibleByAdmin() throws Exception {
        init();
        String adminToken = tokenService.generateToken(new UserRole(1, "username", "password", true));

        mockMvc.perform(post("/api/v1/customers/475d9655-4630-4a8e-8b15-1f2d6849785b/products")
                        .header("Authorization", adminToken))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(notNullValue()));

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/customers/988025e7-32c8-4a9c-a578-ff09681c8931")
                        .header("Authorization", adminToken))
                .andDo(print()).andExpect(status().isNoContent())
                .andExpect(content().string(notNullValue()));

        //...
    }

    @Test
    public void modifiedMethodsAreNotAccessibleByCommonUser() throws Exception {
        init();
        String token = tokenService.generateToken(new UserRole(1, "username", "password", false));

        mockMvc.perform(post("/api/v1/customers/475d9655-4630-4a8e-8b15-1f2d6849785b/products")
                        .header("Authorization", token))
                .andDo(print()).andExpect(status().isForbidden())
                .andExpect(content().string(Matchers.blankString()));

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/customers/475d9655-4630-4a8e-8b15-1f2d6849785b")
                        .header("Authorization", token))
                .andDo(print()).andExpect(status().isForbidden())
                .andExpect(content().string(Matchers.blankString()));

        //...
    }
}
