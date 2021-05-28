module.exports = async function (context, req) {
    context.log('Getting the tasks of the task planner');
	
	//Se genera la id para simular la persistencia de los objetos.
	const { v4: uuidv4 } = require('uuid');
	//Se genera la información mockeada.
	const tasks=[{
		id: uuidv4(),
		description: "Do The Front Component",
		responsible: {
			name: "Daniel Walteros",
			email: "daniel@gmail.com"
		},
		status: "ready",
		dueDate: 156464645646
		},{
		id: uuidv4(),
		description: "Work On The Project",
		responsible: {
			name: "Daniel Walteros",
			email: "daniel@gmail.com"
		},
		status: "done",
		dueDate: 156464888646
	}];

    context.res = {
        status: 200,
        body: tasks
    };
}