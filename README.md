# Test-Task-for-backend-developer-from-the-company-Natlex
# ООО «Натлекс»
[![N|Solid](https://natlex.ru/img/logo-natlex-e.8501c838.svg)](https://nodesource.com/products/nsolid)
## Test Task for backend developer 
1) Add REST CRUD API for Sections and GeologicalClasses. Each Section has structure: 
{  
    “name”: “Section 1”,
    “geologicalClasses”: [
                    { “name”: “Geo Class 11”, ”code”: “GC11” }, 
                    { “name”: “Geo Class 12”, ”code”: “GC12” }, ...
]} 

2) Add API GET /sections/by-code?code=... that returns a list of all Sections that have geologicalClasses with the specified code.
3) Add APIs for importing and exporting XLS files. Each XLS file contains headers and list of sections with it’s geological classes. Example: 

| Section name | Class 1 name | Class 1 code | Class 2 name | Class 2 code | Class M name | Class M code |
| ------ | ------ | ------ | ------ |------ | ------ |------ |
| Section 1 | Geo Class 11 | GC11 |Geo Class 12 | GC12 |Geo Class 1M | GC1M |
| Section 2 | Geo Class 21 | GC21 |Geo Class 22 | GC22 |  |  |
| Section 3 | Geo Class 31 | GC31 ||  |Geo Class 3M |GC3M |
| Section 4 | Geo Class N1 | GCN1 |Geo Class N2 | GCN2 |Geo Class NM | GCNM |
###
Files should be processed asynchronously, results should be stored id DB.<br>
    • API POST /import (file) returns ID of the Async Job and launches importing.<br>
    • API GET /import/{id} returns result of importing by Job ID ("DONE", "IN PROGRESS", "ERROR") <br>
    • API GET /export returns ID of the Async Job and launches exporting.<br>
    • API GET /export/{id} returns result of parsed file by Job ID ("DONE", "IN PROGRESS", "ERROR") <br>
    • API GET /export/{id}/file returns a file by Job ID (throw an exception if exporting is in process)<br>
 
    Requirements:
    • Technology stack: Spring, Hibernate, Spring Data, Spring Boot, Gradle/Maven.
    • All data (except files) should be in JSON format. 
    • In export and import use Apache POI for parsing.
    • (Optional) Basic Authorization should be supported.
>I have used the following librariess: Spring Data, Spring Boot, Spring Security,  Flyway,Hibernate,Lombok, ApachePOI;
Building a project: Maven
Database: PostgreSQL 
For testing: Postman
##### To run application, start flyway:migrate; 


POST:  {"name": "Section 1", "geologicalClasses": [ 
{ "name": "Geo Class 11",   "code": "GC11"  },
      {  "name": "Geo Class 12",   "code": "GC12"   }
    ] }
| HTTP-Methods for sections | URL |
| ------ | ------ |
| POST |http://localhost:8080/natlex/api/sections | 
| GET | http://localhost:8080/natlex/api/sections |
| GET | http://localhost:8080/natlex/api/sections/1 |
| PUT | http://localhost:8080/natlex/api/sections/1 |
| DELETE | http://localhost:8080/natlex/api/sections/1 |
| GET | http://localhost:8080/natlex/api/sections/by-code?code=GC12 |

#
#### Import and export paths:
>Import = C:\\\\Natlex\\\\Download\\\\import\\\\<br>
Export = C:\\\\Natlex\\\\Download\\\\export\\\\

| HTTP-Methods for jobs | export/import | URL |
| ------ | ------ |------ |
| POST | import | http://localhost:8080/natlex/api/jobs/import/|
| GET | list-jobs |http://localhost:8080/natlex/api/jobs|
| GET | import | http://localhost:8080/natlex/api/jobs/import/1|
| GET | export |http://localhost:8080/natlex/api/jobs/export|
| GET |export  |http://localhost:8080/natlex/api/jobs/export/1|
| GET | export |http://localhost:8080/natlex/api/jobs/export/2/file|






