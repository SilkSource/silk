Narrative:
In order to focus on the creative work of coding
As a developer
I want to have code automatically completed

Scenario: Create missing class under test
Given a test class com.foo.BarTest 
When I reference a non-existing class Bar from the test
Then the class com.foo.Bar exists

Scenario: Don't create class that isn't under test
Given a test class com.foo.BarTest 
When I reference a non-existing class Baz from the test
Then the class com.foo.Baz does not exist

Scenario: Auto-rename auto-generated class under test
Given a test class com.foo.BarTest 
When I reference a non-existing class Bar from the test
And I change the reference from Bar to Baz
Then the class com.foo.Bar does not exist
And the class com.foo.Baz exists
