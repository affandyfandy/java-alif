# Assignment 1: Normalization

## Explain 1NF, 2NF, 3NF with example

Database `normalization` is a process used to organize a database into tables and columns to reduce data redundancy and improve data integrity. The main steps in normalization are defined by the normal forms or `NF`s. In this explanation, I'll using a `Purchase` table as an example for each normalizations.

### 1NF - First Normal Form

A relation is in first normal form if every attribute in that relation is `single-valued` attribute.

Rules:
1. All columns contain `atomic` or `indivisible` values.
2. Each column contains values of a single type.
3. Each column contains `unique` values, or a set of value repeated in another row must relate to different values in other columns with no `duplicated` rows.

\
**Example Table**

| PurchaseID | CustomerName | ProductID | PurchaseDate |
| --- | --- | --- | --- |
| 1 | John Doe | 101, 102 | 27-06-2024 |
| 2 | Alice | 103 | 27-06-2024 |
| 3 | Michael Brown | 104, 105, 106 | 27-06-2024 |

This table is not in 1NF because the `ProductID` column contains multiple values of product id.

\
**Normalized to 1NF**

| PurchaseID | CustomerName | ProductID | PurchaseDate |
| --- | --- | --- | --- |
| 1 | John Doe | 101 | 27-06-2024 |
| 1 | John Doe | 102 | 27-06-2024 |
| 2 | Alice | 103 | 27-06-2024 |
| 3 | Michael Brown | 104 | 27-06-2024 |
| 3 | Michael Brown | 105 | 27-06-2024 |
| 3 | Michael Brown | 106 | 27-06-2024 |

The table has beed normalized to 1NF by ensuring each column contains only atomic values. The `ProductID` column was split into individual entries to satisfy the 1NF rules.

### 2NF - Second Normal Form

A relation that is in 1NF and every `non-primary-key` attribute is fully functionally dependent on the `primary key`.

Rules:
1. It is in `1NF`.
2. All non-key attributes are fully functionally dependent on the `primary key` (no partial dependency).

\
**1NF Table**

| PurchaseID | CustomerName | ProductID | PurchaseDate |
| --- | --- | --- | --- |
| 1 | John Doe | 101 | 27-06-2024 |
| 1 | John Doe | 102 | 27-06-2024 |
| 2 | Alice | 103 | 27-06-2024 |
| 3 | Michael Brown | 104 | 27-06-2024 |
| 3 | Michael Brown | 105 | 27-06-2024 |
| 3 | Michael Brown | 106 | 27-06-2024 |

In this table, the `CustomerName` and `PurchaseDate` depend only on the `PurchaseID`, this condition is called partial dependencies.

\
**Normalized to 2NF**

`Purchases` table:

| PurchaseID | CustomerName | PurchaseDate |
| --- | --- | --- |
| 1 | John Doe | 2024-06-01 |
| 2 | Alice | 2024-06-02 |
| 3 | Michael Brown | 2024-06-03 |

\
`PurchaseDetails` table:

| PurchaseID | ProductID
| --- | --- |
| 1 | 101 |
| 1 | 102 |
| 2 | 103 |
| 3 | 104 |
| 3 | 105 |
| 3 | 106 |

The table has been split into two `Purchases` and `PurchaseDetails`. The `Purchases` table stores data related to the purchase of customer, while the `PurchaseDetails` table stores the relationship between purchases and products. In this normalization removes partial dependencies, ensuring that non-key attributes depend on the whole `primary key`.

### 3NF - Third Normal Form

In this relation, if there is no transitive dependency for non-prime attributes as as well as it is in the `2NF`.

Rules:

1. It is in `2NF`.
2. It has no transitive dependency, meaning non-prime attributes are not dependent on other non-prime attributes.
3. A relation is in `3NF` if at least one of the following conditions holds in every non-trivial function dependency X –> Y. With X is a super key, and Y is a prime attribute (each element of Y is part of some candidate key).

\
**2NF Table**

`Purchases` table:

| PurchaseID | CustomerName | PurchaseDate |
| --- | --- | --- |
| 1 | John Doe | 2024-06-01 |
| 2 | Alice | 2024-06-02 |
| 3 | Michael Brown | 2024-06-03 |

\
`PurchaseDetails` table:

| PurchaseID | ProductID
| --- | --- |
| 1 | 101 |
| 1 | 102 |
| 2 | 103 |
| 3 | 104 |
| 3 | 105 |
| 3 | 106 |

If `CustomerName` is associated with additional customer information, it creates a transitive dependency.

\
**Normalized to 3NF**

`Purchases` table:

| PurchaseID | CustomerID | PurchaseDate |
| --- | --- | --- |
| 1 | 1 | 2024-06-01 |
| 2 | 2 | 2024-06-02 |
| 3 | 3 | 2024-06-03 |

\
`PurchaseDetails` table:

| PurchaseID | ProductID
| --- | --- |
| 1 | 101 |
| 1 | 102 |
| 2 | 103 |
| 3 | 104 |
| 3 | 105 |
| 3 | 106 |

\
`Customers` table:

| CustomerID | CustomerName |
| --- | --- |
| 1 | John Doe |
| 2 | Jane Smith |
| 3 | Mike Brown |

The `Purchases` table now references a `CustomerID` instead of storing customer directly, then the customer detail are stored in a `Customers` table. This condition removes a transitive dependencies, ensuring that non-key attributes are not dependant on other non-key attributes.