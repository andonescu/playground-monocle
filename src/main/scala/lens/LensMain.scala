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
    val _company: Lens[Employee, Company] = GenLens[Employee](_.company)

    // create the initial obj
    val employee: Employee = Employee(Company(Address(Street("London Boulevard"))))

    // update the street name from this obj
    val newEmployeeAddress = (_company ^|-> _address ^|-> _street ^|-> _name).modify(_.toUpperCase)(employee)

    // se the result $ Employee(Company(Address(Street(LONDON BOULEVARD))))
    println(newEmployeeAddress)
  }
}
