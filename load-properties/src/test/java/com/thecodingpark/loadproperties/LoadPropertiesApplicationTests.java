package com.thecodingpark.loadproperties;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LoadPropertiesApplicationTests {

	@Autowired
	private MyProperties myProperties;

	@Test
	public void testServicesList() {
		List<ServiceProperties> list = myProperties.getServicesList();

		assertThat(list, notNullValue());
		assertThat(list.size(), equalTo(6));
		assertThat(list.get(2).isRequired(), is(false));
	}

	@Test
	public void testServicesMap() {
		Map<String, ServiceProperties> map = myProperties.getServicesMap();

		assertThat(map, notNullValue());
		assertThat(map.size(), equalTo(2));
		assertThat(map.get("key2").getServiceId(), equalTo("AB2"));
	}
}
