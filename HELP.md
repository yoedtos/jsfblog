
***

Known Issues:
- Mass registration attacks
- Security wickness

This web application require java 8 and was tested in Tomcat 7.0.108.  
To easily set java 8, use
[sdkman](https://sdkman.io/)

### Demo files to run test
In order to run the test put this files in your user's home on directory `data/`  
Ex: `/home/me/data`

| File name | Description |
| ----------- | ----------- |
| demo-data.sql  | sql script for test |
| jsfblog.properties | properties file for test |

#### Update project
`mvn dependency:resolve`

#### Test
`mvn test`

#### Integration Test
`mvn integration-test`

#### Run the project
`mvn jetty:run`

Open your browser in
http://localhost:8080/jsfblog/index.jsf

And login with default credentials:  
 >username: _admin-user_  
 password: _abcde_
