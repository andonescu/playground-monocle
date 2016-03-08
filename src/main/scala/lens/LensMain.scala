package lens

import monocle._
import monocle.macros.GenLens

/**
  * Created by Ionut on 08.03.2016.
  */
object LensMain {


  def main(args: Array[String]) {

    // preparing multiple lens
    val _name = GenLens[Street](_.name)
    val _street = Lens((_: Address).street)(s => a => a.copy(street = s))
    val _address: Lens[Company, Address] = Lens[Company, Address](_.address)(a => e => e.copy(address = a))

    val _employeeCompany: Lens[Employee, Company] = GenLens[Employee](_.company)
    val _employeeName = GenLens[Employee](_.name)


    // create the initial obj
    val employee: Employee = Employee("John", Company(Address(Street("London Boulevard"))))

    //1. update the street name to upper case
    val newEmployeeAddress1 = (_employeeCompany ^|-> _address ^|-> _street ^|-> _name)
      .modify(_.toUpperCase)(employee)

    // see the result $ Employee(Company(Address(Street(LONDON BOULEVARD))))
    println(newEmployeeAddress1)

    //2. change the name of the employee and update the street name to lower case
    val newEmployeeAddress2 =
      (_employeeCompany ^|-> _address ^|-> _street ^|-> _name)
        .modify(_.toLowerCase)(
          _employeeName.modify(_.toUpperCase)(employee)
        )

    // see the result $ Employee(JOHN,Company(Address(Street(london boulevard))))
    println(newEmployeeAddress2)
  }
}
