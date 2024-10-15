package com.example.hoover.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
public class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void testHandleGlobalException() throws Exception {
        // Simulate a request that triggers an exception
        MockHttpServletResponse response = mockMvc.perform(get("/some-endpoint"))
                .andReturn().getResponse();

        // Deserialize the response to a Map
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> jsonResponse = mapper.readValue(response.getContentAsString(), Map.class);

        // Extract the 'message' field and assert
        String actualMessage = (String) jsonResponse.get("message");
        String expectedMessage = "An unexpected error occurred.";

        assertEquals(expectedMessage, actualMessage);
    }
}
