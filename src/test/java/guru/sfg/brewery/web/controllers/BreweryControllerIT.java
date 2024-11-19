package guru.sfg.brewery.web.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
public class BreweryControllerIT extends BaseIT {

    @Test
    void listBreweriesAdminRoleSuccess() throws Exception {
        mockMvc.perform(get("/brewery/breweries")
                        .with(httpBasic("spring", "guru")))
                .andExpect(status().isOk());
    }

    @Test
    void listBreweriesCustomerRoleSuccess() throws Exception {
        mockMvc.perform(get("/brewery/breweries")
                        .with(httpBasic("scott", "tiger")))
                .andExpect(status().isOk());
    }

    @Test
    void listBreweriesUserRoleForbidden() throws Exception {
        mockMvc.perform(get("/brewery/breweries")
                        .with(httpBasic("user", "password")))
                .andExpect(status().isForbidden());
    }

    @Test
    void listBreweriesNotAuthForbidden() throws Exception {
        mockMvc.perform(get("/brewery/breweries"))
                .andExpect(status().isUnauthorized());
    }


    @Test
    void getBreweriesJsonAdminRoleSuccess() throws Exception {
        mockMvc.perform(get("/brewery/api/v1/breweries")
                        .with(httpBasic("spring", "guru")))
                .andExpect(status().isOk());

    }

    @Test
    void getBreweriesJsonCustomerRoleSuccess() throws Exception {
        mockMvc.perform(get("/brewery/api/v1/breweries")
                        .with(httpBasic("scott", "tiger")))
                .andExpect(status().isOk());

    }

    @Test
    void getBreweriesJsonUserRoleForbidden() throws Exception {
        mockMvc.perform(get("/brewery/api/v1/breweries")
                        .with(httpBasic("user", "password")))
                .andExpect(status().isForbidden());

    }


    @Test
    void getBreweriesJsonNotAuthForbidden() throws Exception {
        mockMvc.perform(get("/brewery/api/v1/breweries"))
                .andExpect(status().isUnauthorized());

    }
}