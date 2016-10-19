=Overview Of Project=

This project is a LittleProlog Interpreter designed for Android devices. This project 
is targeted at high school students, allowing them to learn LittleProlog interactively 
through a drag and drop interface.

=Who Is Responsible=

The developers responsible for this project are:
- Hong Chin Choong (cchon3@student.monash.edu)
- Chan Kai Ying (kychan68@student.monash.edu)

=Implemented in this release=

v0.1: 
- Ability to create new blank LittleProlog programs
- Ability to create predicates with one or many constants
- Ability to perform query search on predicates created with output on console
- Ability to perform variable search on predicates created with output on console
- Ability to modify the arguments of predicates created including number of arguments and value of arguments
- Ability to delete predicates created
- Ability to run the LittleProlog program
- Error checking on invalid predicate arguments

v0.2:
-Ability to set metainformation for a program
-Ability to create mathematical rule for mathematical computation.
-Ability to modify mathematical rule created including adding more Read object, Write object and Operator Object
-Ability to delete mathematical rule or arguments inside mathematical rule
-Ability to perform interpretation of mathematical rules created with output on console
-Ability to save and load files.


=Getting started=

	~Creating a New Blank Program~

	- User can begin creating a LittleProlog program by clicking on the create new program button on
  	  the start screen

	~Creating Predicates~

	- In the program, the user could create new predicates by dragging a predicate element from the 
  	  left sidebar into the coding playground in the middle of the application

	~Modifying Predicates~

	- Users could modify the predicate name and its arguments by clicking on the input boxes on the predicate
	- Additional arguments can be added or removed by clicking on the + or x button respectively on the predicate
	- Values of these arguments can be changed by selecting the desired argument input box and typing its new value

	~Deleting a Predicate~

	- To delete a predicate, long click on the predicate and drag it into the bin on the top right corner of the application

	~Query Search on Predicates~

	- To perform a query search on the predicates, run the interpreter by clicking the run button on the top left corner of the
	  screen. Then drag a query element into the console command line from the left side bar of the application and enter the desired query
	  into the query element and click on the send button on the console command line.

	~Variable Search on Predicates~

	- To perform a variable search on the predicates, run the interpreter by clicking the run button on the top left corner of the
	  screen. Then drag a query element into the console command line from the left side bar of the application and enter the desired query
	  into the query element (take note that the variable has to begin with an uppercase letter) and click on the send button on the console command line.
	  Values which could satisfy the variable will be printed on the console. The user could go through the list of values which could satisfy the variable
	  by clicking on the ";" button on the console command line or the user could stop going through the list by clicking on the send button again. 
          When no value could satisfy the variable anymore, the console will output false.

	~ Adding Metainformation

	- Users can add additional information for a program.

	~ Creating Mathematical Rules

	- In the program, the user could create new mathematical rules by dragging a Mathematical Computation element from the 
  	  left sidebar into the coding playground in the middle of the application
	- Read element, Write element and Operator element can be added into Mathematical Computation element by dragging them from left sidebar into Mathematical Computation
	  element.

	~ Modifying Mathematical Rules
	
	- Users could modify the mathematical rule's name and its arguments by clicking on the input boxes on the mathematical rules.
	- Additional arguments can be added dragging Read, Write and Operator object into mathematical rule.
	- For Operator Object, users can click on plus button to expand it.
	- Values of these arguments can be changed by selecting the desired argument input box and typing its new value
	- Arguments can be removed by long click on individual argument and drag them and drop them on bin on the top right corner of the application.

	~ Deleting Mathematical Rules
	
	- To delete a mathematical rules, long click on the mathematical rules and drag it into the bin on the top right corner of the application

	~ Interpretation of Mathematical Rule

	- To perform interpretation on mathematical rule, run the interpreter by clicking the run button on the top left corner of the screen. Then drag a query rule element
	  into the console command line from the left sidebar of the application and enter the desired mathematical rule's name into query rule element. Then, click on "interpret"
	  button on the console command line. Result will be output in console. Users may be prompt for input for Read functionality. When all parameters in mathematical rule
	  has been interpreted, console will output true if all parameters in mathematical rule are true, else false will be output in console.

	~ Save and Load Files~
	
	- To save a program, first enter the metainformation on the right side of the application. Click on the save button on the bottom of the metainformation section to save.
	- To load a program, load into the start screen and select the program to load on the left side of the screen.

=Known Bugs & limitations=

- Predicates and rules are not saved in order.
- No validation for mathematical computations.

=Library imported=

1. EvalEx library
	- source : https://github.com/uklimaschewski/EvalEx
	- usage  : Evaluate mathematical expression and boolean expression