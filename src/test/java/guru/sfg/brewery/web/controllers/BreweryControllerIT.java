package guru.sfg.brewery.web.controllers;

import guru.sfg.brewery.security.annotation.AuthAsAdmin;
import guru.sfg.brewery.security.annotation.AuthAsCustomer;
import guru.sfg.brewery.security.annotation.AuthAsUser;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
public class BreweryControllerIT extends BaseIT {

    @Test
    @AuthAsAdmin
    void listBreweriesAdminRoleSuccess() throws Exception {
        mockMvc.perform(get("/brewery/breweries"))
//                        .with(httpBasic("spring", "guru")))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @AuthAsCustomer
    void listBreweriesCustomerRoleSuccess() throws Exception {
        mockMvc.perform(get("/brewery/breweries"))
                // .with(httpBasic("scott", "tiger")))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @AuthAsUser
    void listBreweriesUserRoleForbidden() throws Exception {
        mockMvc.perform(get("/brewery/breweries"))
//                        .with(httpBasic("user", "password")))
                .andExpect(status().isForbidden());
    }

    @Test
    void listBreweriesNotAuthForbidden() throws Exception {
        mockMvc.perform(get("/brewery/breweries"))
                .andExpect(status().isUnauthorized());
    }


    @Test
    @AuthAsAdmin
    void getBreweriesJsonAdminRoleSuccess() throws Exception {
        mockMvc.perform(get("/brewery/api/v1/breweries"))
//                        .with(httpBasic("spring", "guru")))
                .andExpect(status().is2xxSuccessful());

    }

    @Test
    @AuthAsCustomer
    void getBreweriesJsonCustomerRoleSuccess() throws Exception {
        mockMvc.perform(get("/brewery/api/v1/breweries"))
//                        .with(httpBasic("scott", "tiger")))
                .andExpect(status().is2xxSuccessful());

    }

    @Test
    @AuthAsUser
    void getBreweriesJsonUserRoleForbidden() throws Exception {
        mockMvc.perform(get("/brewery/api/v1/breweries"))
//                        .with(httpBasic("user", "password")))
                .andExpect(status().isForbidden());

    }


    @Test
    void getBreweriesJsonNotAuthForbidden() throws Exception {
        mockMvc.perform(get("/brewery/api/v1/breweries"))
                .andExpect(status().isUnauthorized());

    }
}