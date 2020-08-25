package com.mm.homeworks;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class DevcampHomeworksApplicationTests {
	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;
	
	@Autowired
    private TestRestTemplate template;

	private String getRootUrl() {
		return "http://localhost:" + port;
	}

	@Test
	void contextLoads() {
	}

	@Test
	public void testGetAllUsers() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/users", HttpMethod.GET, entity,
				String.class);
		Assert.assertNotNull(response.getBody());
	}

	@Test
	public void givenAuthRequestOnPrivateService_shouldSucceedWith200() throws Exception {
		ResponseEntity<String> result = template.withBasicAuth("spring", "secret").getForEntity("/private/hello",
				String.class);
		Assert.assertEquals(HttpStatus.OK, result.getStatusCode());
	}
}
