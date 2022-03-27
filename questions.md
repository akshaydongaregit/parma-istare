# How can we implement has-a relation in java?
[https://www.geeksforgeeks.org/association-composition-aggregation-java/]

**Association** is a relation between two separate classes which **establishes through their Objects.** Association can be 
- one-to-one, 
- one-to-many, 
- many-to-one, 
- many-to-many. 

In Object-Oriented programming, an Object communicates to an other object to use functionality and services provided by that object.**Composition** and **Aggregation** are the two forms of association.

![image](https://user-images.githubusercontent.com/34915737/160273169-6a580669-c88b-4f8d-81e2-faa3adf0935c.png)

                                  **Aggregation**
It is a special form of Association where:  

- It represents Has-A’s relationship.
- It is a unidirectional association i.e. a one-way relationship. For example, a department can have students but vice versa is not possible and thus unidirectional in nature.
- In Aggregation, both the entries can survive individually which means ending one entity will not affect the other entity.


