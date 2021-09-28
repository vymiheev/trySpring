### Setup Postgres:
```
create role appsmart with login password 'appsmart';
create database dbsmart;
grant all privileges on database dbsmart to appsmart;
grant all privileges on all tables in schema public to appsmart;

create role testsmart with login password 'testsmart';
create database dbsmart_test;
grant all privileges on database dbsmart_test to testsmart;
grant all privileges on all tables in schema public to testsmart;
```

### Run application:
```
gradle bootRun
```

### Run test:
```
gradle test
```

