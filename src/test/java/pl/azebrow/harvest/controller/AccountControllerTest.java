package pl.azebrow.harvest.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.azebrow.harvest.model.Account;
import pl.azebrow.harvest.model.AccountStatus;
import pl.azebrow.harvest.request.AccountEmailUpdateRequest;
import pl.azebrow.harvest.request.AccountRequest;
import pl.azebrow.harvest.request.AccountUpdateRequest;
import pl.azebrow.harvest.service.AccountBuilder;
import pl.azebrow.harvest.service.AccountService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountController.class)
class AccountControllerTest {


    @Autowired
    MockMvc mockMvc;

    @MockBean
    AccountService accountService;

    @SpyBean
    ModelMapper mapper;

    private Account sampleAccount;

    @BeforeEach
    void setup(){
        sampleAccount = AccountBuilder.account().setId(123L).build();
    }

    @Test
    @WithMockUser(roles = "STAFF")
    void getAccount_with_valid_param_should_return_ok() throws Exception {
        when(accountService.findAccountById(any())).thenReturn(sampleAccount);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/v1/account/1")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(accountService, times(1)).findAccountById(anyLong());

    }

    @Test
    void getAccount_with_no_authority_should_return_unauthorized() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/v1/account/1")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "STAFF")
    void getAccount_with_zeroed_param_should_return_bad_request() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/account/0")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }


    @Test
    @WithMockUser(roles = "STAFF")
    void createStaffAccount_with_valid_params_should_return_ok() throws Exception {
        var validRequest = new AccountRequest("First", "Last", "e@ma.il");

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/account/")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(validRequest))
        ).andExpect(status().isCreated());
        verify(accountService, times(1)).createStaffAccount(any());
    }

    @Test
    @WithMockUser(roles = "STAFF")
    void createStaffAccount_with_invalid_param_should_return_bad_request() throws Exception {
        var invalidRequest = new AccountRequest("F", "", "not@valid");

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/account/")
                        .with(csrf()).with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(invalidRequest))
        ).andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "STAFF")
    void updateAccount_with_valid_params() throws Exception {
        var validRequest = new AccountUpdateRequest("Va", "Lid", false);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/v1/account/1")
                        .with(csrf()).with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(validRequest))
        ).andExpect(status().isNoContent());
        verify(accountService, times(1)).updateAccount(any(), any());
    }

    @Test
    @WithMockUser(roles = "STAFF")
    void updateAccount_with_invalid_params() throws Exception {
        var invalidRequest = new AccountUpdateRequest("Invalid", "", false);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/v1/account/1")
                        .with(csrf()).with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(invalidRequest))
        ).andExpect(status().isBadRequest());
        verify(accountService, times(0)).updateAccount(any(), any());
    }

    @Test
    @WithMockUser(roles = "STAFF")
    void updateAccountEmail_with_valid_params() throws Exception {
        var validRequest = new AccountEmailUpdateRequest("e@mail.qp");
        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/v1/account/email/1")
                        .with(csrf()).with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(validRequest))
        ).andExpect(status().isNoContent());
        verify(accountService, times(1)).updateAccountEmail(any(), any());
    }

    @Test
    @WithMockUser(roles = "STAFF")
    void updateAccountEmail_with_invalid_params() throws Exception {
        var invalidRequest = new AccountEmailUpdateRequest("");
        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/v1/account/email/1")
                        .with(csrf()).with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(invalidRequest))
        ).andExpect(status().isBadRequest());
        verify(accountService, times(0)).updateAccountEmail(any(), any());
    }


    private String asJson(Object o) {
        try {
            return new ObjectMapper().writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}