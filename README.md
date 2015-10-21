# Java GraphQL Implementation

## Purpose
To provide a lightweight, modular Java implementation of the GraphQL specification (http://facebook.github.io/graphql/). This project is intended to allow clients to choose their level of integration with GraphQL. It can be as lightweight as providing a means to express which fields to fetch on existing document models or a full stack implementation.

## Current Features
* GraphQL Query Deserialization
  * Document definitions
  * Aliases
  * Typed arguments
* GraphQL Query Serialization
  * Full serialization support for deserialization features
  * Added "pretty print" flag to nicely format output
* Spring HTTP Message Converter
* FieldTrimmer for pruning unnecessary fields

## Documentation
More robust documentation is coming. However, in the interim, please refer below.

### Data Types
[com.dibs.graphql.data.request.Query] (https://github.com/1stdibs/graphql/blob/master/src/main/java/com/dibs/graphql/data/request/Query.java)- A POJO representing a GraphQL query. Not incredibly easy to work with, but it's a pure representation of the query. This is what the serialization and deserialization layers work with.

[com.dibs.graphql.data.QueryTree] (https://github.com/1stdibs/graphql/blob/master/src/main/java/com/dibs/graphql/data/request/QueryTree.java) - This is the data type the services are most likely to interact with. It's composed of a Query and exposes methods that mirror the way services interact with the Query


### Serialization
[com.dibs.graphql.serialize.impl.QuerySerializerImpl] (https://github.com/1stdibs/graphql/blob/master/src/main/java/com/dibs/graphql/serialize/impl/QuerySerializerImpl.java) - Responsible for serializing a Query into the GraphQL syntax. The only serialization option at this point is a "prettyPrint" that will put spaces, tabs, and new lines in the output.

[com.dibs.graphql.serialize.SerializationTest] (https://github.com/1stdibs/graphql/blob/master/src/test/java/com/dibs/graphql/serialize/SerializationTest.java) - Test case demonstrating serialization


### Deserialization
[com.dibs.graphql.deserialize.parser.impl.QueryTokenParser] (https://github.com/1stdibs/graphql/blob/master/src/main/java/com/dibs/graphql/deserialize/parser/impl/QueryTokenParser.java) - This parses the query character by character, building up tokens until it finds delimiters. This is the preferred parser.

[com.dibs.graphql.deserialize.impl.QueryDeserializerImpl] (https://github.com/1stdibs/graphql/blob/master/src/main/java/com/dibs/graphql/deserialize/impl/QueryDeserializerImpl.java) - Given one of the parsers above, this will construct the Query from the individual tokens

[com.dibs.graphql.deserialize.DeserializationTest] (https://github.com/1stdibs/graphql/blob/master/src/test/java/com/dibs/graphql/deserialize/DeserializationTest.java) - Test case showing deserialization

### Pruning
[com.dibs.graphql.prune.FieldTrimmer] (https://github.com/1stdibs/graphql/blob/master/src/main/java/com/dibs/graphql/prune/FieldTrimmer.java) - This is Vadim's trimmer from shared-lib. It's responsible for nulling out fields on beans given a specific field policy.

### Integration
I created a dummy subsystem "BreakRoom" to demonstrate how this integrates with a Java system. A BreakRoom has VendingMachines which have VendingMachineProducts. You'll see examples of the code making decisions on whether a BreakRoom should fetch VendingMachines depending on whether they're specified in the request. Also, this will perform pruning at the facade level but this could easily be moved the the DAO level.

## Roadmap
1. QueryResponse object that allows the facade to respond with a GraphQL message. This will be validated against the request Query.
2. Better error messages and input validation for the deserialization layer.
3. More robust support for GraphQL specification including fragments and variables.

## Contributing and Bug Reports
Pull requests are welcome. For inquiries on contributing and bug reports, please email matthew.mason@1stdibs.com.


