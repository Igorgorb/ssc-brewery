package guru.sfg.brewery.web.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
public class BreweryControllerIT extends BaseIT {

    @ParameterizedTest(name = "#{index} with [{arguments}]")
    @MethodSource("guru.sfg.brewery.web.controllers.BeerControllerIT#getStreamAdminCustomer")
    void listBreweriesAdminRoleSuccess(String user, String pwd) throws Exception {
        mockMvc.perform(get("/brewery/breweries")
                        .with(httpBasic(user, pwd)))
                .andExpect(status().is2xxSuccessful());
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


    @ParameterizedTest(name = "#{index} with [{arguments}]")
    @MethodSource("guru.sfg.brewery.web.controllers.BeerControllerIT#getStreamAdminCustomer")
    void getBreweriesJsonAdminRoleSuccess(String user, String pwd) throws Exception {
        mockMvc.perform(get("/brewery/api/v1/breweries")
                        .with(httpBasic(user, pwd)))
                .andExpect(status().is2xxSuccessful());

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