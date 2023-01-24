# Shopping Cart RestApi

. This is a REST API service for customers to add products to the their cart, submitting their order then receiving an invoice for the order. 

# Technologies Used
* Java                                                                                                                                                      
* Spring Boot
* REST API
* Hibernate
* Spring JPA
* Junit
* Mockito
* MySQL Database
* Maven Build Tool
* Intellij Idea
* Postman


> Posting Order Request with { product Ids, quantity, customer email and name }
![Screenshot (2)](https://user-images.githubusercontent.com/77440941/211189277-f4a3ee11-cb43-4f94-b7ab-46676478bf18.png)

> The total amount will be calculated and returned with invoice number as a response to the customer.
![Screenshot (4)](https://user-images.githubusercontent.com/77440941/211189363-cb88b0fe-af74-4209-994b-3bdb50bb7b93.png)


> It also handles if the product id is null.
 
![11](https://user-images.githubusercontent.com/77440941/212068401-02cc7d55-af69-499d-8601-9bda50664ac2.png)


> Also if the cart has some product which isn't existed in the warehouse.

![22](https://user-images.githubusercontent.com/77440941/212068854-a0e771e5-be4a-4a91-8cdf-e5cd74205a99.png)
