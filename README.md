## jsfblog
A simple web blog made with JSF with run on servlet containers, created in 2011. It's using JSF 2.0, JPA 2.0 and java 8.

#### Sample files for database configuration
> [!NOTE]
>not required for testing.

| File name | Description |
| ----------- | ----------- |
| configuration.properties | for H2 DB |
| configuration.properties.mysql | for MySQL DB[^1] |


[^1]: database should be created before.

Change the following fields:

> url = jdbc:_`mysql://localhost:3306/blogdb?useSSL=false`_  
user =_`bloguser`_  
password = _`blogpass`_

and put on user's home in data directory `data/`

[Here more help](HELP.md)
***
#### Screenshot

![alt text](img/jsfblog.gif?raw=true)
