## Development
1. Import as Maven Project

2. Add ``myhost`` envoirement property

3. Add ``setting`` file with ``$myhost`` value

4. Run on Tomcat 8+ 

5. Create a database by the name mentioned in your config.``$myhost``.properties file.
For example if your db name is `hspSportHibernate`, then use this script: 
CREATE DATABASE `hspSportHibernate` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

6. Build the application (mvn clean package -Dmaven.test.skip=true)

7. Start the tomcat, and try to open the link:
http://localhost:8080/hspSport

8. Administrator Login Details:
Username: admin@hsp.am
Password: 123456789