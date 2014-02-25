//	This is the thing that builds collections of information. For example, this is where post data would be
//	aggregated

app.factory('employeeFactory', function(){
	var factory = {};
	
	var employees = [
	           		{
	           			id: 0,
	           			name: "John Smith",
	           			role: "Manager",
	           			project : [
	           				'Android',
	           				'Java Servlets',
	           				'Youtube' ]
	           		},
	           		{
	           			id: 1,
	           			name: "Mary Anderson",
	           			role: "Developer",
	           			project : [
	           				'Android',
	           				'Facebook'
	           				]
	           		},
	           		{
	           			id: 2,
	           			name: "Robert Ludlum",
	           			role: "Developer",
	           			project : [
	           				'Youtube',
	           				'Java Servlets'
	           				]
	           		},
	           		{
	           			id: 3,
	           			name: "Stephen King",
	           			role: "QA",
	           			project : [
	           				'Youtube',
	           				'Facebook'
	           				]
	           		}
	           	];
	factory.getEmployees = function(){
		return employees;
	}
	return factory;
});