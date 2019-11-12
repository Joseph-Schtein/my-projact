# polynom project
This project implements basic functions of polynom and monom such as add, substracr, multiply, devide, derivative, area and etc.
The Monom class can receive any symple monom of the form "+-ax^b" while 'a' belongs to the rationale numbers and 'b' belongs to the Natural numbers.(https://en.wikipedia.org/wiki/Monomial)

The polynom class can receive any kind of addition and subtraction of monom type objects of the form "ax^b + cx^d +e" while 'a','c' and 'e' belongs to the rationale numbers, and 'b' and 'd' belong to the natural numbers.(https://en.wikipedia.org/wiki/Polynomial)


# Mononm has the main functions:
1. Constructor - receives two numbers: 'a' Double, 'b' Integer and builds a Monom with coefficient 'a' and power 'b'.
2. add - receives Monom and add it to another Monom only if the exponent are equal.
3. multiply - receives Monom and multiplies it with another Monom, by multiplying the coefficient and adding the exponent.
4. string - String constructor, recives string from user and converts it to Monom type.
