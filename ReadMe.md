# COMPTA-BACKEND

Compta app's REST API based backend

## Changelog

### 1.0.0 (WIP)
- User CRUD
- Exception handling
- I18n handling

## Technical environment

### Database requirements

Compta App needs a PostgreSQL 11.18 database.<br>
An owner dedicated role (different from applicative role) should handle :

- compta's database creation
- compta's applicative role creation

This role needs to be named 'compta_owner' and created with following privileges :

- LOGIN
- NOSUPERUSER
- CREATEDB
- CREATEROLE
- INHERIT
- NOREPLICATION

The applicative role should be created with adequate privileges and by compta_owner role.
The applicative role must be named 'compta_rw'.

- LOGIN
- NOSUPERUSER
- NOCREATEDB
- NOCREATEROLE
- INHERIT
- NOREPLICATION

Then the Compta's database should be created as following : <br>
CREATE DATABASE _bdd_name_
WITH
OWNER = compta_owner
ENCODING = 'UTF8'
CONNECTION LIMIT = -1;

Applicative scripts can be found in sources at /src/main/resources/database/ and should be run by compta_owner role.

## Application server
Compta run on JBoss/Wildfly 25.0.1.Final.

In standalone.xml, set a datasource named <i>java:jboss/datasources/ds</i> :

```
<subsystem xmlns="urn:jboss:domain:datasources:6.0">
    <datasources>
        <datasource jndi-name="java:/datasources/ds" pool-name="java:/datasources/ds">
            <connection-url>jdbc:postgresql://localhost:5432/compta_prd</connection-url>
            <driver>postgresql.jar</driver>
            <security>
                <user-name>compta_rw</user-name>
                <password>***********</password>
            </security>
        </datasource>
    </datasources>
</subsystem>
```
And it's driver
```
    ...
	<deployments>
		<deployment name="postgresql.jar" runtime-name="postgresql.jar"/>
	</deployments>
</server>
```

App 
## Build
> **Warning**: Build requires a running local docker engine to generate Jooq classes.<br>

Run ```mvn clean install``` in project's root directory 


