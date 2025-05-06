## Vending Machine – Low-Level Design (LLD)

### 📌 Overview

* A low-level design (LLD) implementation of a Vending Machine system demonstrating core object-oriented principles like encapsulation, abstraction, inheritance, and polymorphism.

### TODO: 

- [x] Gather Requirements
- [x] Define Models
- [x] Define Repositories
- [ ] Define UML Class Diagram
- [ ] Write code
- [ ] Write Unit Tests

### 🧠 Requirements

1. Users can buy products from the vending machine.
2. The vending machine maintains an inventory of available products.
3. Users can purchase multiple products in a single transaction.
4. A user can select products and add them to a virtual cart.
5. After completing product selection, the user can proceed to payment.
6. Upon successful payment, the vending machine dispenses the selected products.
7. Each product includes details such as name, type and price.
8. The cart contains a list of selected products along with the total payable amount.

### ⚙️ How to Run

```shell
git clone  https://github.com/mandalavijaysurya/lld-vending-machine
cd lld-vending-machine/lynx-vending-machine

# Building the project
maven clean install

# Executing the project
maven exec: java -Dexec.mainClass="<update the main class"
```

### 🧼 Code Structure

Brief explanation of the directory structure and class breakdown.
