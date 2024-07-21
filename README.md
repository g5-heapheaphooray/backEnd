<a id="readme-top"></a>

<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About The Project

We hope to create a platform where people can find volunteering and donation opportunities and get small rewards for their help. Users would be rewarded with points for their volunteering efforts and donations, where they can use these rewards to redeem some forms of incentives. Though these incentives would not be comparable to the efforts the users gave, it would still be a reward to the users. Through these, we hope to encourage more people to help those in need through donations and volunteering efforts.


<p align="right">(<a href="#readme-top">back to top</a>)</p>



### Built With

* [![Spring boot][spring boot-logo]][spring boot-url]
* [![Maven Apache][maven-logo]][maven-url]
* [![MySQL][mysql-logo]][mysql-url]
* [![Huawei Cloud][Huawei-logo]][huawei-url]

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- GETTING STARTED -->
## Getting Started



### Prerequisites

* Java 17
* Maven 3.6+
* MySQL 8.0+

### Installation

1. **Clone the repo**
    ```sh
    git clone https://github.com/g5-heapheaphooray/backEnd
    ```
2. **Install the necessary maven packages**
   <br>
* Navigate to required directory
    ```sh
    cd backEnd/demo
    ```
* Install packages
  <br>
    ```sh
    mvn clean install
    ```

3. **Set up MySQL database**
    ```sql
    create database {DB_NAME};
    use {DB_NAME};
    ```
4. **Edit application properties**
   <br>
   Navigate to src/main/resources/application.properties in your project directory. Edit the following:
    ```
    spring.datasource.url=${DB_URL}
    spring.datasource.username=${DB_USERNAME}
    spring.datasource.password=${DB_PW}
    ```

5. **Run spring boot**
    ```sh
    mvn spring-boot:run
    ```

<p align="right">(<a href="#readme-top">back to top</a>)</p>


<!-- MARKDOWN LINKS & IMAGES -->
[spring boot-logo]: https://img.shields.io/badge/Spring_Boot-F2F4F9?style=for-the-badge&logo=spring-boot
[spring boot-url]:https://spring.io/projects/spring-boot
[maven-logo]: https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white
[maven-url]: https://maven.apache.org/
[mysql-logo]: https://img.shields.io/badge/mysql-4479A1.svg?style=for-the-badge&logo=mysql&logoColor=white
[mysql-url]: https://www.mysql.com/
[Huawei-logo]: https://img.shields.io/badge/Huawei-%23FF0000.svg?style=for-the-badge&logo=huawei&logoColor=white
[Huawei-url]: https://www.huaweicloud.com/intl/en-us/