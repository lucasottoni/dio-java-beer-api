package one.digitalinnovation.beerstock.controller.beercontroller;

import one.digitalinnovation.beerstock.builder.BeerDTOBuilder;
import one.digitalinnovation.beerstock.controller.BeerController;
import one.digitalinnovation.beerstock.dto.BeerDTO;
import one.digitalinnovation.beerstock.exception.BeerNotFoundException;
import one.digitalinnovation.beerstock.service.BeerService;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static one.digitalinnovation.beerstock.utils.JsonConvertionUtils.asJsonString;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BeerControllerDELETEHelper {

    private static final long INVALID_BEER_ID = 2L;

    private MockMvc mockMvc;

    private BeerService beerService;

    private BeerController beerController;

    private String rootPath;

    public BeerControllerDELETEHelper(MockMvc mockMvc, BeerService beerService, BeerController beerController, String rootPath) {
        this.mockMvc = mockMvc;
        this.beerService = beerService;
        this.beerController = beerController;
        this.rootPath = rootPath;
    }

    public void whenDELETEIsCalledWithValidIdThenNoContentStatusIsReturned() throws Exception {
        // given
        BeerDTO beerDTO = BeerDTOBuilder.builder().build().toBeerDTO();

        //when
        doNothing().when(beerService).deleteById(beerDTO.getId());

        // then
        mockMvc.perform(MockMvcRequestBuilders.delete(rootPath + "/" + beerDTO.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    public void whenDELETEIsCalledWithInvalidIdThenNotFoundStatusIsReturned() throws Exception {
        //when
        doThrow(BeerNotFoundException.class).when(beerService).deleteById(INVALID_BEER_ID);

        // then
        mockMvc.perform(MockMvcRequestBuilders.delete(rootPath + "/" + INVALID_BEER_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
