# How can we implement has-a relation in java?
 https://www.geeksforgeeks.org/association-composition-aggregation-java/

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

                                  **Composition**
Composition is a restricted form of Aggregation in which two entities are highly dependent on each other.  
- It represents part-of relationship.
- In composition, both entities are dependent on each other.
- When there is a composition between two entities, the composed object cannot exist without the other entity.

**1. Dependency:** Aggregation implies a relationship where the child can exist independently of the parent. For example, Bank and Employee, delete the Bank and the Employee still exist. whereas Composition implies a relationship where the child cannot exist independent of the parent. Example: Human and heart, heart don’t exist separate to a Human

**2. Type of Relationship:** Aggregation relation is “has-a” and composition is “part-of” relation.

**3. Type of association:** Composition is a strong Association whereas Aggregation is a weak Association.

