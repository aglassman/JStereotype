JStereotype is a library that gives you the ability to flatten class hierarchies.  This ability also gives you greater flexibility in mixing up object composition at runtime.

JStereotype works off of the Composition principal of object-oriented programming.  Rather then inheriting functionality through class hierarchies, classes are composed of many objects.

See  src/test/java/org/gmjm/stereotype.example.Example for an example of how to use this library.


JStereotype could be thought of a type of dependency injector, but it is not intended to be used as such.

The library uses reflection and byte code manipulation to create subclasses of your existing classes.  These enhanced classes are backed by a backing entity (IDynamicEntity).  This entity can be thought of as a map of properties.  When you stereotype an IDynamicEntity, the properties in the map are automatically set in the objects attributes.  The names of your object's field attributes are used to extract the objects out of the map.  Since this operation is only done upon stereotyping, this makes your objects fast, as all operations are done via direct propety access.  

The basic idea is that you could have 3 different services that all consume IDynamic objects.  The service can use the appropriate StereotypeFactory to determine if IDynamic has the necessary properties to act on.  If it does, the service stereotypes the IDynamic object to the usable Stereotype object and then continues operation.

It is easy to turn any class into a stereotyped class:
IEntityStereotyper<MyObject> stereotyper = EntityStereotyperFactory(MyObject.class);

The stereotyper creats a new subclass of MyObject at runtime called MyObject_Stereotype.  This new class has MyObject as its superclass, so you can use the object as normal.  The subclass implements the IDynamic interface, which gives any stereotyper access to it's backing entity.  Backing entities are implementations of the IDynamicEntity interface.  This is what gives stereotyped objects their unique ability to be morphed into any other stereotype.

You can even stereotype a non stereotyped class into a stereotyped class.  The stereotyper will inspect the current object's attributes and add them to a new backing entity.  This backing entity is then used to create a new instance of a Stereotype object.

