package one.digitalinnovation.beerstock.controller;

import one.digitalinnovation.beerstock.builder.BeerDTOBuilder;
import one.digitalinnovation.beerstock.controller.beercontroller.BeerControllerDELETEHelper;
import one.digitalinnovation.beerstock.controller.beercontroller.BeerControllerGETHelper;
import one.digitalinnovation.beerstock.controller.beercontroller.BeerControllerPATCHHelper;
import one.digitalinnovation.beerstock.controller.beercontroller.BeerControllerPOSTHelper;
import one.digitalinnovation.beerstock.dto.BeerDTO;
import one.digitalinnovation.beerstock.dto.QuantityDTO;
import one.digitalinnovation.beerstock.exception.BeerNotFoundException;
import one.digitalinnovation.beerstock.exception.BeerStockExceededException;
import one.digitalinnovation.beerstock.service.BeerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Collections;

import static one.digitalinnovation.beerstock.utils.JsonConvertionUtils.asJsonString;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class BeerControllerTest {

    private static final String BEER_API_URL_PATH = "/api/v1/beers";
    private static final long VALID_BEER_ID = 1L;

    private MockMvc mockMvc;

    @Mock
    private BeerService beerService;

    @InjectMocks
    private BeerController beerController;

    private BeerControllerGETHelper getHelper;
    private BeerControllerPOSTHelper postHelper;
    private BeerControllerDELETEHelper deleteHelper;
    private BeerControllerPATCHHelper patchHelper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(beerController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();

        getHelper = new BeerControllerGETHelper(mockMvc, beerService, beerController, BEER_API_URL_PATH);
        postHelper = new BeerControllerPOSTHelper(mockMvc, beerService, beerController, BEER_API_URL_PATH);
        deleteHelper = new BeerControllerDELETEHelper(mockMvc, beerService, beerController, BEER_API_URL_PATH);
        patchHelper = new BeerControllerPATCHHelper(mockMvc, beerService, beerController, BEER_API_URL_PATH);

    }

    @Test
    void whenGETIsCalledWithValidNameThenOkStatusIsReturned() throws Exception {
        getHelper.whenGETIsCalledWithValidNameThenOkStatusIsReturned();
    }

    @Test
    void whenGETIsCalledWithoutRegisteredNameThenNotFoundStatusIsReturned() throws Exception {
        getHelper.whenGETIsCalledWithoutRegisteredNameThenNotFoundStatusIsReturned();
    }

    @Test
    void whenGETListWithBeersIsCalledThenOkStatusIsReturned() throws Exception {
        getHelper.whenGETListWithBeersIsCalledThenOkStatusIsReturned();
    }

    @Test
    void whenGETListWithoutBeersIsCalledThenOkStatusIsReturned() throws Exception {
        getHelper.whenGETListWithoutBeersIsCalledThenOkStatusIsReturned();
    }

    @Test
    void whenPOSTIsCalledThenABeerIsCreated() throws Exception {
        postHelper.whenPOSTIsCalledThenABeerIsCreated();
    }

    @Test
    void whenPOSTIsCalledWithoutRequiredFieldThenAnErrorIsReturned() throws Exception {
        postHelper.whenPOSTIsCalledWithoutRequiredFieldThenAnErrorIsReturned();
    }

    @Test
    void whenDELETEIsCalledWithValidIdThenNoContentStatusIsReturned() throws Exception {
        deleteHelper.whenDELETEIsCalledWithValidIdThenNoContentStatusIsReturned();
    }

    @Test
    void whenDELETEIsCalledWithInvalidIdThenNotFoundStatusIsReturned() throws Exception {
        deleteHelper.whenDELETEIsCalledWithInvalidIdThenNotFoundStatusIsReturned();
    }

    @Test
    void whenPATCHIsCalledToIncrementDiscountThenOKStatusIsReturned() throws Exception {
        patchHelper.whenPATCHIsCalledToIncrementDiscountThenOKStatusIsReturned();
    }

    @Test
    void whenPATCHIsCalledToIncrementGreaterThanMaxThenBadRequestStatusIsReturned() throws Exception {
        patchHelper.whenPATCHIsCalledToIncrementGreaterThanMaxThenBadRequestStatusIsReturned();
    }

    @Test
    void whenPATCHIsCalledWithInvalidBeerIdToIncrementThenNotFoundStatusIsReturned() throws Exception {
        patchHelper.whenPATCHIsCalledWithInvalidBeerIdToIncrementThenNotFoundStatusIsReturned();
    }

    @Test
    void whenPATCHIsCalledToDecrementDiscountThenOKstatusIsReturned() throws Exception {
        patchHelper.whenPATCHIsCalledToDecrementDiscountThenOKstatusIsReturned();
    }

    @Test
    void whenPATCHIsCalledToDecrementLowerThanZeroThenBadRequestStatusIsReturned() throws Exception {
        patchHelper.whenPATCHIsCalledToDecrementLowerThanZeroThenBadRequestStatusIsReturned();
    }

    @Test
    void whenPATCHIsCalledWithInvalidBeerIdToDecrementThenNotFoundStatusIsReturned() throws Exception {
        patchHelper.whenPATCHIsCalledWithInvalidBeerIdToDecrementThenNotFoundStatusIsReturned();
    }
}
