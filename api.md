### API
This section describes available endpoints, its usages and how to use them.
#### Account
| Endpoint                     | Method | Description                              |
|------------------------------|--------|------------------------------------------|
| `/api/v1/account/{id}`       | GET    | Get account with id                      |
| `/api/v1/account/{id}`       | PUT    | Update account with id                   |
| `/api/v1/account`            | POST   | Create new staff account                 |
| `/api/v1/account/email/{id}` | PUT    | Updates email and sends validation email |

#### Password recovery
| Endpoint                   | Method   | Description                                      |
|----------------------------|----------|--------------------------------------------------|
| `/api/v1/recovery`         | POST     | Create password recovery token and send by email |
| `/api/v1/recovery/{token}` | POST     | Set new password with token                      |

#### Locations
| Endpoint                 | Method | Description                     |
|--------------------------|--------|---------------------------------|
| `/api/v1/location`       | GET    | Get list of locations/farmlands |
| `/api/v1/location`       | POST   | Create new location             |
| `/api/v1/location/{id}`  | PUT    | Get location by id              |

#### Jobs
| Endpoint           | Method | Description                         |
|--------------------|--------|-------------------------------------|
| `/api/v1/job`      | GET    | Get paged list of jobs with filters |
| `/api/v1/job`      | POST   | Create new job                      |
| `/api/v1/job/{id}` | GET    | Get job by id                       |
| `/api/v1/job/{id}` | PUT    | Update existing job                 |

#### Job types
| Endpoint                 | Method   | Description                     |
|--------------------------|----------|---------------------------------|
| `/api/v1/job-type`       | GET      | Get list of job types           |
| `/api/v1/job-type`       | POST     | Create new job type             |
| `/api/v1/job-type/{id}`  | PUT      | Get                             |
| `/api/v1/job-type/units` | GET      | Get list of accepted work units |

#### Employees
| Endpoint                          | Method   | Description                 |
|-----------------------------------|----------|-----------------------------|
| `/api/v1/employee`                | GET      | Get list of employees       |
| `/api/v1/employee`                | POST     | Create new employee account |
| `/api/v1/employee/search/{query}` | GET      | Find employee               |
| `/api/v1/employee/code/{code}`    | GET      | Get employee by code        |
| `/api/v1/employee/id/{id}`        | GET      | Get employee by id          |
| `/api/v1/employee/id/{id}`        | PUT      | Update employee             |

#### Settlements
| Endpoint             | Method   | Description |
|----------------------|----------|-------------|
| `/api/v1/settlement` | GET      |             |

#### Reports
| Endpoint                                      | Method   | Description                         |
|-----------------------------------------------|----------|-------------------------------------|
| `/api/v1/report/locations/report`             | GET      | Create XLS report for all locations |
| `/api/v1/report/employee/{employeeId}`        | GET      | Get employee report                 |
| `/api/v1/report/employee/{employeeId}/report` | GET      | Create XLS report for employee      |

#### Payments
| Endpoint                        | Method   | Description                      |
|---------------------------------|----------|----------------------------------|
| `/api/v1/payment/employee/{id}` | GET      | Get salary list for the employee |
| `/api/v1/payment/{id}`          | GET      | Get payroll list for employees   |
| `/api/v1/payment/{id}`          | PUT      | Update payment                   |
| `/api/v1/payment`               | POST     | Create new payment               |

#### Insurances
| Endpoint                          | Method | Description                       |
|-----------------------------------|--------|-----------------------------------|
| `/api/v1/insurance/employee/{id}` | GET    | Get the employee insurance info   |
| `/api/v1/insurance`               | POST   | Add new insurance policy          |
| `/api/v1/insurance/{id}`          | DELETE | Delete the insurance policy by id |