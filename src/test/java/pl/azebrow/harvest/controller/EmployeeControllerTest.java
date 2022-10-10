package pl.azebrow.harvest.controller;


import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.transaction.annotation.Transactional;
import pl.azebrow.harvest.integration.BaseIntegrationTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class EmployeeControllerTest extends BaseIntegrationTest {

    private final static String BASE_URL = "/login";

    @Test
    public void contextLoads() {

    }

    @Test
    @Transactional
    @WithUserDetails("janusz@admin.qp")
    public void contextLoads2() throws Exception {
        mockMvc.perform(get("/api/v1/employee"))
                .andExpect(status().isOk())
                .andDo(print());
    }

}