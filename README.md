JStereotype is a library that gives you the ability to flatten class hierarchies.  This ability also gives you greater flexibility in mixing up object composition at runtime.

JStereotype works off of the Composition principal of object-oriented programming.  Rather then inheriting functionality through class hierarchies, classes are composed of many objects.

See  src/test/java/org/gmjm/stereotype.example.Example for an example of how to use this library.


JStereotype could be thought of a type of dependency injector, but it is not intended to be used as such.

The basic idea is that you could have 3 different services that all consume IDynamic objects.  The service can use the appropriate StereotypeFactory to determine if IDynamic 