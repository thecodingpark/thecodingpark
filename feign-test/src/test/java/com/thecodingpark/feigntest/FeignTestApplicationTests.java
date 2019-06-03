package com.thecodingpark.feigntest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ServerList;
import com.thecodingpark.feigntest.api.UserFeignClient;
import com.thecodingpark.feigntest.domain.User;
import com.thecodingpark.feigntest.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.cloud.netflix.ribbon.RibbonAutoConfiguration;
import org.springframework.cloud.netflix.ribbon.StaticServerList;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.cloud.openfeign.ribbon.FeignRibbonClientAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
public class FeignTestApplicationTests {

	private static ObjectMapper mapper = new ObjectMapper();

	@Configuration
	@EnableFeignClients(clients = {UserFeignClient.class})
	@ImportAutoConfiguration({
			HttpMessageConvertersAutoConfiguration.class,
			RibbonAutoConfiguration.class,
			FeignRibbonClientAutoConfiguration.class,
			FeignAutoConfiguration.class})
	@Import(UserService.class)
	static class ContextConfiguration {

		@Autowired
		Environment env;

		@Bean
		ServletWebServerFactory servletWebServerFactory(){
			return new TomcatServletWebServerFactory();
		}

		@Bean
		public ServerList<Server> ribbonServerList() {
			return new StaticServerList<>(new Server("localhost", Integer.valueOf(this.env.getProperty("wiremock.server.port"))));
		}
	}

	@Autowired
	private UserFeignClient userFeignClient;

	@Autowired
	private UserService userService;

	@Test
	public void getUsersTest() throws Exception {
		String expected = mapper.writeValueAsString(getUsers());

		stubFor(get(urlEqualTo("/users"))
				.willReturn(aResponse()
						.withHeader("Content-Type", "application/json")
						.withBody(expected)
				)
		);

		String actual = mapper.writeValueAsString(userService.getUsers());
		assertEquals(expected, actual);
	}

	private List<User> getUsers() {
		List<User> users = new ArrayList<>();
		User user1 = new User();
		user1.setAge(39);
		user1.setEmail("test1@mail.com");
		user1.setName("Mike");
		user1.setUsername("bamboo12");

		User user2 = new User();
		user2.setAge(23);
		user2.setEmail("roger@mail.com");
		user2.setName("Roger");
		user2.setUsername("aligator23");

		users.add(user1);
		users.add(user2);
		return users;
	}

}

