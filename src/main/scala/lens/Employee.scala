package lens

case class Street(name: String)

case class Address(street: Street)

case class Company(address: Address)

case class Employee(name: String, company: Company)
