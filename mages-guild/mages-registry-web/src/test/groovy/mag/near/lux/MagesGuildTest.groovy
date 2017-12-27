package mag.near.lux

import ch.qos.logback.classic.LoggerContext
import mag.near.lux.model.Rank
import mag.near.lux.model.Sex
import mag.near.lux.util.EnumMatcher
import mag.near.lux.util.RegexpMatcher
import org.slf4j.ILoggerFactory
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.ResultMatcher
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import org.testng.annotations.BeforeClass
import org.testng.annotations.Test

import java.util.regex.Matcher
import java.util.regex.Pattern
import java.util.stream.Collectors

import static org.hamcrest.Matchers.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@ContextConfiguration(locations = 'classpath:spring/mvc-rest-dispatcher-servlet.xml')
@WebAppConfiguration
class MagesGuildTest extends AbstractTestNGSpringContextTests{

    MockMvc mockMVC
    private static final LOGGER = LoggerFactory.getLogger(MagesGuild.class)
    
    @Autowired
    private WebApplicationContext ctx
    
    @BeforeClass
    void setUp(){
        mockMVC = MockMvcBuilders.webAppContextSetup(ctx).build()
    }

    @Test
    public void getListOfMagesSuccess() throws Exception {
        LOGGER.debug("In getListOfMagesSuccess")
        Pattern expectedGuid = ~/[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}/
        Pattern expectedName = ~/^[А-Я]?[а-я](-|\s)?[А-Я]?[а-я].*$/
        def limit = 100


        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get('/mages/' + limit)

        ResultActions result = mockMVC.perform(request)
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=UTF-8"))
            .andExpect(jsonPath('$').isArray())
        for (def i = 0; i< limit; i++){
            result
                    .andExpect(jsonPath('$[' + i +'].guid').value(RegexpMatcher.of(expectedGuid)))
                    .andExpect(jsonPath('$[' + i + '].name').value(RegexpMatcher.of(expectedName)))
                    .andExpect(jsonPath('$[' + i + '].surname').value(RegexpMatcher.of(expectedName)))
                    .andExpect(jsonPath('$[' + i + '].sex').value(EnumMatcher.of(Sex.class)))
                    .andExpect(jsonPath('$[' + i + '].rank').value(EnumMatcher.of(Rank.class)))
        }



        LOGGER.debug("Result: ${result.andReturn().getResponse().getContentAsString()}")
    }
}
