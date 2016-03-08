case class Street(name: String)

case class Address(street: Street)

case class Company(address: Address)

case class Employee(company: Company)

import monocle.Lens
import monocle.macros.GenLens

// to use headOption (a generic optic)          // to get String instance for HeadOption

val _name = GenLens[Street](_.name)
val _street = Lens((_: Address).street)(s => a => a.copy(street = s))
val _address: Lens[Company, Address] = Lens[Company, Address](_.address)(a => e => e.copy(address = a))
val _company: Lens[Employee, Company] = GenLens[Employee](_.company)


val employee: Employee = Employee(Company(Address(Street("London Boulevard"))))


(_company ^|-> _address ^|-> _street ^|-> _name).modify(_.toUpperCase)(employee)