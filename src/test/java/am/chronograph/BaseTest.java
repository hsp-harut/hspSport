package am.chronograph;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.lang3.RandomStringUtils;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import am.chronograph.domain.user.Role;
import am.chronograph.domain.user.User;
import am.chronograph.service.user.RoleService;
import am.chronograph.service.user.UserService;

/**
 * Base test to extend to be able to use the spring managed beans.
 * 
 * @author tigranbabloyan
 *
 */
@ContextConfiguration(classes = { TestApplicationContext.class })
@TestExecutionListeners(listeners = { DependencyInjectionTestExecutionListener.class,
		DirtiesContextTestExecutionListener.class, TransactionalTestExecutionListener.class,
		WithSecurityContextTestExecutionListener.class })
@WithUserDetails("admin@hsp.am")
@Transactional
@Rollback
@TestPropertySource({ "classpath:settings/config.${myhost}.properties" })
public class BaseTest {

	@Autowired
	protected UserService userService;

	@Autowired 
	protected RoleService roleService;
	
	@Value("classpath:test-image/test.jpg")
	protected Resource resource;
	
	@Value("classpath:test-image/tasks.xlsx")
	protected Resource resourceXlsx;

	/**
	 * Returns the newly randomly created user.
	 * 
	 * @return the newly randomly created user.
	 */
	protected final User createRandomUser() {
		User user = new User();
		user.setEmail(RandomStringUtils.randomAlphabetic(10) + "@mail.ru");
		user.setFirstName("Test");
		user.setLastName("Testikyan");
		user.getRoles().add(createRandomRole());
		userService.saveUser(user);
		return user;
	}

	/**
	 * Returns the newly randomly created role.
	 * 
	 * @return the newly randomly created role.
	 */
	protected final Role createRandomRole() {
		Role role = new Role();
		role.setName(RandomStringUtils.randomAlphabetic(10));
		
		roleService.saveRole(role);
		return role;
	}	
	
	protected UploadedFile createUploadedFile(){
		UploadedFile file = new UploadedFile() {
			
			@Override
			public void write(String filePath) throws Exception {
				Path path = Paths.get(filePath);
				Files.copy(getInputstream(), path);
			}
			
			@Override
			public long getSize() {
				long size = 0;
				try {
					size = resource.contentLength();
				} catch (IOException ioex){
					
				}
				return size;
			}
			
			@Override
			public InputStream getInputstream() throws IOException {
				return resource.getInputStream();
			}
			
			@Override
			public String getFileName() {
				return resource.getFilename();
			}
			
			@Override
			public byte[] getContents() {
				return  null;
			}
			
			@Override
			public String getContentType() {
				return "image/jpg";
			}
		};
		return file;
	}
	
	protected UploadedFile createCorruptedUploadedFile(){
		UploadedFile file = new UploadedFile() {
			
			@Override
			public void write(String filePath) throws Exception {
				Path path = Paths.get(filePath);
				Files.copy(getInputstream(), path);
			}
			
			@Override
			public long getSize() {
				long size = 0;
				try {
					size = resourceXlsx.contentLength();
				} catch (IOException ioex){
					
				}
				return size;
			}
			
			@Override
			public InputStream getInputstream() throws IOException {
				return resourceXlsx.getInputStream();
			}
			
			@Override
			public String getFileName() {
				return resourceXlsx.getFilename();
			}
			
			@Override
			public byte[] getContents() {
				return  null;
			}
			
			@Override
			public String getContentType() {
				return "application/vnd.openxmlformats-officedocument.spreadsheetml.shee";
			}
		};
		return file;
	}

}
